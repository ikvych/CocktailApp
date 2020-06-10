package com.devlight.school.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class FlyModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action: String? = intent?.action
        val isAirplaneModeOn = if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            intent!!.getBooleanExtra("state", false)
        } else {
            false
        }

        if (isAirplaneModeOn) {
            // AP mode is on
            Toast.makeText(context, "Смакуйте маргаритку із відчуттям міри. І надіємося, що Ви не пілот під  час польоту", Toast.LENGTH_LONG).show()
        }
    }
}