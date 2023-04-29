package com.ldv.linuxhelper.ui.text

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ldv.linuxhelper.db.TopicDao

class WebInfoViewModel() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это окно текста"
    }
    val text: LiveData<String> = _text
}