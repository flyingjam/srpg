package com.mygdx.game.TileMap

/**
 * Created by FlyingJam on 6/16/2016.
 */

class Tile{

}

class Map(val width : Int = 32, val height : Int = 32, val tileW : Int = 32, val tileH : Int = 32){

    val internalArray : Array<Tile?> = arrayOfNulls(width * height)

    fun add(x : Int, y : Int, tile : Tile){
        val index = convertPoint(x, y)
        internalArray[index] = tile
    }

    fun get(x : Int, y : Int) : Tile? {
        //hey
        val index = pointToIndex(x, y)
        return internalArray[index]
    }

    fun pointToIndex(x : Int, y : Int) : Int{
        return (x + y*(width - 1))
    }

    fun indexToPoint(index : Int) : Pair<Int, Int>{
        val y = indexToY(index)
        val x = indexToX(index)
        return Pair(y, x)
    }

    fun getNeighbors(x : Int, y : Int) : Array<Int>{
        var north = clamp(y - 1, 0, height - 1);
        var south = clamp(y + 1, 0, height - 1);
        var east = clamp(x - 1, 0, width - 1);
        var west = clamp(x + 1, 0, width - 1);

        return arrayOf(pointToIndex(x, north), pointToIndex(x, south), pointToIndex(east, y), pointToIndex(west, y));
    }

    //using these functions saves an object allocation
    fun indexToX(index : Int) : Int{

        return (index % (width - 1))
    }

    fun indexToY(index : Int) : Int {
        return (index / (width - 1))
    }

    private fun convertPoint(x : Int, y : Int) : Int{
        return (x  + y * width)
    }

    private fun clampCoordinate(){

    }

    private fun clamp(value : Int, min : Int, max : Int) : Int{
        if(value < min) return min
        else if (value > max) return max
        else return value
    }
}
