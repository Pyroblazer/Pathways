package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import app.stefanny.pathway.adapter.QualifiedPeopleAdapter
import app.stefanny.pathway.adapter.UserProfileCompletedTaskAdapter
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityJobDescriptionCompanyBinding
import app.stefanny.pathway.response.CompletedTaskResponseItem
import app.stefanny.pathway.response.JobOpeningResponse
import app.stefanny.pathway.response.UserGeneralResponse
import app.stefanny.pathway.user.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobDescriptionCompany : AppCompatActivity() {

    private lateinit var binding: ActivityJobDescriptionCompanyBinding
    private lateinit var adapterPeople: QualifiedPeopleAdapter

    private val _job = MutableLiveData<JobOpeningResponse>()
    val jobDesc: LiveData<JobOpeningResponse> = _job

    private lateinit var adapterCompletedTask: UserProfileCompletedTaskAdapter
    private val listCompletedTask = ArrayList<CompletedTaskResponseItem>()

    private val listQUalified = ArrayList<UserGeneralResponse>()

    companion object {
        private const val TAG = "JobDescriptionCompany"
        private const val JOB_ID = "1"
        private const val USERNAME = "test_user_general"
        const val EXTRA_JOB = "extra_job"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobDescriptionCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterPeople = QualifiedPeopleAdapter(listQUalified)
        adapterCompletedTask = UserProfileCompletedTaskAdapter(listCompletedTask)

        jobDesc.observe(this, { jobDesc ->
            binding.idJob.text = jobDesc.title
            binding.idCompanyName.text = jobDesc.company
            binding.idCompanyLokasi.text = jobDesc.location
            binding.idCompanySalary.text = jobDesc.salary
            binding.idDesc.text = jobDesc.jobDescription
        })

        viewConfigPeople()
        findJobDesc()
        getQualifiedPeople()
        viewTaskConfig()
        getListTask()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditJobDescriptionCompany::class.java)
            startActivity(intent)
        }

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }
    }

    private fun viewConfigPeople() {
        binding.rvQualified.layoutManager = LinearLayoutManager(this)
        binding.rvQualified.setHasFixedSize(true)
        binding.rvQualified.adapter = adapterPeople
    }

    fun findJobDesc() {
        ApiConfig.getApiService().getJobDesc(JOB_ID).enqueue(object : Callback<JobOpeningResponse> {
            override fun onResponse(
                call: Call<JobOpeningResponse>,
                response: Response<JobOpeningResponse>
            ) {
                if (response.isSuccessful) {
                    _job.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<JobOpeningResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getQualifiedPeople() {
        ApiConfig.getApiService().getQualifiedPeople().enqueue(object : Callback<ArrayList<UserGeneralResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserGeneralResponse>>,
                response: Response<ArrayList<UserGeneralResponse>>
            ) {
                response.body().let {
                    if (it != null) {
                        listQUalified.addAll(it)
                    }
                    binding.rvQualified.adapter = adapterPeople
                }
            }

            override fun onFailure(call: Call<ArrayList<UserGeneralResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun viewTaskConfig() {
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.setHasFixedSize(true)
        binding.rvTasks.adapter = adapterCompletedTask
    }

    fun getListTask() {
        ApiConfig.getApiService().getCompletedTask(USERNAME).enqueue(object :
            Callback<ArrayList<CompletedTaskResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<CompletedTaskResponseItem>>,
                response: Response<ArrayList<CompletedTaskResponseItem>>
            ) {
                response.body().let {
                    if (it != null) {
                        listCompletedTask.addAll(it)
                    }
                    binding.rvTasks.adapter = adapterCompletedTask
                }
            }

            override fun onFailure(call: Call<ArrayList<CompletedTaskResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}