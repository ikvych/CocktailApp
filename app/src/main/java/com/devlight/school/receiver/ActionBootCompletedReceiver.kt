package com.devlight.school.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.devlight.school.service.ActionBootCompletedService
import com.devlight.school.ui.activity.MainActivity

class ActionBootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("MyLog", "StartUpBootReceiver BOOT_COMPLETED");
            val newIntent = Intent(context, ActionBootCompletedService::class.java)
            context?.startService(newIntent)
        }
    }

}