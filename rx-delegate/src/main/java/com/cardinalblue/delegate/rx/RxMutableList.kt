package com.cardinalblue.delegate.rx

import com.cardinalblue.RxMutableListWrapper
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class RxMutableList<T : Any>(actual: MutableList<T>) : ReadWriteProperty<Any, MutableList<T>> {
    @Volatile
    private var actualWrapper = RxMutableListWrapper(actual = actual)

    val itemAdded: Observable<T> get() = actualWrapper.addedSignal
    val itemRemoved: Observable<T> get() = actualWrapper.removedSignal

    private val changedSignal = BehaviorRelay.createDefault(actualWrapper as MutableList<T>).toSerialized()
    val changed: Observable<MutableList<T>> get() = changedSignal.hide()

    override fun getValue(thisRef: Any, property: KProperty<*>): MutableList<T> {
        return actualWrapper
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: MutableList<T>) {
        val newOne = synchronized(this) {
            actualWrapper = RxMutableListWrapper(actual = value)
            actualWrapper
        }
        changedSignal.accept(newOne)
    }
}
