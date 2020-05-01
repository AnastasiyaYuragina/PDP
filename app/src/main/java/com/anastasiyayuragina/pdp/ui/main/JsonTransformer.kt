package com.anastasiyayuragina.pdp.ui.main

class JsonTransformer {
    fun <K, V> toJson(map: Map<K, V>): String {
        val builder = mutableListOf<String>()
        map.forEach {
            builder.add(
                when (val value = it.value) {
                    is Int -> value toString it.key.toString()
                    is String -> value toString it.key.toString()
                    is Map<*, *> -> value toString it.key.toString()
                    is Collection<*> -> value toString it.key.toString()
                    is Boolean -> value toString it.key.toString()
                    null -> toNullString(it.key.toString())
                    else -> ""
                }
            )
        }

        return "{${builder.joinToString(",")}}"
    }

    private infix fun Int.toString(key: String): String {
        return "\"$key\": $this"
    }

    private infix fun Map<*, *>.toString(key: String): String {
        return "\"$key\": ${toJson(this)}"
    }

    private infix fun Collection<*>.toString(key: String): String {
        return "\"$key\": ${this.map {
            when (it) {
                is Map<*, *> -> it.toString()
                is Collection<*> -> it.toString()
                else -> it
            }
        }}"
    }

    private infix fun Boolean.toString(key: String): String {
        return "\"$key\": $this"
    }

    private fun toNullString(key: String): String {
        return "\"$key\": null"
    }

    private infix fun String.toString(key: String): String {
        return "\"$key\": \"$this\""
    }
}

fun jsonTransformer(init: JsonTransformer.() -> Unit): JsonTransformer {
    val jsonTransformer = JsonTransformer()
    jsonTransformer.init()
    return jsonTransformer
}