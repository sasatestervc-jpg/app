package com.example.animerpg.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

enum class Screen { EXPLORE, BATTLE, GAME_OVER, VICTORY_TOWN }

class GameViewModel : ViewModel() {

    var screen by mutableStateOf(Screen.EXPLORE)
        private set

    // --- State eksplorasi ---
    var playerX by mutableStateOf(GameMap.startX)
        private set
    var playerY by mutableStateOf(GameMap.startY)
        private set

    val player = mutableStateOf(Player())

    // --- State battle ---
    var currentEnemy by mutableStateOf<Enemy?>(null)
        private set
    var battleLog by mutableStateOf(listOf<String>())
        private set
    var isPlayerTurn by mutableStateOf(true)
        private set

    private val encounterChance = 0.22

    fun movePlayer(dx: Int, dy: Int) {
        if (screen != Screen.EXPLORE) return
        val newX = playerX + dx
        val newY = playerY + dy
        if (!GameMap.isWalkable(newX, newY)) return

        playerX = newX
        playerY = newY

        when (GameMap.tileAt(newX, newY)) {
            TileType.TOWN -> {
                // Heal penuh saat menginjak town
                player.value = player.value.copy(hp = player.value.maxHp, mp = player.value.maxMp)
            }
            TileType.ENCOUNTER_ZONE -> {
                if (Random.nextDouble() < encounterChance) {
                    startBattle()
                }
            }
            else -> {}
        }
    }

    private fun startBattle() {
        currentEnemy = Enemy.spawnRandom()
        battleLog = listOf("Seekor ${currentEnemy?.name} muncul!")
        isPlayerTurn = true
        screen = Screen.BATTLE
    }

    fun playerAttack() {
        val enemy = currentEnemy ?: return
        if (!isPlayerTurn) return

        val p = player.value
        val damage = (p.attack - enemy.defense / 2).coerceAtLeast(1)
        enemy.hp = (enemy.hp - damage).coerceAtLeast(0)
        addLog("${p.name} menyerang! ${enemy.name} terkena $damage damage.")

        if (!enemy.isAlive) {
            onEnemyDefeated()
        } else {
            enemyTurn()
        }
    }

    fun playerUseSkill(skill: Skill) {
        val enemy = currentEnemy ?: return
        if (!isPlayerTurn) return
        var p = player.value
        if (p.mp < skill.mpCost) {
            addLog("MP tidak cukup untuk ${skill.displayName}!")
            return
        }

        p = p.copy(mp = p.mp - skill.mpCost)

        when (skill) {
            Skill.SLASH_KI -> {
                val damage = ((p.attack * skill.powerMultiplier) - enemy.defense / 2).toInt().coerceAtLeast(1)
                enemy.hp = (enemy.hp - damage).coerceAtLeast(0)
                addLog("${p.name} melepaskan ${skill.displayName}! ${enemy.name} terkena $damage damage!")
            }
            Skill.HEAL -> {
                val healAmount = (p.maxHp * 0.35).toInt()
                p = p.copy(hp = (p.hp + healAmount).coerceAtMost(p.maxHp))
                addLog("${p.name} menggunakan ${skill.displayName}, memulihkan $healAmount HP.")
            }
        }

        player.value = p

        if (!enemy.isAlive) {
            onEnemyDefeated()
        } else {
            enemyTurn()
        }
    }

    private fun enemyTurn() {
        isPlayerTurn = false
        val enemy = currentEnemy ?: return
        var p = player.value
        val damage = (enemy.attack - p.defense / 2).coerceAtLeast(1)
        p = p.copy(hp = (p.hp - damage).coerceAtLeast(0))
        player.value = p
        addLog("${enemy.name} menyerang balik! ${p.name} terkena $damage damage.")

        if (!p.isAlive) {
            addLog("${p.name} kalah...")
            screen = Screen.GAME_OVER
        } else {
            isPlayerTurn = true
        }
    }

    private fun onEnemyDefeated() {
        val enemy = currentEnemy ?: return
        addLog("${enemy.name} dikalahkan!")
        val expLog = player.value.gainExp(enemy.expReward)
        player.value = player.value // trigger recomposition
        expLog.forEach { addLog(it) }
        currentEnemy = null
    }

    fun leaveBattleAfterVictory() {
        screen = Screen.EXPLORE
        battleLog = emptyList()
    }

    fun runFromBattle() {
        addLog("Kamu melarikan diri!")
        screen = Screen.EXPLORE
        battleLog = emptyList()
        currentEnemy = null
    }

    fun restartGame() {
        player.value = Player()
        playerX = GameMap.startX
        playerY = GameMap.startY
        currentEnemy = null
        battleLog = emptyList()
        screen = Screen.EXPLORE
    }

    private fun addLog(message: String) {
        battleLog = battleLog + message
    }
}
