package com.mythqian.pluginservice

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import com.mythqian.pluginstanderlib.CommonInterfaceService

open class BaseService  : Service(), CommonInterfaceService {
    private var that: Service? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun attach(proxyService: Service) {
        this.that = proxyService
    }


    override fun onCreate() {
        // TODO Auto-generated method stub
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO Auto-generated method stub
        return Service.START_STICKY
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // TODO Auto-generated method stub
    }

    override fun onLowMemory() {
        // TODO Auto-generated method stub
    }

    override fun onTrimMemory(level: Int) {
        // TODO Auto-generated method stub

    }

    override fun onUnbind(intent: Intent): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun onRebind(intent: Intent) {
        // TODO Auto-generated method stub
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        // TODO Auto-generated method stub
    }
}