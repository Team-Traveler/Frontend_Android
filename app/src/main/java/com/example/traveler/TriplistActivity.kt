package com.example.traveler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ActivityTriplistBinding

class TriplistActivity : Fragment() {
    private lateinit var tListBinding: ActivityTriplistBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var triplistAdapter: TriplistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tListBinding = ActivityTriplistBinding.inflate(inflater, container, false)
        return tListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = requireArguments().getString("etname") ?: "Default Name"
        val dayNight = requireArguments().getString("etDayNight") ?: "Default Name"
        val date = requireArguments().getString("etDate") ?: "Default Name"

        val tripList = listOf(
            Tripname("OO여행", "O박O일", "시작날짜 ~ 끝 날짜")
        )

        triplistAdapter = TriplistAdapter(requireContext(), tripList, name, dayNight, date)
        tListBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = triplistAdapter
        }
    }
}
