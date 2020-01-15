package id.my.tabin.ligabola

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Event>)
}