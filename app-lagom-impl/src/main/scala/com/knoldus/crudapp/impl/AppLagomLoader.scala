package com.knoldus.crudapp.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.knoldus.crudapp.api.AppService
import com.knoldus.myuser.api.UserOperationService
import com.softwaremill.macwire._

class AppLagomLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new AppLagomApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new AppLagomApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[AppService])
}

abstract class AppLagomApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[AppService](wire[AppServiceImpl])
  lazy val UserOperationService = serviceClient.implement[UserOperationService]
  // Register the JSON serializer registry
//  override lazy val jsonSerializerRegistry = ApplagomSerializerRegistry
  // Register the App-lagom persistent entity
//  persistentEntityRegistry.register(wire[ApplagomEntity])


}
