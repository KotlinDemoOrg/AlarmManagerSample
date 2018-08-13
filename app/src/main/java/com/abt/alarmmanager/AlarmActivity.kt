package com.abt.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

/**
 * @描述： @AlarmActivity
 * @作者： @黄卫旗
 * @创建时间： @13/08/2018
 */
class AlarmActivity : AppCompatActivity(), View.OnClickListener {

    val ALARM_EVENT:String = "com.abt.alarmmanager.AlarmActivity.AlarmReceiver"
    var mDalay:Int = 5
    var mDesc:String = ""
    var bArrived:Boolean = false
    private var alarmReceiver:AlarmReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        btn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        alarmReceiver = AlarmReceiver()
        val filter = IntentFilter(ALARM_EVENT)
        registerReceiver(alarmReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(alarmReceiver)
    }

    override fun onClick(v: View) {
        if (v!!.id == R.id.btn) {
            var intent = Intent(ALARM_EVENT)
            var pIntent:PendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            var alarmManager:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            calendar.add(Calendar.SECOND, mDalay)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pIntent)
            text.setText("设置闹钟")
        }
    }

    inner class AlarmReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (text != null && bArrived == false) {
                    mDesc = "闹钟时间到达"
                    Log.d("TAG", mDesc)
                    text.setText(mDesc)
                }
            }
        }
    }

}
