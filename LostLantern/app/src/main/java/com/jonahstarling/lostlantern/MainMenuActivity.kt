package com.jonahstarling.lostlantern

import android.app.Activity
import android.os.Bundle

/**
 * Created by jonah on 11/11/17.
 */
class MainMenuActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val fragment = MainMenuFragment()
        fragmentManager.beginTransaction().add(R.id.main_menu_view, fragment).commit()
    }

}