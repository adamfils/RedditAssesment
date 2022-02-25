package com.techguy.redditassesment.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.techguy.redditassesment.adapter.CharacterAdapter
import com.techguy.redditassesment.databinding.ActivityMainBinding
import com.techguy.redditassesment.model.Character
import com.techguy.redditassesment.model.CharacterResults
import com.techguy.redditassesment.utils.SharedPreferenceUtil.CHARACTER_KEY
import com.techguy.redditassesment.utils.SharedPreferenceUtil.getPrefString
import com.techguy.redditassesment.utils.SharedPreferenceUtil.setPrefString
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume

object APIClient : ViewModel() {
    private const val BASE_URL = "https://rickandmortyapi.com/api/character/"
    var characterData: ArrayList<Character> = ArrayList()

    suspend fun Context.getCharacters(): ArrayList<Character> =
        suspendCancellableCoroutine { continuation ->

            if (getPrefString(CHARACTER_KEY).isNotEmpty()) {
                //Load from cache
                val characterResults =
                    Gson().fromJson(getPrefString(CHARACTER_KEY), CharacterResults::class.java)
                continuation.resume(characterResults.results)
                characterData = characterResults.results
                return@suspendCancellableCoroutine
            }

            //Load from API
            AndroidNetworking.get(BASE_URL)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        val data =
                            Gson().fromJson(response.toString(), CharacterResults::class.java)
                        continuation.resume(data.results)
                        characterData = data.results
                        setPrefString(CHARACTER_KEY, response.toString())
                    }

                    override fun onError(anError: ANError?) {
                        continuation.resume(arrayListOf())
                    }
                })
        }

    fun ActivityMainBinding.queryData(query: String) {
        val list = characterData.filter {
            it.name.contains(query, true)
        }.toCollection(ArrayList())
        characterList.adapter = CharacterAdapter(list)
        if (list.isEmpty())
            errorLayout.visibility = android.view.View.VISIBLE
        else {
            errorLayout.visibility = android.view.View.GONE
        }
    }
}