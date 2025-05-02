package com.example.innostudent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.databinding.ItemProjectBinding
import com.example.innostudent.models.Project


class ProjectAdapter(private val projects: List<Project>) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)
    }

    override fun getItemCount(): Int = projects.size

    inner class ProjectViewHolder(private val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(project: Project) {
            binding.tvProjectName.text = project.name
            binding.tvProjectDescription.text = project.description
            // Bind other project details like image, category, etc.
        }
    }
}
