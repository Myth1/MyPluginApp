package com.mythqian.mypluginapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mythqian.mypluginapp.constant.Constants
import com.mythqian.mypluginapp.manager.PluginManager
import com.mythqian.mypluginapp.proxy.ProxyActivity
import com.mythqian.mypluginapp.proxy.ProxyService
import kotlinx.android.synthetic.main.activity_main_with_coroutine.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivityWithCoroutine : AppCompatActivity() {
    val TAG = MainActivityWithCoroutine::class.java.canonicalName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_with_coroutine)

        tv_openPluginActivity.setOnClickListener {
            //协程加载插件
            runBlocking {
                var name = "pluginactivity_debug.apk"
                downPluginapk(name, R.raw.pluginactivity_debug)
                loadPlugin(name)
            }
            val intent = Intent()
            intent.setClass(this@MainActivityWithCoroutine, ProxyActivity::class.java)
            Log.d(TAG, PluginManager.pluginManager.packageInfoActivity!!.activities[0].name)
            intent.putExtra(Constants.className, PluginManager.pluginManager.packageInfoActivity!!.activities[0].name)
            startActivity(intent)
        }
        tv_openPluginservice.setOnClickListener {
            //协程加载插件
            runBlocking {
                var name = "pluginservice_debug.apk"
                downPluginapk(name, R.raw.pluginservice_debug)
                loadPlugin(name)
            }
            val intent = Intent()
            intent.setClass(this@MainActivityWithCoroutine, ProxyService::class.java)
            Log.d(TAG, PluginManager.pluginManager.packageInfoService!!.services[0].name)
            intent.putExtra(Constants.className, PluginManager.pluginManager.packageInfoService!!.services[0].name)
            //如果没有在插件apk的清单文件注册，就只能手写全类名
//            intent.putExtra(Constants.className,"com.mythqian.pluginservice.StartService")
            startService(intent)
        }
    }

    suspend fun downPluginapk(name: String, fileId: Int) {
        var openRawResource: FileInputStream? = null
        var fos: FileOutputStream? = null
        try {
            val openRawResource = resources.openRawResource(fileId)
            var fos = FileOutputStream(File(Environment.getExternalStorageDirectory(), name))
            var len = 0;
            val buffer = ByteArray(1024)
            while (openRawResource.read(buffer).also { len = it } != -1) {
                fos.write(buffer, 0, len)
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        } finally {
            try {
                fos?.close()
                openRawResource?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    suspend fun loadPlugin(name: String) {
        val filesDir = this@MainActivityWithCoroutine.getDir("plugin", Context.MODE_PRIVATE)
        val filePath = File(filesDir, name).absolutePath
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null
        try {
            Log.i(TAG, "加载插件 " + File(Environment.getExternalStorageDirectory(), name).absolutePath)
            fis = FileInputStream(File(Environment.getExternalStorageDirectory(), name))
            fos = FileOutputStream(filePath)
            var len = 0
            val buffer = ByteArray(1024)
            while (fis.read(buffer).also { len = it } != -1) {
                fos.write(buffer, 0, len)
            }
            val f = File(filePath)
            if (f.exists()) {
                Log.d(TAG, "dex overwrite")
            }
            PluginManager.pluginManager.loadPath(this@MainActivityWithCoroutine, name)
        } catch (e: IOException) {
            Log.d(TAG, e.message)
        } finally {
            try {
                fos?.close()
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
