package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.stefanny.pathway.R
import app.stefanny.pathway.company.CompanyUserProfile
import app.stefanny.pathway.databinding.ActivityAddTaskJobDescriptionCompanyBinding

class AddTaskJobDescriptionCompany : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskJobDescriptionCompanyBinding

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
    }

}