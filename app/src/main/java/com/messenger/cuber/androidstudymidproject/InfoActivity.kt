package com.messenger.cuber.androidstudymidproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.gotoWithInt
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.persons
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.refreshFile
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    //bundle
    internal var extras: Bundle? = null
    //当前人物在人物列表中的位置
    var position: Int = -1

    //回到主页面
    private fun gotoMain() = startActivity(Intent(this@InfoActivity, MainActivity::class.java))

    //初始化监听器
    //含有返回/删除/修改按钮的监听器
    private fun initListeners() {
        //返回主页面
        infoBackButton.setOnClickListener { gotoMain() }

        //删除人物
        infoDeleteButton.setOnClickListener {
            //在内存中删除
            persons.removeAt(position)
            //同步到文件
            refreshFile(filesDir)
            //返回主页面
            gotoMain()
        }

        //修改：去往用于修改或添加人物的页面， 并传递当前人物的position
        infoChangeButton.setOnClickListener { gotoWithInt(this@InfoActivity, AddChangeActivity::class.java, extras!!.getInt("position")) }
    }

    //用于显示出当前人物的各项信息
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

    //初始化
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