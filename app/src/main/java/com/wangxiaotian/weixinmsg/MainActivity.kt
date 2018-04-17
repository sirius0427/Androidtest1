package com.wangxiaotian.weixinmsg


import android.annotation.SuppressLint
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

import android.graphics.Rect
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

import android.content.ClipData
import android.content.ClipboardManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


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


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission", "HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TestAdapter(this)
        listview_dynamic.adapter = adapter

        //val secureID = encode(SecureID().toString().reversed()).reversed() //取由机器码算出的序列号
        val MachineID = MachineID()
        var timeStamp = System.currentTimeMillis()  //获取当前时间戳
        println( "----------------------")
        println( MachineID )
        println( timeStamp )
        var SecureID = encode(MachineID.toString().reversed()+ timeStamp.toString().reversed()).reversed()


        doAsync {
            timeStamp = System.currentTimeMillis()
            SecureID = encode(MachineID.toString().reversed()+ timeStamp.toString().reversed()).reversed()
            //val json = URL("http://weixin.wangxiaotian.com:8080/recentMsg").readText()
            val postjson="{\"MachineId\":\""+MachineID+"\",\"timeStamp\":\""+timeStamp + "\",\"secureID\":\""+SecureID+"\"}"
            println( postjson )
            val json = HttpUtil.httpPost( "http://weixin.wangxiaotian.com:8080/recentMsg", postjson, "utf-8" )
            uiThread { json2Msg(json) }
        }

        copy2Clipboard( this, MachineID)

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
                timeStamp = System.currentTimeMillis()
                SecureID = encode(MachineID.toString().reversed()+ timeStamp.toString().reversed()).reversed()
                val postjson="{\"MachineId\":\""+MachineID+"\",\"timeStamp\":\""+timeStamp + "\",\"secureID\":\""+SecureID+"\"}"
                println( postjson )
                val json = HttpUtil.httpPost( "http://weixin.wangxiaotian.com:8080/recentMsg", postjson, "utf-8" )
                uiThread { json2Msg(json) }
            }


            //val json:String="""{"data":[{"chatroom":"366734628@chatroom","content":"【华泰建筑建材鲍荣富】周观点20180414： 建筑关注利率及业绩预期落地，建材成长价值共舞_建筑关注利率及业绩预期落地，建材成长价值共舞","createtime":"1523719535000","nickname":"鲍荣富@华泰建筑建材★★★★★","url":"http://mp.weixin.qq.com/s?__biz=MzI0MzI2MTA1Mg==\u0026amp;mid=2654387919\u0026amp;idx=1\u0026amp;sn=e81795f5874ab7c42c4d969ef7995e84\u0026amp;chksm=f2add5dcc5da5cca083e5f6466f00ddcc2da55ab4144451870265ff5e3d13a719af36c8c4bcc\u0026amp;mpshare=1\u0026amp;scene=1\u0026amp;srcid=0414IKYvJL1JcgXIQ71m18pt#rd"},{"chatroom":"919462930@chatroom","content":"【华泰建筑建材鲍荣富】周观点20180414： 建筑关注利率及业绩预期落地，建材成长价值共舞_建筑关注利率及业绩预期落地，建材成长价值共舞","createtime":"1523719440000","nickname":"鲍荣富@华泰建筑建材★★★★★","url":"http://mp.weixin.qq.com/s?__biz=MzI0MzI2MTA1Mg==\u0026amp;mid=2654387919\u0026amp;idx=1\u0026amp;sn=e81795f5874ab7c42c4d969ef7995e84\u0026amp;chksm=f2add5dcc5da5cca083e5f6466f00ddcc2da55ab4144451870265ff5e3d13a719af36c8c4bcc\u0026amp;mpshare=1\u0026amp;scene=1\u0026amp;srcid=0414IKYvJL1JcgXIQ71m18pt#rd"},{"chatroom":"599175155@chatroom","content":" https://www.ccn.com/data-in-seoul-attending-1st-blockchain-asia-meetup/","createtime":"1523719083000","nickname":"叶阳Victor","url":""}]}"""

        }

        search.setOnClickListener { view ->
            Snackbar.make(view, getResources().getString (R.string.str_freshing), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            doAsync {
                //val json = URL("http://weixin.wangxiaotian.com:8080/recentMsg").readText()
                timeStamp = System.currentTimeMillis()
                SecureID = encode(MachineID.toString().reversed()+ timeStamp.toString().reversed()).reversed()
                val postjson="{\"MachineId\":\""+MachineID+"\",\"timeStamp\":\""+timeStamp + "\",\"secureID\":\""+SecureID+"\",\"keyword\":\""+keyword.text+"\"}"
                //val postjson="{\"keyword\":\""+keyword.text+"\"}"
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

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    private fun MachineID() : String? {
        val Android_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
        println( "Android_ID:"+ Android_ID)
        var wm=""
        try {
             wm = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address").replace(":", "")
        }
        catch (e: Exception) {
            wm = "123456789012345"
        }

        println( "WIFI:"+ wm )

        val iMei = NetUtils(this)
        var IMEI1=""
        var IMEI2=""
        try {
            IMEI1 = iMei.TELEPHONY_MANAGER.getDeviceId(0)
        }
        catch (e: Exception)
        {
            IMEI1 = "123456789012345"
        }

        try {
            IMEI2 = iMei.TELEPHONY_MANAGER.getDeviceId(1)
        }
        catch (e: Exception)
        {
            IMEI2 = "123456789012345"
        }

        println( "IMEI1:"+ IMEI1 )
        println( "IMEI2:"+ IMEI2 )

        println("WIFIMAC:" + iMei.WIFI_MAC_ADDRESS)
        println("MAC_ADDRESS:"+ iMei.MAC_ADDRESS)

        var MachineID = Android_ID + wm + IMEI1 + IMEI2
        println( "MachineID:"+ MachineID )
        return MachineID

    }

    fun copy2Clipboard(context: Context?, text: String?) {
        if (context == null || text == null) {
            return
        }

        if (Build.VERSION.SDK_INT >= 11) {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                    as ClipboardManager
            val clipData = ClipData.newPlainText(null, text)
            clipboardManager.primaryClip = clipData
        } else {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                    as android.text.ClipboardManager
            clipboardManager.text = text
        }
    }

    fun encode(password: String): String {
        try {
            val instance: MessageDigest = MessageDigest.getInstance("MD5")//获取md5加密对象
            val digest:ByteArray = instance.digest(password.toByteArray())//对字符串加密，返回字节数组
            var sb : StringBuffer = StringBuffer()
            for (b in digest) {
                var i :Int = b.toInt() and 0xff//获取低八位有效值
                var hexString = Integer.toHexString(i)//将整数转化为16进制
                if (hexString.length < 2) {
                    hexString = "0" + hexString//如果是一位的话，补0
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }


}

class NetUtils(val context: Context) {
    /**
     * 当前WIFI是否可用
     */
    val WIFI_AVAILABLE: Boolean
        get() {
            val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connManager.activeNetworkInfo
            return (info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI)
        }
    /**
     * 当前网络是否可用
     */
    val NETWORK_ENABLE: Boolean
        get() {
            val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connManager.activeNetworkInfo
            return info.state == NetworkInfo.State.CONNECTED
        }

    /**
     * 手机管理器
     */
    val TELEPHONY_MANAGER = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    /**
     * WiFi管理器
     */
    val WIFI_MANAGER = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    /**
     * 连接活动管理器
     */
    val CONNECTIVITY_MANAGER = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * WiFi网卡的Mac地址
     */
    val WIFI_MAC_ADDRESS: String?
        get() {
            val info = WIFI_MANAGER.connectionInfo
            if (WIFI_MANAGER.isWifiEnabled) {
                val localMac = info.macAddress
                if (localMac != null) return localMac.toUpperCase()
            }
            return null
        }

    /**
     * 通过callCmd("busybox ifconfig","HWaddr")获取mac地址
     *
     * @attention 需要设备装有busybox工具
     *
     * @return Mac Address
     */
    val MAC_FROM_CALL_CMD: String?
        get() {
            val result = callCmd("busybox ifconfig", "HWaddr")
            if (result == null || result.length <= 0) {
                return null
            }
            // 对该行数据进行解析
            // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
            if (result.length > 0 && result.contains("HWaddr") == true) {
                val Mac = result.substring(result.indexOf("HWaddr") + 6,
                        result.length - 1)
                if (Mac.length > 1) {
                    return Mac.replace(" ".toRegex(), "")
                }
            }
            return result
        }

    /**
     * CMD调用
     */
    private fun callCmd(cmd: String, filter: String): String? {
        val pro = Runtime.getRuntime().exec(cmd)
        val br = BufferedReader(InputStreamReader(pro.inputStream))

        // 执行命令cmd，只取结果中含有filter的这一行
        var line: String? = ""
        do {
            if (line!!.contains(filter)) break
            line = br.readLine()
        } while (line != null)
        return line
    }

    /**
     * 本机Mac地址
     */
    val MAC_ADDRESS: String?
        get() {
            return if (WIFI_AVAILABLE) {
                WIFI_MAC_ADDRESS
            } else {
                try {
                    MAC_FROM_CALL_CMD
                } catch (e: Throwable) {
                    Log.e("Mac-CMD", e.message)
                    null
                }
            }
        }

    /**
     * 中国运营商的名字
     */
    val PROVIDER: String
        @SuppressLint("MissingPermission")
        get() {
            val IMSI = TELEPHONY_MANAGER.subscriberId
            if (IMSI == null) {
                if (TelephonyManager.SIM_STATE_READY == TELEPHONY_MANAGER.simState) {
                    val operator = TELEPHONY_MANAGER.simOperator
                    if (operator != null) {
                        if (operator.equals("464000") || operator.equals("464002") || operator.equals("464007")) {
                            return "中国移动"
                        } else if (operator.equals("464001")) {
                            return "中国联通"
                        } else if (operator.equals("464003")) {
                            return "中国电信"
                        }
                    }
                }
            } else {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
                        || IMSI.startsWith("46007")) {
                    return "中国移动"
                } else if (IMSI.startsWith("46001")) {
                    return "中国联通"
                } else if (IMSI.startsWith("46003")) {
                    return "中国电信"
                }
            }
            return "UnKnown"
        }

    /**
     * 当前网络的连接类型
     */
    val NET_WORK_CLASS: Int?
        get() {
            val netWorkInfo = CONNECTIVITY_MANAGER.activeNetworkInfo
            if (netWorkInfo != null && netWorkInfo.isAvailable && netWorkInfo.isConnected) {
                return netWorkInfo.type
            } else {
                return null
            }
        }

    /**
     * 当前网络的连接类型
     */
    val NET_WORK_TYPE: NetworkType
        get() {
            if (WIFI_AVAILABLE) return NetworkType.WIFI
            return when (NET_WORK_CLASS) {
                TelephonyManager.NETWORK_TYPE_GPRS,
                TelephonyManager.NETWORK_TYPE_EDGE,
                TelephonyManager.NETWORK_TYPE_CDMA,
                TelephonyManager.NETWORK_TYPE_1xRTT,
                TelephonyManager.NETWORK_TYPE_IDEN -> {
                    NetworkType.DATA2G
                }

                TelephonyManager.NETWORK_TYPE_UMTS,
                TelephonyManager.NETWORK_TYPE_EVDO_0,
                TelephonyManager.NETWORK_TYPE_EVDO_A,
                TelephonyManager.NETWORK_TYPE_HSDPA,
                TelephonyManager.NETWORK_TYPE_HSUPA,
                TelephonyManager.NETWORK_TYPE_HSPA,
                TelephonyManager.NETWORK_TYPE_EVDO_B,
                TelephonyManager.NETWORK_TYPE_EHRPD,
                TelephonyManager.NETWORK_TYPE_HSPAP -> {
                    NetworkType.DATA3G
                }

                TelephonyManager.NETWORK_TYPE_LTE -> {
                    NetworkType.DATA4G
                }

                else -> {
                    NetworkType.UNKNOWN
                }
            }
        }

    /**
     * 当前WiFi信号强度,单位"dBm"
     */
    val WIFI_RSSI: Int
        get() {
            if (NET_WORK_TYPE == NetworkType.WIFI) {
                val wifiInfo = WIFI_MANAGER.connectionInfo
                return if (wifiInfo == null) 0 else wifiInfo.rssi
            }
            return 0
        }

    /**
     * 获得连接WiFi的名称
     */
    val WIFI_SSID: String?
        get() {
            if (NET_WORK_TYPE == NetworkType.WIFI) {
                val wifiInfo = WIFI_MANAGER.connectionInfo
                return wifiInfo?.ssid
            }
            return null
        }

    /**
     * 当前SIM卡状态
     */
    val SIM_STATE: Boolean
            = !(TELEPHONY_MANAGER.simState == TelephonyManager.SIM_STATE_ABSENT
            || TELEPHONY_MANAGER.simState == TelephonyManager.SIM_STATE_UNKNOWN)


    /**
     * 手机串号
     */
    val IMEI: String? @SuppressLint("MissingPermission")
    get() = TELEPHONY_MANAGER.deviceId

    /**
     * 国际移动用户标识码
     */
    val IMSI: String? @SuppressLint("MissingPermission")
    get() = TELEPHONY_MANAGER.subscriberId



}

enum class NetworkType {
    UNKNOWN,
    DISCONNECTION,
    WIFI,
    DATA2G,
    DATA3G,
    DATA4G,
    DATA5G
}
