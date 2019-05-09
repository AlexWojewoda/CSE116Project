package game.model.physics

import game.model.Boundary

class World(var g: Double) {
  var objects: List[PhysicalObject] = List()
  var boundaries: List[Boundary] = List()
}
