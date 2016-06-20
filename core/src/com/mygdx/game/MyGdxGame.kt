package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.TileMap.Map


fun draw_map_lines(map : Map, shape : ShapeRenderer){

    for (column in 0..(map.width - 1)){
        val x : Float = (column * map.tileW).toFloat()
        shape.line(x, 0f, x, (map.height * map.tileH).toFloat())
    }

    for(row in 0..(map.height - 1)){
        val y = (row * map.tileH).toFloat()
        shape.line(0f, y, (map.width * map.tileH).toFloat(), y)
    }
}

class MyGdxGame : ApplicationAdapter() {
    internal lateinit var batch: SpriteBatch
    internal lateinit var img: Texture


    var map : Map = Map()
    internal lateinit var shape : ShapeRenderer

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        shape = ShapeRenderer()
        val whatever = woo()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        shape.begin(ShapeRenderer.ShapeType.Line)
        draw_map_lines(map, shape)
        shape.end()
    }
}
