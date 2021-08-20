package app.stefanny.pathway.request

import com.google.gson.annotations.SerializedName

class JobRecommenderRequest {

    @field:SerializedName("text")
    var text: String? = null

    @field:SerializedName("job")
    val job: String? = null
}