package edu.hanu.mvvmcoroutinessample.api

import edu.hanu.mvvmcoroutinessample.model.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
        @GET("api/eventing/getAllEvents")
        suspend fun getAllEvents(@Query("meetingId") meetingId: String): Response<EventResponse>
    }
