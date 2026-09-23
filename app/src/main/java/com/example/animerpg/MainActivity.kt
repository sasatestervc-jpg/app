package com.example.animerpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.animerpg.game.BattleScreen
import com.example.animerpg.game.ExploreScreen
import com.example.animerpg.game.GameOverScreen
import com.example.animerpg.game.GameViewModel
import com.example.animerpg.game.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier) {
                    GameRoot(viewModel)
                }
            }
        }
    }
}

@Composable
fun GameRoot(viewModel: GameViewModel) {
    when (viewModel.screen) {
        Screen.EXPLORE -> ExploreScreen(viewModel)
        Screen.BATTLE -> BattleScreen(viewModel)
        Screen.GAME_OVER -> GameOverScreen(viewModel)
        Screen.VICTORY_TOWN -> ExploreScreen(viewModel)
    }
}
