package com.ldv.linuxhelper.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldv.linuxhelper.db.Tip
import com.ldv.linuxhelper.db.TipDao
import com.ldv.linuxhelper.db.Topic
import com.ldv.linuxhelper.db.TopicDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ContentViewModel(val topicDao: TopicDao) : ViewModel() {

    val command = MutableSharedFlow<TopicCommand>(replay = 0)


    fun getList(): Flow<List<Topic>> = topicDao.getTopics()
    fun getTopic(number:Long) = topicDao.getTopic(number)



    fun updateTopic(topic: Topic) {
        viewModelScope.launch(Dispatchers.IO) {
            topicDao.update(topic)
        }
    }

    fun shareTopic(topic: Topic) {
        viewModelScope.launch {
            command.emit(ShareTopic(topic))
        }

    }

    fun openTopic(topic: Topic) {
        viewModelScope.launch {
            command.emit(OpenTopic(topic))
        }
    }



    private val _text = MutableLiveData<String>().apply {
        value = "Это главный экран"
    }
    val text: LiveData<String> = _text

    sealed class TopicCommand
    class OpenTopic(val topic: Topic) : TopicCommand()
    class ShareTopic(val topic: Topic) : TopicCommand()


}