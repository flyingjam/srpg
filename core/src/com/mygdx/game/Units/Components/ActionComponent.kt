package com.mygdx.game.Units.Components

import com.mygdx.game.Map.Cursor.Cursor
import com.mygdx.game.Map.Cursor.CursorAction
import com.mygdx.game.Units.GameUnit
import java.util.*

/**
 * Created by FlyingJam on 7/31/2016.
 */

class ActionComponent(vararg constructors : (Cursor, GameUnit) -> CursorAction) : Component(){
    val actions = ArrayList<(Cursor, GameUnit) -> CursorAction>()

    init{
        for(con in constructors){
            actions.add(con)
        }
    }

    fun add(vararg constructors : (Cursor, GameUnit) -> CursorAction){
        for(constructor in constructors){
            actions.add(constructor)
        }
    }


    fun initialize(cursor : Cursor, unit : GameUnit) : Array<CursorAction>{
        return actions.map { it(cursor, unit) }.toTypedArray()
    }

    override fun update(dt : Float){}
}

