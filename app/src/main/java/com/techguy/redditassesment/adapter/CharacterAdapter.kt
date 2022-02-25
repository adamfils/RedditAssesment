package com.techguy.redditassesment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.techguy.redditassesment.R
import com.techguy.redditassesment.databinding.CharacterListItemBinding
import com.techguy.redditassesment.model.Character
import com.techguy.redditassesment.utils.ViewUtils.showCharacterDetails

class CharacterAdapter(val data: ArrayList<Character>) : RecyclerView.Adapter<CharacterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_list_item, parent, false)
        return CharacterVH(view)
    }

    override fun onBindViewHolder(holder: CharacterVH, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class CharacterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = CharacterListItemBinding.bind(itemView)

    fun bind(character: Character) {
        binding.characterName.text = character.name
        if (character.image.isNotEmpty())
            Picasso.get().load(character.image).into(binding.characterImage)
        itemView.setOnClickListener {
            itemView.context.showCharacterDetails(character)
        }
    }
}