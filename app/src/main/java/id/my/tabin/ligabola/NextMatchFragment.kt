package id.my.tabin.ligabola


import android.content.ContentValues.TAG
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
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.*

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment(),MatchListView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var adapter: MatchRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val league = arguments?.getParcelable<League>("league")

        val request = ApiRepository()
        val gson = Gson()

        recycler_next_list_match.layoutManager = LinearLayoutManager(context)
        adapter = MatchRecyclerViewAdapter(events,context!!){
            val toast = Toast.makeText(context, it.eventName, Toast.LENGTH_SHORT)
            toast.show()
            Log.d(TAG, "Click: $it.id")
            val intent = Intent(context, DetailEventActivity::class.java)
            intent.putExtra("id_match", it.id )
            startActivity(intent)
            //startActivity<DetailEventActivity>("id_match" to it.id )
        }
        recycler_next_list_match.adapter = adapter

        presenter  = MatchListPresenter(this,request,gson)
        presenter.getMatchNextList(league?.id)

    }
    override fun showLoading() {
        progress_bar_next.visible()
    }

    override fun hideLoading() {
        progress_bar_next.invisible()
    }

    override fun showMatchList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
