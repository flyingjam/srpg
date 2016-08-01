package com.mygdx.game.Screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.InputProcessorStack
import com.mygdx.game.InputSystem.inputConverter
import com.mygdx.game.Map.Cursor.Cursor
import com.mygdx.game.Map.GameMap
import com.mygdx.game.UI.ActionList

/**
 * Created by FlyingJam on 7/12/2016.
 */

class MapScreen (val parent : Game): Screen {


    val stage = Stage()
    val skin = Skin(Gdx.files.internal("uiskin.json"))

    val button = TextButton("Test", skin, "default")
    val list = List<String>(skin)//com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin)
    val scroll = ScrollPane(list)


    val input = InputMultiplexer()

    val listener = object: ChangeListener(){
        override fun changed (event : ChangeEvent, actor : Actor){
            println("Uh, something")
        }
    }

    init{
        input.addProcessor(stage)
        input.addProcessor(inputConverter)
        //Gdx.input.inputProcessor = input

        list.setItems("a", "b", "c", "d")
        scroll.setPosition(100f, 100f)
        scroll.width = 100f
    }

    val map = GameMap(10, 4, 64, 64, this)

    override fun dispose(){

    }

    override fun hide(){

    }

    override fun pause(){

    }

    override fun render(dt : Float){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_STENCIL_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)
        Gdx.gl20.glDepthMask(true)

        map.render()
        stage.draw()

        map.update(dt)
    }

    override fun resize(width : Int, height : Int){

    }

    override fun resume(){

    }

    override fun show(){

    }
}