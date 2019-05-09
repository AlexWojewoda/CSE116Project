package game.model.physics

class PhysicalObject(var location: PhysicsVector, var velocity: PhysicsVector) extends GameObject {
  def onGround(): Unit = {}
  def collide(): Unit = {}
}
