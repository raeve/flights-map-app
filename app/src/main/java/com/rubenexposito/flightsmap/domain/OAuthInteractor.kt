package com.rubenexposito.flightsmap.domain

import android.annotation.SuppressLint
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.SharedRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy

interface OAuthInteractor {
    fun getAuthToken()
}

class OAuthInteractorImpl(
        private val lufthansaRepository: LufthansaRepository, private val sharedRepository: SharedRepository, private val observeOn: Scheduler, private val subscribeOn: Scheduler) : OAuthInteractor {

    private var subscription = Disposables.empty()

    @SuppressLint("CheckResult")
    override fun getAuthToken() {
        lufthansaRepository.oauthToken()
                .map {
                    sharedRepository.saveToken(it.accessToken, it.tokenType, it.expiresIn)
                }
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy {
                    dispose()
                }
    }

    fun dispose() = subscription.dispose()
}