package app.stefanny.pathway.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.stefanny.pathway.databinding.ItemListRecommendedPeopleBinding
import app.stefanny.pathway.response.UserGeneralAppliesJobOpeningResponse

class RecommendedPeopleAdapter(private val listPeople : ArrayList<UserGeneralAppliesJobOpeningResponse>) : RecyclerView.Adapter<RecommendedPeopleAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: ItemListRecommendedPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(people: UserGeneralAppliesJobOpeningResponse) {
            with(binding) {
                binding.txtUsername.text = people.username
                binding.txtChallenges.text = people.doneChallenges
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListRecommendedPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listPeople[position])
    }

    override fun getItemCount(): Int = listPeople.size
}