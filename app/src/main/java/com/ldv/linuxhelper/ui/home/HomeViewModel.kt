package com.ldv.linuxhelper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldv.linuxhelper.db.Topic
import com.ldv.linuxhelper.db.TopicDao
import com.ldv.linuxhelper.db.TopicPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.sql.CommonDataSource

class HomeViewModel(val topicDao: TopicDao) : ViewModel() {

    private val list = listOf(
        Topic(1, "Работа с файлами", "", "null", false, 0,
        listOf(
            TopicPart("cd","Изменяет текущий каталог","Пример: cd <адрес директории>  "),
            TopicPart("mkdir","Создает каталог","Пример: mkdir<адрес директории>"),
            TopicPart("rm","Удаление файла","Пример: rm <название файла>"),
            TopicPart("rmdir","Удаление указанной директории","Пример: rmdir <адрес директории>"),
            TopicPart("ls","Отображение содержимого директории","Пример: ls <адрес директории>"),
            TopicPart("cp","Копирование файлов из одной директории в другую","Пример: cp <начальная точка> <конечная точка>"),
            TopicPart("mv","Перемещение файла из одного каталога в другой","Пример: mv <начальная точка> <конечная точка>"),
            TopicPart("tar","Архивация и разархивирование файла","Пример: Для архивации: tar cvzf названиефайла.tar.gz Для извлечения:tar xvzf названиефайла.tar.gz"),

            )
        ),
        Topic(2, "Управление системой", "", "null", false, 0,
                listOf(
                    TopicPart("ps", "Снятие задачи/процесса с используя ID",""),
                    TopicPart("kill", "Снятие задачи/процесса с используя ID","Пример: kill <PID процесса>"),
                    TopicPart("killall", "Снятие задачи/процесса используя имя","Пример: killall <имя процесса>"),
                    TopicPart("lscpu", "Отображение информации о процессорах",""),
                    TopicPart("shutdown -r", "Перезапускает устройство",""),



                    )
        ),
        Topic(3, "Системная информация", "", "null", false, 0,
            listOf(
                TopicPart("lsusb", "Отобразить все USB устройства",""),
                TopicPart("lspci", "Отобразить все PCI устройства",""),
                TopicPart("neofetch", "Отобразить информацию о системе",""),
                TopicPart("df -k", "Отобразить использование диска",""),
                TopicPart("uname -a", "Отображение информации о ядре",""),
                TopicPart("lshw", "Отображение всей информации о железе",""),



                )
        ),
        Topic(4, "Пользователи", "", "null", false, 0,
            listOf(
                TopicPart("adduser", "Добавить пользователя","Пример: adduser <имя пользователя>"),
                TopicPart("deluser", "Удалить пользователя" ,"Пример: adduser <имя пользователя>"),

                TopicPart("passwd", "Установить пароль для пользователя","Пример: passwd<пароль>"),
                TopicPart("addgroup", "Создать группу пользователей","Пример: addgroup <название группы>"),
                TopicPart("groupdel", "Удалить группу пользователей","Пример: groupdel <название группы>"),
                TopicPart("usermod -l", "Переименовать пользователя","Пример: usermod -l <новое имя><старое имя>"),
                TopicPart("getent group", "Список всех групп пользователей",""),
                TopicPart("useradd -G", "Добавить пользователя в группу","Пример: useradd -G <имя группы><имя пользователя>"),
                TopicPart("deluser [user] [group]", "Удалить пользователя из группы","Пример: deluser <имя пользователя><группа>"),
                TopicPart("usermod -a -G sudo", "Предоставить пользователю права администратора","Пример: usermod -a -G sudo <имя пользователя>"),
                TopicPart("finger", "Информация о пользователе","finger <имя пользователя>"),


                )
        ),
        Topic(5, "Пакетный менеджер", "", "null", false, 0,
            listOf(
                TopicPart("Установка из репозитория", "","apt install [имя пакета]\n" +
                        "yum install [имя пакета]\n" +
                        "dnf install [имя пакета]\n" +
                        "pkg install [имя пакета]\n" +
                        "apt-get install [имя пакета]\n" +
                        "pacman -S [имя пакета]\n" +
                        "pip install [имя пакета]\n"),
                TopicPart("Установка из файла", "","dpkg -i [пакет.deb]\n" +
                        "dnf install [пакет.rpm]\n" +
                        "yun install [пакет.rpm]\n" +
                        "pip install [путь к пакету]\n"),
                TopicPart("Удаление пакета", "","apt remove [имя пакета]\n" +
                        "dnf remove [имя пакета]\n" +
                        "pip uninstall [имя пакета]\n" +
                        "pacman -R [имя пакета]\n"),
                TopicPart("Обновить список пакетов", "","apt update\n" +
                        "dnf update\n" +
                        "yum check-update\n" +
                        "pacman -S\n"),
                TopicPart("Обновить установленные пакеты", "","apt upgrade\n" +
                        "dnf upgrade\n" +
                        "pkg upgrade\n" +
                        "yum upgrade\n" +
                        "pacman -Syu\n"),

                )
        )
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