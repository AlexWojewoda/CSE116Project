package game.model

import game.model.physics.{PhysicalObject, PhysicsVector}

class Player(inputLocation: PhysicsVector,
             inputVelocity: PhysicsVector) extends PhysicalObject(inputLocation, inputVelocity) {

  val speed: Double = 6.5
  var points: Int = 0

  def move(direction: PhysicsVector){
    val normalDirection = direction.normal2d()
    this.velocity = new PhysicsVector(normalDirection.x * speed, normalDirection.y * speed, 0.0)
  }

  def stop(): Unit ={
    this.velocity = new PhysicsVector(0, 0, 0)
  }

}
