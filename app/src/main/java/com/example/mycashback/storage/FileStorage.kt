package com.example.mycashback.storage

import org.json.JSONObject
import java.io.File

class FileStorage(private val path: String) {
    private val storage = File(path)

    init {
        if (!storage.exists()) {
            storage.createNewFile()
            storage.writeText("{}")
        }
    }

    fun load(): JSONObject {
        return JSONObject(storage.readText())
    }

    fun save(obj: JSONObject) {
        storage.writeText(obj.toString())
    }
}