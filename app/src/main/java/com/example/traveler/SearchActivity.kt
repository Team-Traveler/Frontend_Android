package com.example.traveler

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.traveler.databinding.ActivitySearchBinding
import com.example.traveler.databinding.FragmentStoryBinding

class SearchActivity : Fragment() {
    private lateinit var searchBinding: ActivitySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchBinding = ActivitySearchBinding.inflate(inflater, container, false)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //추천검색어
        // 자동완성으로 보여줄 내용들 -> auto 웅앵 써서 얘 히스토리를 걍 남겨야겠음
        var items = arrayOf("서울", "서울여행", "서울맛집", "부산", "부산여행", "부산맛집", "대구", "대구여행", "대구맛집", "대전", "대전여행", "대전맛집",
            "광주", "광주여행", "광주맛집", "제주도", "제주도여행", "제주도맛집", "국내", "국내여행", "강릉", "강릉여행", "강릉맛집",
            "전주", "전주여행", "전주맛집")

        var autoCompleteTextView = searchBinding.svSearch2

        var adapter = context?.let { ArrayAdapter(it, R.layout.simple_dropdown_item_1line, items) }
        autoCompleteTextView.setAdapter(adapter)

        //api 확인
        //내가 주는거니까 keyword 줘야됨
//        api.requestCode("Sample Post").enqueue(object: retrofit2.Callback<SearchDto> {
//            override fun onResponse(call: Call<SearchDto>, response: Response<SearchDto>) {
//                val data = response.body()
//
//                //성공
//                if(data != null){
//                    if(data.code == 1000){
//                        Log.d("code", data.toString())
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<SearchDto>, t: Throwable) {
//                Log.e("code", "API request failed", t)
//            }
//        })

        //검색했을 때
        val imgSearch2 = searchBinding.imgSearch2
        imgSearch2.setOnClickListener {
            addSearchToRecent()
        }
    }

    //최근검색어 기록 남기는 함수
    private fun addSearchToRecent() {
        val searchText = searchBinding.svSearch2.text.toString().trim()

        if (searchText.isNotEmpty()) {
            val recentSearchesTextView = searchBinding.tvRecentSearches

            if (recentSearchesTextView.visibility == View.GONE) {
                recentSearchesTextView.visibility = View.VISIBLE
            }

            val currentText = recentSearchesTextView.text.toString()

            // 연속으로 같은 검색어 검색시 최근검색어로 안넘김
            if (!currentText.split("\n").any { it.trim() == searchText}) {
                val newText = if (currentText.isEmpty()) {
                    searchText
                } else {
                    "$searchText\n$currentText"
                }

                //최근검색어 밑에 text 생기게 함
                recentSearchesTextView.text = newText
            }

            //돋보기 클릭시 검색어 내용 지움
            searchBinding.svSearch2.text.clear()
        }
    }
}