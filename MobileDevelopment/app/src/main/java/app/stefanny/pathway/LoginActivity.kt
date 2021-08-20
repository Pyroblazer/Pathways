package app.stefanny.pathway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyDashboard
import app.stefanny.pathway.databinding.ActivityLoginBinding
import app.stefanny.pathway.request.LoginRequest
import app.stefanny.pathway.request.UserRequest
import app.stefanny.pathway.response.AllUsersResponse
import app.stefanny.pathway.response.AllUsersResponseItem
import app.stefanny.pathway.user.UserDashboard
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object{
        private const val TAG = "LoginActivity"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDontHaveAnAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val pass = binding.edtPassword.text.toString().trim()

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

            login()

        }
    }

    fun login() {
        var userType = "COMPANY"

        val loginRequest = LoginRequest()
        loginRequest.email = binding.edtEmail.text.toString().trim()
        loginRequest.userPassword = binding.edtPassword.text.toString().trim()
        loginRequest.typeOfUser = userType


        ApiConfig.getApiService().login(loginRequest).enqueue(object : Callback<AllUsersResponse> {
            override fun onResponse(
                call: Call<AllUsersResponse>,
                response: Response<AllUsersResponse>
            ) {
                if (userType == "COMPANY") {
                    val intent = Intent(this@LoginActivity, CompanyDashboard::class.java)
                    startActivity(intent)

                } else {
                    val company = Intent(this@LoginActivity, UserDashboard::class.java)
                    startActivity(company)
                }

            }

            override fun onFailure(call: Call<AllUsersResponse>, t: Throwable) {
                Log.d(TAG, "input failed")
            }
        })
    }


}