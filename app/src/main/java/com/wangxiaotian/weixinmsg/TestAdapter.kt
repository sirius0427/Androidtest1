package com.wangxiaotian.weixinmsg

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by Administrator on 2017/7/22.
 */
class TestAdapter(context: Context) : BaseAdapter() {

    var context = context

    var items = ArrayList<HashMap<String, Any>>()

    fun setData(items: ArrayList<HashMap<String, Any>>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (p1 == null) {
            view = View.inflate(context, R.layout.listitem_test, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = p1
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.item_test.text = items.get(p0).get("text").toString()
        viewHolder.foot_test.text = items.get(p0).get("title").toString()

        return view
    }

    override fun getItem(p0: Int): Any {
        return items.get(p0).get("text").toString()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    class ViewHolder(var v: View) {
        var item_test: TextView = v.findViewById(R.id.item_test)
        var foot_test: TextView = v.findViewById(R.id.foot_test)
    }

}

