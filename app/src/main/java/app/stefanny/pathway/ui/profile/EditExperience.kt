package app.stefanny.pathway.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.stefanny.pathway.R
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.databinding.ActivityEditExperienceBinding
import app.stefanny.pathway.response.UserProfileExperienceResponse
import app.stefanny.pathway.user.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditExperience : AppCompatActivity() {

    private lateinit var binding: ActivityEditExperienceBinding

    companion object{
        private const val TAG = "EditExperience"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditExperienceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        binding.idBack.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.idSave.setOnClickListener {
            addNewExperience()
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }
    }

    private fun addNewExperience() {

        val idPosition = binding.idTitle.text.toString()
        val idCompany = binding.idCompany.text.toString()
        val idStartDate = binding.idStDate.text.toString()
        val idEndDate = binding.idEdDate.text.toString()
        val positionDesc = binding.idDesc.text.toString()

        ApiConfig.getApiService().postUserExperience( "5", "test_user_general", idPosition, idCompany, idStartDate, idEndDate, positionDesc
        ).enqueue(object: Callback<UserProfileExperienceResponse> {
            override fun onResponse(
                call: Call<UserProfileExperienceResponse>,
                response: Response<UserProfileExperienceResponse>
            ) {
                Log.d(TAG, "success input")
            }
            override fun onFailure(call: Call<UserProfileExperienceResponse>, t: Throwable) {
                Log.d(TAG, "fail input")
            }
        })
    }
}