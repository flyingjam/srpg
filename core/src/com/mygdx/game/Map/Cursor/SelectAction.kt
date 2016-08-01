package com.mygdx.game.Map.Cursor

import com.mygdx.game.InputSystem.addListener
import com.mygdx.game.InputSystem.removeListener
import com.mygdx.game.UI.ActionList
import com.mygdx.game.Units.Factory
import com.mygdx.game.Units.GameUnit

/**
 * Created by FlyingJam on 7/30/2016.
 */

class SelectAction(val cursor : Cursor, val unit : GameUnit) : ImplementedCursorAction(){
    val x = cursor.parent.mapToWorldX(cursor.x) + cursor.parent.tileWidth
    val y = cursor.parent.mapToWorldY(cursor.y)
    val list = ActionList(cursor.parent.parentScreen.skin, unit ?: Factory.test(), x, y, cursor)
    val stage = cursor.parent.parentScreen.stage

    override fun onEnter(){
        cursor.disable()
        stage.addActor(list.container)
        addListener(list)
    }

    override fun onExit(){
        cursor.enable()
        list.container.remove()
        removeListener(list)
    }
}