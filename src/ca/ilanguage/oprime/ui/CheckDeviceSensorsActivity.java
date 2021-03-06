package ca.ilanguage.oprime.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.speech.tts.TextToSpeech;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import ca.ilanguage.oprime.R;


public class CheckDeviceSensorsActivity extends Activity implements TextToSpeech.OnInitListener {
	private static final String TAG = "RunExperimentActivity";
	/** Talk to the user */
	private TextToSpeech mTts;
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// Note that a language may not be available, and the result will
			// indicate this.
			int result = mTts.setLanguage(Locale.US);
			// Try this someday for some interesting results.
			// int result mTts.setLanguage(Locale.FRANCE);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Language data is missing or the language is not supported.
				Log.e(TAG, "Language is not available.");
			} else {
				//everything is working.
			}
		} else {
			// Initialization failed.
			Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        mTts = new TextToSpeech(this, this);
	}
	public void onTouchClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		startActivity(new Intent(this, ClearActivity.class));
	}
	public void onMultiTouchClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		startActivity(new Intent(this, MultiTouchVisualTestActivity.class));
	}
	public void onDrawingClick(View v){
		Toast.makeText(CheckDeviceSensorsActivity.this, "This hasn't been implemented yet.", Toast.LENGTH_LONG).show(); 

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		//startActivity(new Intent(this, TranslucentUserDrawActivity.class));
	}
	public void onAccelerometerClick(View v){

		Toast.makeText(CheckDeviceSensorsActivity.this, "This hasn't been implemented yet.", Toast.LENGTH_LONG).show(); 

	}
	public void onTestPlayAudioClick(View v){

        Toast.makeText(CheckDeviceSensorsActivity.this, "Now playing audio, can you hear it? \n\nIf not check to see if the media volumn is on in the Settings. \n\nOr, you can hold down the power button until you get the menu, maybe the device's sound is off.", Toast.LENGTH_LONG).show(); 
		MediaPlayer mp = MediaPlayer.create(this, R.raw.clubcreate);
	    mp.start();
	}
	public void onTestTTSClick(View v){

        Toast.makeText(CheckDeviceSensorsActivity.this, "Now playing some generated speech, can you hear it? The sound might be turned off, or maybe you dont have Text to Speech (TTS) installed.\n\nIf not you check the Settings>Voice input and Output>TextToSpeech>Install voice data to make sure you have some voice data installed. You can install Pico, its an OpenSource and free voice for English, French, German etc if you look for Pico on the market and install \n\nIf you don't like the voice you can search the Market for Loquendo, and install Susan, she costs about $3 (and she can make kissy noises :).", Toast.LENGTH_LONG).show(); 
		mTts.speak("If you can hear this, then your Android can talk! If you don't like my voice, you can get Loquendo Susan on the market for 3 dollars.",
        TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
        null);
	}
	public void onTestRecordAudioClick(View v){
		Toast.makeText(CheckDeviceSensorsActivity.this, "This hasn't been implemented yet.", Toast.LENGTH_LONG).show(); 

	}
	public void onTestRecordVideoClick(View v){
		Toast.makeText(CheckDeviceSensorsActivity.this, "This hasn't been implemented yet.", Toast.LENGTH_LONG).show(); 

		
	}
	
}
