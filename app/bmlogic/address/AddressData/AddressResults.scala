package bmlogic.address.AddressData

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

/**
  * Created by jeorch on 17-9-15.
  */
trait AddressResults {

    implicit val ar : DBObject => Map[String, JsValue] = { obj =>

        val service_id_list = obj.getAs[MongoDBList]("service_id").get.toList.asInstanceOf[List[String]]

        val loc_map = obj.getAs[MongoDBObject]("location").map { loc =>
            val pin = loc.getAs[MongoDBObject]("pin").get
            val cor = pin.getAs[MongoDBList]("coordinates").get
            val longitude = toJson(cor.head.asInstanceOf[Number].floatValue)
            val latitude = toJson(cor.tail.head.asInstanceOf[Number].floatValue)
//            val loc_images = loc.getAs[MongoDBList]("loc_images").get.toList.asInstanceOf[List[BasicDBObject]]
            Map(
                "address" -> loc.getAs[String]("address").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "pin" -> toJson(
                    Map(
                        "latitude" -> latitude,
                        "longitude" -> longitude
                    )
                )
            )
        }.getOrElse(throw new Exception("service result error"))

        Map(
            "service_id" -> toJson(service_id_list),
            "address_id" -> toJson(obj.getAs[String]("address_id").get)
        ) + ("location" -> toJson(loc_map))

    }

    implicit val dr : DBObject => Map[String, JsValue] = { obj =>

        val service_id_list = obj.getAs[MongoDBList]("service_id").get.toList.asInstanceOf[List[String]]

        val loc_map = obj.getAs[MongoDBObject]("location").map { loc =>
            val pin = loc.getAs[MongoDBObject]("pin").get
            val cor = pin.getAs[MongoDBList]("coordinates").get
            val longitude = toJson(cor.head.asInstanceOf[Number].floatValue)
            val latitude = toJson(cor.tail.head.asInstanceOf[Number].floatValue)

            val loc_images = loc.getAs[MongoDBList]("loc_images").get.toList.asInstanceOf[List[BasicDBObject]]

            Map(
                "province" -> loc.getAs[String]("province").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "city" -> loc.getAs[String]("city").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "district" -> loc.getAs[String]("district").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "address" -> loc.getAs[String]("address").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "location_type" -> loc.getAs[String]("location_type").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "loc_images" -> toJson(loc_images.map{ one =>
                    toJson(Map(
                        "pic" -> toJson(one.get("pic").asInstanceOf[String]),
                        "tag" -> toJson(one.get("tag").asInstanceOf[String])
                    ))
                }),
                "pin" -> toJson(
                    Map(
                        "latitude" -> latitude,
                        "longitude" -> longitude
                    )
                )
            )
        }.getOrElse(throw new Exception("service result error"))

        Map(
            "service_id" -> toJson(service_id_list),
            "address_id" -> toJson(obj.getAs[String]("address_id").get)
        ) + ("location" -> toJson(loc_map))

    }

    implicit val sr : DBObject => Map[String, JsValue] = { obj =>

        val category_map = obj.getAs[MongoDBObject]("category").map { cat =>
            Map(
                "service_cat" -> cat.getAs[String]("service_cat").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "cans_cat" -> cat.getAs[String]("cans_cat").map (x => toJson(x)).getOrElse(throw new Exception("service result error")),
                "concert" -> cat.getAs[String]("concert").map (x => toJson(x)).getOrElse(throw new Exception("service result error"))
            )
        }.getOrElse(throw new Exception("service result error"))

        val detail_map = obj.getAs[MongoDBObject]("detail").map { det =>
            Map(
                "price" -> det.getAs[Number]("price").map (x => toJson(x.intValue)).getOrElse(throw new Exception("service result error")),
                "age_boundary" -> toJson(
                    Map(
                        "lsl" -> toJson(det.getAs[MongoDBObject]("age_boundary").get.getAs[Number]("lsl").get.floatValue),
                        "usl" -> toJson(det.getAs[MongoDBObject]("age_boundary").get.getAs[Number]("usl").get.floatValue)
                    )
                )
            )
        }.getOrElse(throw new Exception("service result error"))

        val images = obj.getAs[MongoDBList]("images").get.toList.asInstanceOf[List[String]]

        Map(
            "owner_id" -> toJson(obj.getAs[String]("owner_id").get),
            "service_id" -> toJson(obj.getAs[String]("service_id").get),
            "address_id" -> toJson(obj.getAs[String]("address_id").get),
            "title" -> toJson(obj.getAs[String]("title").get),
            "images" -> toJson(if (images.isEmpty) ""
            else images.head)
        ) + ("category" -> toJson(category_map)) + ("detail" -> toJson(detail_map))
    }

}