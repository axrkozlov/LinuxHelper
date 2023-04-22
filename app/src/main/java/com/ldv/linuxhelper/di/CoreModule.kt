package com.ldv.linuxhelper.di


import com.ldv.linuxhelper.db.DbModule
import com.ldv.linuxhelper.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    single {
        ProvideCoreInstances(androidContext())
    }

    single { get<ProvideCoreInstances>().provideDbModule() }
    single { get<DbModule>().provideDatabase().topicDao() }
//    single { DbModule.Base(androidContext()).provideDatabase().topicDao() }

    viewModel {
        HomeViewModel(get())
    }
//    single<DispatchersList> { DispatchersList.Base() }
//    single<ManageResources> { ManageResources.Base(androidContext()) }
//
//    single<WorkManagerWrapper> { WorkManagerWrapper.Base(androidContext()) }

}