package app.stefanny.pathway.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.stefanny.pathway.databinding.ItemListQualifiedPeopleBinding
import app.stefanny.pathway.response.UserGeneralResponse
import com.bumptech.glide.Glide

class QualifiedPeopleAdapter(private val listPeople: ArrayList<UserGeneralResponse>) : RecyclerView.Adapter<QualifiedPeopleAdapter.ListViewHolder>() {
    private lateinit var context: Context

    class ListViewHolder(private val binding: ItemListQualifiedPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(people: UserGeneralResponse) {
            with(binding) {
                binding.txtName.text = people.username
                binding.txtInterestSubject.text = people.interestSubject
                binding.txtShortDesc.text = people.shortDescription
                Glide.with(itemView.context)
                    .load("https://suaraborneo.co.id/wp-content/uploads/2020/05/783px-Test-Logo.svg_-1-750x359.png")
                    .into(binding.imgRecommendedPeople)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListQualifiedPeopleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listPeople[position])
    }

    override fun getItemCount(): Int = listPeople.size

}