package com.vp.core.presentation

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultThreadScheduler : ThreadScheduler {

    override fun getMainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun getIoThread(): Scheduler {
        return Schedulers.io()
    }
}