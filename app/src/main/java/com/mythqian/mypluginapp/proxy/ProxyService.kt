package com.mythqian.mypluginapp.proxy

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.os.IBinder
import com.mythqian.mypluginapp.constant.Constants
import com.mythqian.mypluginapp.manager.PluginManager
import com.mythqian.pluginstanderlib.CommonInterfaceService

class ProxyService :Service() {


    var proxyServiceClassName: String? = null
    var proxyService: CommonInterfaceService? = null

    private fun init(intent :Intent?){
        //拿到插件apk中需要的加载的Service的类名
        proxyServiceClassName = intent!!.getStringExtra(Constants.className)
        //反射实例化被代理的Service类
        proxyService = classLoader.loadClass(proxyServiceClassName).newInstance() as CommonInterfaceService?
        proxyService!!.attach(this)
    }


    override fun getClassLoader(): ClassLoader {
        return PluginManager.pluginManager.dexClassLoader!!
    }

    override fun getResources(): Resources {
        return PluginManager.pluginManager.resources!!
    }

    /**
     * bindService
     */
    override fun onBind(p0: Intent?): IBinder? {
        proxyService?:init(p0)
        proxyService!!.onBind(p0)
        return null
    }

    /**
     * startService
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        proxyService?:init(intent)
        proxyService!!.onStartCommand(intent, flags, startId)
        return super.onStartCommand(intent, flags, startId)
    }



    override fun onRebind(intent: Intent?) {
        proxyService!!.onRebind(intent)
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        proxyService!!.onUnbind(intent)
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        proxyService!!.onDestroy()
        super.onDestroy()
    }

}