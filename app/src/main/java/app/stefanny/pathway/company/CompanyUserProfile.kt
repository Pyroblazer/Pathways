package app.stefanny.pathway.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import app.stefanny.pathway.R
import app.stefanny.pathway.adapter.UserProfileCompletedTaskAdapter
import app.stefanny.pathway.adapter.UserProfileExperienceAdapter
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.databinding.ActivityCompanyUserProfileBinding
import app.stefanny.pathway.response.CompletedTaskResponseItem
import app.stefanny.pathway.response.UserGeneralResponse
import app.stefanny.pathway.response.UserProfileExperienceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyUserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyUserProfileBinding
    private lateinit var adapterExperience: UserProfileExperienceAdapter
    private val listExperience = ArrayList<UserProfileExperienceResponse>()

    private val _userGeneral = MutableLiveData<UserGeneralResponse>()
    val userGeneral: LiveData<UserGeneralResponse> = _userGeneral

    private lateinit var adapterCompletedTask: UserProfileCompletedTaskAdapter
    private val listCompletedTask = ArrayList<CompletedTaskResponseItem>()

    companion object {
        private const val TAG = "CompanyUserProfile"
        private const val USERNAME = "test_user_general"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompanyUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnEdit.setOnClickListener {
//            val intent = Intent(this, EditProfileActivity::class.java)
//            startActivity(intent)
//        }

        userGeneral.observe(this, { userGeneral ->
            binding.tvUserGeneralName.text = userGeneral.username
            binding.tvShortDescUserGeneral.text = userGeneral.shortDescription

        })

        //user profile
        getUserGeneral()

        //list eperience
        adapterExperience = UserProfileExperienceAdapter(listExperience)

        viewConfig()
        getListExperience()

        //list completed task
        adapterCompletedTask = UserProfileCompletedTaskAdapter(listCompletedTask)

        viewTaskConfig()
        getListTask()



    }


    fun getUserGeneral() {
        ApiConfig.getApiService().getUserGeneral(USERNAME).enqueue(object :
            Callback<UserGeneralResponse> {
            override fun onResponse(
                call: Call<UserGeneralResponse>,
                response: Response<UserGeneralResponse>
            ) {
                if (response.isSuccessful) {
                    _userGeneral.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<UserGeneralResponse>, t: Throwable) {
            }
        })
    }

    private fun viewConfig() {
        binding.rvExperience.layoutManager = LinearLayoutManager(this)
        binding.rvExperience.setHasFixedSize(true)
        binding.rvExperience.adapter = adapterExperience
    }

    fun getListExperience() {
        ApiConfig.getApiService().getUserProfileExperience().enqueue(object :
            Callback<ArrayList<UserProfileExperienceResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserProfileExperienceResponse>>,
                response: Response<ArrayList<UserProfileExperienceResponse>>
            ) {
                response.body().let {
                    if (it != null) {
                        listExperience.addAll(it)
                    }
                    binding.rvExperience.adapter = adapterExperience
                }
            }

            override fun onFailure(call: Call<ArrayList<UserProfileExperienceResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun viewTaskConfig() {
        binding.rvCompletedTask.layoutManager = LinearLayoutManager(this)
        binding.rvCompletedTask.setHasFixedSize(true)
        binding.rvCompletedTask.adapter = adapterCompletedTask
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
                    binding.rvCompletedTask.adapter = adapterCompletedTask
                }
            }

            override fun onFailure(call: Call<ArrayList<CompletedTaskResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}