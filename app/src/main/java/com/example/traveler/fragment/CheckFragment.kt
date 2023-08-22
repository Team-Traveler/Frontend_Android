package com.example.traveler.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.Interface.OuterDialogInterface
import com.example.traveler.adapter.InnerAdapter
import com.example.traveler.adapter.OuterAdapter
import com.example.traveler.databinding.FragmentCheckBinding
import com.example.traveler.dialog.OuterDialog
import com.example.traveler.model.InnerDto
import com.example.traveler.model.OuterDto
import com.example.traveler.viewmodel.InnerViewModel
import com.example.traveler.viewmodel.OuterViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class CheckFragment : Fragment(), OuterDialogInterface, OuterAdapter.UiUpdateListener {
    private var binding: FragmentCheckBinding? = null
    private val innerViewModel: InnerViewModel by viewModels()
    private val outerViewModel: OuterViewModel by viewModels()
    private val outerList = ArrayList<OuterDto>()
    private val innerList = ArrayList<InnerDto>()
    private val adapter: OuterAdapter by lazy { OuterAdapter(outerList, innerViewModel, outerViewModel, this) }
    private val innerAdapter: InnerAdapter by lazy { InnerAdapter(innerList, innerViewModel, this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. View Binding 설정
        binding = FragmentCheckBinding.inflate(inflater, container, false)

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding!!.outerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding!!.outerRecyclerView.adapter = adapter

        // 리스트 관찰하여 변경시 어댑터에 전달해줌
        outerViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setOuterData(it)
        })

        binding!!.categoryBtnAdd.setOnClickListener {
            onBtnClicked()
        }
        return binding!!.root
    }

    // Fab 클릭시 다이얼로그 띄움
    private fun onBtnClicked() {
        val outerDialog = OuterDialog(requireActivity(), this)
        outerDialog.show()
    }

    // 다이얼로그에서 추가버튼 클릭 됐을 때
    override fun onOkButtonClicked(title: String) {
        val outer = OuterDto(0, title)
        outerViewModel.addOuter(outer)
        Toast.makeText(activity, "카테고리 추가", Toast.LENGTH_SHORT).show()

        val client = OkHttpClient()
        val mediaType = "application/json".toMediaType()
        val body = "{\r\n\t\t\t\t\t  \"title\": \"new\",\r\n\"item\" : []\r\n}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://15.164.232.95:9000/checklist/7863")
            .post(body)
            .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjg5Njk1NTE0fQ.mXTd2f1NwwTKygOxknRJTp-NnAinpE_w1IHAnGTDya-aWQuQDXT_E0a8i1NP4Qd8vRrkmdD9Nie41Mx4ruLb1w")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    // 성공 처리
                    val responseBody = response.body?.string()
                    activity?.runOnUiThread {
                        Log.d("CheckFragment", "체크리스트 생성 성공")
                        Toast.makeText(activity, "체크리스트 생성 성공", Toast.LENGTH_SHORT).show()
                        Log.d("CheckFragment", "Response Body: $responseBody") // API 응답 본문 출력
                    }
                } else {
                    // 실패 처리
                    activity?.runOnUiThread {
                        Log.e("CheckFragment", "체크리스트 생성 실패")
                        Toast.makeText(activity, "체크리스트 생성 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // 네트워크 실패 처리
                activity?.runOnUiThread {
                    Log.e("CheckFragment", "네트워크 오류")
                    Toast.makeText(activity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onUiUpdateSuccess(responseBody: String) {
        activity?.runOnUiThread {
            Log.d("CheckFragment", "체크리스트 삭제 성공")
            Toast.makeText(activity, "체크리스트 삭제 성공", Toast.LENGTH_SHORT).show()
            Log.d("CheckFragment", "Response Body: $responseBody")
        }
    }

    override fun onUiUpdateFailure() {
        activity?.runOnUiThread {
            Log.e("CheckFragment", "체크리스트 삭제 실패")
            Toast.makeText(activity, "체크리스트 삭제 실패", Toast.LENGTH_SHORT).show()
        }
    }
}
