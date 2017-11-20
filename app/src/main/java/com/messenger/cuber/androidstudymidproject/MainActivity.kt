package com.messenger.cuber.androidstudymidproject

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        var persons: MutableList<Person> = ArrayList<Person>()
        val names: MutableList<String> = ArrayList<String>()
        var first_open: Boolean = true

        fun refreshFile(filesDir: File) {
            File(filesDir, "person_data").delete()
            var originData: String = ""
            for (p in MainActivity.persons)
                originData += p.name + "," + p.sex + "," + p.bdData + "," + p.homeTown + "," + p.info + "," + p.master.toString() + "," + p.star.toString() + "," + p.imgPath.toString() + "\n"
            File(filesDir, "person_data").writeText(originData)
        }

        fun gotoWithInt(context: Context, destination: Class<*>, position: Int) {
            val intentToInfo: Intent = Intent(context, destination)
            intentToInfo.putExtra("position", position)
            context.startActivity(intentToInfo)
        }
    }


    private fun initListeners() {
        searchButton.setOnClickListener {
            searchAutoSuggestTextView.hint = ""
            val searchText: String = searchAutoSuggestTextView.text.toString()
            if (names.contains(searchText))
                gotoWithInt(this@MainActivity, InfoActivity::class.java, names.indexOfFirst { name -> name == searchText })
            else {
                searchAutoSuggestTextView.setText("")
                searchAutoSuggestTextView.hint = getString(R.string.suggest)
            }
        }

        addButton.setOnClickListener { gotoWithInt(this@MainActivity, AddChangeActivity::class.java, -1) }

        //发送广播
        person_list.setOnItemClickListener { parent, view, position, id -> gotoWithInt(this@MainActivity, InfoActivity::class.java, position) }
    }

    private fun clearData() {
        if (filesDir.list().contains("person_data")) {
            File(filesDir, "person_data").delete()
        }
    }

    private fun refreshData() {
        //如果有文件的话，把文件内容全部读取到persons列表中
        if (filesDir.list().contains("person_data")) {
            File(filesDir, "person_data").readLines().forEach {
                val rd = it.split(',')
                persons.add(Person(rd[0], rd[1], rd[2], rd[3], "        " + rd[4], rd[5].toInt(), rd[6].toInt(), rd[7].toInt()))
                names.add(rd[0])
            }
        }
        //如果没有文件，也就是第一次打开应用，就自己创建一个带有初始信息的文件
        else {
            val file = File(filesDir, "person_data")
            var originData: String = ""
            originData += getString(R.string.caocao_info) + R.drawable.wei.toString() + "," + R.drawable.five_star.toString() + "," + R.drawable.caocao.toString() + "\n"
            originData += getString(R.string.guanyu_info) + R.drawable.shu.toString() + "," + R.drawable.five_star.toString() + "," + R.drawable.guanyu.toString() + "\n"
            originData += getString(R.string.liubei_info) + R.drawable.shu.toString() + "," + R.drawable.four_star.toString() + "," + R.drawable.liubei.toString() + "\n"
            originData += getString(R.string.simayi_info) + R.drawable.wei.toString() + "," + R.drawable.four_star.toString() + "," + R.drawable.simayi.toString() + "\n"
            originData += getString(R.string.sunce_info) + R.drawable.wu.toString() + "," + R.drawable.one_star.toString() + "," + R.drawable.sunce.toString() + "\n"
            originData += getString(R.string.sunquan_info) + R.drawable.wu.toString() + "," + R.drawable.four_star.toString() + "," + R.drawable.sunquan.toString() + "\n"
            originData += getString(R.string.xunyu_info) + R.drawable.wei.toString() + "," + R.drawable.two_star.toString() + "," + R.drawable.xunyu.toString() + "\n"
            originData += getString(R.string.zhangfei_info) + R.drawable.shu.toString() + "," + R.drawable.five_star.toString() + "," + R.drawable.zhangfei.toString() + "\n"
            originData += getString(R.string.zhouyu_info) + R.drawable.wu.toString() + "," + R.drawable.three_star.toString() + "," + R.drawable.zhouyu.toString() + "\n"
            originData += getString(R.string.zhugeliang_info) + R.drawable.shu.toString() + "," + R.drawable.four_star.toString() + "," + R.drawable.zhugeliang.toString() + "\n"
            println(originData)
            file.writeText(originData)
            refreshData()
        }
    }

    private fun init() {
        initListeners()
        searchAutoSuggestTextView.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names))
        searchAutoSuggestTextView.threshold = 1

        if (first_open) {
            first_open = false
            clearData()
            refreshData()
        }

        val listView = findViewById<View>(R.id.person_list) as ListView
        val myAdapter = MyAdapter(this, persons)
        listView.adapter = myAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }
}