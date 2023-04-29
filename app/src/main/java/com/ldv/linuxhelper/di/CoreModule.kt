package com.ldv.linuxhelper.di


import com.ldv.linuxhelper.db.DbModule
import com.ldv.linuxhelper.db.TopicDatabase
import com.ldv.linuxhelper.ui.home.HomeViewModel
import com.ldv.linuxhelper.ui.text.TextViewModel
import com.ldv.linuxhelper.ui.content.ContentViewModel
import com.ldv.linuxhelper.ui.tips.TipsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    single { DbModule(androidContext()) }
    single { get<DbModule>().provideDatabase() }

    single { get<TopicDatabase>().topicDao() }
    single { get<TopicDatabase>().tipDao() }

//    single { DbModule.Base(androidContext()).provideDatabase().topicDao() }

    viewModel { HomeViewModel(get()) }
    viewModel { TextViewModel(get()) }
    viewModel { TipsViewModel(get()) }
    viewModel { ContentViewModel(get()) }

//    single<DispatchersList> { DispatchersList.Base() }
//    single<ManageResources> { ManageResources.Base(androidContext()) }
//
//    single<WorkManagerWrapper> { WorkManagerWrapper.Base(androidContext()) }

}