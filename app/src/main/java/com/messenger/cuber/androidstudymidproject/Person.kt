package com.messenger.cuber.androidstudymidproject

/**
 * Created by cuber on 2017/11/18.
 */

//人物类的声明，由于涉及到修改，所以都使用var
//一个人物的信息包括：姓名/性别/生卒/籍贯/国家/星级/头像，最后三个为图片的id
//声明为data类，所以自带get/set函数
data class Person(var name: String, var sex: String, var bdData: String, var homeTown: String, var info: String, var master: Int, var star: Int, var imgPath: Int)