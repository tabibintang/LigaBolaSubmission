package id.my.tabin.ligabola.view

import id.my.tabin.ligabola.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<Team>)
}