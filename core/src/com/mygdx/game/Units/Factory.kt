package com.mygdx.game.Units

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Map.Cursor.AttackAction
import com.mygdx.game.Map.Cursor.*
import com.mygdx.game.Units.Components.ActionComponent
import com.mygdx.game.Units.Components.TextureDrawingComponent

/**
 * Created by FlyingJam on 7/9/2016.
 */

class Factory{
    companion object Factory{
        fun test(x : Int = 0, y : Int = 0) : GameUnit{
            val move = ActionComponent(::EndAction, ::EndAction, ::AttackAction)
            val unit = GameUnit(x, y, TextureDrawingComponent(Texture("char.png")), move)
            return unit
        }
    }
}