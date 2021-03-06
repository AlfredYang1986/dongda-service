package bmlogic.phonecode.PhoneCodeData

import bmlogic.common.sercurity.Sercurity
import bmlogic.common.sms.smsModule
import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

trait PhoneCodeData {
    implicit val m2d : JsValue => DBObject = { js =>
        val builder = MongoDBObject.newBuilder

        val phoneNo = (js \ "phone").asOpt[String].map (x => x).getOrElse(throw new Exception("reg push error"))
        val reg_token = Sercurity.md5Hash(phoneNo + Sercurity.getTimeSpanWithMinutes)
        val code = 1111.toString // fake one
//		val code = (scala.util.Random.nextInt(9000) + 1000).toString

        /**
		 * send code to the phone
		 */
//		import play.api.Play.current
//		smsModule().sendSMS(phoneNo, code.toString)

        builder += "phone" -> phoneNo
        builder += "code" -> code
        builder += "reg_token" -> reg_token

        builder.result
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "phone" -> toJson(obj.getAs[String]("phone").map (x => x).getOrElse(throw new Exception("db prase error"))),
            "code" -> toJson(obj.getAs[String]("code").map (x => x).getOrElse(throw new Exception("db prase error"))),
            "reg_token" -> toJson(obj.getAs[String]("reg_token").map (x => x).getOrElse(throw new Exception("db prase error")))
        )
    }
}
