package app.stefanny.pathway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.company.CompanyDashboard
import app.stefanny.pathway.databinding.ActivityCreateAccountBinding
import app.stefanny.pathway.response.AllUsersResponseItem
import app.stefanny.pathway.ui.job.AddJobOpeningCompany
import app.stefanny.pathway.user.UserDashboard
import com.google.firebase.auth.FirebaseAuth
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
            val typeOfUser = binding.typeUser.text.toString().trim()

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

            binding.typeUser.setOnCheckedChangeListener { typeUser, isChecked ->
                if (isChecked) {
                    val company = Intent(this, CompanyDashboard::class.java)
                    startActivity(company)
                } else {
                    val user = Intent(this, UserDashboard::class.java)
                    startActivity(user)
                }
            }

            ApiConfig.getApiService().register(username, email, pass, typeOfUser).enqueue(object : Callback<AllUsersResponseItem> {
                override fun onResponse(
                    call: Call<AllUsersResponseItem>,
                    response: Response<AllUsersResponseItem>
                ) {
                    Log.d(TAG, "success input")

                }

                override fun onFailure(call: Call<AllUsersResponseItem>, t: Throwable) {
                    Log.d(TAG, "input failed")
                }
            })
        }

    }

}