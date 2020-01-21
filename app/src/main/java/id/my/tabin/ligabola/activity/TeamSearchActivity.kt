package id.my.tabin.ligabola.activity

import android.content.Intent
import android.os.Bundle
import android.support.constraint.Constraints
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.adapter.TeamRecyclerViewAdapter
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.presenter.TeamListPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.TeamListView
import kotlinx.android.synthetic.main.activity_team_search.*

class TeamSearchActivity : AppCompatActivity() {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamListPresenter
    private lateinit var adapter: TeamRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_search)
        setSupportActionBar(toolbar_search)

        recycler_search_team.layoutManager = LinearLayoutManager(applicationContext)
        adapter =
            TeamRecyclerViewAdapter(teams, this) {
                val toast = Toast.makeText(this, it.teamName, Toast.LENGTH_SHORT)
                toast.show()
                Log.d(Constraints.TAG, "Click: $it.id")
                val intent = Intent(this, DetailTeamActivity::class.java)
                intent.putExtra("team", it)
                startActivity(intent)
            }
        recycler_search_team.adapter = adapter
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
            TeamListView {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    hideLoading()
                    return false
                }

                val request = ApiRepository()
                val gson = Gson()

                Toast.makeText(applicationContext, "Searching " + query, Toast.LENGTH_SHORT).show()
                presenter = TeamListPresenter(
                    this,
                    request,
                    gson
                )
                presenter.getSearchTeamList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun showLoading() {
                progress_bar_search_team.visible()
            }

            override fun hideLoading() {
                progress_bar_search_team.invisible()
            }

            override fun showTeamList(data: List<Team>) {
                teams.clear()
                teams.addAll(data)
                if (data.isNullOrEmpty()) {
                    Toast.makeText(applicationContext, "Not found", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            }
        })
    }
}
