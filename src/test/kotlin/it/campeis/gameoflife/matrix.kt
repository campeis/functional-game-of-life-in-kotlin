package it.campeis.gameoflife

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse

class MatrixWorldTests {

    @Test
    fun `in a matrix world a cell set as x is reported as contained`() {
        val matrix = matrixWorld {
            r(x, x)
            r(x, x)
        }
        assertTrue(matrix(Cell(0, 0)))
        assertTrue(matrix(Cell(0, 1)))
        assertTrue(matrix(Cell(1, 0)))
        assertTrue(matrix(Cell(1, 1)))
    }

    @Test
    fun `in a matrix world a cell set as o is reported as not contained`() {
        val matrix = matrixWorld {
            r(o,o)
            r(o,o)
        }
        assertFalse(matrix(Cell(0, 0)))
        assertFalse(matrix(Cell(0, 1)))
        assertFalse(matrix(Cell(1, 0)))
        assertFalse(matrix(Cell(1, 1)))
    }

    @Test
    fun `in a matrix world a cell with negative y is reported as not contained`() {
        val matrix = matrixWorld {
            r(x, x)
            r(x, x)
        }
        assertFalse(matrix(Cell(0, -1)))
    }

    @Test
    fun `in a matrix world a cell with y major to the number of rows is reported as not contained`() {
        val matrix = matrixWorld {
            r(x, x)
            r(x, x)
        }
        assertFalse(matrix(Cell(0, 2)))
    }

    @Test
    fun `in a matrix world a cell with negative x is reported as not contained`() {
        val matrix = matrixWorld {
            r(x, x)
            r(x, x)
        }
        assertFalse(matrix(Cell(-1, 0)))
    }

    @Test
    fun `in a matrix world a cell with x major to the number of elements on a row is reported as not contained`() {
        val matrix = matrixWorld {
            r(x, x)
            r(x, x)
        }
        assertFalse(matrix(Cell(2, 0)))
    }
}