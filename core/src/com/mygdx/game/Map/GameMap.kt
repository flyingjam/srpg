package com.mygdx.game.Map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.mygdx.game.InputSystem.ConvertedInputListener
import com.mygdx.game.InputProcessorStack
import com.mygdx.game.InputSystem.addListener
import com.mygdx.game.Map.Cursor.Cursor
import com.mygdx.game.Screens.MapScreen
import java.util.*
import com.mygdx.game.Units.*

/**
 * Created by FlyingJam on 7/9/2016.
 */

class GameMap(val width : Int,
              val height : Int,
              val tileWidth : Int,
              val tileHeight : Int,
              val parentScreen : MapScreen) {

    val path = PathMap(width, height)
    val units = ArrayList<GameUnit>(22)
    val batch = SpriteBatch()
    val shape = ShapeRenderer()

    val cursor = Cursor(2, 2, this)
    val unit = Factory.test(2, 3)

    internal lateinit var region : ArrayList<Int>

    init {
        setInt(path, testMap)
        units.add(unit)
        units.add(Factory.test(8,0))
        region = path.movableRegion(0, 10)

        addListener(cursor)

        val wx = cursor.getMapX()
        val xy = cursor.getMapY()
    }

    fun mapToWorld(x: Int, y: Int, width: Int = tileWidth, height: Int = tileHeight): Pair<Float, Float> {
        return Pair(mapToWorldX(x, width), mapToWorldY(y, height))
    }

    fun mapToWorldX(x: Int, width: Int = tileWidth): Float {
        val offset = (tileWidth - width) / 2
        return (x * tileWidth).toFloat() + offset
    }

    fun getUnit(x : Int, y : Int) : GameUnit?{
        val query = units.find {unit -> unit.getMapX() == x && unit.getMapY() == y}
        return query
    }

    fun mapToWorldY(y: Int, height: Int = tileHeight): Float {
        val offset = (tileHeight - height) / 2
        val realY = y * tileHeight
        return (Gdx.graphics.height - realY - tileHeight).toFloat() + offset
    }

    fun drawUnits() {
        for(unit in units){
            drawMapObject(unit)
        }
    }

    var tac = 0f

    fun update(dt : Float){
        cursor.update(dt)
    }

    fun render(){
        shape.begin(ShapeRenderer.ShapeType.Line)
        drawPathMap(path, shape, tileWidth, tileHeight)
        //drawPath(path, shape, p, tileWidth, tileHeight)
        val reg = cursor.movableRegion
        if(reg != null){
            drawRegion(path, shape, reg, tileWidth, tileHeight)
        }

        val p = cursor.path
        if(p != null){
            drawPath(path, shape, p, tileWidth, tileHeight)
        }

        shape.end()

        batch.begin()
        drawUnits()
        drawMapObject(cursor)
        cursor.render(batch)
        batch.end()
    }

    fun drawMapObject(obj : MapRenderable){
        val draw = obj.getDrawing()

        if(!draw.invisible()) {
            val x = mapToWorldX(obj.getMapX(), draw.getWidth())
            val y = mapToWorldY(obj.getMapY(), draw.getHeight())

            draw.draw(batch, x, y)
        }
    }
}