package com.ibm.csi.npm.storage.collagen.core

import org.scalatest.FunSpec

class EpochSupportTest extends FunSpec with EpochSupport {
  describe("nextEpoch toInt") {
    var epoch = this.nextEpoch
    var epochInt = epoch.toInt
    
    it("should be type of Int") {
      assert(epochInt.isInstanceOf[Int])
    }
    
    it("should be different Int value") {
      var epochInt2 = this.nextEpoch.toInt
      info(s"epochInt - $epochInt")
      info(s"epochInt2 - $epochInt2")
      assert(epochInt != epochInt2)
    }
  }
}