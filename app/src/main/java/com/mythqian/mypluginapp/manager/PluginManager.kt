package com.mythqian.mypluginapp.manager

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File

class PluginManager private constructor() {
    val pluginCacheDirName: String = "plugin"
    var packageInfoActivity :PackageInfo? = null
    var packageInfoService :PackageInfo? = null
    var dexClassLoader :ClassLoader? = null
    var resources : Resources? = null
    companion object {
        val pluginManager: PluginManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PluginManager()
        }
    }

    fun loadPath(context: Context,pluginName:String) {
        val pluginCacheDir = context.getDir(pluginCacheDirName, Context.MODE_PRIVATE)
        val pluginPath  =  File(pluginCacheDir,pluginName).absolutePath
        Log.d("pluginPath",pluginPath)
        packageInfoActivity = context.packageManager.getPackageArchiveInfo(pluginPath,PackageManager.GET_ACTIVITIES)
        packageInfoService = context.packageManager.getPackageArchiveInfo(pluginPath,PackageManager.GET_SERVICES)
        dexClassLoader = DexClassLoader(pluginPath,context.getDir("dex",Context.MODE_PRIVATE).absolutePath,null,context.classLoader)
        val assetManagerClass = AssetManager::class
        val assetManager = assetManagerClass.java.newInstance()
        val addAssetPathMethod = assetManagerClass.java.getMethod("addAssetPath", String::class.java)
        addAssetPathMethod.invoke(assetManager,pluginPath)
        resources = Resources(assetManager,context.resources.displayMetrics,context.resources.configuration)
    }
}