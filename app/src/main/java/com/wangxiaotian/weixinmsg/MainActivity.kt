package com.wangxiaotian.weixinmsg

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TestAdapter
    var items = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TestAdapter(this)
        listview_dynamic.adapter = adapter

        val map = HashMap<String, Any>()
        map.put("text", "☎【平安医药叶寅团队】正海生物（300653）2017年报电话会邀请！   公司是生物再生材料领域稀缺标的，一季度业绩大幅超预期（+160%~+190%），我们持续重点覆盖和跟踪，后续空间巨大，建议积极关注！   会议时间：2018年4月2日（周一）上午10:00 出席领导：董秘 陆总 接入号码：025-68673555 会议室号：9880 参会密码：7496 [玫瑰]欢迎咨询 叶寅15618967386、韩盟盟18521517741 [玫瑰]平安医药团队：叶寅、魏巍、倪亦道、张熙、韩盟盟" )
        map.put("title","韩盟盟@平安医药叶寅团队 2018/4/1 19:39:31")
        items.add(map)

        val map1 = HashMap<String, Any>()
        map1.put("text", "【浙商家电】家电行业周报（3.26-4.1）：2月黑电出口实现高速增长_本周板块表现本周沪深300指数下跌0.16%，家电指数下跌1.24%。从各行业本周涨跌幅来看，家电板块位列中信29个一级行业涨幅榜的第25位；从家电和其他行业PE(TTM)对比来看，家电行业PE(TTM)为21.91倍，位列中信29个一级行业排行榜的第21位，处于较低水平。从家电细分板块来看，本周照\n" +
                "http://note.youdao.com/noteshare?id=5468225c9fa208360e2a54b4c78fd3f9" )
        map1.put("title","陈曦 2018/4/1 19:39:00")
        items.add(map1)

        for (i in 1..15) {
            val map = HashMap<String, Any>()
            map.put("text", "test_" + i )
            map.put("title","texttitle"+i)
            items.add(map)
        }

        adapter.setData(items)

        //setSupportActionBar(toolbar)

        fab_fresh.setOnClickListener { view ->
            Snackbar.make(view, getResources().getString (R.string.str_freshing), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            toast("点了一下下")

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
