package com.university.superheroes.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.university.superheroes.R
import com.university.superheroes.api.ApiClient
import com.university.superheroes.api.CharacterDTO
import com.university.superheroes.db.DatabaseHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailedCharacterFragment : Fragment(R.layout.fragment_character_detailed) {

    private lateinit var characterIV: ImageView
    private lateinit var characterNameTV: TextView
    private lateinit var characterBiographyTV: TextView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var characterName: String
    private lateinit var characterImage: String

    private val arguments by navArgs<DetailedCharacterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterId: Int = arguments.characterId
        characterIV = view.findViewById(R.id.characterIV)
        characterNameTV = view.findViewById(R.id.characterNameTV)
        characterBiographyTV = view.findViewById(R.id.characterBiographyTV)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        ApiClient.apiService().getCharacter(characterId).enqueue(object : Callback<CharacterDTO> {
            override fun onResponse(call: Call<CharacterDTO>, response: Response<CharacterDTO>) {
                if (response.body() != null) {
                    val body = response.body()!!
                    Glide.with(context!!).load(body.image.url).centerCrop().into(characterIV)
                    characterNameTV.text = body.name
                    characterBiographyTV.text = body.biography.fullName
                    characterName = body.name
                    characterImage = body.image.url
                }
            }

            override fun onFailure(call: Call<CharacterDTO>, t: Throwable) {}
        })

        val dbHelper = DatabaseHelper(requireContext())
        val cursor = dbHelper.getById(characterId)
        cursor!!.moveToFirst()
        if (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("id")) == characterId) {
                toolbar.menu.findItem(R.id.favorites).setIcon(R.drawable.ic_baseline_favorite_24)
            }
        }
        cursor.close()

        toolbar.menu.findItem(R.id.favorites).setOnMenuItemClickListener {
            dbHelper.addCharacter(characterName, characterName)
            toolbar.menu.findItem(R.id.favorites).setIcon(R.drawable.ic_baseline_favorite_24)
            true
        }
    }
}
