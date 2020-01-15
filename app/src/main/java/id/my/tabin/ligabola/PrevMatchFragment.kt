package id.my.tabin.ligabola


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
import kotlinx.android.synthetic.main.fragment_prev_match.*

/**
 * A simple [Fragment] subclass.
 */
class PrevMatchFragment : Fragment(),MatchListView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var adapter: MatchRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prev_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val league = arguments?.getParcelable<League>("league")

        val request = ApiRepository()
        val gson = Gson()

        recycler_prev_list_match.layoutManager = LinearLayoutManager(context)
        adapter = MatchRecyclerViewAdapter(events,context!!){
            val toast = Toast.makeText(context, it.eventName, Toast.LENGTH_SHORT)
            toast.show()
            Log.d(ContentValues.TAG, "Click: $it.id")
            val intent = Intent(context, DetailEventActivity::class.java)
            intent.putExtra("id_match", it.id )
            startActivity(intent)
            //startActivity<DetailEventActivity>("id_match" to it.id )
        }
        recycler_prev_list_match.adapter = adapter

        presenter  = MatchListPresenter(this,request,gson)
        presenter.getMatchPrevList(league?.id)

    }
    override fun showLoading() {
        progress_bar_prev.visible()
    }

    override fun hideLoading() {
        progress_bar_prev.invisible()
    }

    override fun showMatchList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
