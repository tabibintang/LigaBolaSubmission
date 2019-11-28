package id.my.tabin.ligabola

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity(), AnkoLogger {
    private var items: MutableList<Tim> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        initData()
        showRecyclerList()
    }

    private fun initData(){
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val description = resources.getStringArray(R.array.club_description)
        items.clear()
        for (i in name.indices){
            items.add(
                Tim(
                    name[i],
                    image.getResourceId(i,0),
                    description[i]
                )
            )
        }
        image.recycle()


    }
    private fun showRecyclerList() {
        relativeLayout{
            lparams(width= matchParent, height = matchParent)
            padding = dip(16)
            recyclerView {
                lparams(width= matchParent, height = matchParent)
                id = R.id.club_list
                layoutManager = GridLayoutManager(this@MainActivity,2)
                adapter = RecyclerViewAdapter(this@MainActivity,items){
                    val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
                    toast.show()
                    info("MainActivity: Item clicked")
                    debug("MainActivity: Name="+it.name)
                    debug("MainActivity: Description="+it.description)
                    startActivity<DetailActivity>("tim" to it )
                }
            }
        }
    }

}
