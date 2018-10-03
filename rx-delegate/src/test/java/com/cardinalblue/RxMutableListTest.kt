package com.cardinalblue

import com.cardinalblue.delegate.rx.RxMutableList
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RxMutableListTest {

    private var mutableList by RxMutableList(mutableListOf<String>())

    @Test
    fun `observe value replacement`() {
        val tester = this::mutableList
            .changed()
            .test()

        mutableList = mutableListOf("1", "2")

        Assert.assertEquals(2, mutableList.size)
        tester.assertValueCount(2)
    }

    @Test
    fun `observe item added`() {
        this::mutableList
        val tester = this::mutableList
            .elementAdded()
            .test()

        mutableList.add("555")
        mutableList.add("666")
        mutableList.add("777")

        Assert.assertEquals(3, mutableList.size)
        tester.assertValueCount(3)
    }

    @Test
    fun `observe item removed`() {
        // Reflect the prop$delegate
        val tester = this::mutableList
            .elementRemoved()
            .test()

        mutableList.add("5")
        mutableList.add("6")
        mutableList.add("7")

        mutableList.remove("5")
        mutableList.remove("6")
        mutableList.remove("7")

        Assert.assertEquals(0, mutableList.size)
        tester.assertValueCount(3)
    }

    @Test
    fun `observe item replaced`() {
        // Reflect the prop$delegate
        val additionTester = this::mutableList
            .elementAdded()
            .test()
        val removalTester = this::mutableList
            .elementRemoved()
            .test()


        mutableList.add("555")
        mutableList[0] = "555-new"

        Assert.assertEquals(1, mutableList.size)
        additionTester.assertValueCount(2)
        removalTester.assertValueCount(1)
    }
}
