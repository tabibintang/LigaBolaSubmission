package id.my.tabin.ligabola

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showDetailLeague(data: List<League>)
}