package org.example

import java.net.HttpURLConnection
import java.net.URL

import org.scalatest.FunSpec

class HttpClientTest extends FunSpec {
  val protocol = "http"
  val host = "localhost"
  val port = 8081
  val url = "/service/login"
  
  val npiLogin = s"http://localhost:8081/service/login"
  
  def connect(url: String) = new URL(npiLogin).openConnection().asInstanceOf[HttpURLConnection]
  def connect(protocol: String, host: String, port: Int, url: String) = 
    new URL(protocol, host, port, url).openConnection().asInstanceOf[HttpURLConnection]
  
  
  describe("Attempts to connect to /service/login without credentials") {
    ignore("should fail in some ways") {
      info(npiLogin)
      val c = connect(npiLogin)
      c.connect()
      info(s"${c.getResponseCode}")
      assert(c.getResponseCode == HttpURLConnection.HTTP_OK)
      
      info(s"${c.getHeaderFields}")
    }
  }
  
  describe("Attempts to connect to /service/login?username=a&password=b") {
    it("should fail in some ways") {
      val loginUrl = s"$url?username=a&password=b"
      info(loginUrl)
      val c = connect(protocol, host, port, loginUrl)
      c.setRequestMethod("GET")
      c.setDoInput(true);
      c.setDoOutput(true);
      c.setUseCaches(false);
      // -H 'Cookie: JSESSIONID=xyz1p2v7g6blifu1mfxhbqru5k' 
      // -H 'Accept-Encoding: gzip, deflate, sdch' 
      // -H 'Accept-Language: en-US,en;q=0.8' 
      // -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/39.0.2171.65 Chrome/39.0.2171.65 Safari/537.36'
      // -H 'Content-Type: application/x-www-form-urlencoded' 
      // -H 'Accept: */*' 
      // -H 'Referer: http://localhost:8081/' 
      // -H 'X-Requested-With: XMLHttpRequest' 
      // -H 'Connection: keep-alive' --compressed
      c.setRequestProperty("Cookie", "JSESSIONID=1p2v7g6blifu1mfxhbqru5k")
      c.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch")
      c.setRequestProperty("Accept-Language", "en-US,en;q=0.8")
      c.setRequestProperty("User-Agent", "Mozilla/5.0")
      c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
      c.setRequestProperty("Accept", "*/*")
      c.setRequestProperty("Referer", "http://localhost:8081/")
      c.setRequestProperty("X-Requested-With", "XMLHttpRequest")
      c.setRequestProperty("Connection", "keep-alive")
      c.setRequestProperty("Host", "localhost:8081")      
      info(s"${c.getRequestProperties}")
      c.connect()
      info(s"${c.getResponseCode}")
      assert(c.getResponseCode == HttpURLConnection.HTTP_OK)
      
      info(s"${c.getHeaderFields}")
    }
  }
  
  describe("Attempts to connect to /service/login?username=npiuser&password=npiuser") {
    ignore("should fail in some ways") {
      val url = s"$npiLogin?username=npiuser&password=npiuser"
      info(url)
      val c = connect(url)
      c.connect()
      info(s"${c.getResponseCode}")
      assert(c.getResponseCode == HttpURLConnection.HTTP_OK)
      
      info(s"${c.getHeaderFields}")
    }
  }
}