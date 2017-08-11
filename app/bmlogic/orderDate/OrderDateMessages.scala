package bmlogic.orderDate

import bmmessages.CommonMessage
import play.api.libs.json.JsValue

abstract class msg_OrderDateCommand extends CommonMessage

object OrderDateMessages {
    case class msg_OrderDateLstPush(data : JsValue) extends msg_OrderDateCommand
    case class msg_QueryOrderDate(data : JsValue) extends msg_OrderDateCommand
    case class msg_QueryUsersOrdersDate(data : JsValue) extends msg_OrderDateCommand
}