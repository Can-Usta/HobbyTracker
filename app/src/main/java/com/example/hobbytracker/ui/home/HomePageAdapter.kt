package com.example.hobbytracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbytracker.R
import com.example.hobbytracker.data.local.entities.Hobby
import com.example.hobbytracker.databinding.HobbyItemBinding

class HomePageAdapter(private var hobbies : List<Hobby>) : RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>(){
    inner class HomeViewHolder(var bind : HobbyItemBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = DataBindingUtil.inflate<HobbyItemBinding>(LayoutInflater.from(parent.context), R.layout.hobby_item, parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val hobby = hobbies[position]
        holder.bind.apply {
            hobbyHeaderTV.text = hobby.title
            hobbyDescTV.text = hobby.description
            hobbyDateTV.text = hobby.dateFormatted
            hobbyTimeTV.text = hobby.timeFormatted
            hobbyUpdateButton.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToHobbyUpdateFragment(hobby.id)
                it.findNavController().navigate(action)
            }
        }
    }
    fun updateHobbies(newHobbies: List<Hobby>){
        this.hobbies = newHobbies
        notifyDataSetChanged()
    }
}