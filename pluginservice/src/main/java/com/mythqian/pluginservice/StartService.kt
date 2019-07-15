package com.mythqian.pluginservice

import android.content.Intent
import android.os.IBinder
import android.util.Log

class StartService :BaseService() {

    override fun onBind(p0: Intent?): IBinder? {
        super.onBind(p0)
        Thread{
            var i = 0
            while (i < 100){
                i++
                Log.d("StartService", ""+i)
            }
        }.start()
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StartService", "onStartCommand")
        Thread{
            var i = 0
            while (i < 100){
                i++
                Log.d("StartService", ""+i)
            }
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }
}