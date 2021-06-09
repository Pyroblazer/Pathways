package app.stefanny.pathway

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import app.stefanny.pathway.api.ApiConfigVM
import app.stefanny.pathway.databinding.ActivityThirdBinding
import app.stefanny.pathway.request.JobRecommenderRequest
import app.stefanny.pathway.response.JobRecommenderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    companion object {
        const val TAG = "ThirdActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSubmit.setOnClickListener {

            dialog()
        }

    }


    fun dialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.activity_result)

        val btnBack = dialog.findViewById<Button>(R.id.btn_back_result)
        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        val jobs = dialog.findViewById<TextView>(R.id.id_pathway)
        jobs.text = ""
        var jobRequest = JobRecommenderRequest()
        jobRequest.text = binding.edtIntroduce.text.toString().trim()


        ApiConfigVM.getApiService().jobRecommender(jobRequest).enqueue(object : Callback<JobRecommenderResponse> {
            override fun onResponse(
                call: Call<JobRecommenderResponse>,
                response: Response<JobRecommenderResponse>
            ) {
                val job = response.body()
                jobs.text = job?.job.toString()

            }

            override fun onFailure(call: Call<JobRecommenderResponse>, t: Throwable) {
                Log.d(TAG, "input failed")
            }
        })

        dialog.show()
    }
}