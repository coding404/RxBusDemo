package com.example.liushu.rxbusdemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.liushu.rxbusdemo.R
import com.example.liushu.rxbusdemo.baserx.RxBus
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btn_update.setOnClickListener {
            RxBus.getInstance()?.post("666", "界面2数据")
        }
        btn_finish.setOnClickListener {
            finish()
        }
    }
}
