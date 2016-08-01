package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy
import com.badlogic.gdx.graphics.g3d.decals.Decal
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.InputSystem.INPUTS
import com.mygdx.game.InputSystem.inputConverter
import com.mygdx.game.InputSystem.setKey
import com.mygdx.game.Map.*
import com.mygdx.game.Screens.MapScreen
import com.mygdx.game.Screens.TempScreen
import java.util.*

/*
fun draw_map_lines(map : Map, shape : ShapeRenderer){
    shape.setColor(1f, 1f, 1f, 1f)
    for (column in 0..(map.width - 1)){
        val x : Float = (column * map.tileW).toFloat()
        shape.line(x, 0f, x, (map.height * map.tileH).toFloat())
    }

    for(row in 0..(map.height - 1)){
        val y = (row * map.tileH).toFloat()
        shape.line(0f, y, (map.width * map.tileH).toFloat(), y)
    }
}

fun draw_map_squares(map : Map, shape : ShapeRenderer){
    for(x in 0..(map.width-1)){
        for(y in 0..(map.height-1)){
            val tile = map.get(x, y)
            if(tile != null){
                val color = (tile == Tile.YELLOW)
                if(tile == Tile.YELLOW) {
                    shape.setColor(0.5f, 0.5f, 0.5f, 1f)
                }
                else if (tile == Tile.BROWN){
                    shape.setColor(0f, 0.5f, 0.5f, 1f)
                }
                else {
                    shape.setColor(1f, 1f, 1f, 1f)
                }

                val x_coor = x * map.tileW
                val y_coor = y * map.tileH
                //print("Drawing $x and $y which is $color \n")
                shape.rect(x_coor.toFloat(), y_coor.toFloat(), map.tileW.toFloat(), map.tileH.toFloat())
            }
            shape.setColor(1f, 1f, 1f, 1f)
        }
    }
}

fun draw_path(map : Map, path : ArrayList<Int>, shape : ShapeRenderer) {

    for (index in path) {
        val x = map.indexToX(index) * map.tileW
        val y = map.indexToY(index) * map.tileH
        shape.setColor(0f, 1f, 0f, 1f)
        shape.rect(x.toFloat(), y.toFloat(), map.tileW.toFloat(), map.tileH.toFloat())
    }
}

*/

class MyGdxGame : Game() {
    internal lateinit var batch: SpriteBatch
    internal lateinit var img: Texture
    internal lateinit var tr : TextureRegion


    internal lateinit var shape : ShapeRenderer
    internal lateinit var bpath : ArrayList<Int>

    internal lateinit var map : PathMap
    internal lateinit var path : Array<Int>

    internal lateinit var decal : Decal
    internal lateinit var dbatch : DecalBatch

    internal lateinit var camera : PerspectiveCamera
    internal lateinit var controller : CameraGroupStrategy

    internal lateinit var gmap : GameMap

    override fun create() {
        Gdx.input.inputProcessor = inputConverter
        setKey(Input.Keys.UP, INPUTS.UP)
        setKey(Input.Keys.DOWN, INPUTS.DOWN)
        setKey(Input.Keys.LEFT, INPUTS.LEFT)
        setKey(Input.Keys.RIGHT, INPUTS.RIGHT)
        setKey(Input.Keys.ENTER, INPUTS.ACTIVATE)
        this.setScreen(MapScreen(this))
    }

    override fun render(){
        super.render()
    }

    override fun dispose(){
    }
}
