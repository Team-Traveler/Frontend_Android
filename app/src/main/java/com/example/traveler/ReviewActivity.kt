package com.example.traveler

import android.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.traveler.databinding.ActivityReviewBinding

class ReviewActivity : Fragment() {
    companion object {
        private const val REQUEST_IMAGE_PICK = 2
    }

    private lateinit var reviewBinding: ActivityReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewBinding = ActivityReviewBinding.inflate(inflater, container, false)
        return reviewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //spinner 관련 항목들
        val memberOptions = arrayOf("친구", "가족", "혼자", "기타")
        val howManyOptions = arrayOf("1명", "2명", "3명", "4명", "5명", "6인이상")
        val intensityOptions = arrayOf("바쁘게", "유명한 곳만", "여유롭게", "휴식")
        val themeOptions = arrayOf("드라이브", "먹방", "액티비티", "인스타 따라잡기", "힐링", "효도", "허니문", "워크샵", "체험")

        val memberAdapter =
            context?.let { ArrayAdapter(it, R.layout.simple_dropdown_item_1line, memberOptions) }
        val howManyAdapter =
            context?.let { ArrayAdapter(it, R.layout.simple_dropdown_item_1line, howManyOptions) }
        val intensityAdapter =
            context?.let { ArrayAdapter(it, R.layout.simple_dropdown_item_1line, intensityOptions) }
        val themeAdapter =
            context?.let { ArrayAdapter(it, R.layout.simple_dropdown_item_1line, themeOptions) }

        val spMember = reviewBinding.spMember
        val spHowmany = reviewBinding.spHowmany
        val spIntensity = reviewBinding.spIntensity
        val spTheme = reviewBinding.spTheme

        spMember.adapter = memberAdapter
        spHowmany.adapter = howManyAdapter
        spIntensity.adapter = intensityAdapter
        spTheme.adapter = themeAdapter

        //글쓰기 버튼 눌렸을 때
        val btnDone = reviewBinding.btnDone
        btnDone.setOnClickListener {
            val tripName = reviewBinding.etTripName.text.toString() //여행이름 변수
            val hashtagText = reviewBinding.etHashtag.text.toString() //해시태그 변수
            val goodText = reviewBinding.etGood.text.toString() //좋았던 점 변수
            val shameText = reviewBinding.etShame.text.toString() //아쉬웠던 점 변수
            val impact = reviewBinding.etOneline.text.toString() //impact 한줄평 변수
            val rating = reviewBinding.rtbScore.rating //총 여행 별점 변수
            val rtbFood = reviewBinding.rtbFood.rating //총 음식 별점 변수
            val rtbActivity = reviewBinding.rtbActivity.rating //총 액티비티 별점 변수
            ///ggvgvv
            val selectedMember = spMember.selectedItem.toString() //누구랑 변수
            val selectedHowmany = spHowmany.selectedItem.toString() //몇명 변수
            val selectedIntensity = spIntensity.selectedItem.toString() //여행강도 변수
            val selectedTheme = spTheme.selectedItem.toString() //여행테마 변수

            val detailIntent = Intent(requireActivity(), DetailActivity::class.java)

            detailIntent.putExtra("tripName", tripName) //입력한 여행이름 넣어줌
            detailIntent.putExtra("hashtag", hashtagText) //입력한 해시태그 넣어줌
            detailIntent.putExtra("goodText", goodText) //입력한 좋았던점 넣어줌
            detailIntent.putExtra("shameText", shameText) //입력한 아쉬웠던점 넣어줌
            detailIntent.putExtra("impact", impact) //입력한 한 줄 impact 넣어줌
            detailIntent.putExtra("rating", rating) //입력한 총여행별점 넣어줌
            detailIntent.putExtra("rtbFood", rtbFood) //입력한 음식별점 넣어줌
            detailIntent.putExtra("rtbActivity", rtbActivity) //입력한 액티비티별점 넣어줌
            detailIntent.putExtra("selectedMember", selectedMember) //누구랑 보내줌
            detailIntent.putExtra("selectedHowmany", selectedHowmany) //몇명 보내줌
            detailIntent.putExtra("selectedIntensity", selectedIntensity) //여행강도 보내줌
            detailIntent.putExtra("selectedTheme", selectedTheme) //여행테마 보내줌

            startActivity(detailIntent)
        }

        //저장버튼 눌렸을 때
        val btnSave = reviewBinding.btnSave
        btnSave.setOnClickListener {
            val tListIntent = Intent(requireActivity(), TriplistActivity::class.java)

            //여행 이름 보내주기
            val etname = reviewBinding.etTripName.text.toString()
            val etDayNight = reviewBinding.etDayNight.text.toString()
            val etDate = reviewBinding.etDate.text.toString()

            tListIntent.putExtra("etname", etname)
            tListIntent.putExtra("etDayNight", etDayNight)
            tListIntent.putExtra("etDate", etDate)

            //여행 일정 보내주기
            startActivity(tListIntent)
        }

        //앨범 들어가기
        reviewBinding.imgPhoto.setOnClickListener {
            // Open the gallery to select an image
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PICK) {
            val selectedImageUri: Uri? = data?.data
            reviewBinding.imgPhoto.setImageURI(selectedImageUri)
        }
    }
}
