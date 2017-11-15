package com.jonahstarling.lostlantern

import android.app.Activity
import android.os.Bundle

/**
 * Created by jonah on 11/15/17.
 */
class GameActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val fragment = GameFragment()
        fragmentManager.beginTransaction().add(R.id.game_view, fragment).commit()
    }

}