package app.stefanny.pathway.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import app.stefanny.pathway.adapter.RecommendCoursesAdapter
import app.stefanny.pathway.adapter.UserDashboardAdapter
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityUserDashboardBinding
import app.stefanny.pathway.response.JobOpeningResponse
import app.stefanny.pathway.response.SuggestedCourseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDashboard : AppCompatActivity() {

    private lateinit var adapterCourse : RecommendCoursesAdapter
    private lateinit var binding: ActivityUserDashboardBinding
    private lateinit var adapterJob: UserDashboardAdapter
    private val list = ArrayList<JobOpeningResponse>()
    private val listCourse = ArrayList<SuggestedCourseResponse>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterJob = UserDashboardAdapter(list)
        adapterCourse = RecommendCoursesAdapter(listCourse)

        viewConfigJob()
        getListJobOpening()

        viewConfigCourses()
        getListCourses()

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }
    }

    private fun viewConfigJob() {
        binding.rvJobOpening.layoutManager = LinearLayoutManager(this)
        binding.rvJobOpening.setHasFixedSize(true)
        binding.rvJobOpening.adapter = adapterJob
    }

    private fun viewConfigCourses() {
        binding.rvRecommendedCourses.layoutManager = LinearLayoutManager(this)
        binding.rvRecommendedCourses.setHasFixedSize(true)
        binding.rvRecommendedCourses.adapter = adapterCourse
    }

    fun getListJobOpening() {
        ApiConfig.getApiService().getJobOpening().enqueue(object : Callback<ArrayList<JobOpeningResponse>>{
            override fun onResponse(
                call: Call<ArrayList<JobOpeningResponse>>,
                response: Response<ArrayList<JobOpeningResponse>>
            ) {
                response.body().let {
                    if (it != null) {
                        list.addAll(it)
                    }
                    binding.rvJobOpening.adapter = adapterJob
                }
            }

            override fun onFailure(call: Call<ArrayList<JobOpeningResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListCourses() {
        ApiConfig.getApiService().getCourses().enqueue(object : Callback<ArrayList<SuggestedCourseResponse>> {
            override fun onResponse(
                call: Call<ArrayList<SuggestedCourseResponse>>,
                response: Response<ArrayList<SuggestedCourseResponse>>
            ) {
                response.body().let {
                    if (it != null) {
                        listCourse.addAll(it)
                    }
                    binding.rvRecommendedCourses.adapter = adapterCourse
                }
            }

            override fun onFailure(call: Call<ArrayList<SuggestedCourseResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "UserDashboard"
    }

}