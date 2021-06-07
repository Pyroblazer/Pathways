package app.stefanny.pathway.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.stefanny.pathway.databinding.ItemListExperienceBinding
import app.stefanny.pathway.response.UserProfileExperienceResponse

class UserProfileExperienceAdapter (private val list : ArrayList<UserProfileExperienceResponse>) : RecyclerView.Adapter<UserProfileExperienceAdapter.ListViewHolder>(){

    private lateinit var context: Context

    class ListViewHolder(private val binding: ItemListExperienceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(experience: UserProfileExperienceResponse) {
            with(binding) {
                txtExperience.text = experience.position
                txtCompanyName.text = experience.companyName
                txtDate.text = "${experience.startDate} - ${experience.endDate}"
                txtCompanyLocation.text = experience.positionDescription
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}