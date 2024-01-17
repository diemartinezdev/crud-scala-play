package repositories

import models.Invoice
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


class InvoiceTable(tag: Tag) extends Table[Invoice](tag, "invoices") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def invoiceNumber = column[Option[String]]("invoice_number")
  def commodity = column[String]("commodity")
  def siteId = column[String]("site_id")
  def consumption = column[BigDecimal]("consumption")
  def totalPrice = column[BigDecimal]("total_price")

  def * = (id.?, invoiceNumber, commodity, siteId, consumption, totalPrice) <> ((Invoice.apply _).tupled, Invoice.unapply)
}

@Singleton
class InvoiceRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  private val invoices = TableQuery[InvoiceTable]

  def getAllInvoices: Future[Seq[Invoice]] = {
    dbConfig.db.run(invoices.result)
  }
}