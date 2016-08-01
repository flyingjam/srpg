package com.mygdx.game.Map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.clamp
import org.luaj.vm2.*
import org.luaj.vm2.lib.jse.JsePlatform
import java.util.*

/**
 * Created by FlyingJam on 6/16/2016.
 */

val testMap = arrayOf(
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
        0, 1, 1, 1, 0, 1, 0, 1, 1, 0,
        0, 0, 0, 1, 0, 1, 0, 0, 1, 0,
        0, 1, 0, 0, 0, 1, 1, 0, 0, 0
)

fun setInt(map : PathMap, tiles : Array<Int>){
    for(i in 0..tiles.size-1){
        val tile = tiles[i]
        map.setTile(i, TileAttributes(1, 1))
        if(tile == 1){
            map.setWall(i)
            val w = map.isWall(i)
        }
    }
}

fun drawPathMap(map : PathMap, batch : ShapeRenderer, tileWidth : Int, tileHeight : Int){
    batch.setColor(1f, 1f, 1f, 1f)
    for(y in 0..map.height-1){
        for(x in 0..map.width-1){
            val realX = x * tileWidth

            //flip y
            val realY = y * tileHeight//(map.height - 1 - y) * tileHeight
            val flippedY = 720 - realY - tileHeight
            batch.rect(realX.toFloat(), flippedY.toFloat(), tileWidth.toFloat(), tileHeight.toFloat())


            if (map.isWall(x, y)){
                val radius = 10
                val offset = (tileWidth )/2
                batch.circle(realX.toFloat() + offset, flippedY.toFloat() + offset, radius.toFloat())
            }
        }
    }
}

fun drawPath(map : PathMap, batch : ShapeRenderer, path : Array<Int>, tileWidth : Int, tileHeight : Int){
    for(tile in path){
        val x = map.costMap.indexToX(tile)
        val y = map.costMap.indexToY(tile)
        val realX = x * tileWidth
        val realY = y * tileHeight
        val flipped = Gdx.graphics.height - realY - tileHeight

        val offset = tileWidth / 2

        batch.circle(realX.toFloat() + offset, flipped.toFloat() + offset, 5f)
    }
}


fun drawRegion(map : PathMap, batch : ShapeRenderer, path : ArrayList<Int>, tileWidth : Int, tileHeight : Int){
    for(tile in path){
        val x = map.costMap.indexToX(tile)
        val y = map.costMap.indexToY(tile)
        val realX = x * tileWidth
        val realY = y * tileHeight
        val flipped = Gdx.graphics.height - realY - tileHeight

        val offset = tileWidth / 2

        batch.circle(realX.toFloat() + offset, flipped.toFloat() + offset, 2f)
    }
}




class PathMap(width : Int, height : Int) : Map(width, height){

    val costMap = Array2D<TileAttributes>(width, height)
    val wallMap = Array2D<Boolean>(width, height)

    fun setTile(index : Int, value : TileAttributes){
        costMap.set(index, value)
    }

    fun setWall(index : Int){
        wallMap.set(index, true)
    }

    fun getTile(x : Int, y : Int) : TileAttributes?{
        return costMap.get(x, y)
    }

    fun getNeighbors(x : Int, y : Int) : List<Int>{
        val north = clamp(y - 1, 0, height - 1)
        val south = clamp(y + 1, 0, height - 1)
        val east = clamp(x - 1, 0, width - 1)
        val west = clamp(x + 1, 0, width - 1)

        val list = arrayListOf(
                costMap.pointToIndex(west, y),
                costMap.pointToIndex(x, north),
                costMap.pointToIndex(east, y),
                costMap.pointToIndex(x, south)
        ).filter {x -> !isWall(x)}

        return list
    }

    fun getNeighbors(index : Int) : List<Int>{
        val x = costMap.indexToX(index)
        val y = costMap.indexToY(index)
        return getNeighbors(x, y)
    }

    fun inBounds(index : Int) : Boolean{
        val x = costMap.indexToX(index)
        val y = costMap.indexToY(index)
        val result = ((x >= 0) && (x < width) && (y >= 0) && (y < height))
        return result
    }

    fun isWall(index: Int) : Boolean{
        return wallMap.exist(index)
    }

    fun isWall(x : Int, y : Int) : Boolean{
        return wallMap.exist(x, y)
    }

    fun heuristic(a : Int, b : Int) : Int{
        val x1 = costMap.indexToX(a)
        val y1 = costMap.indexToX(a)
        val x2 = costMap.indexToX(b)
        val y2 = costMap.indexToX(b)
        return Math.abs(x1 - x2) + Math.abs(y1 - y2)
    }

    fun cost(a : Int, b : Int) : Int{
        return costMap.get(b)?.cost ?: 1
    }

    fun movableRegion(x : Int, y : Int, cost : Int) : ArrayList<Int>{
        return movableRegion(costMap.pointToIndex(x,y), cost)
    }

    fun movableRegion(pos : Int, cost : Int) : ArrayList<Int>{
        val comp = Comparator { a : Pair<Int, Int>, b : Pair<Int, Int> -> a.second - b.second}
        val frontier = PriorityQueue<Pair<Int, Int>>(comp)
        frontier.add(Pair(pos, 0))

        val costSoFar = Array2D<Int>(width, height)
        costSoFar.set(pos, 0)
        var current = pos

        val region = ArrayList<Int>()
        region.add(pos)

        val maxiter = 100

        var iter = 0

        while(!frontier.isEmpty()){

            iter++
            if(iter > maxiter){
                println("Mercy Killed")
                break
            }

            current = frontier.poll().first

            for(next in getNeighbors(current)){
                var newCost = (costSoFar.get(current) ?: 1) + cost(current, next)

                if(newCost < cost && (!costSoFar.exist(next) || newCost < costSoFar.get(next) ?: 0)){
                    costSoFar.set(next, newCost)
                    region.add(next)
                    val priority = newCost
                    frontier.add(Pair(next, priority))
                }
            }
        }

        val len = region.size
        return region
    }

    fun AStar(start: Int, goal: Int, limit : Int = -1) : Array2D<Int> {

        val maxIter = width * height

        val maxDist = if(limit > 0) limit else heuristic(start, goal)

        val comp = Comparator { a : Pair<Int, Int>, b : Pair<Int, Int> -> a.second - b.second}
        val frontier = PriorityQueue<Pair<Int, Int>>(comp)
        frontier.add(Pair(start, 0))

        val cameFrom = Array2D<Int>(width, height)
        val costSoFar = Array2D<Int>(width, height)
        costSoFar.set(start, 0)

        var current = start

        var iter = 0
        while(!frontier.isEmpty()){

            current = frontier.poll().first

            if(current == goal) break
            else if (iter > maxIter) break

            for(next in getNeighbors(current)){

                var wall = isWall(next)
                var newCost = (costSoFar.get(current) ?: 1) + cost(current, next)

                if(!costSoFar.exist(next) || newCost < costSoFar.get(next) ?: 1){
                    costSoFar.set(next, newCost)
                    val priority = newCost + heuristic(goal, next)
                    frontier.add(Pair(next, priority))
                    cameFrom.set(next, current)
                }
            }
        }

        val thing = cameFrom == null
        return cameFrom
    }

    fun reconstructPath(cameFrom : Array2D<Int>, start : Int, goal : Int) : Array<Int>{

        var current = goal
        val size = cameFrom.filterNotNull().size
        val path = ArrayList<Int>()
        if(size < 1){
            return path.toTypedArray()
        }

        if(!cameFrom.exist(current)){
            var least = heuristic(start, goal)
            for(node in cameFrom.filterNotNull()){
                val dist = heuristic(node, goal)
                if (dist < least){
                    least = dist
                    current = node
                }
            }
        }

        path.add(current)

        while(current != start){
            val potential = cameFrom.get(current)
            if(potential != null){
                path.add(potential)
                current = potential
            }
            else{
                break
            }
        }
        path.reverse()
        return path.toTypedArray()
    }

    fun pathfind(start : Int, goal : Int, limit : Int = -1) : Array<Int>{
        val potentials = AStar(start, goal, limit)
        val path = reconstructPath(potentials, start, goal)
        return path
    }

    fun pathfind(startX : Int, startY : Int, goalX : Int, goalY : Int, limit : Int = -1) : Array<Int>{
        val start = costMap.pointToIndex(startX, startY)
        val end = costMap.pointToIndex(goalX, goalY)
        return pathfind(start, end, limit)
    }




}