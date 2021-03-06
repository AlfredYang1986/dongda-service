package bmlogic.brand.BrandData

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

/**
  * Created by jeorch on 17-12-20.
  */
trait BrandResults {
    implicit val sbr : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "brand_name" -> toJson(obj.get("brand_name").asInstanceOf[String])
        )
    }

    implicit val sbdr : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "brand_id" -> toJson(obj.get("_id").asInstanceOf[ObjectId].toString),
            "brand_name" -> toJson(obj.get("brand_name").asInstanceOf[String]),
            "brand_tag" -> toJson(obj.get("brand_tag").asInstanceOf[String]),
            "about_brand" -> toJson(obj.get("about_brand").asInstanceOf[String]),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }

    implicit val hssbr : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "brand_name" -> toJson(obj.get("brand_name").asInstanceOf[String])
        )
    }

    implicit val bsbr : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "brand_id" -> toJson(obj.get("brand_id").asInstanceOf[ObjectId].toString),
            "service_id" -> toJson(obj.get("service_id").asInstanceOf[ObjectId].toString)
        )
    }

    implicit val bubr : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "brand_id" -> toJson(obj.get("brand_id").asInstanceOf[ObjectId].toString),
            "user_id" -> toJson(obj.get("user_id").asInstanceOf[ObjectId].toString)
        )
    }
}
