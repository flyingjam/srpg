package com.mygdx.game.Screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.mygdx.game.Map.GameMap

/**
 * Created by FlyingJam on 7/12/2016.
 */

class TempScreen (val parent : Game): Screen{


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


    }

    override fun resize(width : Int, height : Int){

    }

    override fun resume(){

    }

    override fun show(){

    }
}
