package com.university.superheroes.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.university.superheroes.R
import com.university.superheroes.api.ApiClient
import com.university.superheroes.api.SearchDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private lateinit var charactersRV: RecyclerView
    private lateinit var searchET: EditText
    private lateinit var search: Button
    private lateinit var adapter: CharactersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CharactersAdapter()
        charactersRV = view.findViewById(R.id.charactersRV)
        searchET = view.findViewById(R.id.searchET)
        search = view.findViewById(R.id.search)
        search.setOnClickListener {
            get(searchET.text.toString())
            searchET.setText("")
        }
        charactersRV.adapter = adapter
        charactersRV.layoutManager = LinearLayoutManager(context)
        get("a")
    }

    fun get(keyword: String) {
        ApiClient.apiService().search(keyword).enqueue(object : Callback<SearchDTO> {
            override fun onResponse(call: Call<SearchDTO>, response: Response<SearchDTO>) {
                if (response.body() != null) {

                    val res = response.body()?.results?.map {
                        Character(
                            it.name,
                            it.image.url
                        ) {
                            val action = CharactersFragmentDirections
                                .actionCharactersFragmentToDetailedCharacterFragment(it.id)
                            findNavController().navigate(action)
                        }
                    }
                    adapter.setData(res!!)
                }
            }

            override fun onFailure(call: Call<SearchDTO>, t: Throwable) {}
        })
    }
}
