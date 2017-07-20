package bmlogic.profile.ProfileConditions

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue

trait multiQueryConditions {
    implicit val mc : JsValue => DBObject = { js =>
        $or(
            (js \ "condition" \ "lst")
                .asOpt[List[String]].map (x => x)
                .getOrElse(throw new Exception("profile multi query input error"))
                map (x => DBObject("user_id" -> x))
        )
    }
}
