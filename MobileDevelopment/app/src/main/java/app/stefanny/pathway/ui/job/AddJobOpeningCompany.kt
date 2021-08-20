package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyDashboard
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityAddJobOpeningCompanyBinding
import app.stefanny.pathway.response.JobOpeningResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddJobOpeningCompany : AppCompatActivity() {

    private lateinit var binding : ActivityAddJobOpeningCompanyBinding

    companion object{
        private const val TAG = "AddJobOpeningCompany"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddJobOpeningCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, CompanyDashboard::class.java)
            startActivity(intent)
        }

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }

        binding.idSave.setOnClickListener {
            createNewJob()
            val intent = Intent(this, CompanyDashboard::class.java)
            startActivity(intent)
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTaskJobDescriptionCompany::class.java)
            startActivity(intent)
        }
    }

    private fun createNewJob(){

        val idDesc = binding.idDesc.text.toString().trim()
        val idCompany =  binding.idCompany.text.toString().trim()
        val idLoc =  binding.idLocation.text.toString().trim()
        val idTitle = binding.idJobTitle.text.toString().trim()
        val idSalary =  binding.idSalary.text.toString().trim()


        ApiConfig.getApiService().postJobOpening( idDesc, "test_user_company", idCompany, idLoc, "5", idTitle, idSalary
        ).enqueue(object: Callback<JobOpeningResponse> {
            override fun onResponse(
                call: Call<JobOpeningResponse>,
                response: Response<JobOpeningResponse>
            ) {
                Log.d(TAG, "success input")
            }

            override fun onFailure(call: Call<JobOpeningResponse>, t: Throwable) {
                Log.d(TAG, "fail input")
            }

        })

    }
}