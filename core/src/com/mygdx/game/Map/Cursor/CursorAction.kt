package com.mygdx.game.Map.Cursor

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Units.GameUnit
import com.mygdx.game.Units.MoveAnimation

/**
 * Created by FlyingJam on 7/26/2016.
 */

abstract class CursorAction(){
    abstract fun update(dt : Float)
    abstract fun render(batch : SpriteBatch)
    abstract fun activate()
    abstract fun onEnter()
    abstract fun onExit()
    abstract fun condition() : Boolean
    abstract fun moved(x : Int, y : Int)
}

open class ImplementedCursorAction() : CursorAction(){
    override fun update(dt : Float){

    }

    override fun render(batch : SpriteBatch) {

    }

    override fun activate(){

    }

    override fun onEnter(){

    }

    override fun onExit(){

    }

    override fun condition() : Boolean{
        return false
    }

    override fun moved(x : Int, y : Int){

    }
}

open class SelectMenuAction(val cursor : Cursor, val unit : GameUnit) : ImplementedCursorAction(){

}

class DefaultAction(val cursor : Cursor) : CursorAction(){

    override fun update(dt : Float){

    }

    override fun render(batch : SpriteBatch) {

    }


    override fun activate(){
        val unit = cursor.parent.getUnit(cursor.x, cursor.y)
        if(unit != null){
            cursor.switch(MoveAction(cursor))
        }
    }

    override fun onExit(){

    }

    override fun onEnter(){

    }

    override fun condition() : Boolean{
        return true
    }

    override fun moved(x : Int, y : Int){

    }

    override fun toString() : String{
        return "End"
    }
}

class MoveAction(val cursor : Cursor) : CursorAction(){

    var animation : MoveAnimation? = null
    var holdx = 0
    var holdy = 0

    val speed = 0.08f

    override fun activate(){
        //val index = cursor.parent.path.costMap.pointToIndex(cursor.x, cursor.y)
        if(isReleasable()){
            startAnimation()
        }
    }

    fun startAnimation(){
        val unit = cursor.hold
        val path = cursor.path
        unit?.draw?.invisible(true)
        val nullity = path == null
        println("Path is: $nullity")
        if(unit != null && path != null){
            animation = MoveAnimation(unit, path, cursor.parent, speed)
        }
    }

    fun endAnimation(){
        cursor.hold?.draw?.invisible(false)
        cursor.path = null
        cursor.movableRegion = null

        val unit = cursor.hold
        if(unit != null){
            cursor.switch(SelectAction(cursor, unit))
        }
    }

    override fun update(dt : Float){
        animation?.update(dt)
        if(animation?.isDone() ?: false){
            releaseUnit()
            endAnimation()
        }
    }

    override fun render(batch : SpriteBatch) {
        animation?.draw(batch)
    }

    override fun onExit() {

    }

    override fun onEnter() {
        holdUnit()
    }

    override fun moved(x : Int, y : Int){
        cursor.path = cursor.parent.path.pathfind(holdx, holdy, x, y)
    }

    override fun condition() : Boolean{
        //A unit must be under the cursor
        val unit = cursor.parent.getUnit(cursor.x, cursor.y)
        return unit != null
    }

    fun holdUnit(){
        val unit = cursor.parent.getUnit(cursor.x, cursor.y)
        if(unit != null){
            cursor.hold = unit
            cursor.movableRegion = cursor.parent.path.movableRegion(unit.getMapX(),
                    unit.getMapY(), unit.stat.move)
            holdx = cursor.x
            holdy = cursor.y
            cursor.path = cursor.parent.path.pathfind(holdx, holdy, holdx, holdy)
        }
    }

    fun isReleasable() : Boolean{
        val region = cursor.movableRegion
        if(region != null){
            val index = cursor.parent.path.costMap.pointToIndex(cursor.x, cursor.y)
            val pos = region.find {it == index}
            if(pos != null){
                return true
            }
        }
        return false
    }


    fun releaseUnit(){
        val region = cursor.movableRegion
        if(region != null){
            val index = cursor.parent.path.costMap.pointToIndex(cursor.x, cursor.y)
            val pos = region.find {it == index}
            val unit = cursor.hold
            if(pos != null && unit != null){
                //temp
                unit.x = cursor.x
                unit.y = cursor.y
            }
        }
    }
}

