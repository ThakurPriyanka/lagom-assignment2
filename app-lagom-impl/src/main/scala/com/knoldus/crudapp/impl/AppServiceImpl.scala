package com.knoldus.crudapp.impl

import akka.NotUsed
import com.knoldus.crudapp.api.{AppService, User}
import com.knoldus.myuser.api.UserOperationService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}


class AppServiceImpl(userService: UserOperationService)(implicit ec: ExecutionContext)  extends AppService{

  override def getDetail(id: Int) = ServiceCall[NotUsed, User] { _ =>
   val resultData =  userService.getDetail(id).invoke()

    val finalResult = Await.result(resultData,Duration.Inf)
    val userData = new User(finalResult.id, finalResult.name)
    Future.successful(userData)
  }

 override def insert() = ServiceCall[User, String] { request =>
   val newUser = com.knoldus.myuser.api.User(request.id, request.name)
   val resultInsert =  userService.insert().invoke(newUser)
   val finalResult = Await.result(resultInsert,Duration.Inf)
   Future.successful(finalResult)
  }

  override def getUpdate(id: Int) = ServiceCall[User, String] { request =>
    val newUser = com.knoldus.myuser.api.User(request.id, request.name)
    val resultUpdate =  userService.getUpdate(id).invoke(newUser)
    val finalResult = Await.result(resultUpdate,Duration.Inf)
    Future.successful(finalResult)
  }

  def deleteDetail(id: Int) = ServiceCall[NotUsed, String] { _ =>
    val resultDelete =  userService.deleteDetail(id).invoke()
    val finalResult = Await.result(resultDelete,Duration.Inf)
    Future.successful(finalResult)
  }
}
