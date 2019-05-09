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
//    import java.awt.geom.Line2D
//    val line1 = new Line2D.Double(b.startpt.x, b.startpt.y, b.endpt.x, b.endpt.y)
//    val line2 = new Line2D.Double(a.location.x, a.location.y, pLoc.x, pLoc.y)
//    val result = line2.intersectsLine(line1)
//    if(result == true){
//      false
//    }
//    else{
//      true
//    }


//        var ax = b.endpt.x - b.startpt.x
//        var bx = pLoc.x - a.location.x
//        var ay = b.endpt.y - b.startpt.y
//        var by = pLoc.y - a.location.y
//
//        var c = -ay * (b.startpt.x - a.location.x) + ax * (b.startpt.y - a.location.y) / (-bx * ay + ax * by)
//        var d = bx * (b.startpt.y - a.location.y) - by * (b.startpt.x - a.location.x) / (-bx * ay + ax * by)
//
//        if(c >= 0 && c <= 1 && d >= 0 && d <= 1){
//          false
//        }
//        else{
//          true
//        }
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


//def main(args: Array[String]): Unit ={
//    var w: World = new World(9.8)
//    var location: PhysicsVector = new PhysicsVector(10, 10, 5)
//    var velocity: PhysicsVector = new PhysicsVector(5, 3, 1)
//    var one: PhysicalObject = new PhysicalObject(location, velocity)
//    var loc: PhysicsVector = new PhysicsVector(3, 4, 1)
//    var vel: PhysicsVector = new PhysicsVector(2, 4, 5)
//    var two: PhysicalObject = new PhysicalObject(loc, vel)
//    updateVelocity(two, w, 10.0)
//    println(two.velocity.z)
//  }

////val line: Double = sqrt(pow(b.endpt1.x - b.endpt2.x, 2.0) + pow(b.endpt1.y - b.endpt2.y ,2.0))
//    val dx: Double = pLoc.x - b.endpt1.x
//    val dy: Double = pLoc.y - b.endpt1.y
//    val dx1: Double = b.endpt2.x - b.endpt1.x
//    val dy1: Double = b.endpt2.y - b.endpt2.y
//    val line: Double = dx * dy1 - dy * dx1
//    if (line == 0){
//        false
//    }
//    else{
//      true

//    var ax = b.endpt.x - b.startpt.x
//    var bx = pLoc.x - a.location.x
//    var ay = b.endpt.y - b.startpt.y
//    var by = pLoc.y - a.location.y
//
//    var c = -ay * (b.startpt.x - a.location.x) + ax * (b.startpt.y - a.location.y) / (-bx * ay + ax * by)
//    var d = bx * (b.startpt.y - a.location.y) - by * (b.startpt.x - a.location.x) / (-bx * ay + ax * by)
//
//    if(c >= 0 && c <= 1 && d >= 0 && d <= 1){
//      false
//    }
//    else{
//      true
//    }

//import scala.math.sqrt
//import scala.math.pow