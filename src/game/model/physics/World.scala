package game.model.physics

class World(var g: Double) {
  var objects: List[PhysicalObject] = List()
  var boundaries: List[Boundary] = List()
}
