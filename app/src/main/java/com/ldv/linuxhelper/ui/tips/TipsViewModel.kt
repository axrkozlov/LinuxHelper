package com.ldv.linuxhelper.ui.tips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это окно полезной информации"
    }
    val text: LiveData<String> = _text
}