package com.example.mycashback.storage

class BanksFileStorageProxy(path: String) : FileStorageProxy(path) {
    init {
        compact()
    }

    private fun compact() {
        val banks = mutableSetOf<String>()
        val currentKeys = mutableListOf<String>()

        data.keys().withIndex().forEach {
            banks.add(data.getString(it.value))
            currentKeys.add(it.value)
        }

        currentKeys.forEach { super.remove(it) }

        banks.sorted().withIndex().forEach {
            super.add(it.index.toString(), it.value)
        }
    }

    fun add(value: String) {
        super.add(size().toString(), value)
        compact()
    }

    override fun remove(key: String) {
        super.remove(key)
        compact()
    }
}