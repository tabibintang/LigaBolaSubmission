package id.my.tabin.ligabola

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity(), AnkoLogger {

    lateinit var teamImage: ImageView
    lateinit var teamName: TextView
    lateinit var teamDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            scrollView {
                linearLayout {
                    padding = dip(16)
                    orientation = LinearLayout.VERTICAL
                    teamImage = imageView(R.drawable.american_mayor_league) {
                        id = R.id.second_team_image
                        scaleType = ImageView.ScaleType.FIT_XY
                    }.lparams(
                        width = dip(160),
                        height = dip(160)
                    ) {
                        gravity = Gravity.CENTER
                    }
                    teamName = textView {
                        id = R.id.second_team_name
                        textSize = 25f
                        gravity = Gravity.CENTER
                    }.lparams {
                        padding = dip(5)
                        width = matchParent
                        height = matchParent
                        topMargin = dip(10)
                    }
                    teamDescription = textView {
                        id = R.id.second_team_description
                        textSize = 15f
                    }.lparams {
                        padding = dip(5)
                        leftMargin = dip(8)
                        rightMargin = dip(8)
                    }
                }
            }

            val intent = intent
            val tim = intent.getParcelableExtra<Tim>("tim")

            teamName.text = tim.name
            teamDescription.text = tim.description
            tim.image!!.let { Picasso.get().load(it).fit().into(teamImage) }
        }
        catch(e: Exception) {
            info("DetailActivity: Load Data")
            error(e.message)
        }
    }
}
