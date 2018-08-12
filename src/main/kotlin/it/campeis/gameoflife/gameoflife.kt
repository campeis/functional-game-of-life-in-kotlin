package it.campeis.gameoflife
import kotlin.math.sqrt
import kotlin.math.pow

typealias World<T> = (T) -> Boolean
typealias Rules = (alive:Boolean,neighboursAlive:Int) -> Boolean
typealias Neighbours<T> = (T) -> Set<T>
typealias GameOfLife<T> = (Set<T>) -> Set<T>

fun <T> makeGame(worldContains:World<T>,neightbours: Neighbours<T>,rules:Rules):GameOfLife<T> = { alive ->
    alive.filter { worldContains(it) }
            .flatMap(neightbours)
            .filter { worldContains(it) }
            .fold(mapOf<T,Int>(),{map, e -> map + Pair(e,map.getOrDefault(e,0) + 1)})
            .filter { rules(alive.contains(it.key), it.value) }
            .keys
}

data class Cell(val x:Int, val y:Int)

fun rectangularWorld(x: Int,y: Int): World<Cell> = { cell:Cell -> 0 <= cell.x && cell.x < x && 0 <= cell.y && cell.y < y }
fun triangularWorld(side:Int):World<Cell> = {cell:Cell -> 0 <= cell.x && 0 <= cell.y && (cell.x + cell.y) < side}
fun circularWorld(x:Int, y:Int, radius:Int):World<Cell> = {cell:Cell ->  sqrt((cell.x-x).toDouble().pow(2)+(cell.y-y).toDouble().pow(2))< radius}
fun unboundedWorld():World<Cell> = { true }

private val moves2D:List<Pair<Int,Int>> = (-1..1).flatMap { x -> (-1..1).map { y -> Pair(x,y) } }.filter { p -> p.first != 0 || p.second != 0 }
fun neighbours2D(cell:Cell): Set<Cell> = moves2D.map { move -> Cell(cell.x+move.first, cell.y +move.second )}.toSet()

fun classicRules(alive:Boolean, neighboursAlive: Int):Boolean = when(neighboursAlive){
    2 -> alive
    3 -> true
    else -> false
}

fun classicGame(world: World<Cell>):GameOfLife<Cell> = makeGame(world,::neighbours2D,::classicRules)