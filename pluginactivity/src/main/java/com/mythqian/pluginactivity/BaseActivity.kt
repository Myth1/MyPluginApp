package com.mythqian.pluginactivity

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mythqian.mypluginapp.constant.Constants
import com.mythqian.pluginstanderlib.CommonInterfaceActivity

/**
 *  ###代理如果存在则 走代理的生命周期 不回调 activity
 */
open class BaseActivity : Activity(), CommonInterfaceActivity {
    protected var that: Activity? = null
    override fun attach(proxyActivity: Activity?) {

        that = proxyActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (that == null) {
            super.onCreate(savedInstanceState)
        }
    }


    override fun startActivity(intent: Intent?) {
        if (that != null) {
            val intent1 = Intent()
            intent1.putExtra(Constants.className, intent!!.component.className)
            that!!.startActivity(intent1)
        } else {
            super.startActivity(intent)
        }
    }

    override fun bindService(service: Intent?, conn: ServiceConnection, flags: Int): Boolean {
        if (that != null) {
            val intent1 = Intent()
            intent1.putExtra(Constants.className, intent!!.component.className)
            return that!!.bindService(intent1,conn,flags)
        } else {
            return super.bindService(intent,conn,flags)
        }
    }

    override fun unbindService(conn: ServiceConnection) {
        if (that != null) {
            return that!!.unbindService(conn)
        } else {
            return super.unbindService(conn)
        }
    }

    override fun startService(service: Intent?): ComponentName? {
        if (that != null) {
            val intent1 = Intent()
            intent1.putExtra(Constants.className, intent!!.component.className)
            return that!!.startService(intent1)
        } else {
           return super.startService(intent)
        }
    }

    override fun setContentView(view: View?) {
        that?.let {
            it.setContentView(view)
            return
        }
        Log.d("sssss", "testtest")
        super.setContentView(view)
    }

    override fun setContentView(layoutResID: Int) {
        that?.let {
            it.setContentView(layoutResID)
            return
        }
        super.setContentView(layoutResID)
    }


    override fun <T : View?> findViewById(id: Int): T {
        if (that != null) return that!!.findViewById<T>(id)
        return super.findViewById<T>(id)
    }

    override fun getIntent(): Intent {
        if (that != null) return that!!.intent
        return super.getIntent()
    }

    override fun getClassLoader(): ClassLoader {
        if (that != null) return that!!.classLoader
        return super.getClassLoader()
    }

    override fun getResources(): Resources {
        if (that != null) return that!!.resources
        return super.getResources()
    }

    override fun getApplicationInfo(): ApplicationInfo {
        if (that != null) return that!!.applicationInfo
        return super.getApplicationInfo()
    }

    override fun getWindow(): Window {
        if (that != null) return that!!.window
        return super.getWindow()
    }

    override fun getWindowManager(): WindowManager {
        if (that != null) return that!!.windowManager
        return super.getWindowManager()
    }

    override fun getLayoutInflater(): LayoutInflater {
        if (that != null) return that!!.layoutInflater
        return super.getLayoutInflater()
    }

    override fun onRestart() {
        if (that == null) {
            super.onRestart()
        }
    }

    override fun onResume() {
        if (that == null) {
            super.onResume()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (that == null) {
            super.onSaveInstanceState(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (that == null) {
            super.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onStart() {
        if (that == null) {
            super.onStart()
        }
    }

    override fun onStop() {
        if (that == null) {
            super.onStop()
        }
    }

    override fun onPause() {
        if (that == null) {
            super.onPause()
        }
    }

    override fun onDestroy() {
        if (that == null) {
            super.onDestroy()
        }
    }
}