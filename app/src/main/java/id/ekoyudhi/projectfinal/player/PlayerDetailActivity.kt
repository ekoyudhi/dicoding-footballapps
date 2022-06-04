package id.ekoyudhi.projectfinal.player

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.ekoyudhi.projectfinal.R
import id.ekoyudhi.projectfinal.model.Player

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var player : Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val data = intent.extras
        player = data.getParcelable("pl") as Player

        supportActionBar?.title = player.playerName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!player.playerThumb.isNullOrEmpty() || !player.playerThumb.isNullOrBlank())
            Picasso.get().load(Uri.parse(player.playerThumb)).into(findViewById<ImageView>(R.id.img_player))

        (findViewById<TextView>(R.id.weight_player)).text = player.playerWeight
        (findViewById<TextView>(R.id.height_player)).text = player.playerHeight?.replace("m","")?.replace(" ","")
        (findViewById<TextView>(R.id.pos_player)).text = player.playerPosition
        (findViewById<TextView>(R.id.desc_player)).text = player.playerDescriptionEN
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
