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
    webAppCtx.setContextPath("/app")
    handlerCollection.addHandler(webAppCtx)
    // Context 
    val webAppCtx2 = new WebAppContext()
    webAppCtx2.setResourceBase("/home/sherpa/sandbox/resources")
    webAppCtx2.setContextPath("/app2")
    handlerCollection.addHandler(webAppCtx2)
    
    // Security
    val hashLoginService = new HashLoginService("TestAuthorization")
    hashLoginService.setConfig("/home/sherpa/sandbox/resources/WEB-INF/credentials.txt")
    jettyServer.addBean(hashLoginService)
    
    jettyServer.setHandler(handlerCollection)
    
    jettyServer.start()
    jettyServer.join()
  }
}