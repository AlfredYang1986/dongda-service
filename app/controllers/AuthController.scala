package controllers

import javax.inject._

import akka.actor.ActorSystem
import bminjection.db.DBTrait
import bminjection.token.AuthTokenTrait
import bmlogic.auth.AuthMessage._
import bmlogic.common.requestArgsQuery
import bmlogic.phonecode.PhoneCodeMessages.msg_CheckSMSCode
import bmmessages._
import bmpattern.LogMessage.msg_log
import bmpattern.ResultMessage.msg_CommonResultMessage
import play.api.libs.json.Json.toJson
import play.api.mvc._

class AuthController @Inject () (as_inject : ActorSystem, dbt : DBTrait, att : AuthTokenTrait) extends Controller {
    implicit val as = as_inject

    def authLogin = Action (request => requestArgsQuery().requestArgsV2(request) { jv =>
            import bmpattern.LogMessage.common_log
            import bmpattern.ResultMessage.common_result
            MessageRoutes(msg_log(toJson(Map("method" -> toJson("dongda login"))), jv)
                :: msg_AuthLogin(jv) :: msg_GenerateToken()
                :: msg_CommonResultMessage() :: Nil, None)(CommonModules(Some(Map("db" -> dbt, "att" -> att))))
        })

    def authWithPhoneCode = Action (request => requestArgsQuery().requestArgsV2(request) { jv =>
            import bmpattern.LogMessage.common_log
            import bmpattern.ResultMessage.common_result
            MessageRoutes(msg_log(toJson(Map("method" -> toJson("dongda login phone code"))), jv)
                :: msg_CheckSMSCode(jv)
                :: msg_AuthLogin(jv) :: msg_GenerateToken()
                :: msg_CommonResultMessage() :: Nil, None)(CommonModules(Some(Map("db" -> dbt, "att" -> att))))
        })

    def authWithSNS = Action (request => requestArgsQuery().requestArgsV2(request) { jv =>
            import bmpattern.LogMessage.common_log
            import bmpattern.ResultMessage.common_result
            MessageRoutes(msg_log(toJson(Map("method" -> toJson("dongda login phone code"))), jv)
                :: msg_AuthLogin(jv) :: msg_GenerateToken()
                :: msg_CommonResultMessage() :: Nil, None)(CommonModules(Some(Map("db" -> dbt, "att" -> att))))
        })
}