package com.mythqian.mypluginapp.proxy

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import com.mythqian.mypluginapp.constant.Constants
import com.mythqian.mypluginapp.manager.PluginManager
import com.mythqian.pluginstanderlib.CommonInterfaceActivity


class ProxyActivity : Activity() {
    var proxyActivityClassName: String? = null
    var proxyActivity: CommonInterfaceActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        //拿到插件apk中需要的加载的activity的类名
        proxyActivityClassName = intent.getStringExtra(Constants.className)
        //反射实例化被代理的Activity类
        proxyActivity = classLoader.loadClass(proxyActivityClassName).newInstance() as CommonInterfaceActivity?
        proxyActivity!!.attach(this)
        //可以传递 参数
        proxyActivity!!.onCreate(Bundle())
    }

    override fun getClassLoader(): ClassLoader {
        return PluginManager.pluginManager.dexClassLoader!!
    }

    override fun getResources(): Resources {
        return PluginManager.pluginManager.resources!!
    }

    override fun startActivity(intent: Intent?) {
        val proxyIntent = Intent()
        proxyIntent.setClass(this,ProxyActivity::class.java)
        proxyIntent.putExtra(Constants.className,intent!!.getStringExtra(Constants.className))
        super.startActivity(proxyIntent)
    }

    override fun startService(service: Intent?): ComponentName? {
        val proxyIntent = Intent()
        proxyIntent.setClass(this,ProxyActivity::class.java)
        proxyIntent.putExtra(Constants.className,service!!.getStringExtra(Constants.className))
        return super.startService(proxyIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        proxyActivity!!.onDestroy()
    }
}