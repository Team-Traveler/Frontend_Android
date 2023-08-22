package com.example.traveler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.databinding.ActivityAllcontentsBinding

class AllcontentsActivity : AppCompatActivity() {
    lateinit var contentsBinding: ActivityAllcontentsBinding
    private var recmdCnt = 0
    private var likeCnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentsBinding = ActivityAllcontentsBinding.inflate(layoutInflater)
        setContentView(contentsBinding.root)

        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val projection = arrayOf("tripName", "hashtag", "good", "shame", "oneline", "rtbScore", "rtbFood", "rtbActivity",
            "selectedMember", "selectedHowmany", "selectedIntensity", "selectedTheme")
        val selection = "id = ?"
        val selectionArgs = arrayOf("1")

        val cursor = db.query(
            DBHelper.TABLE_TRIP_NAMES,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val tripName = cursor.getString(cursor.getColumnIndexOrThrow("tripName"))
            val hashtag = cursor.getString(cursor.getColumnIndexOrThrow("hashtag"))
            val good = cursor.getString(cursor.getColumnIndexOrThrow("good"))
            val shame = cursor.getString(cursor.getColumnIndexOrThrow("shame"))
            val oneline = cursor.getString(cursor.getColumnIndexOrThrow("oneline"))
            val rtbScore = cursor.getFloat(cursor.getColumnIndexOrThrow("rtbScore"))
            val rtbFood = cursor.getFloat(cursor.getColumnIndexOrThrow("rtbFood"))
            val rtbActivity = cursor.getFloat(cursor.getColumnIndexOrThrow("rtbActivity"))
            val selectedMember = cursor.getString(cursor.getColumnIndexOrThrow("selectedMember"))
            val selectedHowmany = cursor.getString(cursor.getColumnIndexOrThrow("selectedHowmany"))
            val selectedIntensity = cursor.getString(cursor.getColumnIndexOrThrow("selectedIntensity"))
            val selectedTheme = cursor.getString(cursor.getColumnIndexOrThrow("selectedTheme"))

            contentsBinding.tvTripName.text = tripName
            contentsBinding.tvHashtag.text = hashtag
            contentsBinding.tvGood.text = good
            contentsBinding.tvShame.text = shame
            contentsBinding.tvOneline.text = oneline
            contentsBinding.rtbTotallStars.rating = rtbScore
            contentsBinding.rtbAfterFood.rating = rtbFood
            contentsBinding.rtbAfterActivity.rating = rtbActivity
            contentsBinding.tvMemberHowmany.text = "$selectedMember, $selectedHowmany"
            contentsBinding.tvIntensity.text = selectedIntensity
            contentsBinding.tvTheme.text = selectedTheme

        }

        cursor.close()
        dbHelper.close()

        //아이콘 누르면 숫자 올라가는거, 따봉
        val imgThumb = contentsBinding.imgThumb
        val tvRecmd = contentsBinding.tvRecmd

        //따봉
        imgThumb.setOnClickListener {
            recmdCnt++
            tvRecmd.text = "$recmdCnt"
        }

        //아이콘 누르면 숫자 올라가는거, 하트
        val imgLike = contentsBinding.imgLike
        val tvLike = contentsBinding.tvLike

        //하트
        imgLike.setOnClickListener {
            likeCnt++
            tvLike.text = "$likeCnt"
        }

        //댓글 기능 할 때 필요한 변수들
        val etComment = contentsBinding.etComment
        val btnSubmitComment = contentsBinding.btnSubmitComment
        val tvCommentDisplay = contentsBinding.tvCommentDisplay

        //댓글 다는 함수
        var commentCount = 1
        btnSubmitComment.setOnClickListener {
            val newComment = etComment.text.toString()
            if (newComment.isNotEmpty()) {
                val existingComments = tvCommentDisplay.text.toString()
                val updatedComments = "$existingComments\n댓글$commentCount: $newComment"
                tvCommentDisplay.text = updatedComments

                etComment.text.clear()
                commentCount++
            }
        }
    }
}