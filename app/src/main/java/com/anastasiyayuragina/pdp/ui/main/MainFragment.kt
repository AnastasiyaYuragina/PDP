package com.anastasiyayuragina.pdp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.anastasiyayuragina.pdp.R

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

        map.toJson()
    }
}

private fun <K, V> Map<K, V>.toJson(): String {
    return "{ ${this.map {
        when(val value = it.value) {
            is Int -> value.toString(it.key as String)
            is String -> {}
            is Map<*, *> -> {}
            is Collection<*> -> {}
            is Boolean -> {}
            is Nullable -> {}
            else -> {}
        }
    }
    }" +
            "}"

}

infix fun Int.toString(key: String): String {
    return "$key: $this"
}