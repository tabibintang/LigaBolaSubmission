package id.my.tabin.ligabola.response

import id.my.tabin.ligabola.model.Event

data class EventSearchResponse(
    val event: List<Event>)