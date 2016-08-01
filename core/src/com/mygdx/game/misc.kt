package com.mygdx.game

import com.badlogic.gdx.InputProcessor
import java.util.*

/**
 * Created by FlyingJam on 7/6/2016.
 */

fun clamp(value : Int, min : Int, max : Int) : Int{
    return Math.max(min, Math.min(max, value))
}

class InputProcessorStack : InputProcessor {

    val stack = ArrayDeque<InputProcessor>()

    fun push(processor : InputProcessor){
        stack.addFirst(processor)
    }

    fun pop() {
        stack.removeFirst()
    }

    fun get(){
        stack.peekFirst()
    }

    override fun keyDown(key: Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.keyDown(key)
        }
        return false
    }

    override fun keyUp(key: Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.keyUp(key)
        }
        return false
    }

    override fun keyTyped(character : Char) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.keyTyped(character)
        }
        return false
    }

    override fun touchDown(x : Int, y : Int, pointer : Int, button : Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.touchDown(x, y, pointer, button)
        }
        return false
    }

    override fun touchUp(x : Int, y : Int, pointer : Int, button : Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.touchUp(x, y, pointer, button)
        }

        return false
    }

    override fun touchDragged(x : Int, y : Int, pointer : Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.touchDragged(x, y, pointer)
        }
        return false
    }

    override fun mouseMoved(x : Int, y : Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.mouseMoved(x, y)
        }

        return false
    }

    override fun scrolled(amount : Int) : Boolean{
        val processor = stack.peekFirst()
        if(processor != null){
            return processor.scrolled(amount)
        }
        return false
    }
}