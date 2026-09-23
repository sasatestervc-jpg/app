package com.example.animerpg.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExploreScreen(viewModel: GameViewModel) {
    val player by viewModel.player

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1B2F))
            .padding(16.dp)
    ) {
        // Status bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "${player.name}  Lv.${player.level}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text("HP: ${player.hp}/${player.maxHp}", color = Color(0xFFFF6B81))
                Text("MP: ${player.mp}/${player.maxMp}", color = Color(0xFF6BCBFF))
            }
            Text(
                "EXP: ${player.exp}/${player.expToNextLevel}",
                color = Color(0xFFFFD56B)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Map grid
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MapGrid(viewModel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol arah (D-Pad sederhana)
        DPad(viewModel)
    }
}

@Composable
private fun MapGrid(viewModel: GameViewModel) {
    Column {
        for (y in 0 until GameMap.height) {
            Row {
                for (x in 0 until GameMap.width) {
                    val tile = GameMap.tileAt(x, y)
                    val isPlayer = (x == viewModel.playerX && y == viewModel.playerY)
                    val color = when {
                        isPlayer -> Color(0xFFFFD56B)
                        tile == TileType.WALL -> Color(0xFF0D0D17)
                        tile == TileType.TOWN -> Color(0xFF6BFFA1)
                        else -> Color(0xFF2E2E4E)
                    }
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .padding(1.dp)
                            .background(color, RoundedCornerShape(3.dp))
                    )
                }
            }
        }
    }
}

@Composable
private fun DPad(viewModel: GameViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { viewModel.movePlayer(0, -1) }) { Text("▲") }
        Row {
            Button(onClick = { viewModel.movePlayer(-1, 0) }) { Text("◀") }
            Spacer(modifier = Modifier.width(48.dp))
            Button(onClick = { viewModel.movePlayer(1, 0) }) { Text("▶") }
        }
        Button(onClick = { viewModel.movePlayer(0, 1) }) { Text("▼") }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Jalan-jalan di rumput (petak abu-abu) untuk memicu battle. Petak hijau (T) = kota, tempat heal.",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
