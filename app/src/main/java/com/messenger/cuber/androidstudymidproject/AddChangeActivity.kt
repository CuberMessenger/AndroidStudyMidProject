package com.messenger.cuber.androidstudymidproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.persons
import android.widget.ArrayAdapter
import android.widget.Toast
import com.messenger.cuber.androidstudymidproject.MainActivity.Companion.refreshFile
import kotlinx.android.synthetic.main.activity_add_change.*

class AddChangeActivity : AppCompatActivity() {
    internal var extras: Bundle? = null
    var position: Int = -1
    val avatarPaths = arrayListOf(R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7, R.drawable.r8, R.drawable.r9, R.drawable.r10, R.drawable.r11, R.drawable.r12, R.drawable.r13, R.drawable.r14)
    val genderSpinnerValues = arrayListOf(" ", "男", "女")
    val starSpinnerValues = arrayListOf(" ", "1", "2", "3", "4", "5")
    val starPaths = arrayListOf(R.drawable.one_star, R.drawable.two_star, R.drawable.three_star, R.drawable.four_star, R.drawable.five_star)
    val countryPaths = arrayListOf(R.drawable.wei, R.drawable.shu, R.drawable.wu)

    private fun gotoMain() = startActivity(Intent(this@AddChangeActivity, MainActivity::class.java))

    private fun spinner_init() {
        val avatars: MutableList<Int> = ArrayList<Int>()
        if (position != -1) avatars.add(persons[position].imgPath)
        avatars.addAll(avatarPaths)

        avatarSpinner.adapter = SpinnerAdapter(this, avatars, R.layout.pic_spinner_item)
        genderSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderSpinnerValues)
        starSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, starSpinnerValues)
        countrySpinner.adapter = SpinnerAdapter(this, countryPaths, R.layout.small_pic_spinner_item)
        if (position == -1) return

        genderSpinner.setSelection(genderSpinnerValues.indexOf(persons[position].sex))
        starSpinner.setSelection(starPaths.indexOf(persons[position].star) + 1)
        countrySpinner.setSelection(countryPaths.indexOf(persons[position].master))
    }

    private fun initListeners() {
        addChangeBackButton.setOnClickListener { gotoMain() }

        addChangeSaveButton.setOnClickListener {
            if (position == -1) {
                persons.add(Person(nameEdit.text.toString(), genderSpinnerValues[genderSpinner.selectedItemPosition], bornDeathEdit.text.toString(), homeEdit.text.toString(), infoEdit.text.toString(), countryPaths[countrySpinner.selectedItemPosition], starPaths[starSpinner.selectedItemPosition], avatarPaths[avatarSpinner.selectedItemPosition]))
            }
            else {
                persons[position].name = nameEdit.text.toString()
                persons[position].sex = genderSpinnerValues[genderSpinner.selectedItemPosition]
                persons[position].bdData = bornDeathEdit.text.toString()
                persons[position].homeTown = homeEdit.text.toString()
                persons[position].info = infoEdit.text.toString()
                if (avatarSpinner.selectedItemPosition != 0) persons[position].imgPath = avatarPaths[avatarSpinner.selectedItemPosition]
                persons[position].master = countryPaths[countrySpinner.selectedItemPosition]
                persons[position].star = starPaths[starSpinner.selectedItemPosition - 1]
            }
            refreshFile(filesDir)
            Toast.makeText(this, "保存成功！", Toast.LENGTH_LONG).show()
            gotoMain()
        }
    }

    private fun setUp() {
        nameEdit.setText(persons[position].name)
        bornDeathEdit.setText(persons[position].bdData)
        homeEdit.setText(persons[position].homeTown)
        infoEdit.setText(persons[position].info)
    }

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
