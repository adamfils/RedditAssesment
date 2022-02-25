package com.techguy.redditassesment.utils

import android.content.Context
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.squareup.picasso.Picasso
import com.techguy.redditassesment.R
import com.techguy.redditassesment.databinding.CharacterDetailsLayoutBinding
import com.techguy.redditassesment.model.Character

object ViewUtils {

    fun Context.showCharacterDetails(character: Character) {
        val dialog = RoundedBottomSheetDialog(this)
        val binding = CharacterDetailsLayoutBinding.bind(
            dialog.layoutInflater.inflate(R.layout.character_details_layout, null)
        )
        dialog.setContentView(binding.root)
        binding.characterName.text = "Name: ${character.name}"
        binding.characterGender.text = "Gender: ${character.gender}"
        binding.characterStatus.text = "Status: ${character.status}"
        binding.characterSpecies.text = "Species: ${character.species}"
        binding.characterLocation.text = "Location: ${character.location.name}"
        binding.characterOrigin.text = "Origin: ${character.origin.name}"
        if (character.image.isNotEmpty()) {
            Picasso.get().load(character.image).into(binding.characterImage)
        }
        dialog.show()
    }

}