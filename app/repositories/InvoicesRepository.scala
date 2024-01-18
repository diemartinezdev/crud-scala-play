package repositories

import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

class InvoiceTable(tag: Tag) extends Table[Invoice](tag, "invoices") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def invoiceNumber = column[Option[String]]("invoice_number")
  def siteId = column[String]("site_id")
  def commodity = column[String]("commodity")
  def consumption = column[BigDecimal]("consumption")
  def totalPrice = column[BigDecimal]("total_price")

  def * = (id, invoiceNumber, siteId, commodity, consumption, totalPrice) <> ((Invoice.apply _).tupled, Invoice.unapply)
}

@Singleton
class InvoiceRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  private val invoices = TableQuery[InvoiceTable]

  def getAllInvoices: Future[Seq[(Invoice)]] = {
    dbConfig.db.run(invoices.result)
  }

  def getInvoicesWithSites: Future[Seq[(Invoice, Site)]] = {
    val query = for {
      (invoice, site) <- invoices join TableQuery[SitesTable] on (_.siteId === _.id)
    } yield (invoice, site)
    dbConfig.db.run(query.result)
  }
}