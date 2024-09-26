package com.example.mycashback.storage

class CategoriesFileStorageProxy(path: String) : FileStorageProxy(path) {
    fun list(): List<String> {
        val keys = mutableListOf<String>()
        data.keys().forEach { keys.add(it) }
        keys.sort()
        return keys
    }
}