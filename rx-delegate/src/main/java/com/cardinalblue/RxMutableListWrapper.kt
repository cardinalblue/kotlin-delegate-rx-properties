package com.cardinalblue

import com.jakewharton.rxrelay2.BehaviorRelay

/**
 * Encapsulate [MutableMap] and add ReactiveX observable functionality in the
 * wrapper class.
 */
class RxMutableListWrapper<T>(private val actual: MutableList<T>) : MutableList<T> {

    val addedSignal = BehaviorRelay.create<T>()!!
    val removedSignal = BehaviorRelay.create<T>()!!

    override fun get(index: Int): T {
        return actual[index]
    }

    override fun indexOf(element: T): Int {
        return actual.indexOf(element)
    }

    override fun lastIndexOf(element: T): Int {
        return actual.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> {
        return actual.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return actual.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return actual.subList(fromIndex, toIndex)
    }

    private val lock = Any()

    override val size: Int
        get() = synchronized(lock) { actual.size }

    override fun contains(element: T): Boolean {
        return synchronized(lock) {
            actual.contains(element)
        }
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return synchronized(lock) {
            actual.containsAll(elements)
        }
    }

    override fun isEmpty(): Boolean {
        return synchronized(lock) {
            actual.isEmpty()
        }
    }

    override fun iterator(): MutableIterator<T> {
        // TODO: Fix the addition and removal observable
        return actual.iterator()
    }

    override fun add(element: T): Boolean {
        return synchronized(lock) {
            if (actual.add(element)) {
                addedSignal.accept(element)
                true
            } else {
                false
            }
        }
    }

    override fun add(index: Int, element: T) {
        return synchronized(lock) {
            actual.add(index, element)
            addedSignal.accept(element)
        }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return synchronized(lock) {
            if (actual.addAll(index, elements)) {
                elements.forEach { addedSignal.accept(it) }
                true
            } else {
                false
            }
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return synchronized(lock) {
            if (actual.addAll(elements)) {
                elements.forEach { addedSignal.accept(it) }
                true
            } else {
                false
            }
        }
    }

    override fun set(index: Int, element: T): T {
        return synchronized(lock) {
            val result = actual.set(index, element)
            removedSignal.accept(result)
            addedSignal.accept(element)
            return result
        }
    }

    override fun clear() {
        synchronized(lock) {
            val items = actual.toList()
            actual.clear()
            items.forEach { removedSignal.accept(it) }
        }
    }


    override fun removeAt(index: Int): T {
        return synchronized(lock) {
            val result = actual.removeAt(index)
            removedSignal.accept(result)
            result
        }
    }

    override fun remove(element: T): Boolean {
        return synchronized(lock) {
            if (actual.remove(element)) {
                removedSignal.accept(element)
                true
            } else {
                false
            }
        }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return synchronized(lock) {
            if (actual.removeAll(elements)) {
                elements.forEach { removedSignal.accept(it) }
                true
            } else {
                false
            }
        }
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        // TODO: Fix the addition and removal observable
        return actual.retainAll(elements)
    }
}
