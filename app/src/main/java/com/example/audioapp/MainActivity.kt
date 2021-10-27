package com.example.audioapp

import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mr : MediaRecorder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var path = Environment.getExternalStorageDirectory().toString()+"/myrec.wav"
        mr = MediaRecorder()

        start.isEnabled = false
        stop.isEnabled = false

        if (ActivityCompat.checkSelfPermission(this, RECORD_AUDIO)!= PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(RECORD_AUDIO, MANAGE_EXTERNAL_STORAGE),111)

        start.isEnabled = true
        start.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()
            stop.isEnabled = true
            start.isEnabled =false
        }

        stop.setOnClickListener {
            mr.stop()
            start.isEnabled = true
            stop.isEnabled =false
        }

        play.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PERMISSION_GRANTED) {
            start.isEnabled = true
        }


    }
}