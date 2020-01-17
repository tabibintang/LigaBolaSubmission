package id.my.tabin.ligabola.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.activity.LeagueDetailActivity
import id.my.tabin.ligabola.adapter.RecyclerViewAdapter
import id.my.tabin.ligabola.model.League
import kotlinx.android.synthetic.main.fragment_league_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info

/**
 * A simple [Fragment] subclass.
 */
class LeagueListFragment : Fragment(), AnkoLogger {

    private var items: MutableList<League> = mutableListOf()
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        recycler_league_list.layoutManager = GridLayoutManager(context, 2)
        adapter = RecyclerViewAdapter(context!!, items) {
            val toast = Toast.makeText(context, it.name, Toast.LENGTH_SHORT)
            toast.show()
            info("MainActivity: Item clicked")
            debug("MainActivity: Name=" + it.name)
            debug("MainActivity: Description=" + it.description)
            val intent = Intent(context, LeagueDetailActivity::class.java)
            intent.putExtra("league", it)
            startActivity(intent)
        }
        recycler_league_list.adapter = adapter
    }

    private fun initData() {
        val id = resources.getStringArray(R.array.league_id)
        val name = resources.getStringArray(R.array.league_name)
        val image = resources.obtainTypedArray(R.array.league_image)
        val description = resources.getStringArray(R.array.league_description)
        items.clear()
        for (i in name.indices) {
            items.add(
                League(
                    id[i],
                    name[i],
                    image.getResourceId(i, 0),
                    null,
                    description[i]
                )
            )
        }
        image.recycle()
    }
}
