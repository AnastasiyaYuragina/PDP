package com.anastasiyayuragina.pdp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.anastasiyayuragina.pdp.R
import org.json.JSONObject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = "{" +
                "\"array\": [" +
                "1," +
                "2," +
                "3" +
                "]," +
                "\"boolean\": true," +
                "\"color\": \"gold\"," +
                "\"null\": null," +
                "\"number\": 123," +
                "\"object\": {" +
                "\"a\": \"b\"," +
                "\"c\": \"d\"" +
                "}," +
                "\"string\": \"Hello World\"" +
                "}"

        val map = emptyMap<String, Any?>().toMutableMap()

        map["array"] = listOf(1, 2, 3)
        map["boolean"] = true
        map["color"] = "gold"
        map["null"] = null
        map["number"] = 123
        map["object"] = mapOf(Pair("a", "b"), Pair("c", "d"))
        map["string"] = "Hello World"

        println(" @@ ${map.toJson()}")

        JSONObject(map).toString()
    }
}

private fun <K, V> Map<K, V>.toJson(): String {
    var i = ""
    this.forEach {
        i += when(val value = it.value) {
            is Int -> value.toString(it.key.toString())
            is String -> value.toString(it.key.toString())
            is Map<*, *> -> value.toString(it.key.toString())
            is Collection<*> -> value.toString(it.key.toString())
            is Boolean -> value.toString(it.key.toString())
            is Nullable -> value.toString(it.key.toString())
            else -> ""
        }
    }

    return "{$i}"
}

infix fun Int.toString(key: String): String {
    return "\"$key\": $this"
}

infix fun Map<*, *>.toString(key: String): String {
    return "\"$key\": ${this.toJson()}"
}

infix fun Collection<*>.toString(key: String) : String {
    return "\"$key\": ${this.map { 
        when(it) {
            is Map<*, *> -> it.toJson()
            is Collection<*> -> it.toString() 
            else -> it
        }
    }}"
}

infix fun Boolean.toString(key: String): String {
    return "\"$key\": $this"
}

infix fun Nullable.toString(key: String): String {
    return "\"$key\": null"
}

infix fun String.toString(key: String): String {
    return "\"$key\": \"$this\""
}
