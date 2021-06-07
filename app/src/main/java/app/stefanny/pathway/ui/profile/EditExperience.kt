package app.stefanny.pathway.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.stefanny.pathway.R
import app.stefanny.pathway.databinding.ActivityEditExperienceBinding
import app.stefanny.pathway.user.UserProfile

class EditExperience : AppCompatActivity() {

    private lateinit var binding: ActivityEditExperienceBinding

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
    }
}