package com.example.animerpg.game

/**
 * Data class untuk karakter pemain.
 */
data class Player(
    val name: String = "Yuki",
    var level: Int = 1,
    var maxHp: Int = 30,
    var hp: Int = 30,
    var maxMp: Int = 10,
    var mp: Int = 10,
    var attack: Int = 8,
    var defense: Int = 3,
    var exp: Int = 0,
    var expToNextLevel: Int = 20
) {
    val isAlive: Boolean get() = hp > 0

    fun gainExp(amount: Int): List<String> {
        val log = mutableListOf<String>()
        exp += amount
        log.add("$name mendapat $amount EXP!")
        while (exp >= expToNextLevel) {
            exp -= expToNextLevel
            levelUp()
            log.add("$name naik ke Level $level!")
        }
        return log
    }

    private fun levelUp() {
        level += 1
        maxHp += 8
        maxMp += 3
        attack += 3
        defense += 2
        hp = maxHp
        mp = maxMp
        expToNextLevel = (expToNextLevel * 1.3).toInt()
    }
}

/**
 * Data class untuk musuh, dengan template agar bisa dibuat ulang tiap encounter.
 */
data class Enemy(
    val name: String,
    val maxHp: Int,
    var hp: Int,
    val attack: Int,
    val defense: Int,
    val expReward: Int,
    val emoji: String
) {
    val isAlive: Boolean get() = hp > 0

    companion object {
        fun spawnRandom(): Enemy {
            val templates = listOf(
                Triple("Slime Bayangan", 18, 5) to Pair(6, "🟣"),
                Triple("Kitsune Liar", 25, 7) to Pair(10, "🦊"),
                Triple("Oni Kecil", 35, 9) to Pair(15, "👹")
            )
            val (stats, extra) = templates.random()
            val (name, hp, atk) = stats
            val (exp, emoji) = extra
            return Enemy(
                name = name,
                maxHp = hp,
                hp = hp,
                attack = atk,
                defense = (atk / 3),
                expReward = exp,
                emoji = emoji
            )
        }
    }
}

enum class Skill(val displayName: String, val mpCost: Int, val powerMultiplier: Double) {
    SLASH_KI("Slash Ki", 4, 1.8),
    HEAL("Heal", 3, 0.0)
}

/** Tipe ubin pada peta eksplorasi. */
enum class TileType {
    GRASS, WALL, TOWN, ENCOUNTER_ZONE
}
