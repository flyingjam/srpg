package com.mygdx.game.Units.Components

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Units.GameUnit

/**
 * Created by FlyingJam on 7/9/2016.
 */

abstract class Component{
    abstract fun update(dt : Float)
}

abstract class DrawingComponent : Component(){
    enum class DrawingMode {NORMAL, LEFT, RIGHT, UP, DOWN}
    abstract fun draw(batch : SpriteBatch, x : Float, y : Float, mode : DrawingMode = DrawingMode.NORMAL)
    abstract fun getWidth(): Int
    abstract fun getHeight() : Int
    abstract fun invisible() : Boolean
    abstract fun invisible(inv : Boolean)
}

class TextureDrawingComponent(val texture : Texture) : DrawingComponent(){

    var invisible = false

    override fun update(dt : Float){
    }

    override fun draw(batch : SpriteBatch, x : Float, y : Float, mode : DrawingMode){
        batch.draw(texture, x, y)
    }

    override fun getWidth() : Int{
        return texture.width
    }

    override fun getHeight() : Int{
        return texture.height
    }

    override fun invisible() : Boolean{
        return invisible
    }

    override fun invisible(inv : Boolean) {
        invisible = inv
    }


}

abstract class CombatComponent : Component(){
    abstract fun attack(self : GameUnit, enemy: GameUnit)
}

class DefaultCombatComponent : CombatComponent(){
    override fun update(dt : Float){}
    override fun attack(self : GameUnit, enemy: GameUnit){
        var enemydamage = self.weapon.getMT() + self.stat.strength - enemy.stat.defense
        var selfdamage = enemy.weapon.getMT() + enemy.stat.strength - self.stat.defense
    }
}
