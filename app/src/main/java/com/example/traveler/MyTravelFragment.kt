package com.example.traveler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityMytripBinding

class MyTravelFragment : Fragment(), MyAdapter.OnItemClickListener {

    private val list = ArrayList<Contents>()
    val REQUEST_CODE = 100
    val adapter = MyAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private lateinit var _binding: ActivityMytripBinding

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMytripBinding.inflate(inflater, container, false)

        adapter.setOnItemClickListener(MyTravelActivity())

        binding.recyclerView.layoutManager = LinearLayoutManager(MyTravelActivity())
        binding.recyclerView.adapter = adapter

        // 플로팅 버튼 클릭시 에니메이션 동작 기능
        var fab=binding.fab
        fab.setOnClickListener {
            // SecondActity 화면으로 이동하게 Intent 사용
            val myIntent = Intent(activity, MakeActivity::class.java)
            startActivity(myIntent)
//            startActivityForResult(myIntent,REQUEST_CODE)

        }


        //[프로필 편집] 클릭
        binding.editProfile.setOnClickListener {
            //editProfile 로 이동
            val myIntent = Intent(activity, EditProfile::class.java)
            startActivity(myIntent)


        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // 결과 처리
            // 받아온 데이터를 처리하는 로직 작성

            //secondactivity에서 값 받아오기
            val name=data?.getStringExtra("course")
            val day=data?.getStringExtra("day")
            val date=data?.getStringExtra("date")


            Log.d("day","day 는 ${day}")
            Log.d("date","date 는 ${date}")

            list.add(Contents("$name","$day","$date"))  //넘겨받은 값 db에 추가
            binding.recyclerView.adapter?.notifyItemInserted((list.size -1))

        }
    }
    override fun onItemClick(position: Int) {
    }

}




