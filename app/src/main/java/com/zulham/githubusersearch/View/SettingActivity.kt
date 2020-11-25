package com.zulham.githubusersearch.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import com.zulham.githubusersearch.R
import com.zulham.githubusersearch.Utils.AlarmReceiver

class SettingActivity : AppCompatActivity(){

    private lateinit var switch: Switch
    private lateinit var textView: TextView

    private lateinit var alarmReceiver: AlarmReceiver

    companion object{

        private const val PREFS_NAME = "Preference"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val sharePref = applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        alarmReceiver = AlarmReceiver()

        textView = findViewById(R.id.alarmSetUp)

        showText(sharePref.getBoolean(PREFS_NAME, false))

        switch = findViewById(R.id.switch1)
        switch.isChecked = sharePref.getBoolean(PREFS_NAME, false)
        switch.setOnCheckedChangeListener { compoundButton, b -> val editor = sharePref.edit()

            editor.putBoolean(PREFS_NAME, b)
                    editor.apply()

            showText(b)

            when (b){
                true -> alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING)
                false -> alarmReceiver.cancelAlarm(this)
            }

        }

        setUpToolbar()

    }

    private fun showText(state : Boolean){
        if (state){
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.setTitle(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

}