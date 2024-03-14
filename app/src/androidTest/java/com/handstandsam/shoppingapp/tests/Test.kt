package com.handstandsam.shoppingapp.tests

import org.junit.Test

class Test {

  @Test
  fun testException() {
    throw RuntimeException("Test Exception!")
  }

  @Test
  fun testSuccess() {
    throw RuntimeException("Test Success!")
  }

}