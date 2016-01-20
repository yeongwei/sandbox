package org.example

import org.scalatest.FunSpec

object ClassSetting {
  val x = 1
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
}