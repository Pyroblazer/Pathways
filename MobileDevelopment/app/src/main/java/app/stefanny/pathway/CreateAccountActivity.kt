package app.stefanny.pathway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyDashboard
import app.stefanny.pathway.databinding.ActivityCreateAccountBinding
import app.stefanny.pathway.request.UserRequest
import app.stefanny.pathway.response.AllUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    companion object{
        private const val TAG = "CreateAccountActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnHaveAnAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateAccount.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val pass = binding.edtPassword.text.toString().trim()
            val repass = binding.edtReenterPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.edtUsername.error = "Username can't be empty"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = "Email must be valid"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            if (pass.length <= 5) {
                binding.edtPassword.error = "Password must be more than 6 characters"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

            if (repass != pass) {
                binding.edtReenterPassword.error = "Password is not valid"
            }

            register()

            var userType = "GENERAL"
            binding.typeUser.setOnCheckedChangeListener { typeUser, isChecked ->
                if (isChecked) {
                    userType = "COMPANY"
                } else {
                    userType = "GENERAL"
                }
            }
            if (userType == "GENERAL") {
                val company = Intent(this@CreateAccountActivity, FirstActivity::class.java)
                startActivity(company)

            }


        }

    }

    fun register() {

        var userType = "COMPANY"
        val userRequest = UserRequest()
        userRequest.username = binding.edtUsername.text.toString().trim()
        userRequest.email = binding.edtEmail.text.toString().trim()
        userRequest.userPassword = binding.edtPassword.text.toString().trim()
        userRequest.typeOfUser = userType

        ApiConfig.getApiService().register(userRequest).enqueue(object : Callback<AllUsersResponse> {
            override fun onResponse(
                call: Call<AllUsersResponse>,
                response: Response<AllUsersResponse>
            ) {
                if (userType == "GENERAL") {
                    val company = Intent(this@CreateAccountActivity, FirstActivity::class.java)
                    startActivity(company)

                } else {
                    val intent = Intent(this@CreateAccountActivity, CompanyDashboard::class.java)
                    startActivity(intent)
                }

            }

            override fun onFailure(call: Call<AllUsersResponse>, t: Throwable) {
                Log.d(TAG, "input failed")
            }
        })
    }

}