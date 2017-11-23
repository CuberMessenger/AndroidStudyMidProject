package com.messenger.cuber.androidstudymidproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by GUGE on 2017/10/23.
 */

//基本与之前实验的ListView Adapter一致，改成了用于Person的kotlin版本
class MyAdapter(private val context: Context, private val list: MutableList<Person>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size ?: 0
    }

    override fun getItem(i: Int): Any? {
        return if (list == null) null else list[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val convertView: View
        val viewHolder: ViewHolder
        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.person_list_item, null)
            viewHolder = ViewHolder()
            viewHolder.pimg = convertView.findViewById<View>(R.id.person_img) as ImageView
            viewHolder.pname = convertView.findViewById<View>(R.id.person_name) as TextView
            viewHolder.pgender = convertView.findViewById<View>(R.id.person_gender) as TextView
            viewHolder.pbirth = convertView.findViewById<View>(R.id.person_birth) as TextView
            viewHolder.pcountry = convertView.findViewById<View>(R.id.country) as ImageView
            viewHolder.pstar = convertView.findViewById<View>(R.id.person_star) as ImageView
            convertView.tag = viewHolder
        }
        else {
            convertView = view
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.pimg!!.setImageResource(list[i].imgPath)
        viewHolder.pname!!.text = list[i].name
        viewHolder.pgender!!.text = list[i].sex
        viewHolder.pbirth!!.text = list[i].bdData
        viewHolder.pcountry!!.setImageResource(list[i].master)
        viewHolder.pstar!!.setImageResource(list[i].star)

        return convertView
    }

    private inner class ViewHolder {
        var pimg: ImageView? = null;
        var pname: TextView? = null;
        var pgender: TextView? = null;
        var pbirth: TextView? = null;
        var pcountry: ImageView? = null;
        var pstar: ImageView? = null;
    }
}
