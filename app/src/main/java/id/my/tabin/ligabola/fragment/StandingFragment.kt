package id.my.tabin.ligabola.fragment


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson

import id.my.tabin.ligabola.R
import id.my.tabin.ligabola.adapter.TableRecycleViewAdapter
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.League
import id.my.tabin.ligabola.model.Table
import id.my.tabin.ligabola.presenter.StandingPresenter
import id.my.tabin.ligabola.support.invisible
import id.my.tabin.ligabola.support.visible
import id.my.tabin.ligabola.view.StandingListView
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.fragment_standings.*

/**
 * A simple [Fragment] subclass.
 */
class StandingFragment : Fragment(), StandingListView {

    private var tables: MutableList<Table> = mutableListOf()
    private lateinit var presenter: StandingPresenter
    private lateinit var adapter: TableRecycleViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val league = arguments?.getParcelable<League>("league")

        val request = ApiRepository()
        val gson = Gson()

        recycler_standing_list.layoutManager = LinearLayoutManager(context)
        adapter = TableRecycleViewAdapter(
            tables,
            context!!
        ) {
            val toast = Toast.makeText(context, it.teamName, Toast.LENGTH_SHORT)
            toast.show()
            Log.d(ContentValues.TAG, "Click: $it.id")
//            val intent = Intent(context, DetailEventActivity::class.java)
//            intent.putExtra("id_match", it.id)
//            startActivity(intent)
            //startActivity<DetailEventActivity>("id_match" to it.id )
        }
        recycler_standing_list.adapter = adapter

        presenter = StandingPresenter(
            this,
            request,
            gson
        )
        presenter.getStandingList(league?.id)

    }

    override fun showLoading() {
        progress_bar_standing.visible()
    }

    override fun hideLoading() {

        if(progress_bar_standing != null) {
            progress_bar_standing.invisible()
        }
    }

    override fun showTableList(data: List<Table>) {
        tables.clear()
        tables.addAll(data)
        adapter.notifyDataSetChanged()
    }


}
