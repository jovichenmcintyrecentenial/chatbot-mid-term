package com.jc.bot.data

import android.content.Context

object SharedPreferenceStore {

        private const val MY_PREF = "my_prefs"
        const val USERNAME = "user_name"

        fun saveData(context: Context, key: String, value: String) {
            val prefs = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun loadData(context: Context, key: String, defaultValue: String): String {
            val prefs = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
            return prefs.getString(key, defaultValue) ?: defaultValue
        }
}



