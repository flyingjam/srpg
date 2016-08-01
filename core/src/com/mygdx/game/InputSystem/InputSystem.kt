package com.mygdx.game.InputSystem

import com.badlogic.gdx.InputAdapter
import java.util.*

/**
 * Created by FlyingJam on 8/1/2016.
 */
enum class INPUTS { ACTIVATE, CANCEL, UP, DOWN, LEFT, RIGHT }

open class ConvertedInputListener(){

    open fun keyDown(input : INPUTS) : Boolean{
        return false
    }

    open fun keyUp(input : INPUTS) : Boolean{
        return false
    }

    open fun mouseMoved(screenX : Int, screenY : Int) : Boolean{
        return false
    }

    open fun touchDown(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean{
        return false
    }

    open fun touchUp(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean{
        return false
    }

}

val InputListeners = com.badlogic.gdx.utils.Array<ConvertedInputListener>()

val KeyMap = java.util.HashMap<Int, INPUTS>()

fun setKey(key : Int, action : INPUTS){
    KeyMap.set(key, action)
}

fun removeKey(key : Int){
    KeyMap.remove(key)
}

fun addListener(thing : ConvertedInputListener){
    println("added")
    InputListeners.add(thing)
}

fun removeListener(thing : ConvertedInputListener){
    InputListeners.removeValue(thing, true)
}

class InputConverter : InputAdapter() {

    override fun keyDown(input : Int) : Boolean{
        for(inputListeners in InputListeners){
            if(KeyMap.containsKey(input)){
                val action = KeyMap.get(input)
                if(action != null)
                    if(inputListeners.keyDown(action)) return true
            }
        }
        return false
    }

    override fun keyUp(input : Int) : Boolean{
        for(inputListeners in InputListeners){
            if(KeyMap.containsKey(input)){
                val action = KeyMap.get(input)
                if(action != null)
                    if(inputListeners.keyUp(action)) return true
            }
        }
        return false
    }

    override fun mouseMoved(screenX : Int, screenY : Int) : Boolean{
        for(inputListeners in InputListeners){
            if(inputListeners.mouseMoved(screenX, screenY)) return true
        }
        return false
    }

    override fun touchDown(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean{
        for(inputListeners in InputListeners){
            if(inputListeners.touchDown(screenX, screenY, pointer, button)) return true
        }
        return false
    }

    override fun touchUp(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean{
        for(inputListeners in InputListeners){
            if(inputListeners.touchUp(screenX, screenY, pointer, button)) return true
        }
        return false
    }

}

val inputConverter = InputConverter()
