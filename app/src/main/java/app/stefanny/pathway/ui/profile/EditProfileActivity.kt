package app.stefanny.pathway.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.stefanny.pathway.api.ApiConfig
import app.stefanny.pathway.databinding.ActivityEditProfileBinding
import app.stefanny.pathway.response.JobOpeningResponse
import app.stefanny.pathway.response.UserGeneralResponse
import app.stefanny.pathway.ui.job.AddJobOpeningCompany
import app.stefanny.pathway.user.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
        private const val REQUEST_CAMERA = 3

        private const val TAG = "EditProfile"
        private const val USERNAME = "test_user_general"

    }

    private lateinit var binding: ActivityEditProfileBinding

    private val _userGeneral = MutableLiveData<UserGeneralResponse>()
    val userGeneral: LiveData<UserGeneralResponse> = _userGeneral

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getEditProfile()

        binding.btnPp.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE)
                }
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }

        binding.btnId.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }

        binding.idPp.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }

        binding.idId.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddExperience::class.java)
            startActivity(intent)
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditExperience::class.java)
            startActivity(intent)
        }

        binding.idSave.setOnClickListener {
            editProfile()
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        binding.idBack.setOnClickListener {
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE)
                }
            } else {
                Toast.makeText(this, "Unable access the  camera.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val bitmapPp = data?.extras?.get("data") as Bitmap
            binding.idPp.setImageBitmap(bitmapPp)
        } else if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK){
            val bitmapId = data?.extras?.get("data") as Bitmap
            binding.idId.setImageBitmap(bitmapId)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getEditProfile(){
        userGeneral.observe(this, { userGeneral ->
            binding.idName.text = userGeneral.username
            //        binding.idDesc.text = userGeneral.shortDescription

        })

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



    private fun editProfile(){
        val shortDesc = binding.idDesc.text.toString()

        ApiConfig.getApiService().postEditProfile( shortDesc, "Front-End Engineer", "https://suaraborneo.co.id/wp-content/uploads/2020/05/783px-Test-Logo.svg_-1-750x359.png", "Kotlin_Programming Android_Development", USERNAME
        ).enqueue(object: Callback<UserGeneralResponse> {
            override fun onResponse(
                call: Call<UserGeneralResponse>,
                response: Response<UserGeneralResponse>
            ) {
                Log.d(TAG, "success input")
            }

            override fun onFailure(call: Call<UserGeneralResponse>, t: Throwable) {
                Log.d(TAG, "fail input")
            }

        })
    }

}