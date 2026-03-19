package it.campeis.gameoflife

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameOfLifeTests {
    companion object {
        val game = classicGame(rectangularWorld(3, 3))
    }

    @Test
    fun `a live cell with zero live neighbours will die`() {
        given { setOf(Cell(1, 1)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
    }

    @Test
    fun `a live cell with one live neighbours will die`() {
        given { setOf(Cell(1, 1), Cell(1, 0)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
    }

    @Test
    fun `a live cell with two live neighbours will remain alive`() {
        given { setOf(Cell(1, 1), Cell(0, 0), Cell(0, 1)) }
            .`when` { game(it) }
            .then { assertTrue(it.contains(Cell(1, 1))) }
    }

    @Test
    fun `a live cell with three live neighbours will remain alive`() {
        given { setOf(Cell(1, 1), Cell(0, 0), Cell(0, 1), Cell(0, 2)) }
            .`when` { game(it) }
            .then { assertTrue(it.contains(Cell(1, 1))) }
    }

    @Test
    fun `a live cell with four or more live neighbours will die`() {
        given { setOf(Cell(1, 1), Cell(0, 0), Cell(0, 1), Cell(0, 2), Cell(2, 1)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
        given { setOf(Cell(1, 1), Cell(0, 0), Cell(0, 1), Cell(0, 2), Cell(2, 1), Cell(2, 2)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
    }

    @Test
    fun `a dead cell with exactly three live neighbours becomes alive`() {
        given { setOf(Cell(0, 0), Cell(0, 1), Cell(0, 2)) }
            .`when` { game(it) }
            .then { assertTrue(it.contains(Cell(1, 1))) }
    }

    @Test
    fun `in all other cases a dead cell will stay dead`() {
        given { setOf<Cell>() }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
        given { setOf(Cell(0, 1)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
        given { setOf(Cell(0, 0), Cell(0, 1)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
        given { setOf(Cell(0, 0), Cell(0, 1), Cell(0, 2), Cell(2, 1)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(1, 1))) }
    }

    @Test
    fun `cells outside of world are ignored`() {
        given { setOf(Cell(2, 1), Cell(3, 0), Cell(3, 1), Cell(3, 2)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(2, 1))) }
        given { setOf(Cell(2, 1), Cell(3, 0), Cell(3, 1), Cell(3, 2)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(2, 1))) }
        given { setOf(Cell(3, 0), Cell(3, 1), Cell(3, 2)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(2, 1))) }
        given { setOf(Cell(2, 1), Cell(3, 0), Cell(3, 1), Cell(3, 2)) }
            .`when` { game(it) }
            .then { assertTrue(!it.contains(Cell(2, 1))) }
    }

    fun <T> given(block: () -> T) = block()

    fun <T, K> T.`when`(block: (T) -> K) = block(this)

    fun <T> T.then(block: (T) -> Unit) = block(this)
}
