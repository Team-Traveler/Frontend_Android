package com.example.traveler.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.adapter.InnerAdapter
import com.example.traveler.adapter.OuterAdapter
import com.example.traveler.databinding.FragmentCheckBinding
import com.example.traveler.dialog.OuterDialog
import com.example.traveler.dialogInterface.OuterDialogInterface
import com.example.traveler.model.InnerDto
import com.example.traveler.model.OuterDto
import com.example.traveler.viewmodel.InnerViewModel
import com.example.traveler.viewmodel.OuterViewModel

class CheckFragment : Fragment(), OuterDialogInterface {
    private var binding: FragmentCheckBinding? = null
    private val innerViewModel: InnerViewModel by viewModels()
    private val outerViewModel: OuterViewModel by viewModels()
    private val outerList = ArrayList<OuterDto>()
    private val innerList = ArrayList<InnerDto>()
    private val adapter: OuterAdapter by lazy { OuterAdapter(outerList, innerViewModel, outerViewModel) }
    private val innerAdapter: InnerAdapter by lazy { InnerAdapter(innerList, innerViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. View Binding 설정
        binding = FragmentCheckBinding.inflate(inflater, container, false)

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding!!.outerRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding!!.outerRecyclerView.adapter = adapter

        // 리스트 관찰하여 변경시 어댑터에 전달해줌
        outerViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setOuterData(it)
        })

        innerViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            innerAdapter.setInnerData(it)
        })

        binding!!.categoryBtnAdd.setOnClickListener {
            onBtnClicked()
        }
        return binding!!.root
    }
    // Fab 클릭시 다이얼로그 띄움
    private fun onBtnClicked(){
        val outerDialog = OuterDialog(requireActivity(), this)
        outerDialog.show()
    }
//     다이얼로그에서 추가버튼 클릭 됐을 때
    override fun onOkButtonClicked(title: String) {
        val outer = OuterDto(0, title)
        outerViewModel.addOuter(outer)
        Toast.makeText(activity,"카테고리 추가", Toast.LENGTH_SHORT).show()
    }
}