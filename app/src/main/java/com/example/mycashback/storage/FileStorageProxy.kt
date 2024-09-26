package com.example.mycashback.storage

open class FileStorageProxy(path: String) {
    private val storage = FileStorage(path)
    protected val data = storage.load()

    fun add(key: String, value: String) {
        data.put(key, value)
        storage.save(data)
    }

    fun get(key: String): String = data.optString(key)

    fun size(): Int {
        var count = 0
        data.keys().forEach { _ -> count++ }
        return count
    }

    fun remove(key: String) {
        data.remove(key)
        storage.save(data)
    }
}