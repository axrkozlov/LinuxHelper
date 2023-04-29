package com.ldv.linuxhelper.ui.tips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldv.linuxhelper.db.Tip
import com.ldv.linuxhelper.db.TipDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TipsViewModel(val tipDao: TipDao) : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "Это окно полезной информации"
//    }
//    val text: LiveData<String> = _text

    private val list = listOf(
        Tip(1, "Что такое Linux?", "История создания", "null", false, 0),
        Tip(2, "Дистрибутивы Linux", "Из чего состоят дистрибутивы Linux", "null", false, 0)
    )

    val command = MutableSharedFlow<TipCommand>(replay = 0)


    fun getList(): Flow<List<Tip>> = tipDao.getTips()

    fun seedTip() {
        viewModelScope.launch(Dispatchers.IO) {
            tipDao.insert(list)
        }
    }


    fun updateTopic(tip: Tip) {
        viewModelScope.launch(Dispatchers.IO) {
            tipDao.update(tip)
        }
    }

    fun shareTopic(tip: Tip) {
        viewModelScope.launch {
            command.emit(ShareTip(tip))
        }

    }

    fun openTopic(tip: Tip) {
        viewModelScope.launch {
            command.emit(OpenTip(tip))
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Это главный экран"
    }
    val text: LiveData<String> = _text

    sealed class TipCommand
    class OpenTip(val tip: Tip) : TipCommand()
    class ShareTip(val tip: Tip) : TipCommand()

}