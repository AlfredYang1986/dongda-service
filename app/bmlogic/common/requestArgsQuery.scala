package bmlogic.common

import play.api.mvc._
import play.api.libs.json.JsValue
import play.api.libs.Files.TemporaryFile

import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent.Await

import com.pharbers.bmmessages.MessageRoutes
import com.pharbers.bmmessages.excute
import com.pharbers.bmpattern.RoutesActor

import javax.inject._

object requestArgsQuery {
    def apply()(implicit akkasys : ActorSystem) = new requestArgsQuery()
}

class requestArgsQuery @Inject() (implicit akkasys : ActorSystem) extends Controller {
	implicit val t = Timeout(3000 seconds)

  	def requestArgs(request : Request[AnyContent])(func : JsValue => JsValue) : Result = {
  		try {
			println(s"&&11&&===request=${request}")
  			request.body.asJson.map { x =>
  				Ok(func(x))
  			}.getOrElse (BadRequest("Bad Request for input"))
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input")
  		}  		   
	}
	
  	def requestArgsV2(request : Request[AnyContent])(func : JsValue => MessageRoutes) : Result = {
  		try {
			println(s"&&22&&===request=${request}")
  			request.body.asJson.map { x =>
  				Ok(commonExcution(func(x)))
  			}.getOrElse (BadRequest("Bad Request for input"))
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input")
  		}  		   
	}
  	
  	def commonExcution(msr : MessageRoutes) : JsValue = {
		val act = akkasys.actorOf(Props[RoutesActor])
		val r = act ? excute(msr)
		Await.result(r.mapTo[JsValue], t.duration)
	}
 
  	def uploadRequestArgs(request : Request[AnyContent])(func : MultipartFormData[TemporaryFile] => JsValue) : Result = {
  		try {
			println(s"&&33&&===request=${request}")
   			request.body.asMultipartFormData.map { x =>
   				Ok(func(x))
  			}.getOrElse (BadRequest("Bad Request for input1"))
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input2")
  		}
  	}
}