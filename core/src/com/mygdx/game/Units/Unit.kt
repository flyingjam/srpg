package com.mygdx.game.Units

import com.badlogic.gdx.maps.MapRenderer
import com.mygdx.game.Map.MapRenderable
import com.mygdx.game.Units.Components.ActionComponent
import com.mygdx.game.Units.Components.DrawingComponent

/**
 * Created by FlyingJam on 6/29/2016.
 */

class Stats(var health : Int = 10, var strength : Int = 10, var defense : Int = 10, var move : Int = 5)

class GameUnit(var x : Int = 0,
               var y : Int = 0,
               var draw : DrawingComponent,
               var actions : ActionComponent) : MapRenderable{
    var stat = Stats(10, 10, 5)
    var weapon = Weapon()

    override fun getDrawing() : DrawingComponent {
        return draw
    }

    override fun getMapX() : Int{
        return x
    }

    override fun getMapY() : Int {
        return y
    }

}
