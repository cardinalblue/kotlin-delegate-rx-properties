package com.cardinalblue

import com.cardinalblue.delegate.rx.RxMutableSet
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RxMutableSetTest {

    private var mutableSet by RxMutableSet(mutableSetOf<Int>())

    @Test
    fun `observe value replaced`() {
        // Reflect the prop$delegate
        val tester = this::mutableSet
            .changed()
            .test()

        mutableSet = mutableSetOf(5, 6)

        System.out.println("size=${mutableSet.size}")

        Assert.assertEquals(2, mutableSet.size)
        tester.assertValueCount(2)
    }

    @Test
    fun `observe item added`() {
        // Reflect the prop$delegate
        val tester = this::mutableSet
            .itemAdded()
            .test()

        mutableSet.add(5)
        mutableSet.add(6)
        mutableSet.add(7)

        System.out.println("size=${mutableSet.size}")

        tester.assertValueCount(3)
    }

    @Test
    fun `observe item removed`() {
        // Reflect the prop$delegate
        val tester = this::mutableSet
            .itemRemoved()
            .test()

        mutableSet.add(5)
        mutableSet.add(6)
        mutableSet.add(7)
        mutableSet.remove(5)
        mutableSet.remove(6)
        mutableSet.remove(7)

        System.out.println("size=${mutableSet.size}")

        tester.assertValueCount(3)
    }
}
