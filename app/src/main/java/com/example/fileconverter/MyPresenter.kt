package com.example.fileconverter

import com.example.fileconverter.converter.MyConverter
import com.example.fileconverter.views.MainView
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class MyPresenter(
    private val view: MainView,
    private val scheduler: Scheduler,
    private val converter: MyConverter,
    private val conversationSubscription: Disposable
) {

    fun convertButtonClick() {
        view.pickImage()
    }
}