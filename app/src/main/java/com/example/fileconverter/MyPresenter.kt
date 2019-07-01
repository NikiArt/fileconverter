package com.example.fileconverter

import com.example.fileconverter.conversion.MyConversion
import com.example.fileconverter.converter.MyConverter
import com.example.fileconverter.views.MainView
import io.reactivex.CompletableObserver
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyPresenter(
    private val view: MainView,
    private val scheduler: Scheduler,
    private val converter: MyConverter
    //private var conversationSubscription: Disposable
) {

    fun convertButtonClick() {
        view.pickImage()
    }

    fun pathsSelected(conversionMeta: MyConversion) {
        converter.convertJpegToPng(conversionMeta)
            .subscribeOn(Schedulers.computation())
            .observeOn(scheduler)
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    view.showConvertationSuccessMessage()
                }

                override fun onSubscribe(d: Disposable) {
                    // conversationSubscription = d
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })
    }
}