package com.messenger.cuber.androidstudymidproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by GUGE on 2017/11/20.
 */

//图片下拉框的Adapter，以便在spinner中显示图片
class SpinnerAdapter(private val context: Context, private val list: MutableList<Int>, val layout_size: Int) : BaseAdapter() {
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
            convertView = LayoutInflater.from(context).inflate(layout_size, null)
            viewHolder = ViewHolder()
            viewHolder.pimg = convertView.findViewById<View>(R.id.simg) as ImageView
            convertView.tag = viewHolder
        }
        else {
            convertView = view
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.pimg!!.setImageResource(list[i])
        return convertView
    }

    private inner class ViewHolder {
        var pimg: ImageView? = null;
    }
}
