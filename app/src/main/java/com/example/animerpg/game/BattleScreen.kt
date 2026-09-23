package com.example.animerpg.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BattleScreen(viewModel: GameViewModel) {
    val player by viewModel.player
    val enemy = viewModel.currentEnemy

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B1B3A))
            .padding(16.dp)
    ) {
        Text(
            "⚔ Pertarungan!",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (enemy != null) {
            EnemyPanel(enemy)
        } else if (player.isAlive) {
            // Enemy sudah dikalahkan, tampilkan tombol lanjut
            Text("Kemenangan!", color = Color(0xFFFFD56B), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))
        PlayerPanel(player)

        Spacer(modifier = Modifier.height(12.dp))

        // Battle log
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF1B1128), RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            items(viewModel.battleLog.takeLast(30)) { line ->
                Text(line, color = Color.LightGray, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (enemy != null && player.isAlive) {
            ActionButtons(viewModel)
        } else if (enemy == null && player.isAlive) {
            Button(onClick = { viewModel.leaveBattleAfterVictory() }) {
                Text("Lanjutkan Eksplorasi")
            }
        }
    }
}

@Composable
private fun EnemyPanel(enemy: Enemy) {
    Column {
        Text("${enemy.emoji} ${enemy.name}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            progress = { enemy.hp.toFloat() / enemy.maxHp },
            modifier = Modifier.fillMaxWidth().height(10.dp),
            color = Color(0xFFFF6B6B)
        )
        Text("HP: ${enemy.hp}/${enemy.maxHp}", color = Color(0xFFFF9B9B), fontSize = 12.sp)
    }
}

@Composable
private fun PlayerPanel(player: Player) {
    Column {
        Text("${player.name} (Lv.${player.level})", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            progress = { player.hp.toFloat() / player.maxHp },
            modifier = Modifier.fillMaxWidth().height(10.dp),
            color = Color(0xFF6BFFA1)
        )
        Text("HP: ${player.hp}/${player.maxHp}", color = Color(0xFFA1FFC0), fontSize = 12.sp)
        LinearProgressIndicator(
            progress = { player.mp.toFloat() / player.maxMp },
            modifier = Modifier.fillMaxWidth().height(6.dp),
            color = Color(0xFF6BCBFF)
        )
        Text("MP: ${player.mp}/${player.maxMp}", color = Color(0xFFB0E4FF), fontSize = 12.sp)
    }
}

@Composable
private fun ActionButtons(viewModel: GameViewModel) {
    val isPlayerTurn = viewModel.isPlayerTurn
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.playerAttack() }, enabled = isPlayerTurn) {
                Text("Serang")
            }
            Button(onClick = { viewModel.playerUseSkill(Skill.SLASH_KI) }, enabled = isPlayerTurn) {
                Text("Slash Ki (4 MP)")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.playerUseSkill(Skill.HEAL) }, enabled = isPlayerTurn) {
                Text("Heal (3 MP)")
            }
            Button(onClick = { viewModel.runFromBattle() }, enabled = isPlayerTurn) {
                Text("Lari")
            }
        }
    }
}
