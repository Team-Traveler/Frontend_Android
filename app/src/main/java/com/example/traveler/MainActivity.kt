package com.example.traveler

import android.app.Activity
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  val list = ArrayList<Contents>()
    val REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);

        val view=binding.root
        setContentView(view)

        //추가 시작
        val adapter=MyAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(list)


        // 리사이클러뷰 아이템 클릭 이벤트 처리
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // 아이템을 클릭했을 때 실행될 동작을 여기에 구현
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
               // intent.putExtra("item_data", list[position])
                startActivityForResult(intent, REQUEST_CODE)
            }
        })


        // 플로팅 버튼 클릭시 에니메이션 동작 기능
         var fab=binding.fab
        fab.setOnClickListener {
            // SecondActity 화면으로 이동하게 Intent 사용
            val myIntent = Intent(this, SecondActivity::class.java)

            startActivityForResult(myIntent,REQUEST_CODE)

        }


        }



    // 다음 액티비티로부터 결과를 받기 위한 콜백 메서드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
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




}




