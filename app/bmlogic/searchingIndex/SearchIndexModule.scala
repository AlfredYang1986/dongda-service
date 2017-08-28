package bmlogic.searchingIndex

import bminjection.db.DBTrait
import bmlogic.searchingIndex.SearchIndexMessages._
import bmmessages.{CommonModules, MessageDefines}
import bmpattern.ModuleTrait
import bmutil.errorcode.ErrorCode
import play.api.libs.json.JsValue

object SearchIndexModule extends ModuleTrait {

    def dispatchMsg(msg: MessageDefines)(pr: Option[Map[String, JsValue]])(implicit cm: CommonModules): (Option[Map[String, JsValue]], Option[JsValue]) = msg match {

//        case msg_PushIndexCommand(data) =>
//        case msg_PopIndexCommand(data) =>
//
//        case msg_CategoryIndexingCommand(data) =>
        case _ => ???
    }

    def pushSearchIndex(data : JsValue)
                        (implicit cm : CommonModules) : (Option[Map[String, JsValue]], Option[JsValue]) = {

        try {
            val db = cm.modules.get.get("db").map (x => x.asInstanceOf[DBTrait]).getOrElse(throw new Exception("no db connection"))

            null

        } catch {
            case ex : Exception => (None, Some(ErrorCode.errorToJson(ex.getMessage)))
        }
    }

    def popSearchIndex(data : JsValue)
                      (implicit cm : CommonModules) : (Option[Map[String, JsValue]], Option[JsValue]) = {

        try {
            null


        } catch {
            case ex : Exception => (None, Some(ErrorCode.errorToJson(ex.getMessage)))
        }
    }

    def categoryIndexing(data : JsValue)
                        (implicit cm : CommonModules) : (Option[Map[String, JsValue]], Option[JsValue]) = {

        try {
            null

        } catch {
            case ex : Exception => (None, Some(ErrorCode.errorToJson(ex.getMessage)))
        }
    }

}