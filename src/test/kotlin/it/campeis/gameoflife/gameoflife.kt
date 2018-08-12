package it.campeis.gameoflife

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class GameOfLifeTests {

    companion object {
        val game = classicGame(rectangularWorld(3,3))
    }

    @Test
    fun `a live cell with zero live neighbours will die`() {
        Given { setOf(Cell(1,1)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
    }

    @Test
    fun `a live cell with one live neighbours will die`() {
        Given { setOf(Cell(1,1), Cell(1,0)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
    }

    @Test
    fun `a live cell with two live neighbours will remain alive`() {
        Given { setOf(Cell(1,1),Cell(0,0),Cell(0,1)) }
                .When { game(it) }
                .Then { assertTrue(it.contains(Cell(1,1))) }
    }

    @Test
    fun `a live cell with three live neighbours will remain alive`() {
        Given { setOf(Cell(1,1),Cell(0,0),Cell(0,1),Cell(0,2)) }
                .When { game(it) }
                .Then { assertTrue(it.contains(Cell(1,1))) }
    }

    @Test
    fun `a live cell with four or more live neighbours will die`() {
        Given { setOf(Cell(1,1),Cell(0,0),Cell(0,1),Cell(0,2), Cell(2,1)) }
                .When { game(it) }
                .Then {assertTrue(!it.contains(Cell(1,1))) }
        Given { setOf(Cell(1,1),Cell(0,0),Cell(0,1),Cell(0,2),Cell(2,1), Cell(2,2)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
    }

    @Test
    fun `a dead cell with exactly three live neighbours becomes alive`() {
        Given { setOf(Cell(0,0),Cell(0,1),Cell(0,2)) }
                .When { game(it) }
                .Then { assertTrue(it.contains(Cell(1,1))) }
    }

    @Test
    fun `in all other cases a dead cell will stay dead`() {
        Given { setOf<Cell>() }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
        Given { setOf(Cell(0,1)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
        Given { setOf(Cell(0,0),Cell(0,1)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
        Given { setOf(Cell(0,0),Cell(0,1), Cell(0,2), Cell(2,1)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(1,1))) }
    }

    @Test
    fun `cells outside of world are ignored`() {
        Given { setOf(Cell(2,1),Cell(3,0),Cell(3,1),Cell(3,2)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(2,1))) }
        Given { setOf(Cell(2,1),Cell(3,0),Cell(3,1),Cell(3,2)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(2,1))) }
        Given { setOf(Cell(3,0),Cell(3,1),Cell(3,2)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(2,1))) }
        Given { setOf(Cell(2,1),Cell(3,0),Cell(3,1),Cell(3,2)) }
                .When { game(it) }
                .Then { assertTrue(!it.contains(Cell(2,1))) }
    }

    fun <T> Given(block: () -> T) = block()
    fun <T,K> T.When(block:(T) -> K) = block(this)
    fun <T> T.Then(block: (T)->Unit) = block(this)
}