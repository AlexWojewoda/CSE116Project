package game

import game.model.Game
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.TextField
import scalafx.scene.layout.GridPane

object GUI extends JFXApp{
  val game = new Game()

  this.stage = new PrimaryStage{
    title = "CSE116Project"
    scene = new Scene(){
    }
  }
}
