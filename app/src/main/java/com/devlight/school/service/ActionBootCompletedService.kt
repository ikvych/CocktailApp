package com.devlight.school.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.JobIntentService
import com.devlight.school.R
import com.devlight.school.ui.activity.MainActivity

class ActionBootCompletedService : JobIntentService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val context: Context = applicationContext
        Toast.makeText(context, "Додаток CocktailApp запуститься через: 3 секунд!!", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleWork(intent: Intent) {
        val context: Context = applicationContext

        try {
            Log.d("MyLog", "Start cycle in HandleWork")
            for (i in 0..2 ) {
                Thread.sleep(1000)
                Log.d("MyLog", "Thread sleep $i second Thread - ${Thread.currentThread().name}")
            }
            Log.d("MyLog", "Finish cycle")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Intent(context, MainActivity::class.java).apply {
            startActivity(this)
        }
    }

}