package game.model

import game.model.physics._
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.ListBuffer


class Game {

  val world: World = new World(10)
  var bases: ListBuffer[GridLocation] = ListBuffer()
  var players: Map[String, Player] = Map()
  val playerSize: Double = 0.3
  var lastUpdateTime: Long = System.nanoTime()
  val goal = 5

  blockTile(0, 0, gridWidth, gridHeight)

  // level specs
  var gridWidth: Int = 25
  var gridHeight: Int = 25
  var startingLocation = new GridLocation(scala.util.Random.nextInt(gridWidth), scala.util.Random.nextInt(gridHeight))
  bases = bases :+ new GridLocation(scala.util.Random.nextInt(gridWidth), scala.util.Random.nextInt(gridHeight))

  def addPlayer(id: String): Unit = {
    val player = new Player(startingVector(), new PhysicsVector(0, 0))
    players += (id -> player)
    world.objects = player :: world.objects
    startingLocation = new GridLocation(scala.util.Random.nextInt(gridWidth), scala.util.Random.nextInt(gridHeight))
    bases = bases :+ new GridLocation(scala.util.Random.nextInt(gridWidth), scala.util.Random.nextInt(gridHeight))
    blockTile(0, 0, gridWidth, gridHeight)
  }

  def removePlayer(id: String): Unit = {
    players(id).destroy()
    players -= id
  }

  def blockTile(x: Int, y: Int, width: Int = 1, height: Int = 1): Unit = {
    val ul = new PhysicsVector(x, y)
    val ur = new PhysicsVector(x + width, y)
    val lr = new PhysicsVector(x + width, y + height)
    val ll = new PhysicsVector(x, y + height)

    world.boundaries ::= new Boundary(ul, ur)
    world.boundaries ::= new Boundary(ur, lr)
    world.boundaries ::= new Boundary(lr, ll)
    world.boundaries ::= new Boundary(ll, ul)
  }

// SWAP STARTING VEC WITH STARTLOC RANDOM??
  def startingVector(): PhysicsVector = {
    new PhysicsVector(startingLocation.x + 0.5, startingLocation.y + 0.5)
  }

  def update(): Unit = {
    val time: Long = System.nanoTime()
    val dt = (time - this.lastUpdateTime) / 1000000000.0
    Physics.updateWorld(this.world, dt)
    checkPts()
    checkForHit()
    this.lastUpdateTime = time
  }

  def gameState(): String = {
    val gameState: Map[String, JsValue] = Map(
      "gridSize" -> Json.toJson(Map("x" -> gridWidth, "y" -> gridHeight)),
      "start" -> Json.toJson(Map("x" -> startingLocation.x, "y" -> startingLocation.y)),
      "goal" -> Json.toJson(goal),
      "bases" -> Json.toJson(this.bases.map({ g => Json.toJson(Map("x" -> g.x, "y" -> g.y)) })),
      "players" -> Json.toJson(this.players.map({ case (k, v) => Json.toJson(Map(
        "x" -> Json.toJson(v.location.x),
        "y" -> Json.toJson(v.location.y),
        "pts"-> Json.toJson(v.points),
        "id" -> Json.toJson(k))) }))
    )

    Json.stringify(Json.toJson(gameState))
  }

  def checkForHit(): Unit = {
    for (base <- bases) {
      val center_x = base.x + .5
      val center_y = base.y + .5
      val center = new PhysicsVector(center_x, center_y)
      players.values.foreach(player => if (player.location.distance2d(center) < playerSize) {
        player.points += 1
        bases -= base
        bases += new GridLocation(scala.util.Random.nextInt(gridWidth), scala.util.Random.nextInt(gridHeight))
      }
      else {})
    }
  }

  def checkPts(): Unit = {
    for(p <- players){
      if(p._2.points >= goal){
        p._2.points = 0
      }else{}
    }
  }
}
