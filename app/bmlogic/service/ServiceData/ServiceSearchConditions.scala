package bmlogic.service.ServiceData

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue

/**
  * Created by jeorch on 17-12-20.
  */
trait ServiceSearchConditions {
    implicit val ssc : JsValue => DBObject = { js =>
        val builder = MongoDBObject.newBuilder
        val condition_service_id = (js \ "condition_service_id").asOpt[String].getOrElse("")
        val service_id = (js \ "condition" \ "service_id").asOpt[String].getOrElse("")
        val service_id_lst = condition_service_id :: service_id :: Nil

        if (condition_service_id != "" && service_id != ""){
            if (condition_service_id == service_id) builder += "_id" -> new ObjectId(service_id) else builder += "_id" -> new ObjectId()
        } else {
            service_id_lst.filterNot(x => x == "").map(s => builder += "_id" -> new ObjectId(s))
        }
        (js \ "condition" \ "category").asOpt[String].map (x => builder += "category" -> x).getOrElse(Unit)
        (js \ "condition" \ "service_type").asOpt[String].map (x => builder += "service_type" -> x).getOrElse(Unit)
        (js \ "condition" \ "service_tags").asOpt[String].map (x => builder += "service_tags" -> x).getOrElse(Unit)
        (js \ "condition" \ "service_leaf").asOpt[String].map (x => builder += "service_leaf" -> x).getOrElse(Unit)
        (js \ "condition" \ "operation").asOpt[String].map (x => builder += "operation" -> x).getOrElse(Unit)
        (js \ "condition" \ "album").asOpt[String].map (x => builder += "album" -> x).getOrElse(Unit)

        builder.result
    }

    implicit val sdc : JsValue => DBObject = { js =>
        val builder = MongoDBObject.newBuilder
        (js \ "condition" \ "service_id").asOpt[String].map (x => builder += "_id" -> new ObjectId(x)).getOrElse(throw new Exception("input error"))
        builder.result
    }

    implicit val mdc : JsValue => DBObject = { js =>
        val lst = (js \ "services").asOpt[List[String]].get
        if (!lst.isEmpty) $or(lst.map (x => DBObject("_id" -> new ObjectId(x))))
        else DBObject("fuck" -> "yes")
    }
}
