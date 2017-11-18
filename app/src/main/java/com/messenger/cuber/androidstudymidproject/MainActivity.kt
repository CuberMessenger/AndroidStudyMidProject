package com.messenger.cuber.androidstudymidproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val test: Array<String> = arrayOf("张飞", "赵云", "关羽", "张角", "张姓男子", "马云", "马超")

    private fun initListeners() {
        searchButton.setOnClickListener {
            val s: String = searchAutoSuggestTextView.text.toString()
            testTV.text = s + "\t" + test.find { ts -> ts == s }.toString()
        }
    }

    private fun init() {
        initListeners()
        searchAutoSuggestTextView.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, test))
        searchAutoSuggestTextView.threshold=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }


}
