package id.my.tabin.ligabola

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<Event>)
}