package app.stefanny.pathway.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.stefanny.pathway.databinding.ItemListCompletedTaskBinding
import app.stefanny.pathway.response.CompletedTaskResponseItem
import org.json.JSONObject

class UserProfileCompletedTaskAdapter (private val list : ArrayList<CompletedTaskResponseItem>) : RecyclerView.Adapter<UserProfileCompletedTaskAdapter.ListViewHolder>(){

    private lateinit var context: Context

    class ListViewHolder(private val binding: ItemListCompletedTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(completedtask: CompletedTaskResponseItem) {
            with(binding) {
                txtTask.text = completedtask.taskTitle

                val emp = JSONObject(completedtask.dataRelated).getString("task")
                txtCompanyLocation.text = emp
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListCompletedTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}