package com.example.liushu.rxbusdemo.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.liushu.rxbusdemo.R
import com.example.liushu.rxbusdemo.baserx.RxManage
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mRxManage: RxManage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRxManage = RxManage()
        mRxManage?.on("666", Consumer<String> {
            tv_content.text = it
        })
        btn_main.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }
}
