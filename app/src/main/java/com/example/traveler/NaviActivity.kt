package com.example.traveler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.traveler.databinding.ActivityNaviBinding

private const val TAG_RECOMM = "recomm_fragment"
private const val TAG_STORY = "story_fragment"
private const val TAG_NOTE = "note_fragment"
private const val TAG_MY_TRAVEL = "my_page_fragment"

class NaviActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_RECOMM, RecommendFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.airplane -> setFragment(TAG_RECOMM, RecommendFragment())
                R.id.story -> setFragment(TAG_STORY, StoryFragment())
                R.id.notepad-> setFragment(TAG_NOTE, NoteFragment())
                R.id.my_travel-> setFragment(TAG_MY_TRAVEL, MyTravelFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()



        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val airplane = manager.findFragmentByTag(TAG_RECOMM)
        val story = manager.findFragmentByTag(TAG_STORY)
        val notepad = manager.findFragmentByTag(TAG_NOTE)
        val my_travel = manager.findFragmentByTag(TAG_MY_TRAVEL)

        if (airplane != null){
            fragTransaction.hide(airplane)
        }
        if (story != null){
            fragTransaction.hide(story)
        }
        if (notepad != null) {
            fragTransaction.hide(notepad)
        }
        if (my_travel != null) {
            fragTransaction.hide(my_travel)
        }
        if (tag == TAG_RECOMM) {
            if (airplane!=null){
                fragTransaction.show(airplane)
            }
        }
        else if (tag == TAG_STORY) {
            if (story != null) {
                fragTransaction.show(story)
            }
        }
        else if (tag == TAG_NOTE){
            if (notepad != null){
                fragTransaction.show(notepad)
            }
        }
        else if (tag == TAG_MY_TRAVEL){
            if (my_travel != null){
                fragTransaction.show(my_travel)
            }
        }
        fragTransaction.commitAllowingStateLoss()
    }
}