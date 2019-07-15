package com.mythqian.pluginactivity

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv_enterSecondActivity.setOnClickListener {
            startActivity(Intent(that, SecondActivity::class.java))
        }
    }
}

