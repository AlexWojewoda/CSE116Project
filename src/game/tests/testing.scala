package game.tests

import game.model.{Game, Player}
import game.model.physics.PhysicsVector
import org.scalatest.FunSuite
import play.api.libs.json.Json

class testing extends FunSuite{
  test("test cases"){
    val game = new Game()
    val player = new Player(new PhysicsVector(0,0,0), new PhysicsVector(0,0,0))
  }
}
