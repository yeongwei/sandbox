package com.ibm.csi.npm.storage.jdbc

import org.scalatest.FunSpec
import java.sql.DriverManager
import com.ibm.csi.npm.storage.jdbc.{ Connection => StorageConnection }
import java.sql.{ Connection => sqlConnection }
import java.util.Properties
import java.sql.ResultSet

trait JdbcBaseSpec {
    def jdbcUrl(host: String = "localhost", port: Int = 8082) = 
      s"jdbc:npi:http:url=http://$host:$port"
}

class JdbcTest extends FunSpec with JdbcBaseSpec {
  
  describe("Connect to Storage via JDBC") {
    var connection: sqlConnection = null
    var storageConnection: StorageConnection = null
    
    it("should connect to Storage") {
      val info2 = new Properties()
      info2.put("user", "npiuser")
      info2.put("password", "npiuser")
      info(jdbcUrl())
      
      try {
        connection = DriverManager.getConnection(jdbcUrl(), info2)
        assert(!connection.isClosed())
      } catch {
        case e: Throwable => {
            e.printStackTrace()
            assert(false)
          }
      }
    }
    
    it("should return some databasemetadata") {
      val dbMeta = connection.getMetaData
      val rs = dbMeta.getTables(null, null, null, null)
      var count = 0
      while(rs.next())
        count += 1
        
      assert(count > 0)
    }
    
    def countResultSet(rs: ResultSet) = {
      var count = 0
      while(rs.next())
        count += 1
        
      assert(count > 0)
    }
    
    it("should query some results via Statement") {
      val statement = connection.createStatement()
      val status = statement.execute("SELECT * FROM CFG.DOMAIN_NAMES")
      assert(status)
      
      val rs = statement.getResultSet
      assert(!rs.isClosed())
      
      assert(rs.getMetaData.getColumnCount > 0)
      
      countResultSet(rs)
    }
    
    it("should query some results via PreparedStatement") {
      val pStatement = connection.prepareStatement("SELECT * FROM CFG.DOMAIN_NAMES WHERE 1 = ?")
      pStatement.setInt(1, 1)
      assert(!pStatement.isClosed())
      
      val status = pStatement.execute()
      assert(status)
      
      val rs = pStatement.getResultSet
      assert(!rs.isClosed())
      
      countResultSet(rs)
    }
    
    it("should close connection") {
      connection.close()
      assert(connection.isClosed())
    }
    
    it("should fail on credentails") {
      val info2 = new Properties()
      info2.put("user", "npiuser2")
      info2.put("password", "npiuser2")
      
      try {
        connection = DriverManager.getConnection(jdbcUrl(), info2)
        assert(false, "should fail credentials")
      } catch {
        case ex: Throwable => {
          ex.printStackTrace()
          assert(true)
        }  
      }
    }
    
    it("should re-establish JDBC Connection") {
      val info2 = new Properties()
      info2.put("user", "npiuser")
      info2.put("password", "npiuser")
      
      connection = DriverManager.getConnection(jdbcUrl(), info2)
      val statement = connection.createStatement()
      val status = statement.execute("select NAME, \"VALUE\" from DEFAULTS.CONSOLEDATA")
      assert(status)
      
      val resultset = statement.getResultSet
      assert(resultset.isClosed() == false)
      
      while(resultset.next()) {
        info(s"${resultset.getString(1)}: ${resultset.getString(2)}")
      }
    }
  }
}