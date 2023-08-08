
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

import com.example.traveler.databinding.FragmentMyTravelBinding

class MyTravelFragment : Fragment(), MyAdapter.OnItemClickListener {


    // 뷰 바인딩 객체를 저장할 멤버 변수
    private var _binding: FragmentMyTravelBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<Contents>()
    val REQUEST_CODE = 100
    val adapter = MyAdapter(list)
    private val SECOND_ACTIVITY_REQUEST_CODE = 2

    private var isEditMode = false



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTravelBinding.inflate(inflater, container, false)
        val view = binding.root




        // 플로팅 버튼 클릭시 에니메이션 동작 기능
        var fab=binding.fab
        fab.setOnClickListener {
            // SecondActity 화면으로 이동하게 Intent 사용
            val myIntent = Intent(activity, MakeActivity::class.java)
            // startActivity(myIntent)
            startActivityForResult(myIntent,REQUEST_CODE)

        }


        //[프로필 편집] 클릭
        binding.editProfile.setOnClickListener {
            //editProfile 로 이동
            val myIntent = Intent(activity, EditProfile::class.java)
            startActivityForResult(myIntent,SECOND_ACTIVITY_REQUEST_CODE)
        }



        //[편집] 클릭
        binding.delete.setOnClickListener {
            //item 크기 변경 및 x 표시 나타남
            isEditMode = true
            updateEditModeState()

        }
        //[편집완료] 클릭
        binding.complete.setOnClickListener {
            //item 크기 변경 및 x 표시 나타남
            isEditMode = false
            updateEditModeState()

        }


        //어댑터 초기화
        adapter.setOnItemClickListener(this)
        // RecyclerView 설정
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter=adapter  //추가

        return view
    }



    private fun updateEditModeState() {
        val recyclerView1 = binding.recyclerView //편집 눌렀을때 (x화면)
        val recyclerView2 = binding.editedView


        if (isEditMode) { // 편집 버튼 클릭
            recyclerView1.visibility = View.VISIBLE
            recyclerView2.visibility = View.GONE
            Log.d("edit", "편집 클릭")
        } else { // 편집 완료 버튼 클릭
            recyclerView1.visibility = View.GONE
            recyclerView2.visibility = View.VISIBLE
            Log.d("complete", "편집 완료 클릭")
        }

        val editButton = binding.delete
        val completeButton = binding.complete
        editButton.isVisible = !isEditMode
        completeButton.isVisible = isEditMode


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 결과 처리
            // 받아온 데이터를 처리하는 로직 작성

            //secondactivity에서 값 받아오기
            val name=data?.getStringExtra("course")
            val day=data?.getStringExtra("day")
            val date=data?.getStringExtra("date")


            Log.d("day","day 는 ${day}")
            Log.d("date","date 는 ${date}")

            list.add(Contents("$name","$day","$date"))  //넘겨받은 값 db에 추가
            adapter?.notifyItemInserted((list.size -1))

        }
        else if (requestCode == SECOND_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {

            // SecondActivity에서 결과를 받아와서 처리
            val resultValue = data?.getStringExtra("name")
            binding.username.text=resultValue
            // SecondActivity에서 받아온 결과 처리

        }
    }
    override fun onItemClick(position: Int) {
        // Handle text click event here
        val selectedItem = adapter.getItem(position)
        Toast.makeText(activity, "Clicked on: ${selectedItem.name}", Toast.LENGTH_SHORT).show()

        // 예를 들면, 새로운 액티비티로 이동하려면 아래와 같이 코드를 작성할 수 있습니다.
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("selectedItemName", selectedItem.name)
        intent.putExtra("selectedItemDate", selectedItem.date)
        intent.putExtra("selectedItemDay", selectedItem.day)

        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰 바인딩 객체 해제
    }







}