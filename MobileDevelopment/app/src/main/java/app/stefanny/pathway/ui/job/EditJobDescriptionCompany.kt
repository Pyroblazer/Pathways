package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.databinding.ActivityEditJobDescriptionCompanyBinding
import app.stefanny.pathway.response.JobOpeningResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditJobDescriptionCompany : AppCompatActivity() {

    private lateinit var binding: ActivityEditJobDescriptionCompanyBinding

    companion object{
        private const val TAG = "EditJobOpeningCompany"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditJobDescriptionCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnAdd.setOnClickListener {
                val intent = Intent(this@EditJobDescriptionCompany, AddTaskJobDescriptionCompany::class.java)
                startActivity(intent)
            }

            btnEdit.setOnClickListener {
                val intent = Intent(this@EditJobDescriptionCompany, EditTaskJobDescriptionCompany::class.java)
                startActivity(intent)
            }
            btnBack.setOnClickListener {
                val intent = Intent(this@EditJobDescriptionCompany, JobDescriptionCompany::class.java)
                startActivity(intent)
            }
            idSave.setOnClickListener {
                editJob()
                val intent = Intent(this@EditJobDescriptionCompany, JobDescriptionCompany::class.java)
                startActivity(intent)
            }
        }
    }

    private fun editJob(){
        val idDesc = binding.idDesc.text.toString()
        val idCompany =  binding.idCompany.text.toString()
        val idLoc =  binding.idLocation.text.toString()
        val idTitle = binding.idJobTitle.text.toString()
        val idSalary =  binding.idSalary.text.toString()


        ApiConfig.getApiService().postJobOpening( idDesc, "test_user_company", idCompany, idLoc, "4", idTitle, idSalary
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