package com.university.superheroes.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.university.superheroes.R

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private val data = mutableListOf<Character>()

    fun setData(data: List<Character>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Character) {
            Glide.with(itemView.context)
                .load(item.image)
                .centerCrop()
                .into(itemView.findViewById(R.id.characterIV))
            itemView.findViewById<TextView>(R.id.characterTV).text = item.name
            itemView.rootView.setOnClickListener { item.clickAction.invoke() }
        }
    }
}
