package com.handstandsam.shoppingapp.tests

import android.widget.Toast
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.burst.Burst
import org.junit.Test

enum class Espresso { Decaf, Regular, Double }

enum class Dairy { None, Milk, Oat }

/**
 * Example Test using Burst v2.0
 */
@Burst
class Burst2AndroidTest(
  private val espresso: Espresso,
) {

  @Test
  fun justEspresso() {
    showToastMessage("Running $espresso")
  }

  @Test
  fun espressoAndDairy(dairy: Dairy) {
    showToastMessage("Running $espresso $dairy")
  }

  private fun showToastMessage(message: String) {
    println("Showing toast with: $message")
    val instrumentation = InstrumentationRegistry.getInstrumentation()
    instrumentation.runOnMainSync {
      Toast.makeText(instrumentation.targetContext, message, Toast.LENGTH_SHORT).show()
    }
    Thread.sleep(1000)
  }
}
