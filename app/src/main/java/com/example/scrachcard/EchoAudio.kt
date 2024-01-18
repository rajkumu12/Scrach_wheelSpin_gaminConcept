package com.example.scrachcard

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.audiofx.PresetReverb
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class EchoAudio : AppCompatActivity() {
    lateinit var appDirectory: File
    lateinit var tv_recod: TextView
    lateinit var tv_stop_record: TextView
    lateinit var tv_Play: TextView
    lateinit var tv_stopPlay: TextView
    lateinit var file:File
    private var mRecorder: MediaRecorder? = null

    // creating a variable for mediaplayer class
    private var mPlayer: MediaPlayer? = null

    // string variable is created for storing a file name
    private var fileName: String? = null


    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_echo_audio)
        tv_recod = findViewById(R.id.start_rec)
        tv_stop_record = findViewById(R.id.stop_rec)
        tv_Play = findViewById(R.id.play_rec)
        tv_stopPlay = findViewById(R.id.stop_play)
        createAppDirectoryInDownloads(this)

        tv_recod.setOnClickListener {
            startRecording()
        }

        tv_stop_record.setOnClickListener {
            pauseRecording()
        }
        tv_Play.setOnClickListener {
            playAudio()
        }
        tv_stopPlay.setOnClickListener {
            pausePlaying()
        }


    }

    fun createAppDirectoryInDownloads(context: Context): File? {
        val downloadsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        appDirectory = File(downloadsDirectory, "EchoAudios")

        if (!appDirectory.exists()) {
            val directoryCreated = appDirectory.mkdir()
            if (!directoryCreated) {
                // Failed to create the directory
                return null
            }
        }

        return appDirectory
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        // below method is used to initialize
        // the media recorder class
        mRecorder = MediaRecorder(this@EchoAudio)

        // below method is used to set the audio
        // source which we are using a mic.
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)

        // below method is used to set
        // the output format of the audio.
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

        // below method is used to set the
        // audio encoder for our recorded audio.
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        // below method is used to set the
        // output file location for our recorded audio
        fileName = SimpleDateFormat("yyyyMMddHHmm'.mp3'").format(Date())
        file = File(appDirectory, fileName)
        try {
            if (!file.exists()) {
                val fileCreated = file.createNewFile()
                if (fileCreated) {
                    mRecorder!!.setOutputFile(file.absolutePath)
                    try {
                        // below method will prepare
                        // our audio recorder class
                        mRecorder!!.prepare()
                    } catch (e: IOException) {
                        Log.e("TAG", "prepare() failed  ${e.localizedMessage}")
                    }
                    // start method will start
                    // the audio recording.
                    mRecorder!!.start()
                    Toast.makeText(this@EchoAudio, "Started", Toast.LENGTH_SHORT).show()


                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    fun playAudio() {

        // for playing our recorded audio
        // we are using media player class.
        mPlayer = MediaPlayer()
        try {
            // below method is used to set the
            // data source which will be our file name


            mPlayer!!.setDataSource(file.absolutePath)
            val reverb = PresetReverb(0, mPlayer!!.getAudioSessionId())
            reverb.preset = PresetReverb.PRESET_LARGEROOM
            reverb.enabled=true
            mPlayer!!.setAuxEffectSendLevel(1.0f);
           /* val reverb = EnvironmentalReverb(1, mPlayer!!.audioSessionId)
            reverb.preset = PresetReverb.PRESET_LARGEHALL
            reverb.setEnabled(true)
            mPlayer!!.attachAuxEffect(reverb.id)
            mPlayer!!.setAuxEffectSendLevel(1.0f)*/
            // below method will prepare our media player
            mPlayer!!.prepare()

            // below method will start our media player.
            mPlayer!!.start()
            Toast.makeText(this, "Recording Started Playing", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("TAG", "prepare() failed")
        }
    }

    fun pauseRecording() {
        // below method will stop
        // the audio recording.
        mRecorder!!.stop()

        // below method will release
        // the media recorder class.
        mRecorder!!.release()
        mRecorder = null
        Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()
    }

    fun pausePlaying() {
        // this method will release the media player
        // class and pause the playing of our recorded audio.
        mPlayer!!.release()
        mPlayer = null
        Toast.makeText(this, "Recording Play Stopped", Toast.LENGTH_SHORT).show()
    }


}