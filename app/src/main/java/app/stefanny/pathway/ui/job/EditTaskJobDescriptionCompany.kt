package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.stefanny.pathway.R
import app.stefanny.pathway.company.CompanyDashboard
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityEditTaskJobDescriptionCompanyBinding

class EditTaskJobDescriptionCompany : AppCompatActivity() {

    private lateinit var binding: ActivityEditTaskJobDescriptionCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditTaskJobDescriptionCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBack.setOnClickListener {
            val intent = Intent(this, JobDescriptionCompany::class.java)
            startActivity(intent)
        }

        binding.btnUserProfile.setOnClickListener {
            val intent = Intent(this, CompanyUserProfile::class.java)
            startActivity(intent)
        }
    }
}