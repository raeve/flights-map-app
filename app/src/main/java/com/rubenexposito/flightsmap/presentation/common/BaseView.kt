package com.rubenexposito.flightsmap.presentation.common

import android.support.annotation.StringRes

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(@StringRes resId: Int)
}