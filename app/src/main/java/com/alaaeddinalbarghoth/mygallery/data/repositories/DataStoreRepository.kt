package com.alaaeddinalbarghoth.mygallery.data.repositories

import androidx.datastore.core.DataStore
import com.google.gson.Gson
import java.util.prefs.Preferences

class DataStoreRepository(private val gson: Gson, private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
    }
}