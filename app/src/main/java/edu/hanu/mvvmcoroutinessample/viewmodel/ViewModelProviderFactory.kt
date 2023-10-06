package edu.hanu.mvvmcoroutinessample.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hanu.mvvmcoroutinessample.app.MyApplication
import edu.hanu.mvvmcoroutinessample.repository.EventRepository

class ViewModelProviderFactory(
    private val app: Application,
    private val appRepository: EventRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(app as MyApplication, appRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}