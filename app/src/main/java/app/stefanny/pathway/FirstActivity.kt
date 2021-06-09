package app.stefanny.pathway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.stefanny.pathway.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, BeginActivity::class.java)
            startActivity(intent)
        }
    }
}