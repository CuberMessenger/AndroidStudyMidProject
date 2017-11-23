package com.messenger.cuber.androidstudymidproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.persons
import android.widget.ArrayAdapter
import android.widget.Toast
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.refreshFile
import kotlinx.android.synthetic.main.activity_add_change.*

class AddChangeActivity : AppCompatActivity() {
    //bundle
    internal var extras: Bundle? = null
    //由修改按钮切换来时，原来的人物在列表中的位置
    //增加按钮切换来时此值为-1
    var position: Int = -1
    //可选头像的路径
    val avatarPaths = arrayListOf(R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7, R.drawable.r8, R.drawable.r9, R.drawable.r10, R.drawable.r11, R.drawable.r12, R.drawable.r13, R.drawable.r14)
    val genderSpinnerValues = arrayListOf(" ", "男", "女")
    val starSpinnerValues = arrayListOf(" ", "1", "2", "3", "4", "5")
    //星级图片的路径
    val starPaths = arrayListOf(R.drawable.one_star, R.drawable.two_star, R.drawable.three_star, R.drawable.four_star, R.drawable.five_star)
    //国家图片的路径
    val countryPaths = arrayListOf(R.drawable.wei, R.drawable.shu, R.drawable.wu)

    //返回主页面
    private fun gotoMain() = startActivity(Intent(this@AddChangeActivity, MainActivity::class.java))

    //初始化下拉列表
    //修改/增加页面的头像/性别/星级/国家都使用下拉列表选择
    private fun spinner_init() {
        //若是从修改按钮来的，应该可以选择该人物本身的头像
        //所以获取其头像并加入到下拉列表的第一个
        val avatars: MutableList<Int> = ArrayList<Int>()
        if (position != -1) avatars.add(persons[position].imgPath)
        avatars.addAll(avatarPaths)

        //设置头像下拉列表的adapter，layout内容为一个200x200dp的ImageView
        avatarSpinner.adapter = SpinnerAdapter(this, avatars, R.layout.pic_spinner_item)
        //设置性别下拉列表
        genderSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderSpinnerValues)
        //设置星级下拉列表
        starSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, starSpinnerValues)
        //设置国家下拉列表
        countrySpinner.adapter = SpinnerAdapter(this, countryPaths, R.layout.small_pic_spinner_item)

        //下面的内容是为了修改时，原本人物的星级/性别等属性会默认选中原来的值
        //张飞->修改->性别默认为男，国家默认为蜀...
        //所以若是增加人物，则不同以下部分，直接return
        if (position == -1) return
        genderSpinner.setSelection(genderSpinnerValues.indexOf(persons[position].sex))
        starSpinner.setSelection(starPaths.indexOf(persons[position].star) + 1)
        countrySpinner.setSelection(countryPaths.indexOf(persons[position].master))
    }

    //初始化监听器
    //含有返回按钮和保存按钮
    private fun initListeners() {
        //返回按钮直接返回主界面
        addChangeBackButton.setOnClickListener { gotoMain() }

        //保存按钮需要将页面上输入的信息读取，并添加/修改人物
        //最后写回文件保存并返回主界面
        addChangeSaveButton.setOnClickListener {
            //增加人物：直接把读到的信息拿来创建一个新的Person对象并加入到人物列表里
            if (position == -1)
                persons.add(Person(nameEdit.text.toString(), genderSpinnerValues[genderSpinner.selectedItemPosition], bornDeathEdit.text.toString(), homeEdit.text.toString(), infoEdit.text.toString(), countryPaths[countrySpinner.selectedItemPosition], starPaths[starSpinner.selectedItemPosition], avatarPaths[avatarSpinner.selectedItemPosition]))
            else {//修改人物：将读到的信息分别赋给当前人物，改变其原始值
                persons[position].name = nameEdit.text.toString()
                persons[position].sex = genderSpinnerValues[genderSpinner.selectedItemPosition]
                persons[position].bdData = bornDeathEdit.text.toString()
                persons[position].homeTown = homeEdit.text.toString()
                persons[position].info = infoEdit.text.toString()
                //由于这里只能访问到只有各种普适头像的数组
                //所以若选择的默认头像(position==0)，那就不修改
                if (avatarSpinner.selectedItemPosition != 0) persons[position].imgPath = avatarPaths[avatarSpinner.selectedItemPosition]
                persons[position].master = countryPaths[countrySpinner.selectedItemPosition]
                persons[position].star = starPaths[starSpinner.selectedItemPosition - 1]
            }
            //将更新后的人物列表写回文件
            refreshFile(filesDir)
            //提示
            Toast.makeText(this, "保存成功！", Toast.LENGTH_LONG).show()
            //返回主界面
            gotoMain()
        }
    }

    //设置文本
    //若当前为修改，要将当前人物原来的各种文字信息填到编辑框中
    private fun setUp() {
        nameEdit.setText(persons[position].name)
        bornDeathEdit.setText(persons[position].bdData)
        homeEdit.setText(persons[position].homeTown)
        infoEdit.setText(persons[position].info)
    }

    //初始化
    //需要从来源Activity获取一个position
    //若为-1，代表增加人物
    //若为0~n，表示修改第position个人物
    private fun init() {
        extras = intent.extras
        position = extras!!.getInt("position")
        if (position != -1) setUp()
        initListeners()
        spinner_init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_change)

        init()
    }
}
