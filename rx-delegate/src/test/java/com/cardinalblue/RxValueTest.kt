package com.cardinalblue

import com.cardinalblue.delegate.rx.RxValue
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RxValueTest {

    private var valueActual by RxValue(0)

    @Test
    fun `observe value assignment`() {
        // Reflect the prop$delegate
        val candidate: Observable<Int> = this::valueActual.changed()

        val tester = candidate
            .test()

        valueActual = 5
        valueActual = 6
        valueActual = 7

        System.out.println("foo=$valueActual")

        tester.assertValueCount(4)
    }
}
