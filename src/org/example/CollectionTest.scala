package org.example

import org.scalatest.FunSpec

class CollectionTest extends FunSpec {
  val queue = Seq(1, 2, 3, 4 ,5)
  
  describe("Collection#count") {
    it("should count as 2") {
      val count = queue.count { e => e / 2 == 2 }
      assert(count == 2)      
    }
  }
  
  describe("Collection#count throws exception") {
    it("should translate exception into some int") {
      var xCount = 0
      val count = queue.count(e => {
        try {
          if (e == 3)
            throw new Exception("Just for testing")
          e / 2 == 2
        } catch {
          case _: Throwable => 
            xCount += 1
            false
        }
      })
      info(s"xCount - $xCount")
      info(s"count - $count")
      assert(xCount == 1)
      assert(count == 2)      
    }
  }
  
}