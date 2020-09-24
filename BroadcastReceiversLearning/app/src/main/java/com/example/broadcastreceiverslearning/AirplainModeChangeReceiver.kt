package com.example.broadcastreceiverslearning

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplainModeChangeReceiver : BroadcastReceiver() {
    //intent хранит информацию про то нажата кнопка или нет
    override fun onReceive(context: Context?, intent: Intent?) {

        val isAirplainModeEnabled = intent?.getBooleanExtra("state", false) ?: return

        if (isAirplainModeEnabled) {
            Toast.makeText(context, "AirplaneMode Enabled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "AirplaneMode Disabled", Toast.LENGTH_LONG).show();
        }
    }
}