package com.handstandsam.shoppingapp.cart

import android.os.Handler
import android.os.Looper
import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * How to test [Room] https://developer.android.com/training/data-storage/room/testing-db
 */
class RoomShoppingCartTest {

    private lateinit var testDelegate: RoomShoppingCartTestDelegate

    @Before
    fun setUp() {
        //Using a latch is a hack to force the cart observer to register on the Main thread, but wait until it's done.
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            testDelegate = RoomShoppingCartTestDelegate()
            latch.countDown()
        }
        latch.await(1, TimeUnit.SECONDS)
    }

    @Test
    fun happyPath() = runBlocking<Unit> {
        testDelegate
            .assertTotalItemsInCart(0, 0)
            .incrementItemInCart(item1)
            .assertPersisted(item1, 1)
            .assertTotalItemsInCart(1, 1)
            .incrementItemInCart(item2)
            .assertPersisted(item1, quantity = 1)
            .assertPersisted(item2, quantity = 1)
            .assertTotalItemsInCart(2, 2)
            .incrementItemInCart(item1)
            .assertPersisted(item1, quantity = 2)
            .assertTotalItemsInCart(2, 3)
            .decrementItemInCart(item2)
            .assertTotalItemsInCart(1, 2)
            .assertPersisted(item1, quantity = 2)
            .clearDb()
            .assertTotalItemsInCart(0, 0)
    }

    @Test
    fun removeItemThatIsNotThere() = runBlocking<Unit> {
        testDelegate
            .assertTotalItemsInCart(0, 0)
            .decrementItemInCart(item2)
            .assertTotalItemsInCart(0, 0)
    }

    @Test
    fun add300Items() = runBlocking<Unit> {
        val limit = 300
        (1..limit).forEach { _ ->
            testDelegate.incrementItemInCart(item1)
        }
        testDelegate.assertTotalItemsInCart(1, limit)
    }

    companion object {
        val item1 = Item(
            label = "Cool Thing 1",
            image = "https://...jpg",
            link = null
        )

        val item2 = Item(
            label = "Cool Thing 2",
            image = "https://...jpg",
            link = null
        )
    }

}