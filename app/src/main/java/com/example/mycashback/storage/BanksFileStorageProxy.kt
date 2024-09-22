package com.example.mycashback.storage

class BanksFileStorageProxy(path: String) : FileStorageProxy(path) {
    init {
        compact()
    }

    private fun compact() {
        val banks = mutableMapOf<String, String>()
        val currentKeys = mutableListOf<String>()

        data.keys().withIndex().forEach {
            banks[it.index.toString()] = data.getString(it.value)
            currentKeys.add(it.value)
        }

        currentKeys.forEach { super.remove(it) }

        banks.forEach { super.add(it.key, it.value) }
    }

    fun add(value: String) {
        compact()
        super.add(size().toString(), value)
    }
}