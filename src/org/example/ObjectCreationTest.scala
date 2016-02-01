package org.example

import org.scalatest.FunSpec

object ClassSetting {
  val x = 1
  
  def apply(): Unit = apply(0)
  def apply(a: Int): Unit = apply(a, 1)
  def apply(l: Long): Unit = apply(l.toInt, 1)
  
  def apply(a: Int, b: Int): Unit = {}
}

class Class(_x: Int = ClassSetting.x) {
  val x = _x
}

class ObjectCreationTest extends FunSpec {
  describe("Initialize class with overloaded argument") {
    it("should inherit value from object") {
       val c = new Class
       assert(c.x == ClassSetting.x)
     }
  }
  
  describe("Inspect Scala Object overloading apply method") {
    it("should not have exception") {
      try {
        ClassSetting(100)
        assert(true)
      } catch {
        case ex: Throwable =>
          ex.printStackTrace()
          assert(false)
      }
    }
  }
}