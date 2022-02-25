package com.techguy.redditassesment.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.techguy.redditassesment.adapter.CharacterAdapter
import com.techguy.redditassesment.databinding.ActivityMainBinding
import com.techguy.redditassesment.utils.APIClient.getCharacters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.characterList.layoutManager = GridLayoutManager(this, 3)
        getData()
        binding.searchBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun getData() {
        CoroutineScope(Dispatchers.Default).launch {
            val data = getCharacters()
            Handler(Looper.getMainLooper()).post {
                if (data.size > 0) {
                    binding.errorLayout.visibility = View.GONE
                    binding.characterList.adapter = CharacterAdapter(data)

                } else
                    binding.errorLayout.visibility = View.VISIBLE
            }
        }
    }
}