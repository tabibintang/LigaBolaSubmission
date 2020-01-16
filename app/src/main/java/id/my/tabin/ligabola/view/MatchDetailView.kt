package id.my.tabin.ligabola.view

import id.my.tabin.ligabola.model.Event

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<Event>)
}