package id.my.tabin.ligabola.view

import id.my.tabin.ligabola.model.Team

interface TeamListView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}