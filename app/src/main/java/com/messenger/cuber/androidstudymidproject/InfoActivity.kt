package com.messenger.cuber.androidstudymidproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.gotoWithInt
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.persons
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.refreshFile
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    internal var extras: Bundle? = null
    var position: Int = -1

    private fun gotoMain() = startActivity(Intent(this@InfoActivity, MainActivity::class.java))

    private fun initListeners() {
        infoBackButton.setOnClickListener { gotoMain() }

        //删除元素的操作
        infoDeleteButton.setOnClickListener {
            persons.removeAt(position)
            refreshFile(filesDir)
            gotoMain()
        }

        infoChangeButton.setOnClickListener { gotoWithInt(this@InfoActivity, AddChangeActivity::class.java, extras!!.getInt("position")) }
    }

    private fun setUp() {
        avatarImageView.setImageResource(persons[position].imgPath)
        nameContentTextView.text = persons[position].name
        genderContentTextView.text = persons[position].sex
        bornDeathContentTextView.text = persons[position].bdData
        homeContentTextView.text = persons[position].homeTown
        countryImageView.setImageResource(persons[position].master)
        starImageView.setImageResource(persons[position].star)
        infoContentTextView.text = persons[position].info
    }

    private fun init() {
        extras = intent.extras
        position = extras!!.getInt("position")
        setUp()
        initListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        init()
    }
}