package it.campeis.gameoflife

import java.util.*
import java.util.stream.Stream

fun printGame(xsize:Int, ysize:Int, world:World<Cell>,alive: Set<Cell>) {
    for (y in 0 .. ysize) {
        for (x in 0 .. xsize) {
            when {
                !world(Cell(x,y)) -> print(" ")
                alive.contains(Cell(x,y)) -> print("*")
                else -> print("_")
            }
        }
        println()
    }
}

private val random = Random()
fun randomCells(num:Int,maxX:Int,maxY:Int) = Stream
        .iterate(setOf<Cell>()){set -> set + Cell(random.nextInt(maxX), random.nextInt(maxY))}
        .dropWhile{it.size < num}
        .findFirst()

fun main(args: Array<String>) {
    val width = 20
    setOf(
            rectangularWorld(width,width),
            triangularWorld(width),
            circularWorld(width/2,width/2,width/2),
            unboundedWorld()
    ).forEach { world ->
        val game = classicGame(world)

        randomCells(width*width/2,width,width).ifPresent { alive ->
            Stream.iterate(0 to alive){(index, alive) ->
                index+1 to game(alive)
            }.takeWhile {(index,_) ->
                index < 10
            }.forEach{ (index, alive) ->
                println("generation: $index")
                printGame(width,width,world,alive)
            }
        }

    }

}