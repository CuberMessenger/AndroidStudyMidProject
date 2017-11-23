package com.messenger.cuber.androidstudymidproject

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        //人物列表，用来保存人物的各种信息
        var persons: MutableList<Person> = ArrayList<Person>()
        //人物的名字列表，用于自动补全输入框
        val names: MutableList<String> = ArrayList<String>()
        //用于判断是不是第一次启动主页面
        //也可以理解为人物列表有没有经过初始化
        //若没有，安装后第一次启动会创建一个有10个人物的文件
        //再从这个文件读入到人物列表，否则直接从文件读入
        var first_open: Boolean = true

        //刷新文件
        //用于将sd卡中的文件更新，将当前的人物列表的值写入文件
        //只在人物列表比文件新时使用
        fun refreshFile(filesDir: File) {
            //将原来的文件删掉
            File(filesDir, "person_data").delete()
            var originData = ""
            //组成一个长字符串
            for (p in MainActivity.persons)
                originData += p.name + "," + p.sex + "," + p.bdData + "," + p.homeTown + "," + p.info + "," + p.master.toString() + "," + p.star.toString() + "," + p.imgPath.toString() + "\n"
            //将更新后的数据写入文件
            File(filesDir, "person_data").writeText(originData)
        }

        //跳转到某个页面，同时带一个int型的extra
        fun gotoWithInt(context: Context, destination: Class<*>, position: Int) {
            val intentToInfo = Intent(context, destination)
            intentToInfo.putExtra("position", position)
            context.startActivity(intentToInfo)
        }
    }


    //初始化监听器
    //含有转到/添加/某个人物的点击监听器
    private fun initListeners() {
        //转到(搜索)按钮
        //用于转到输入的人物姓名对应的详情页
        searchButton.setOnClickListener {
            //将之前可能存在的hint清空
            searchAutoSuggestTextView.hint = ""
            //获取当前输入的内容
            val searchText: String = searchAutoSuggestTextView.text.toString()
            //若存在这个人物，转到对应页面
            if (names.contains(searchText))
                gotoWithInt(this@MainActivity, InfoActivity::class.java, names.indexOfFirst { name -> name == searchText })
            else {//否则更新hint
                searchAutoSuggestTextView.setText("")
                searchAutoSuggestTextView.hint = getString(R.string.suggest)
            }
        }

        //添加按钮
        //转到用于修改或添加人物的页面，并标记为添加(position=-1)
        addButton.setOnClickListener { gotoWithInt(this@MainActivity, AddChangeActivity::class.java, -1) }

        //人物列表点击监听器
        //转到对应人物的详情页
        person_list.setOnItemClickListener { parent, view, position, id -> gotoWithInt(this@MainActivity, InfoActivity::class.java, position) }
    }

    //删除sd卡上的文件//测试用
    private fun clearData() {
        if (filesDir.list().contains("person_data")) {
            File(filesDir, "person_data").delete()
        }
    }

    //刷新数据
    //第一次启动主页面时(包括但不限于安装后第一次启动程序)
    //将之前保存的文件里的数据读到人物列表中
    //若没有这个文件，将默认的十个人的数据写入文件并递归调用
    private fun refreshData() {
        //如果有文件的话，把文件内容全部读取到persons列表中
        if (filesDir.list().contains("person_data")) {
            File(filesDir, "person_data").readLines().forEach {
                val rd = it.split(',')
                //初始化人物列表和姓名列表
                persons.add(Person(rd[0], rd[1], rd[2], rd[3], "        " + rd[4], rd[5].toInt(), rd[6].toInt(), rd[7].toInt()))
                names.add(rd[0])
            }
        }
        //如果没有文件，也就是安装后第一次打开应用(或之前删除了文件)，就自己创建一个带有初始信息的文件
        else {
            val file = File(filesDir, "person_data")
            var originData = ""
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
            //递归调用，进入到上面存在文件的部分
            refreshData()
        }
    }

    //初始化
    //主要用于初始化监听器和设置adapter以及给人物列表读入数据
    private fun init() {
        initListeners()
        searchAutoSuggestTextView.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names))
        //因为很多中文名都是两个字的，所以设定输入一个字就开始提示
        searchAutoSuggestTextView.threshold = 1

        if (first_open) {
            first_open = false
            //clearData()//测试用
            refreshData()
        }

        //ListView的Adapter
        person_list.adapter = PersonListAdapter(this, persons)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }
}