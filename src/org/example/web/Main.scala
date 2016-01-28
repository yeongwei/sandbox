package org.example.web

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.security.HashLoginService
import org.eclipse.jetty.server.handler.HandlerCollection

/**
 * Reference:
 * http://examples.javacodegeeks.com/enterprise-java/jetty/jetty-authentication-configuration-example/
 * http://examples.javacodegeeks.com/enterprise-java/jetty/jetty-web-xml-configuration-example/
 */
object Main {
  def main(args: Array[String]) = {
    val jettyServer = new Server(16300)
    
    val handlerCollection = new HandlerCollection()
    
    // Context 
    val webAppCtx = new WebAppContext()
    webAppCtx.setResourceBase("/home/sherpa/sandbox/resources")
    webAppCtx.setContextPath("/")
    // Partial deployment descriptors in coding
    // webAppCtx.setOverrideDescriptor("/home/sherpa/sandbox/src/org/example/web/customWeb.xml")
    handlerCollection.addHandler(webAppCtx)
    
    // Context - IF ABOVE IS "/" THEN THE WEB APP BELOW GOES MISSING
    val webAppCtx2 = new WebAppContext()
    webAppCtx2.setResourceBase("/home/sherpa/sandbox/resources")
    webAppCtx2.setContextPath("app2")
    handlerCollection.addHandler(webAppCtx2)
    
    // Security
    val hashLoginService = new HashLoginService("TestAuthorization")
    hashLoginService.setConfig("/home/sherpa/sandbox/resources/WEB-INF/credentials.txt")
    jettyServer.addBean(hashLoginService)
    
    jettyServer.setHandler(handlerCollection)
    
    jettyServer.start() 
    
    /*
    // CANNOT DYNAMICALLY OVERRIDE DEPLOYMENT DESCRIPTOR
    val targetHandler = handlerCollection.getHandlers.filter { 
      h => if (h.isInstanceOf[WebAppContext]) {
        if (h.asInstanceOf[WebAppContext].getContextPath.equals(webAppCtx.getContextPath)) {
          println(s"YEONGWEI: ${h.getClass.getName}")
          true
        } else {
          false 
        }
      } else {
        false
      }
    }
    // println(s"YEONGWEI: ${targetHandler.getClass.getName}")
    
    targetHandler.foreach { h => {
        // println(s"YEONGWEI: ${h.getClass.getName}")
        h.asInstanceOf[WebAppContext].setOverrideDescriptor("/home/sherpa/sandbox/src/org/example/web/customWeb.xml")
      } 
    }
    */

    jettyServer.join()
  }
}