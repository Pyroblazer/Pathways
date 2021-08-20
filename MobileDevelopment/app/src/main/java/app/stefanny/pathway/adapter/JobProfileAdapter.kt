package app.stefanny.pathway.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.stefanny.pathway.databinding.ItemListJobOpeningsBinding
import app.stefanny.pathway.response.JobOpeningResponse
import app.stefanny.pathway.ui.job.JobDescriptionCompany

class JobProfileAdapter(private val list : ArrayList<JobOpeningResponse>) : RecyclerView.Adapter<JobProfileAdapter.ListViewHolder>() {

    private lateinit var context: Context


    class ListViewHolder(private val binding: ItemListJobOpeningsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobOpeningResponse) {
            with(binding) {
                txtJobOpenings.text = job.title
                txtCompanyName.text = job.company
                txtCompanyLocation.text = job.location
                txtCompanyPaycheck.text = job.salary
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, JobDescriptionCompany::class.java)
                    intent.putExtra(JobDescriptionCompany.EXTRA_JOB, job.id)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListJobOpeningsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}