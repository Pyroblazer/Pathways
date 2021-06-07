package app.stefanny.pathway.ui.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.stefanny.pathway.databinding.ActivityEditJobDescriptionCompanyBinding

class EditJobDescriptionCompany : AppCompatActivity() {

    private lateinit var binding: ActivityEditJobDescriptionCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditJobDescriptionCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnEdit.setOnClickListener {
                val intent = Intent(this@EditJobDescriptionCompany, JobDescriptionCompany::class.java)
                startActivity(intent)
            }

            btnAdd.setOnClickListener {
                val intent = Intent(this@EditJobDescriptionCompany, AddTaskJobDescriptionCompany::class.java)
                startActivity(intent)
            }

            btnEdit.setOnClickListener {
                val intent = Intent(this@EditJobDescriptionCompany, EditTaskJobDescriptionCompany::class.java)
                startActivity(intent)
            }
        }
    }
}