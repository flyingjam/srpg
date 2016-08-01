package com.mygdx.game.Map

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Units.Components.DrawingComponent

/**
 * Created by FlyingJam on 7/12/2016.
 */

interface MapRenderable{
    fun getDrawing() : DrawingComponent
    fun getMapX() : Int
    fun getMapY() : Int
}
