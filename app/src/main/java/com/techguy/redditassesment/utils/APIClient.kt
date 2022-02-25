package com.techguy.redditassesment.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
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
    //var data: CharacterResults = CharacterResults()

    suspend fun Context.getCharacters(): ArrayList<Character> =
        suspendCancellableCoroutine { continuation ->

            if (getPrefString(CHARACTER_KEY).isNotEmpty()) {
                //Load from cache
                val characterResults =
                    Gson().fromJson(getPrefString(CHARACTER_KEY), CharacterResults::class.java)
                continuation.resume(characterResults.results)
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
                        setPrefString(CHARACTER_KEY, response.toString())
                    }

                    override fun onError(anError: ANError?) {
                        continuation.resume(arrayListOf())
                    }
                })
        }

    fun queryData(query: String) {
        
    }
}