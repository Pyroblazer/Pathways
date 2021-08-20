package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.stefanny.pathway.R
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyDashboard
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityAddTaskJobDescriptionCompanyBinding
import app.stefanny.pathway.response.JobTaskResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskJobDescriptionCompany : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskJobDescriptionCompanyBinding

    companion object{
        private const val TAG = "AddTaskJobCompany"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskJobDescriptionCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }

        binding.idBack.setOnClickListener {
            val intent = Intent(this, EditJobDescriptionCompany::class.java)
            startActivity(intent)
        }

        binding.idSave.setOnClickListener {
            createNewJobTask()
            val intent = Intent(this, CompanyDashboard::class.java)
            startActivity(intent)
        }
    }

    private fun createNewJobTask(){

        val idTask = binding.idTask.text.toString()
        val taskType = binding.spType.text.toString()
        val duration = binding.idDuration.text.toString()
        val endDate = binding.idEdDate.text.toString()
        val shortDesc = binding.idDesc.text.toString()

        val dataRelated = JSONObject()
        dataRelated.put("shortDesc", shortDesc)
        dataRelated.put("duration", duration)
        dataRelated.put("endDate", endDate)

        val emp = dataRelated.toString()

        ApiConfig.getApiService().postJobTask( "3", "3", idTask, taskType, emp
        ).enqueue(object: Callback<JobTaskResponse> {
            override fun onResponse(
                call: Call<JobTaskResponse>,
                response: Response<JobTaskResponse>
            ) {
                Log.d(TAG, "success input")
            }

            override fun onFailure(call: Call<JobTaskResponse>, t: Throwable) {
                Log.d(TAG, "fail input")
            }

        })
    }

}