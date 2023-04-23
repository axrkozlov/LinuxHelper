package com.ldv.linuxhelper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldv.linuxhelper.db.Topic
import com.ldv.linuxhelper.db.TopicDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.sql.CommonDataSource

class HomeViewModel(val topicDao: TopicDao) : ViewModel() {

    private val list = listOf(
        Topic(1, "Что такое Linux?", "История создания", "null", false, 0),
        Topic(2, "Дистрибутивы Linux", "Из чего состоят дистрибутивы Linux", "null", false, 0)
    )

     val command = MutableSharedFlow<TopicCommand>(replay = 0)


    fun getList(): Flow<List<Topic>> = topicDao.getTopics()

    fun seedTopics() {
        viewModelScope.launch(Dispatchers.IO) {
            topicDao.insert(list)
        }
    }


    fun updateTopic(topic: Topic) {
        viewModelScope.launch(Dispatchers.IO) {
            topicDao.update(topic)
        }
    }

    fun shareTopic(topic: Topic) {
        viewModelScope.launch {
            command.emit(OpenTopic(topic))
        }

    }

    fun openTopic(topic: Topic) {
        TODO("Not yet implemented")
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Это главный экран"
    }
    val text: LiveData<String> = _text

    sealed class TopicCommand
    class OpenTopic(val topic: Topic) : TopicCommand()

}