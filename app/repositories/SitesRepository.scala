package repositories

import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

class SitesTable(tag: Tag) extends Table[Site](tag, "sites") {
  def id = column[String]("id", O.PrimaryKey, O.AutoInc)
  def shortName = column[String]("short_name")
  def vatNumber = column[Option[String]]("vat_number")

  def * = (id, shortName, vatNumber) <> ((Site.apply _).tupled, Site.unapply)
}


@Singleton
class SiteRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  private val sites = TableQuery[SitesTable]

  def getAllSites: Future[Seq[(Site)]] = {
    dbConfig.db.run(sites.result)
  }
}