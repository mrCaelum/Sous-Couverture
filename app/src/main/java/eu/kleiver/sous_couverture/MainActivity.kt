package eu.kleiver.sous_couverture

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private var state: Int = 1
    private lateinit var playerNameLabel: TextView
    private lateinit var playerNameField: EditText
    private lateinit var playerNameButton: Button
    private lateinit var clearPlayerList: Button
    private lateinit var playersList: ListView
    private lateinit var playButton: ImageButton
    private lateinit var errorLog: TextView
    private var players: ArrayList<String> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerNameLabel = findViewById(R.id.player_name_label)
        playerNameField = findViewById(R.id.player_name_field)
        playerNameButton = findViewById(R.id.add_name_button)
        clearPlayerList = findViewById(R.id.clear_players_list)
        playersList = findViewById(R.id.players_list)
        playButton = findViewById(R.id.play_button)
        errorLog = findViewById(R.id.error_log)

        playersList.adapter = ArrayAdapter(this, R.layout.player_row, R.id.player_name_view, players)
        playerNameLabel.text = "Nom du ${state}e joueur :"
        clearPlayerList.setOnClickListener {
            players.clear()
            (playersList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            state = 1
            playerNameLabel.text = "Nom du ${state}e joueur :"
        }
        playerNameButton.setOnClickListener {
            if (state < 15) {
                if (playerNameField.text.isNotEmpty()) {
                    players.add(playerNameField.text.toString())
                    playerNameField.text.clear()
                    (playersList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    state++
                    playerNameLabel.text = "Nom du ${state}e joueur :"
                    errorLog.text = ""
                }
            } else {
                playerNameLabel.text = "Appuie sur le boutton en bas à droite pour jouer !"
                playerNameField.isVisible = false
                playerNameButton.isVisible = false
            }
        }
        playButton.setOnClickListener {
            if (players.size == 0) {
                errorLog.text = "Ajoute des joueurs pour jouer"
            } else if (players.size < 3) {
                errorLog.text = "Vous devez être au moins 3 !"
            } else {
                val gameIntent = Intent(this, GameActivity::class.java)
                val gameBundle = Bundle()
                gameBundle.putSerializable("ARRAYLIST", players as Serializable)
                gameIntent.putExtra("PLAYERS", gameBundle)
                startActivity(gameIntent)
            }
        }
        playersList.setOnItemClickListener { adapterView, view, position, id ->
            players.removeAt(position)
            (playersList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            state--
            playerNameLabel.text = "Nom du ${state}e joueur :"
        }
    }
}