package com.mygdx.game.UI

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.InputSystem.ConvertedInputListener
import com.mygdx.game.InputSystem.INPUTS
import com.mygdx.game.Map.Cursor.Cursor
import com.mygdx.game.Map.Cursor.CursorAction
import com.mygdx.game.Map.Cursor.DefaultAction
import com.mygdx.game.Units.GameUnit


/**
 * Created by FlyingJam on 7/24/2016.
 */

class Test{
    override fun toString() : String{
        return "test"
    }
}

class ActionList(skin : Skin, val unit : GameUnit, x : Float, y : Float, val cursor : Cursor) : ConvertedInputListener(){

    val list = List<CursorAction>(skin)
    val container = ScrollPane(list, skin)

    val previousActor : Actor? = null

    var changed = false

    fun activated(action : CursorAction){
        cursor.switch(action)
    }

    fun moveDown(){
        list.items.size
        list.selectedIndex =
                if(list.selectedIndex + 1 < list.items.size)
                    list.selectedIndex + 1
                else
                    0
    }

    fun moveUp(){
         list.selectedIndex =
                if(list.selectedIndex - 1 >= 0)
                    list.selectedIndex - 1
                else
                    list.items.size - 1
    }

    override fun keyDown(input : INPUTS) : Boolean{
        when(input){
            INPUTS.DOWN -> moveDown()
            INPUTS.UP -> moveUp()
            INPUTS.ACTIVATE -> activated(list.selected)
        }
        return false
    }

    init{
        val actions = unit.actions.initialize(cursor, unit).filter {it.condition()}.toTypedArray()
        val gdxArray = com.badlogic.gdx.utils.Array<CursorAction>(actions)
        list.setItems(gdxArray)
        //calculate position

        container.setPosition(x.toFloat(), y.toFloat())
        //list.setItems(DefaultAction(cursor))
        //list.setItems("Move", "Item", "End")

        list.addListener(object: InputListener(){
            override fun touchDown(event : InputEvent, x : Float, y : Float, point : Int, button : Int) : Boolean {
                if(!changed){
                    val action = list.selected
                    activated(action)
                }
                changed = false
                return true
            }
        })

        list.addListener(object: ChangeListener(){
            override fun changed(event : ChangeEvent, actor: Actor){
                changed = true
            }
        })
}

}