package com.example.liushu.rxbusdemo.baserx

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

class RxManage {

    var mRxBus = RxBus.getInstance()
    //管理RxBus订阅
    private val mObservables = HashMap<Any, Observable<*>>()
    private val mCompositeDisposable = CompositeDisposable()
    //订阅事件
    fun <T : Any> on(eventName: String, action: Consumer<T>) {
        val mObservable = mRxBus!!.register<T>(eventName)
        mObservables[eventName] = mObservable
        mCompositeDisposable.add(
            mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(
                action,
                Consumer { throwable -> throwable.printStackTrace() })
        )
    }

    fun add(m: Disposable) {
        mCompositeDisposable.add(m)
    }

    fun clear() {
        mCompositeDisposable.clear()
        for ((key, value) in mObservables) {
            mRxBus!!.unRegister(key, value)
        }
    }

    fun post(eventName: Any, content: Any) {
        mRxBus!!.post(eventName, content)
    }
}