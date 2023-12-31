/*
package com.example.traveler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityMytripBinding


class MyTravelActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMytripBinding
    private  val list = ArrayList<Contents>()
    val REQUEST_CODE = 100
    private val SECOND_ACTIVITY_REQUEST_CODE = 2


    val adapter=MyAdapter(list)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMytripBinding.inflate(layoutInflater);

        val view=binding.root
        setContentView(view)


        // 커스텀 액션 바 레이아웃 인플레이트
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)



        //추가 시작
        adapter.setOnItemClickListener(MyTravelFragment())

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter



        // 플로팅 버튼 클릭시 에니메이션 동작 기능
        var fab=binding.fab
        fab.setOnClickListener {
            // SecondActity 화면으로 이동하게 Intent 사용
            val myIntent = Intent(this, MakeActivity::class.java)

            startActivityForResult(myIntent,REQUEST_CODE)

        }


        //[프로필 편집] 클릭
        binding.editProfile.setOnClickListener {
            //editProfile 로 이동
            val myIntent = Intent(this, EditProfile::class.java)
            startActivityForResult(myIntent,SECOND_ACTIVITY_REQUEST_CODE)



        }

    }



    // 다음 액티비티로부터 결과를 받기 위한 콜백 메서드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //생성화면에서 전달받음
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
        else if (requestCode == SECOND_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

                // SecondActivity에서 결과를 받아와서 처리
                val resultValue = data?.getStringExtra("name")
                binding.usrname.text=resultValue
                // SecondActivity에서 받아온 결과 처리

        }


    }

    override fun onItemClick(position: Int) {
        // Handle text click event here
     */
/*   val selectedItem = adapter.getItem(position)
        Toast.makeText(this, "Clicked on: ${selectedItem.name}", Toast.LENGTH_SHORT).show()

        // 예를 들면, 새로운 액티비티로 이동하려면 아래와 같이 코드를 작성할 수 있습니다.
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("selectedItemName", selectedItem.name)
        startActivity(intent)*//*

    }


}
*/
