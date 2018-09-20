package com.cardinalblue

import com.cardinalblue.delegate.rx.RxMutableMap
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RxMutableMapTest {

    private var mutableMap by RxMutableMap(mutableMapOf<Int, String>())

    @Test
    fun `observe value replacement`() {
        // Reflect the prop$delegate
        val tester = this::mutableMap
            .changed()
            .test()

        mutableMap = mutableMapOf(Pair(0, "000"),
                                  Pair(1, "111"))

        Assert.assertEquals(2, mutableMap.size)
        tester.assertValueCount(2)
    }

    @Test
    fun `observe item added`() {
        // Reflect the prop$delegate
        val tester = this::mutableMap
            .tupleAdded()
            .test()

        mutableMap[5] = "555"
        mutableMap[6] = "666"
        mutableMap[7] = "777"

        System.out.println("size=${mutableMap.size}")

        Assert.assertEquals(3, mutableMap.size)
        tester.assertValueCount(3)
    }

    @Test
    fun `observe item removed`() {
        // Reflect the prop$delegate
        val tester = this::mutableMap
            .tupleRemoved()
            .test()

        mutableMap[5] = "555"
        mutableMap[6] = "666"
        mutableMap[7] = "777"
        mutableMap.remove(5)
        mutableMap.remove(6)
        mutableMap.remove(7)

        System.out.println("size=${mutableMap.size}")

        Assert.assertEquals(0, mutableMap.size)
        tester.assertValueCount(3)
    }

    @Test
    fun `observe item replaced`() {
        // Reflect the prop$delegate
        val additionTester = this::mutableMap
            .tupleAdded()
            .test()
        val removalTester = this::mutableMap
            .tupleRemoved()
            .test()

        mutableMap[5] = "555"
        mutableMap[5] = "555-new"

        System.out.println("size=${mutableMap.size}")

        Assert.assertEquals(1, mutableMap.size)
        additionTester.assertValueCount(2)
        removalTester.assertValueCount(1)
    }
}
