package com.example.animerpg.game

/**
 * Peta eksplorasi sederhana berbasis grid.
 * Legenda: # = wall, . = grass (bisa encounter), T = town (aman, tempat heal)
 */
object GameMap {
    val layout: List<String> = listOf(
        "##########",
        "#T.......#",
        "#..#..#..#",
        "#..#..#..#",
        "#........#",
        "#..####..#",
        "#..#..#..#",
        "#........#",
        "#.......T#",
        "##########"
    )

    val width = layout[0].length
    val height = layout.size

    fun tileAt(x: Int, y: Int): TileType {
        if (y !in layout.indices || x !in layout[y].indices) return TileType.WALL
        return when (layout[y][x]) {
            '#' -> TileType.WALL
            'T' -> TileType.TOWN
            else -> TileType.ENCOUNTER_ZONE
        }
    }

    fun isWalkable(x: Int, y: Int): Boolean = tileAt(x, y) != TileType.WALL

    // Titik spawn awal pemain (di dekat town pertama)
    val startX = 1
    val startY = 1
}
