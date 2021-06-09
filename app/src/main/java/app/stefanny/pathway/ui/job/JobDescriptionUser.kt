package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import app.stefanny.pathway.R
import app.stefanny.pathway.adapter.UserProfileCompletedTaskAdapter
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityJobDescriptionUserBinding
import app.stefanny.pathway.response.CompletedTaskResponseItem
import app.stefanny.pathway.response.JobOpeningResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobDescriptionUser : AppCompatActivity() {

    private lateinit var binding: ActivityJobDescriptionUserBinding
    private lateinit var adapterCompletedTask: UserProfileCompletedTaskAdapter

    private val _job = MutableLiveData<JobOpeningResponse>()
    val jobDesc: LiveData<JobOpeningResponse> = _job

    private val listCompletedTask = ArrayList<CompletedTaskResponseItem>()

    companion object {
        private const val TAG = "JobDescriptionUser"
        private const val JOB_ID = "1"
        private const val USERNAME = "test_user_general"
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobDescriptionUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jobDesc.observe(this, { jobDesc ->
            binding.idJob.text = jobDesc.title
            binding.idCompanyName.text = jobDesc.company
            binding.idCompanyLokasi.text = jobDesc.location
            binding.idCompanySalary.text = jobDesc.salary
            binding.idDesc.text = jobDesc.jobDescription
        })

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }

        adapterCompletedTask = UserProfileCompletedTaskAdapter(listCompletedTask)

        findJobDesc()
        viewTaskConfig()
        getListTask()
    }

    fun findJobDesc() {
        ApiConfig.getApiService().getJobDesc(JOB_ID).enqueue(object :
            Callback<JobOpeningResponse> {
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