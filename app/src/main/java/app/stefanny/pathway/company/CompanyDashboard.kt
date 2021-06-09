package app.stefanny.pathway.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import app.stefanny.pathway.adapter.JobProfileAdapter
import app.stefanny.pathway.adapter.RecommendedPeopleAdapter
import app.stefanny.pathway.databinding.ActivityCompanyDashboardBinding
import app.stefanny.pathway.response.JobOpeningResponse
import app.stefanny.pathway.adapter.UserDashboardAdapter
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.response.UserGeneralAppliesJobOpeningResponse
import app.stefanny.pathway.ui.job.AddJobOpeningCompany
import app.stefanny.pathway.user.UserDashboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyDashboardBinding
    private lateinit var adapterJob: JobProfileAdapter
    private lateinit var adapterPeople: RecommendedPeopleAdapter
    private val listJob = ArrayList<JobOpeningResponse>()
    private val listPeople = ArrayList<UserGeneralAppliesJobOpeningResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompanyDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterJob = JobProfileAdapter(listJob)
        adapterPeople = RecommendedPeopleAdapter(listPeople)

        viewConfigJob()
        getListJobOpening()

        viewConfigPeople()
        getListRecommendedPeople()

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddJobOpeningCompany::class.java)
            startActivity(intent)
        }

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }

    }

    private fun viewConfigJob() {
        binding.rvJobOpenings.layoutManager = LinearLayoutManager(this)
        binding.rvJobOpenings.setHasFixedSize(true)
        binding.rvJobOpenings.adapter = adapterJob
    }

    private fun viewConfigPeople() {
        binding.rvRecommendedPeople.layoutManager = LinearLayoutManager(this)
        binding.rvRecommendedPeople.setHasFixedSize(true)
        binding.rvRecommendedPeople.adapter = adapterJob
    }

    fun getListJobOpening() {
        ApiConfig.getApiService().getJobOpening().enqueue(object :
            Callback<ArrayList<JobOpeningResponse>> {
            override fun onResponse(
                call: Call<ArrayList<JobOpeningResponse>>,
                response: Response<ArrayList<JobOpeningResponse>>
            ) {
                response.body().let {
                    if (it != null) {
                        listJob.addAll(it)
                    }
                    binding.rvJobOpenings.adapter = adapterJob
                }
            }

            override fun onFailure(call: Call<ArrayList<JobOpeningResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListRecommendedPeople() {
        ApiConfig.getApiService().getPeople().enqueue(object : Callback<ArrayList<UserGeneralAppliesJobOpeningResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserGeneralAppliesJobOpeningResponse>>,
                response: Response<ArrayList<UserGeneralAppliesJobOpeningResponse>>
            ) {
                response.body().let {
                    if (it != null) {
                        listPeople.addAll(it)
                    }
                    binding.rvRecommendedPeople.adapter = adapterPeople
                }
            }

            override fun onFailure(
                call: Call<ArrayList<UserGeneralAppliesJobOpeningResponse>>,
                t: Throwable
            ) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }



    companion object {
        private const val TAG = "CompanyDashboard"
    }
}