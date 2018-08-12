package it.campeis.gameoflife

typealias MatrixCellValue = Int
class Matrix {

    private val rows = mutableListOf<IntArray>()

    fun r(vararg cellValues:MatrixCellValue){
        rows.add(cellValues)
    }

    fun isSetAt(x:Int,y:Int):Boolean =
            y >= 0  &&
                    y < rows.size &&
                    x >= 0 &&
                    x < rows[y].size &&
                    rows[y][x] == this.x

    val x:MatrixCellValue = 1
    val o:MatrixCellValue = 0
}

fun matrixWorld(configure:Matrix.() -> Unit):World<Cell> {
    val matrix = Matrix()
    configure(matrix)
    return { cell -> matrix.isSetAt(cell.x,cell.y)}
}