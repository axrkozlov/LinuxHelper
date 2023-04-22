package com.ldv.linuxhelper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    fun updateTopic(topic:Topic) {
        TODO("Not yet implemented")
    }

    fun shareTopic(topic:Topic) {
        TODO("Not yet implemented")
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Это главный экран"
    }
    val text: LiveData<String> = _text
}