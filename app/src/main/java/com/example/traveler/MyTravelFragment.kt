//데이터 넘어가고, 편집 완료 텍스트변경 but 리사이클러 뷰 그대로
package com.example.traveler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.traveler.databinding.FragmentMyTravelBinding

class MyTravelFragment : Fragment(), MyAdapter.OnItemClickListener, MyAdapter2.OnItemClickListener {


    // 뷰 바인딩 객체를 저장할 멤버 변수
    private var _binding: FragmentMyTravelBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<Contents>()
    val REQUEST_CODE = 100
    private val SECOND_ACTIVITY_REQUEST_CODE = 2

    private var isEditMode = false


    val adapter = MyAdapter(list)
    val adapter2 = MyAdapter2(list)

    private var recyclerView1: RecyclerView? = null
    private var recyclerView2: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTravelBinding.inflate(inflater, container, false)
        val view = binding.root
        //
        recyclerView1 = binding.recyclerView
        recyclerView2 = binding.editedView


        // 플로팅 버튼 클릭시 에니메이션 동작 기능
        var fab = binding.fab
        fab.setOnClickListener {
            // SecondActity 화면으로 이동하게 Intent 사용
            val myIntent = Intent(activity, MakeActivity::class.java)
            // startActivity(myIntent)
            startActivityForResult(myIntent, REQUEST_CODE)

        }


        //[프로필 편집] 클릭
        binding.editProfile.setOnClickListener {
            //editProfile 로 이동
            val myIntent = Intent(activity, EditProfile::class.java)
            startActivityForResult(myIntent, SECOND_ACTIVITY_REQUEST_CODE)
        }


        //[편집] 클릭
        binding.delete.setOnClickListener {
            //item 크기 변경 및 x 표시 나타남
            isEditMode = true
            recyclerView1?.visibility = View.GONE //false 일땐 원래 목록
            recyclerView2?.visibility = View.VISIBLE

            val editButton = binding.delete
            val completeButton = binding.complete
            editButton.isVisible = !isEditMode
            completeButton.isVisible = isEditMode

            // 편집 모드로 전환할 때 어댑터 및 레이아웃 매니저 설정
            recyclerView2?.layoutManager = LinearLayoutManager(requireContext())
            recyclerView2?.adapter = adapter2
            adapter2.setOnItemClickListener(this)

        }

        //[편집완료] 클릭
        binding.complete.setOnClickListener {
            //원래 화면으로 돌아와야함
            isEditMode = false
            recyclerView2?.visibility = View.GONE   //원래 화면 뜨기
            recyclerView1?.visibility = View.VISIBLE //

            val editButton = binding.delete
            val completeButton = binding.complete
            editButton.isVisible = !isEditMode
            completeButton.isVisible = isEditMode

            // 편집 완료 모드로 전환할 때 어댑터 및 레이아웃 매니저 설정
            recyclerView1?.layoutManager = LinearLayoutManager(requireContext())
            recyclerView1?.adapter = adapter
            adapter.setOnItemClickListener(this)

        }


        //[편집] 클릭 전,
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val editedLayoutManager = LinearLayoutManager(requireContext())

        if (isEditMode) {
            //[편집 ] 클릭 후, 화면에 [편집완료] - x 표시 나타남
            recyclerView2?.layoutManager = editedLayoutManager
            recyclerView2?.adapter = adapter2
            adapter2.setOnItemClickListener(this)


        } else {
            //[편집 완료]클릭 전, [편집] - 원래 목록 나타남
            recyclerView1?.layoutManager = linearLayoutManager
            recyclerView1?.adapter = adapter
            adapter.setOnItemClickListener(this)

        }




        return view
    }    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰 바인딩 객체 해제
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 결과 처리
            // 받아온 데이터를 처리하는 로직 작성

            //secondactivity에서 값 받아오기
            val name = data?.getStringExtra("course")
            val day = data?.getStringExtra("day")
            val date = data?.getStringExtra("date")


            Log.d("day", "day 는 ${day}")
            Log.d("date", "date 는 ${date}")

            list.add(Contents("$name", "$day", "$date"))  //넘겨받은 값 db에 추가
            /*adapter?.notifyItemInserted((list.size -1))
            adapter2?.notifyItemInserted((list.size -1))*/
            adapter.notifyDataSetChanged() // 어댑터에게 데이터 변경을 알려 업데이트
            adapter2.notifyDataSetChanged() // adapter2도 마찬가지로 업데이트
        } else if (requestCode == SECOND_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {

            // SecondActivity에서 결과를 받아와서 처리
            val resultValue = data?.getStringExtra("name")
            binding.username.text = resultValue
            // SecondActivity에서 받아온 결과 처리

        }
    }

    override fun onItemClick(position: Int) {
        // Handle text click event here
        val selectedItem = adapter.getItem(position)
        // 버튼마다 다른 동작을 수행하도록 구분
        if (isEditMode) {
            // [편집모드]일 때 [상세보기] 버튼 클릭 시 동작
            //deleteItem(position)
         } else {
            // [일반모드]일 때 [상세보기] 버튼 클릭 시 동작
            showDetailActivity(selectedItem)
        }
    }

    // 상세보기 액티비티로 이동하는 함수
    private fun showDetailActivity(selectedItem: Contents) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("selectedItemName", selectedItem.name)
        intent.putExtra("selectedItemDate", selectedItem.date)
        intent.putExtra("selectedItemDay", selectedItem.day)
        startActivity(intent)
    }




}





