package com.example.liushu.rxbusdemo.baserx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.jetbrains.annotations.NotNull
import org.reactivestreams.Subscriber
import java.util.concurrent.ConcurrentHashMap

/**
 * 用Rxjava实现eventbus
 */
class RxBus {
    private val mSubjectMapper = ConcurrentHashMap<Any, MutableList<Subject<Any>>>()


    fun <T : Any> register(@NotNull tag: Any): Observable<T> {
        var mutableList = mSubjectMapper[tag]
        if (null == mutableList) {
            mutableList = ArrayList()
            mSubjectMapper[tag] = mutableList
        }
        val subject = PublishSubject.create<T>()
        mutableList.add(subject as Subject<Any>)
        return subject
    }

    fun unRegister(@NotNull tag: Any) {
        val subjets = mSubjectMapper[tag]
        if (subjets != null) {
            mSubjectMapper.remove(tag)
        }
    }

    fun unRegister(@NotNull tag: Any, @NotNull observable: Observable<*>?): RxBus? {
        if (null == observable) {
            return getInstance()
        }
        val subjects = mSubjectMapper[tag]
        if (null!=subjects){
         subjects.remove(observable as Subject<*>?)
            if (isEmpty(subjects)){
                mSubjectMapper.remove(tag)
            }
        }
        return getInstance()
    }

    fun post(@NotNull tag: Any,@NotNull content:Any){
        val subjects = mSubjectMapper[tag]
        if (!isEmpty(subjects)){
            for (subject in subjects!!){
                subject.onNext(content)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: RxBus? = null
        @Synchronized
        fun getInstance(): RxBus? {
            if (instance == null) {
                synchronized(RxBus::class.java) {
                    if (instance == null) {
                        instance = RxBus()
                    }
                }
            }
            return instance
        }
    }

    fun isEmpty(collection: Collection<Subject<*>>?):Boolean{
        return null==collection||collection.isEmpty()
    }
}