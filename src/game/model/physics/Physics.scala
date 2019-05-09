package game.model.physics

object Physics {
  def computePotentialLocation(a: PhysicalObject, deltaTime: Double): PhysicsVector ={
    var xpos: Double = a.location.x + (a.velocity.x * deltaTime)
    var ypos: Double = a.location.y + (a.velocity.y * deltaTime)
    var zpos: Double = a.location.z + (a.velocity.z * deltaTime)
    if (zpos < 0.0){
      zpos = 0.0
    }
    var obj: PhysicsVector = new PhysicsVector(xpos, ypos, zpos)
    obj
  }

  def updateVelocity(a: PhysicalObject, w: World, deltaTime: Double): Unit ={
    var vel: Double = a.velocity.z + (-w.g * deltaTime)
    if (a.location.z < 0.00000001 && a.velocity.z < 0.00000001){
      a.velocity.z = 0.0

    }
    else{
      a.velocity.z = vel

    }
  }

  def detectCollision(a: PhysicalObject, pLoc: PhysicsVector, b: Boundary): Boolean ={
    var z: Double = (a.location.y - pLoc.y) * (b.endpt.x - b.startpt.x) - (a.location.x - pLoc.x) * (b.endpt.y - b.startpt.y)
    var c: Double = ((a.location.x - pLoc.x) * (b.startpt.y - pLoc.y) - (a.location.y - pLoc.y) * (b.startpt.x - pLoc.x))/z
    var d: Double = ((b.endpt.x - b.startpt.x) * (b.startpt.y - pLoc.y) - (b.endpt.y - b.startpt.y) * (b.startpt.x - pLoc.x))/z
    if(c >= 0.0 && c <= 1.0 && d >= 0.0 && d <= 1.0){
      false
    }
    else {
      true
    }
  }

  def updateWorld(w: World, deltaTime: Double): Unit ={
    for(a <- w.objects) {
      var count: Int = 0
      updateVelocity(a, w, deltaTime)
      for(b <- w.boundaries) {
        if (detectCollision(a, computePotentialLocation(a, deltaTime), b) == false) {
          count = count + 1
        }
      }
        if (count > 0) {
          a.location.z = computePotentialLocation(a, deltaTime).z
        }
        else {
          a.location.x = computePotentialLocation(a, deltaTime).x
          a.location.y = computePotentialLocation(a, deltaTime).y
          a.location.z = computePotentialLocation(a, deltaTime).z
        }
    }
  }

  def main(args: Array[String]): Unit ={
    val earth: World = new World(9.81)
    val ball: PhysicalObject = new PhysicalObject(new PhysicsVector(0.0, 0.0, 0.0), new PhysicsVector(1.0, 2.0, 10.0))
    val bound: Boundary = new Boundary(new PhysicsVector(10.0, 10.0, 10.0), new PhysicsVector(20.0, 20.0, 20.0)) //return false
    val bound1: Boundary = new Boundary(new PhysicsVector(0.0, 0.0, 0.0), new PhysicsVector(10.0, 10.0, 10.0)) // true
    val bound2: Boundary = new Boundary(new PhysicsVector(4.0, -10.0, 0.0), new PhysicsVector(4.0, 10.0, 0.0)) //false
    earth.objects = List(ball)
    earth.boundaries = List(bound2)
    var time: Double = 0.0
    var endofTime: Double = 2.5
    var deltaTime: Double = 1.0
    var times = List(time)
    var zVelocity = List(ball.velocity.z)
    var xpos = List(ball.location.x)
    var ypos = List(ball.location.y)
    var height = List(ball.location.z)
    while(time < endofTime){
      Physics.updateWorld(earth, deltaTime)
      time += deltaTime
      times = times :+ time
      zVelocity = zVelocity :+ ball.velocity.z
      height = height :+ ball.location.z
      xpos = xpos :+ ball.location.x
      ypos = ypos :+ ball.location.y
    }
    println(times.mkString("\t"))
    println(zVelocity.mkString("\t"))
    println(height.mkString("\t"))
    println(xpos.mkString("\t"))
    println(ypos.mkString("\t"))
  }

}
