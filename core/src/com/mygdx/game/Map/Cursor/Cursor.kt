package com.mygdx.game.Map.Cursor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.InputSystem.INPUTS
import com.mygdx.game.InputSystem.ConvertedInputListener
import com.mygdx.game.Map.GameMap
import com.mygdx.game.Map.MapRenderable
import com.mygdx.game.Units.Components.DrawingComponent
import com.mygdx.game.Units.GameUnit
import com.mygdx.game.Units.Components.TextureDrawingComponent
import java.util.*

/**
 * Created by FlyingJam on 7/12/2016.
 */

class Cursor(var x : Int, var y : Int, val parent : GameMap) : MapRenderable, ConvertedInputListener() {

    enum class CursorState{
        NONE, MOVE
    }
    //temp
    val texture = Texture("cursor.png")
    val draw = TextureDrawingComponent(texture)
    var disable = false

    var hold : GameUnit? = null
    var movableRegion : ArrayList<Int>? = null
    var path : Array<Int>? = null

    var action : CursorAction = DefaultAction(this)

    fun update(dt : Float){
        action.update(dt)
    }

    fun render(batch : SpriteBatch){
        action.render(batch)
    }

    fun disable(){
        disable = true
    }

    fun enable(){
        disable = false
    }

    override fun getMapX() : Int{
        return x
    }

    override fun getMapY() : Int{
        return y
    }

    override fun getDrawing() : DrawingComponent {
        return draw
    }

    fun moveUp(){
        y -= 1
        moved()
    }

    fun moveDown(){
        y += 1
        moved()
    }

    fun moveLeft(){
        x -= 1
        moved()
    }

    fun moveRight(){
        x += 1
        moved()
    }

    fun beginMove(){

    }

    fun endMove(){

    }

    fun activate(){
        action.activate()
    }

    fun moved(){
        action.moved(x, y)
    }

    fun switch(newState : CursorAction){
        action.onExit()
        action = newState
        action.onEnter()
    }

    override fun keyDown(key : INPUTS) : Boolean{
        if(!disable) {
            when (key) {
                INPUTS.UP -> moveUp()
                INPUTS.DOWN -> moveDown()
                INPUTS.LEFT -> moveLeft()
                INPUTS.RIGHT -> moveRight()
                INPUTS.ACTIVATE -> activate()
            }
        }
        return false
    }

}
