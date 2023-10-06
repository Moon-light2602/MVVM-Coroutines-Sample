package edu.hanu.mvvmcoroutinessample.repository

import edu.hanu.mvvmcoroutinessample.api.RetrofitClient

class EventRepository {
    suspend fun getEvents(meetingId: String) = RetrofitClient.apiInterface.getAllEvents(meetingId)
}