package id.my.tabin.ligabola.view

import id.my.tabin.ligabola.model.Event

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Event>)
}