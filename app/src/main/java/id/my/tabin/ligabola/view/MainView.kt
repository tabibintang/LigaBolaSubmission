package id.my.tabin.ligabola.view

import id.my.tabin.ligabola.model.League
import id.my.tabin.ligabola.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showDetailLeague(data: List<League>)
}