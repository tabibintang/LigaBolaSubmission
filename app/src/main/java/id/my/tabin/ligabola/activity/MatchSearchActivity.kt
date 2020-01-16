package id.my.tabin.ligabola.activity

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.adapter.MatchRecyclerViewAdapter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.presenter.MatchListPresenter
import id.my.tabin.ligabola.view.MatchListView
import id.my.tabin.ligabola.support.visible
import kotlinx.android.synthetic.main.activity_match_search.*

class MatchSearchActivity : AppCompatActivity() {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var adapter: MatchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)
        setSupportActionBar(toolbar)

        recycler_search_list_match.layoutManager = LinearLayoutManager(applicationContext)
        adapter =
            MatchRecyclerViewAdapter(events, this) {
                val toast = Toast.makeText(this, it.eventName, Toast.LENGTH_SHORT)
                toast.show()
                Log.d(ContentValues.TAG, "Click: $it.id")
                val intent = Intent(this, DetailEventActivity::class.java)
                intent.putExtra("id_match", it.id)
                startActivity(intent)
            }
        recycler_search_list_match.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.searchMenu)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchQuery(searchView)
        return super.onCreateOptionsMenu(menu)

    }

    private fun searchQuery(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            MatchListView {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    hideLoading()
                    return false
                }

                val request = ApiRepository()
                val gson = Gson()

                Toast.makeText(applicationContext, "Searching " + query, Toast.LENGTH_SHORT).show()
                presenter = MatchListPresenter(
                    this,
                    request,
                    gson
                )
                presenter.getMatchSearchList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun showLoading() {
                progress_bar_search.visible()
            }

            override fun hideLoading() {
                progress_bar_search.invisible()
            }

            override fun showMatchList(data: List<Event>) {
                events.clear()
                events.addAll(data)
                if(data.isNullOrEmpty()){
                    Toast.makeText(applicationContext, "Not found", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            }
        })
    }
}
