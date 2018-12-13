package com.rubenexposito.flightsmap.presentation.common

import io.reactivex.disposables.Disposable

abstract class RxPresenter : BasePresenter {

    protected var subscription: Disposable? = null

    override fun onPause() {
        subscription?.dispose()
    }
}