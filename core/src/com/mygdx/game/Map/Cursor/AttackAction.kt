package com.mygdx.game.Map.Cursor

import com.mygdx.game.Units.GameUnit

/**
 * Created by FlyingJam on 7/31/2016.
 */

//just a test

class AttackAction(cursor: Cursor, unit : GameUnit) : SelectMenuAction(cursor, unit){


    override fun onEnter() {
        println("Entered Attack")
    }


    override fun condition() : Boolean{
        return false
    }


    override fun toString() : String{
        return "Attack"
    }
}
