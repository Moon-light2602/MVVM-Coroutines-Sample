package edu.hanu.mvvmcoroutinessample.model

data class EventResponse(
    var message: String = "",
    var data: ArrayList<EventData> = ArrayList()
)

data class EventData (
    var id: String = "",
    var meetingId: String = "",
    var dateTime: String = "",
    var name: String = "",
    var purpose: String = "",
    var code: String = ""

)