package id.my.tabin.ligabola.view

import id.my.tabin.ligabola.model.Table
import id.my.tabin.ligabola.model.Team

interface StandingListView {
    fun showLoading()
    fun hideLoading()
    fun showTableList(data: List<Table>)
}