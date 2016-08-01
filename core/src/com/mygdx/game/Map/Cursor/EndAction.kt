package com.mygdx.game.Map.Cursor

import com.mygdx.game.Units.GameUnit

/**
 * Created by FlyingJam on 7/31/2016.
 */

class EndAction(cursor : Cursor, unit : GameUnit) : SelectMenuAction(cursor, unit){

    override fun condition() : Boolean{
        return true
    }

    override fun onEnter(){
        //TODO: should also disable unit
        cursor.switch(DefaultAction(cursor))
    }

    override fun toString() : String{
        return "End"
    }

}