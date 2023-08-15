package com.example.traveler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.traveler.databinding.ActivitySearchBinding
import com.example.traveler.databinding.FragmentStoryBinding

class SearchActivity : Fragment() {
    private lateinit var binding: ActivitySearchBinding
    private val MAX_DISPLAYED_KEYWORDS = 5
    private val keywordList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val svSearch = binding.svSearch
        val tvLately = binding.tvLately
        val tvClose = binding.tvClose

        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotBlank()) {
                    keywordList.add(0, query) // Add the query to the top of the list
                    updateKeywordList()
                    svSearch.setQuery("", false) // Clear the query
                    svSearch.clearFocus() // Remove focus from the SearchView
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        tvClose.setOnClickListener {
            svSearch.setQuery("", false)
            svSearch.clearFocus()
        }
    }

    private fun updateKeywordList() {
        val tvRecentSearches = binding.tvRecentSearches

        tvRecentSearches.text = keywordList.take(MAX_DISPLAYED_KEYWORDS).joinToString("\n")
        tvRecentSearches.visibility = View.VISIBLE
    }
}