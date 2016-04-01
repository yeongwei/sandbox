package com.ibm.csi.npm.storage.jdbc

import java.sql.{ Connection => sqlConnection, DriverManager }
import java.util.Properties
import org.scalatest.FunSpec

class jdbcDashTest extends FunSpec with JdbcBaseSpec {
  
  describe("Connect to a federated NPI instance") {
    var connection: sqlConnection = null
    val jUrl = jdbcUrl("tnpmklig03.ibm.com", 8082)
    
    it("should get connect with DASH credentials") {
      info(jUrl)
      
      val info2 = new Properties()
      info2.put("user", "npiadmin")
      info2.put("password", "smartway")
      
      connection = DriverManager.getConnection(jdbcUrl(), info2)
      assert(connection.isClosed() == false)
      connection.close()
    }
  }
}