package model.metadata

import model.ModelObject

case class Category(id: Long, createdAt: Long, name: String, priority: Priority) extends ModelObject
