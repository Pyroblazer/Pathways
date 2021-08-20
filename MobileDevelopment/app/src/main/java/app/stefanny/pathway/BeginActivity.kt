package app.stefanny.pathway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.stefanny.pathway.databinding.ActivitySecondBinding
import app.stefanny.pathway.user.UserDashboard

class BeginActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFour.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        binding.btnOne.setOnClickListener {
            val intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
        }
    }
}