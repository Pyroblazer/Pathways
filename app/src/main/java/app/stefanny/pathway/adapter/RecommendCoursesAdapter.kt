package app.stefanny.pathway.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.stefanny.pathway.databinding.ItemListRecommendedCoursesBinding
import app.stefanny.pathway.response.SuggestedCourseResponse

class RecommendCoursesAdapter(private val listCourses : ArrayList<SuggestedCourseResponse>) : RecyclerView.Adapter<RecommendCoursesAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: ItemListRecommendedCoursesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: SuggestedCourseResponse) {
            with(binding) {
                binding.txtName.text = course.name
                binding.txtLink.text = course.linkToCourse
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListRecommendedCoursesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listCourses[position])
    }

    override fun getItemCount(): Int = listCourses.size

}