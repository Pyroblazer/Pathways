from flask import Flask, jsonify, request, flash, redirect, url_for
from flask_cors import CORS, cross_origin

# import compute_engine_api
import random
import logging
import threading
import requests

import time

import googleapiclient.discovery

# Machine Learning
import tensorflow
from tensorflow.keras.preprocessing.text import one_hot
from tensorflow.keras.preprocessing.sequence import pad_sequences
from sklearn.preprocessing import LabelBinarizer
import pandas as pd
import numpy as np
import json

# Google Cloud Storage Access
from google.cloud import storage
from io import StringIO
import gcsfs
import h5py

#Werkzeug Utils
from werkzeug.utils import secure_filename
ALLOWED_EXTENSIONS = set(['mp4'])

logging.basicConfig(level=logging.DEBUG)

app = Flask(__name__)
cors = CORS(app)
app.config["CORS_HEADERS"] = "Content-type"


@app.route("/")
@cross_origin()
def hello_world():
    return "<p>Hello, World!</p>"


@app.route("/vm/initialize", methods=["POST"])
@cross_origin()
def initializeVM():
    content = request.get_json()
    job_opening_id = content["job_opening_id"]
    order_number = content["order_number"]
    r = requests.get(
        "http://35.223.231.127:8000/api/challenges/get_or_delete/{}/{}/".format(
            job_opening_id, order_number
        )
    )
    challenge = r.json()

    print(type(challenge))
    data_related = challenge["data_related"]
    print(type(data_related))
    data_related = json.loads(data_related)
    duration = data_related["duration"]
    link_to_boilerplate = data_related["link_to_boilerplate"]

    startup_script = """#!/bin/bash
sudo mkdir /root/$(hostnamectl --transient); 
gsutil cp -r gs://pathways-challenges/challenge-{}_{}/boilerplate/* /root/$(hostnamectl --transient)""".format(job_opening_id, order_number)

    shutdown_script = """#!/bin/bash
gsutil cp -r /root/$(hostnamectl --transient) gs://pathways-challenges/challenge-{}_{}/results/
    """

    compute = googleapiclient.discovery.build("compute", "v1")
    project = "pathways-314723"
    # region = "us-central1"
    # zone = "us-central1-a"
    region = "us-west1"
    zone = "us-west1-a"
    name = "technical-mini-project-" + str(random.getrandbits(32))
    # name = "technical-mini-project-" + "2"
    print("name: ", name)

    def reserveNatIP(project, region, name):
        address_body = {"name": name}
        print("created address body")
        try:
            addressInsert_request = compute.addresses().insert(
                project=project, region=region, body=address_body
            )
            print("addressInsert_request: ", addressInsert_request)
            addressInsert_response = addressInsert_request.execute()
            print(addressInsert_response)
            time.sleep(60)
            addressGet_request = compute.addresses().get(
                project=project, region=region, address=name
            )
            addressGet_response = addressGet_request.execute()
            print(addressGet_response)
            return addressGet_response
        except:
            return 500

    natIP = reserveNatIP(project, region, name)

    startup_script_url = "gs://pathways-create-vm/startup-script.sh"
    shutdown_script_url = "gs://pathways-create-vm/shutdown-script.sh"
    image_response = (
        compute.images()
        .getFromFamily(project="debian-cloud", family="debian-10")
        .execute()
    )
    source_disk_image = image_response["selfLink"]
    machine_type = "zones/%s/machineTypes/f1-micro" % zone
    config = {
        "name": name,
        "machineType": machine_type,
        # Specify the boot disk and the image to use as a source.
        "disks": [
            {
                "boot": True,
                "autoDelete": True,
                "initializeParams": {
                    "sourceImage": source_disk_image,
                },
            }
        ],
        # Specify a network interface with NAT to access the public
        # internet.
        "networkInterfaces": [
            {
                "network": "global/networks/default",
                "accessConfigs": [
                    {
                        "type": "ONE_TO_ONE_NAT",
                        "name": "External NAT",
                        "natIP": natIP["address"],
                    }
                ],
            }
        ],
        # Allow the instance to access cloud storage and logging.
        "serviceAccounts": [
            {
                "email": "default",
                "scopes": [
                    "https://www.googleapis.com/auth/devstorage.read_write",
                    "https://www.googleapis.com/auth/logging.write",
                ],
            }
        ],
        # Metadata is readable from the instance and allows you to
        # pass configuration from deployment scripts to instances.
        "metadata": {
            "items": [
                {
                    # Startup script is automatically executed by the
                    # instance upon startup.
                    "key": "startup-script-url",
                    "value": startup_script_url,
                },
                {
                    "key": "shutdown-script",
                    "value": shutdown_script,
                },
                {"key": "startup-script", "value": startup_script,},
            ]
        },
    }
    json_data = {}
    try:
        insertVM_request = compute.instances().insert(
            project=project, zone=zone, body=config
        )
        insertVM_response = insertVM_request.execute()
        time.sleep(80)
        print(insertVM_response)
        get_request = compute.instances().get(project=project, zone=zone, instance=name)
        instance = get_request.execute()
        print("instance = ", instance)
        while instance["status"] != "RUNNING":
            time.sleep(10)
            get_request = compute.instances().get(
                project=project, zone=zone, instance=name
            )
            instance = get_request.execute()
        print("instance = ", instance)
        instance_code_server_address = (
            instance["networkInterfaces"][0]["accessConfigs"][0]["natIP"] + ":8080"
        )
        json_data["codeServerAddress"] = instance_code_server_address
        json_data["vmInstance"] = instance
        return jsonify(json_data), 200

    except:
        delete_request = compute.instances().delete(
            project=project, zone=zone, instance=name
        )
        delete_response = delete_request.execute()
        print("error")
        return jsonify(json_data), 500


@app.route("/vm/delayed_shutdown", methods=["POST"])
@cross_origin()
def delayedShutdownVM():
    content = request.get_json()
    duration = content["duration"]

    compute = googleapiclient.discovery.build("compute", "v1")
    project = "pathways-314723"
    # region = "us-central1"
    # zone = "us-central1-a"
    region = "us-west1"
    zone = "us-west1-a"
    name = content["name"]
    # name = "technical-mini-project-4007909762"

    def waitThenShutdown(**kwargs):
        task_params = kwargs.get("post_data")
        print(task_params)
        duration = task_params["duration"]
        # while duration > 0:
        #     print(duration)
        #     time.sleep(1)
        #     duration -= 1

        time.sleep(duration)
        deleteVM_request = compute.instances().delete(
            project=task_params["project"],
            zone=task_params["zone"],
            instance=task_params["name"],
        )
        deleteVM_response = deleteVM_request.execute()
        deleteReservedAddress_request = compute.addresses().delete(
            project=task_params["project"],
            region=task_params["region"],
            address=task_params["name"],
        )
        deleteReservedAddress_response = deleteReservedAddress_request.execute()

        json_data = {
            "instance": deleteVM_response,
            "reservedAddress": deleteReservedAddress_response,
        }

        return jsonify(json_data), 200

    data = {}
    data["project"] = project
    data["zone"] = zone
    data["region"] = region
    data["name"] = name
    data["duration"] = duration
    kwargs = {}
    kwargs["post_data"] = data

    thread = threading.Thread(target=waitThenShutdown, kwargs=kwargs)
    thread.start()

    json_data = {"message": "Accepted"}

    return jsonify(json_data), 202


@app.route("/vm/shutdown", methods=["POST"])
@cross_origin()
def shutdownVM():
    content = request.get_json()
    print(content)

    compute = googleapiclient.discovery.build("compute", "v1")
    project = "pathways-314723"
    # region = "us-central1"
    # zone = "us-central1-a"
    region = "us-west1"
    zone = "us-west1-a"
    name = content["name"]

    deleteVM_request = compute.instances().delete(
        project=project, zone=zone, instance=name
    )
    deleteVM_response = deleteVM_request.execute()

    deleteReservedAddress_request = compute.addresses().delete(
        project=project, region=region, address=name
    )
    deleteReservedAddress_response = deleteReservedAddress_request.execute()

    json_data = {
        "vmInstance": deleteVM_response,
        "reservedAddress": deleteReservedAddress_response,
    }

    return jsonify(json_data), 200


@app.route("/ml/jobRecommender", methods=["POST"])
@cross_origin()
def jobRecommender():
    client = storage.Client()
    bucket = client.get_bucket("pathways-ml")
    blob = bucket.blob("jobRecommender/label.csv")
    blob = blob.download_as_string()
    blob = blob.decode("utf-8")
    label = StringIO(blob)
    content = request.get_json()
    input_test = content["text"]
    data = pd.read_csv(label)
    test_labels = data
    # model = tensorflow.kerasmodels.load_model("./jobRecommender/weights2.h5")
    FS = gcsfs.GCSFileSystem(project="pathways-314723")
    with FS.open("gs://pathways-ml/jobRecommender/weights2.h5", "rb") as model_file:
        model_gcs = h5py.File(model_file, "r")
        model = tensorflow.keras.models.load_model(model_gcs)
    # model = tensorflow.kerasmodels.load_model("gs://pathways-ml/jobRecommender/weights2.h5")

    def output(desc):
        vocab_size = 1000
        encoded_docs = [one_hot(desc, vocab_size)]
        padded_text = pad_sequences(encoded_docs, vocab_size, padding="post")
        pred = model.predict(padded_text)
        encoder = LabelBinarizer()
        encoder.fit(test_labels)
        result = encoder.inverse_transform(pred)
        return result[0]

    json_data = {}
    json_data = {"job": output(input_test)}

    return jsonify(json_data), 200


@app.route("/ml/courseRecommender", methods=["POST"])
@cross_origin()
def courseRecommender():
    client = storage.Client()
    bucket = client.get_bucket("pathways-ml")
    blob = bucket.blob("courseRecommender/coursera_courses_new.csv")
    blob = blob.download_as_string()
    blob = blob.decode("utf-8")
    coursera_courses = StringIO(blob)

    content = request.get_json()
    pathway = content["pathway"]
    df = pd.read_csv(coursera_courses)

    # Import TfIdfVectorizer from scikit-learn
    from sklearn.feature_extraction.text import TfidfVectorizer

    # Define a TF-IDF Vectorizer Object. Remove all english stop words such as 'the', 'a'
    tfidf = TfidfVectorizer(stop_words="english")

    # Replace NaN with an empty string
    df["description"] = df["description"].fillna("")

    # Construct the required TF-IDF matrix by fitting and transforming the data
    tfidf_matrix = tfidf.fit_transform(df["description"])

    # Output the shape of tfidf_matrix
    tfidf_matrix.shape

    # Array mapping from feature integer indices to feature name.
    tfidf.get_feature_names()[5000:5010]

    # Import linear_kernel
    from sklearn.metrics.pairwise import linear_kernel

    # Compute the cosine similarity matrix
    cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)

    # Construct a reverse map of indices and course titles
    indices = pd.Series(df.index, index=df["course_name"]).drop_duplicates()

    # Function that takes in corurse title as input and outputs most similar corurses
    def get_recommendations(title, indices=indices, cosine_sim=cosine_sim):
        # Get the index of the course that matches the title
        idx = indices[title]

        # Get the pairwsie similarity scores of all corurses with that corurse
        sim_scores = list(enumerate(cosine_sim[idx]))

        # Sort the corurses based on the similarity scores
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)

        # Get the scores of the 10 most similar corurses
        sim_scores = sim_scores[1:11]

        # Get the corurse indices
        course_indices = [i[0] for i in sim_scores]

        # Return the top 10 most similar corurses
        # return df[df.columns.values.tolist()].iloc[course_indices]
        return df.iloc[course_indices]

    courseRecommendations = get_recommendations(pathway).to_json(orient="records")

    courseRecommendations = json.loads(courseRecommendations)

    json_data = {}
    for i in range(len(courseRecommendations)):
        json_data[i] = courseRecommendations[i]

    return jsonify(json_data), 200

@app.route("/uploadVideo", methods=["POST"])
@cross_origin()
def uploadVideo():
    file = request.files['file']
    content = request.get_json()
    job_opening_id = content['job_opening_id']
    order_number = content['order_number']
    # job_opening_id = '2'
    # order_number = '2'


    def allowed_file(filename):
        return '.' in filename and \
            filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

    if 'file' not in request.files:
        flash('No file part')
        return redirect(request.url)
    file = request.files['input_video-synced-fHbYv.mp4']
    # If the user does not select a file, the browser submits an
    # empty file without a filename.
    if file.filename == '':
        flash('No selected file')
        return redirect(request.url)
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        filepath = 'gs://pathways-challenges/challenge-{}_{}/results/'.format(job_opening_id, order_number)
        client = storage.Client()
        bucket = client.get_bucket("pathways-challenges/challenge-{}_{}/results/".format(job_opening_id, order_number))
        blob = bucket.blob(filename)
        blob.upload_from_string(file.read())

    return {"job_opening_id": job_opening_id, "order_number": order_number}


@app.route("/voicebot", methods=["POST"])
@cross_origin()
def voicebotResponse():
    content = request.get_json()
    project_id = "pathways-314723"
    session_id = content["session_id"]
    texts = content["texts"]
    language_code = "en-US"
    json_data = {}

    def detect_intent_texts(project_id, session_id, texts, language_code):
        """Returns the result of detect intent with texts as inputs.

        Using the same `session_id` between requests allows continuation
        of the conversation."""
        from google.cloud import dialogflow

        session_client = dialogflow.SessionsClient()

        session = session_client.session_path(project_id, session_id)
        print("Session path: {}\n".format(session))

        for text in texts:
            text_input = dialogflow.TextInput(text=text, language_code=language_code)

            query_input = dialogflow.QueryInput(text=text_input)

            response = session_client.detect_intent(
                request={"session": session, "query_input": query_input}
            )

            print("=" * 20)
            print("Query text: {}".format(response.query_result.query_text))
            print(
                "Detected intent: {} (confidence: {})\n".format(
                    response.query_result.intent.display_name,
                    response.query_result.intent_detection_confidence,
                )
            )
            print(
                "Fulfillment text: {}\n".format(response.query_result.fulfillment_text)
            )

            print("Q result: ", response.query_result)
            # return response.query_result

    json_data["queryResult"] = detect_intent_texts(
        project_id=project_id,
        session_id=session_id,
        texts=texts,
        language_code=language_code,
    )

    return jsonify(json_data), 200


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
