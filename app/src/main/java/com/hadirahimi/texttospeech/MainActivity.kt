package com.hadirahimi.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.hadirahimi.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() , TextToSpeech.OnInitListener
{
    private lateinit var binding : ActivityMainBinding
    private lateinit var tts : TextToSpeech
    
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //initViews
        binding.apply {
            tts = TextToSpeech(this@MainActivity,this@MainActivity)
            btnStart.setOnClickListener {
                val text = edittext.text.toString()
                if (text.isEmpty()) Toast.makeText(this@MainActivity , "please enter the text" , Toast.LENGTH_SHORT).show()
                else speak(text)
            }
        }
    }
    
    override fun onInit(status : Int)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            val result = tts.setLanguage(Locale.US)
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                //Handle language not supported
                Toast.makeText(this@MainActivity , "Language not supported" , Toast.LENGTH_SHORT).show()
            }else{
                binding.btnStart.isEnabled = true
                tts.setSpeechRate(0.5f)//set the speech rate to 0.5
            }
        }else
        {
            //Handle TTS initialization failed
            Toast.makeText(this@MainActivity , "error please try again" , Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun speak(text:String)
    {
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
    
    override fun onDestroy()
    {
        if(tts.isSpeaking)
            tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}












