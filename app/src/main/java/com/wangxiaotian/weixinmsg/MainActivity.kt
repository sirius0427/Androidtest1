package com.wangxiaotian.weixinmsg


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import java.util.*

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URLEncoder
import org.json.JSONObject
import android.os.AsyncTask.execute
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


data class MsgContent(
        val groupname: String,
        val content: String,
        val createtime: String,
        val nickname: String,
        val url: String
)
data class WeixinMsg (@SerializedName("data") val msgContent:List<MsgContent>){}


class MainActivity : AppCompatActivity() {

    lateinit var adapter: TestAdapter
    var items = ArrayList<HashMap<String, Any>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TestAdapter(this)
        listview_dynamic.adapter = adapter

        doAsync {
            val json = URL("http://weixin.wangxiaotian.com:8080/recentMsg").readText()
            uiThread { json2Msg(json) }
        }

/*        val map = HashMap<String, Any>()
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

        adapter.setData(items)*/

        //setSupportActionBar(toolbar)

        fab_fresh.setOnClickListener { view ->
            Snackbar.make(view, getResources().getString (R.string.str_freshing), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            doAsync {
                val json = URL("http://weixin.wangxiaotian.com:8080/recentMsg").readText()

//                val postjson="{\"keyword\":\"隆基\"}"
//                val json = HttpUtil.httpPost( "http://weixin.wangxiaotian.com:8080/searchMsg", postjson, "utf-8" )
//                println( json )
                uiThread { json2Msg(json) }
            }


            //val json:String="""{"data":[{"chatroom":"366734628@chatroom","content":"【华泰建筑建材鲍荣富】周观点20180414： 建筑关注利率及业绩预期落地，建材成长价值共舞_建筑关注利率及业绩预期落地，建材成长价值共舞","createtime":"1523719535000","nickname":"鲍荣富@华泰建筑建材★★★★★","url":"http://mp.weixin.qq.com/s?__biz=MzI0MzI2MTA1Mg==\u0026amp;mid=2654387919\u0026amp;idx=1\u0026amp;sn=e81795f5874ab7c42c4d969ef7995e84\u0026amp;chksm=f2add5dcc5da5cca083e5f6466f00ddcc2da55ab4144451870265ff5e3d13a719af36c8c4bcc\u0026amp;mpshare=1\u0026amp;scene=1\u0026amp;srcid=0414IKYvJL1JcgXIQ71m18pt#rd"},{"chatroom":"919462930@chatroom","content":"【华泰建筑建材鲍荣富】周观点20180414： 建筑关注利率及业绩预期落地，建材成长价值共舞_建筑关注利率及业绩预期落地，建材成长价值共舞","createtime":"1523719440000","nickname":"鲍荣富@华泰建筑建材★★★★★","url":"http://mp.weixin.qq.com/s?__biz=MzI0MzI2MTA1Mg==\u0026amp;mid=2654387919\u0026amp;idx=1\u0026amp;sn=e81795f5874ab7c42c4d969ef7995e84\u0026amp;chksm=f2add5dcc5da5cca083e5f6466f00ddcc2da55ab4144451870265ff5e3d13a719af36c8c4bcc\u0026amp;mpshare=1\u0026amp;scene=1\u0026amp;srcid=0414IKYvJL1JcgXIQ71m18pt#rd"},{"chatroom":"599175155@chatroom","content":" https://www.ccn.com/data-in-seoul-attending-1st-blockchain-asia-meetup/","createtime":"1523719083000","nickname":"叶阳Victor","url":""}]}"""

        }

        search.setOnClickListener { view ->
            Snackbar.make(view, getResources().getString (R.string.str_freshing), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            doAsync {
                //val json = URL("http://weixin.wangxiaotian.com:8080/recentMsg").readText()

                val postjson="{\"keyword\":\""+keyword.text+"\"}"
                val json = HttpUtil.httpPost( "http://weixin.wangxiaotian.com:8080/searchMsg", postjson, "utf-8" )
                println( json )
                uiThread { json2Msg(json) }
            } }

    }

    private fun json2Msg( json:String )
    {
        var weixinMsg: WeixinMsg = Gson().fromJson(json, WeixinMsg::class.java)
        items.clear()
        for (i in 0..weixinMsg.msgContent.count() - 1) {
            val map = HashMap<String, Any>()
            map.put("text", weixinMsg.msgContent[i].content)
            //val format = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            map.put("title", weixinMsg.msgContent[i].nickname + " " + weixinMsg.msgContent[i].groupname + " " + java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA).format(weixinMsg.msgContent[i].createtime.toLong()))
            map.put("url", weixinMsg.msgContent[i].url)
            items.add(map)
        }
        adapter.setData(items)

        if (isSoftShowing()) {
            var imm: InputMethodManager = keyword.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val isOpen = imm.isActive
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
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
        // as you specify a parent activity in AndroidMani

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getRequestData(params: Map<String, String>, encode: String): StringBuffer {
        val stringBuffer = StringBuffer()        //存储封装好的请求体信息
        try {
            for ((key, value) in params) {
                stringBuffer.append(key)
                        .append("=")
                        .append(URLEncoder.encode(value, encode))
                        .append("&")
            }
            stringBuffer.deleteCharAt(stringBuffer.length - 1)    //删除最后的一个"&"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuffer
    }

    private fun isSoftShowing(): Boolean {
        //获取当前屏幕内容的高度
        val screenHeight = window.decorView.height
        //获取View可见区域的bottom
        val rect = Rect()
        //DecorView即为activity的顶级view
        window.decorView.getWindowVisibleDisplayFrame(rect)
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom
    }


}
