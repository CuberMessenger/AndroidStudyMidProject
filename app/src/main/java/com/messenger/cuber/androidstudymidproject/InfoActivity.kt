package com.messenger.cuber.androidstudymidproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.persons
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    internal var extras: Bundle? = null

    fun setUp() {
        val position = extras!!.getInt("position")
        avatarImageView.setImageResource(persons[position].imgPath)
        nameContentTextView.text = persons[position].name
        genderContentTextView.text = persons[position].sex
        bornDeathContentTextView.text = persons[position].bdData
        homeContentTextView.text = persons[position].homeTown
        starImageView.setImageResource(persons[position].star)
        infoContentTextView.text = persons[position].info
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        extras = intent.extras
        setUp()
    }
}
