package model.metadata

//import config.Config
//import config.Config.MatchersConfig

import scala.collection.immutable.{Map, Seq, Set}

case class UserMetadata(
  priorities: Set[Priority],
  categories: Set[Category],
  matchers: Set[Matcher]
)/* {

  def toConfig: Config = {
    val priorityConfigs = priorities
      .map(p => PriorityConfig(p.id, p.name))
      .to[Seq]
      .sortBy(_.id)

    val categoryConfigs = categories
      .map(c => CategoryConfig(c.id, c.name, c.priority.id))
      .to[Seq]
      .sortBy(_.id)

    val matcherConfigs = matchers.map {
      case ContainsMatcher(value, category) => Left(ContainsMatcherConfig(value, category.id))
      case EqualityMatcher(value, category) => Right(EqualityMatcherConfig(value, category.id))
    }

    val containsMatchers = for (Left(c) <- matcherConfigs) yield c
    val equalityMatchers = for (Right(c) <- matcherConfigs) yield c

    Config(
      priorityConfigs,
      categoryConfigs,
      MatchersConfig(
        containsMatchers.to[Seq],
        equalityMatchers.to[Seq]))
  }
}

object UserMetadata {

  def fromConfig(config: Config): UserMetadata = {
  }
}*/
