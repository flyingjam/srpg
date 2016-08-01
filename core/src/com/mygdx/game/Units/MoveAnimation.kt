package com.mygdx.game.Units

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Map.GameMap

/**
 * Created by FlyingJam on 7/26/2016.
 */

class MoveAnimation(val unit: GameUnit,
                        val path: Array<Int>,
                        val map: GameMap,
                        val time: Float = 0.5f){
    val draw = unit.getDrawing()

    var screenCoordinates = Vector2()
    val originalPos = Vector2()
    val goalPos = Vector2()

    var timeAccumulator = 0f

    var index = 2

    var done = false

    init{

        if(path.size > 1) {
        val x = map.path.costMap.indexToX(path[0])
        val y = map.path.costMap.indexToY(path[0])
        goalPos.x = map.mapToWorldX(x, draw.getWidth())
        goalPos.y = map.mapToWorldY(y, draw.getHeight())

        move(path[1])
    }
    else{
        done = true
    }

}

    fun isDone() : Boolean{
        return done
    }

    fun move(x : Int, y : Int){
        originalPos.x = goalPos.x
        originalPos.y = goalPos.y

        goalPos.x = map.mapToWorldX(x, draw.getWidth())
        goalPos.y = map.mapToWorldY(y, draw.getHeight())
    }

    fun move(index : Int){
        val x = map.path.costMap.indexToX(index)
        val y = map.path.costMap.indexToY(index)
        move(x, y)
    }

    fun draw(batch : SpriteBatch){
        draw.draw(batch, screenCoordinates.x, screenCoordinates.y)
    }

    fun update(dt : Float){
        val size = path.size
        if(index <= path.size) {
            timeAccumulator += dt
            val alpha = timeAccumulator / time
            if (alpha <= 1) {
                screenCoordinates.x = MathUtils.lerp(originalPos.x, goalPos.x, alpha)
                screenCoordinates.y = MathUtils.lerp(originalPos.y, goalPos.y, alpha)
            }
            else{
                screenCoordinates.x = goalPos.x
                screenCoordinates.y = goalPos.y
                timeAccumulator = 0f
                if(index >= path.size){
                    move(path[path.size-1])
                }
                else{
                    move(path[index])
                }
                index++
            }
        }
        else{
            println("lol")
            done = true
        }
    }

}
