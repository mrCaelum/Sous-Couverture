package eu.kleiver.sous_couverture

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.player_row.view.*
import org.w3c.dom.Text

class GameActivity : AppCompatActivity() {
    private val wordList = arrayOf(
        arrayOf("chien", "chat", "furet", "hamster", "lapin"),
        arrayOf("maison", "appartement", "villa"),
        arrayOf("hippocampe", "poisson", "étoile de mer", "pieuvre", "poulpe", "calamar"),
        arrayOf("fumée", "vapeur", "gaz"),
        arrayOf("volcan", "montagne"),
        arrayOf("cendre", "poussière", "saletée"),
        arrayOf("épée", "couteau"),
        arrayOf("thé", "café"),
        arrayOf("rhum", "whisky", "kirch", "vodka"),
        arrayOf("bière", "cidre", "vin")
    )
    private var state: Int = 0
    private lateinit var players: ArrayList<String>
    private lateinit var playerLabel: TextView
    private lateinit var playerWord: TextView
    private lateinit var discoverButton: Button
    private lateinit var okButton: Button
    private lateinit var whoIsLabel: TextView
    private lateinit var playersBtnList: ListView
    private lateinit var returnButton: Button
    private lateinit var undercover: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val gameBundle: Bundle = intent.getBundleExtra("PLAYERS")!!
        players = gameBundle.getSerializable("ARRAYLIST") as ArrayList<String>
        playerLabel = findViewById(R.id.player_label)
        playerWord = findViewById(R.id.player_word)
        discoverButton = findViewById(R.id.discover_button)
        okButton = findViewById(R.id.ok_button)
        whoIsLabel = findViewById(R.id.who_is_label)
        playersBtnList = findViewById(R.id.players_button_list)
        returnButton = findViewById(R.id.return_button)

        val actWordList: ArrayList<String> = wordList.random().toCollection(ArrayList())
        val word: String = actWordList.random()
        actWordList.remove(word)
        var undercoverWord = actWordList.random()
        undercover = players.random()
        playerLabel.text = players[state]
        playerWord.text = if (players[state] == undercover) undercoverWord else word
        playerWord.isVisible = false
        okButton.isVisible = false
        whoIsLabel.isVisible = false
        playersBtnList.adapter = ArrayAdapter(this, R.layout.player_row, R.id.player_name_view, players)
        playersBtnList.isVisible = false
        returnButton.isVisible = false

        discoverButton.setOnClickListener {
            playerWord.isVisible = true
            playerWord.text = if (players[state] == undercover) undercoverWord else word
            discoverButton.isVisible = false
            okButton.isVisible = true
        }
        okButton.setOnClickListener {
            if (state < players.size - 1) {
                playerWord.isVisible = false
                discoverButton.isVisible = true
                okButton.isVisible = false
                state++
                playerLabel.text = players[state]
            } else {
                playerLabel.isVisible = false
                playerWord.isVisible = false
                discoverButton.isVisible = false
                okButton.isVisible = false
                whoIsLabel.isVisible = true
                playersBtnList.isVisible = true
            }
        }
        returnButton.setOnClickListener {
            finish()
        }
        playersBtnList.setOnItemClickListener { adapterView, view, position, id ->
            if (view.player_name_view.text.toString() == undercover) {
                whoIsLabel.isVisible = false
                playersBtnList.isVisible = false
                playerLabel.isVisible = true
                returnButton.isVisible = true
                playerLabel.text = "Bravo ! C'était bien ${undercover} !"
            } else {
                players.remove(view.player_name_view.text.toString())
                (playersBtnList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            }
        }
    }
}