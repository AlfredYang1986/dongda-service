//import play.api.libs.json._
//import play.api.libs.json.Json.toJson
//import play.api.test._
//
//import scala.concurrent.Await
//import scala.concurrent.duration._
//import org.specs2.mutable.Specification
//import org.specs2.specification.BeforeAll
//import org.specs2.specification.AfterAll
//
//class SelectedTestSpec extends Specification with BeforeAll with AfterAll {
//    import scala.concurrent.ExecutionContext.Implicits.global
//
//    val time_out = 2 second
//
//    var token_1: String = ""
//    var user_id_1: String = ""
//
//    var service_id_1: String = ""
//    var service_id_2: String = ""
//
//    val service_provider_info = toJson(
//        Map(
//            "owner_name" -> "abcde",
//            "social_id" -> "12345678",
//            "company" -> "black mirror",
//            "description" -> "black mirror",
//            "address" -> "black mirror address",
//            "contact_no" -> "87654321"
//        )
//    )
//
//    def service_info(owner_id: String) = toJson(
//        Map(
//            "owner_id" -> toJson(owner_id),
//            "title" -> toJson("service title"),
//            "description" -> toJson("service description"),
//            "images" -> toJson(List("service-photo-1", "service-photo-2")),
//            "location" -> toJson(
//                Map(
//                    "province" -> toJson("p"),
//                    "city" -> toJson("c"),
//                    "district" -> toJson("d"),
//                    "address" -> toJson("address street"),
//                    "adjust" -> toJson("adjust address"),
//                    "pin" -> toJson(
//                        Map(
//                            "latitude" -> toJson(10.0),
//                            "longitude" -> toJson(20.0)
//                        )
//                    )
//
//                )
//            ),
//            "detail" -> toJson(
//                Map(
//                    "price" -> toJson(10000),
//                    "facility" -> toJson("哇哈哈"),
//                    "other_words" -> toJson("其它的屁话")
//                )
//            ),
//            "category" -> toJson(
//                Map(
//                    "service_cat" -> toJson("课程"),
//                    "cans_cat" -> toJson("美术"),
//                    "cans" -> toJson("足球"),
//                    "concert" -> toJson("篮球")
//                )
//            )
//        )
//    )
//
//    val group_1 = "严选"
//    val group_2 = "热门"
//    val category_1 = "艺术"
//    val category_2 = "测试标签"
//
//    var selected_id_1 = ""
//    var selected_id_2 = ""
//
//    override def beforeAll = {
//        WsTestClient.withClient { client =>
//            {
//                val reVal = Await.result(
//                    new DongdaClient(client, "http://127.0.0.1:9999").authLoginWithPhone("13720200890", "alfred", "photo-1"), time_out)
//
//                val result = (reVal \ "result").asOpt[JsValue].get
//                token_1 = (result \ "auth_token").asOpt[String].get
//                user_id_1 = (result \ "user" \ "user_id").asOpt[String].get
//            }
//
//            {
//                val reVal = Await.result(
//                    new DongdaClient(client, "http://127.0.0.1:9999").becomeServiceProvider(token_1, user_id_1, service_provider_info), time_out)
//
//                (reVal \ "status").asOpt[String].get must_== "ok"
//            }
//
//            {
//                val reVal = Await.result(
//                    new DongdaClient(client, "http://127.0.0.1:9999").pushService(token_1, user_id_1, service_info(user_id_1)), time_out)
//
//                (reVal \ "status").asOpt[String].get must_== "ok"
//
//                val result = (reVal \ "result").asOpt[JsValue].get
//                service_id_1 = (result \ "service" \ "service_id").asOpt[String].get
//            }
//
//            {
//                val reVal = Await.result(
//                    new DongdaClient(client, "http://127.0.0.1:9999").pushService(token_1, user_id_1, service_info(user_id_1)), time_out)
//
//                (reVal \ "status").asOpt[String].get must_== "ok"
//
//                val result = (reVal \ "result").asOpt[JsValue].get
//                service_id_2 = (result \ "service" \ "service_id").asOpt[String].get
//            }
//        }
//    }
//
//    override def afterAll = Unit
//
//    override def is = s2"""
//        This is a dongda to check the collections logic string
//
//            The 'dongda' collections functions should
//                push selected test                                              $pushSelectedTest
//                push selected test Another                                      $pushSelectedTestAnother
//                search selected service test                                    $searchSelectedServiceTest
//                search selected service test Another                            $searchSelectedServiceTestAnother
//                pop  selected test                                              $popSelectedTest
//                pop  selected test Another                                      $popSelectedTestAnother
//                                                                              """
//
//    def pushSelectedTest =
//        WsTestClient.withClient { client =>
//            val reVal = Await.result(
//                new DongdaClient(client, "http://127.0.0.1:9999").pushSelectedTest(token_1, user_id_1, service_id_1, category_1, group_1), time_out)
//
//            (reVal \ "status").asOpt[String].get must_== "ok"
//            selected_id_1 = (reVal \ "result" \ "selected" \ "selected_id").asOpt[String].get
//
//            selected_id_1.length must_!= 0
//        }
//
//    def pushSelectedTestAnother =
//        WsTestClient.withClient { client =>
//            val reVal = Await.result(
//                new DongdaClient(client, "http://127.0.0.1:9999").pushSelectedTest(token_1, user_id_1, service_id_1, category_2, group_2), time_out)
//
//            (reVal \ "status").asOpt[String].get must_== "ok"
//            selected_id_2 = (reVal \ "result" \ "selected" \ "selected_id").asOpt[String].get
//
//            selected_id_2.length must_!= 0
//        }
//
//    def popSelectedTest =
//        WsTestClient.withClient { client =>
//            val reVal = Await.result(
//                new DongdaClient(client, "http://127.0.0.1:9999").popSelectedTest(token_1, selected_id_1), time_out)
//
//            (reVal \ "status").asOpt[String].get must_== "ok"
//        }
//
//    def popSelectedTestAnother =
//        WsTestClient.withClient { client =>
//            val reVal = Await.result(
//                new DongdaClient(client, "http://127.0.0.1:9999").popSelectedTest(token_1, selected_id_2), time_out)
//
//            (reVal \ "status").asOpt[String].get must_== "ok"
//        }
//
//    def searchSelectedServiceTest =
//        WsTestClient.withClient { client =>
//            val reVal = Await.result(
//                new DongdaClient(client, "http://127.0.0.1:9999").searchSelectedTest(token_1, user_id_1, category_1), time_out)
//
//            println(reVal)
//            (reVal \ "status").asOpt[String].get must_== "ok"
//        }
//
//    def searchSelectedServiceTestAnother =
//        WsTestClient.withClient { client =>
//            val reVal = Await.result(
//                new DongdaClient(client, "http://127.0.0.1:9999").searchSelectedTest(token_1, user_id_1, category_2), time_out)
//
//            println(reVal)
//            (reVal \ "status").asOpt[String].get must_== "ok"
//        }
//}
