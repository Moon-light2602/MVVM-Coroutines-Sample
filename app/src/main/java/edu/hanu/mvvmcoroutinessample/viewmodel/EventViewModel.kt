package edu.hanu.mvvmcoroutinessample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.hanu.mvvmcoroutinessample.R
import edu.hanu.mvvmcoroutinessample.app.MyApplication
import edu.hanu.mvvmcoroutinessample.model.EventData
import edu.hanu.mvvmcoroutinessample.model.EventResponse
import edu.hanu.mvvmcoroutinessample.repository.EventRepository
import edu.hanu.mvvmcoroutinessample.util.Resource
import edu.hanu.mvvmcoroutinessample.util.Utils.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class EventViewModel (private val app: MyApplication,
                      private val repository: EventRepository
): ViewModel() {
    var eventsData: MutableLiveData<Resource<ArrayList<EventData>>> = MutableLiveData()

    fun getEvents(meetingId: String) = viewModelScope.launch {
        fetchEvents(meetingId)
    }

    private suspend fun fetchEvents(meetingId: String) {
        eventsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(app)) {
                val response = repository.getEvents(meetingId)
                eventsData.postValue(handlePicsResponse(response))
            } else {
                eventsData.postValue(Resource.Error(app.getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> eventsData.postValue(
                    t.localizedMessage?.let { Resource.Error(it) }
                )
                else -> eventsData.postValue(
                    Resource.Error(app.getString(R.string.conversion_error))
                )
            }
        }
    }

    private fun handlePicsResponse(response: Response<EventResponse>): Resource<ArrayList<EventData>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.data)
            }
        }
        return Resource.Error(response.message())
    }
}