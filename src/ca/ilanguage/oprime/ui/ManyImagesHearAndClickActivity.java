package ca.ilanguage.oprime.ui;

import java.util.Locale;

import ca.ilanguage.oprime.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.format.Time;

public class ManyImagesHearAndClickActivity extends Activity implements TextToSpeech.OnInitListener {
	private static final String TAG = "RunExperimentActivity";
	/** Talk to the user */
    private TextToSpeech mTts;

//    private Time startTime = new Time();
//    private Time endTime = new Time();
//    private Time reactionTime = new Time();
    private Long startTime;
    private Long endTime;
    private Long reactionTime;
    
  //implement on Init for the text to speech
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

				// mSpeakButton.setEnabled(true);
				// mPauseButton.setEnabled(true);
				// Greet the user.
				// sayHello();
				
//				mTts.speak("Click on the dog with a coat.",
//		    	        TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
//		    	        null);
				
			}
		} else {
			// Initialization failed.
			Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	//endTime = System.currentTimeMillis();
            	endTime = System.currentTimeMillis();
            	//Long reactionTimeMilliseconds = endTime.normalize(false) - startTime.normalize(false);									//http://developer.android.com/reference/android/text/format/Time.html#toMillis(boolean)
            	Long reactionTimeMilliseconds = endTime-startTime;
            	mTts.speak("The participant clicked on picture "+position+", in "+reactionTimeMilliseconds+" milliseconds. This has been recorded in the Google Spreadsheet.",
            	        TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
            	        null);
                Toast.makeText(ManyImagesHearAndClickActivity.this, "Picture" + position +", in "+reactionTimeMilliseconds+" milliseconds", Toast.LENGTH_LONG).show();
            }
        });
        mTts = new TextToSpeech(this, this);
        
        playAudio();
        
        //turn on video see APIDemos>Graphcis>camerapreview
        //to display rotating rubix cube for mask look at APIDemo>(graphics?)Kubein cube
        //to read and write external storage see apidemos>content>external storage
    }
    
    public void playAudio(){
//    	try {
//			//Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			Toast.makeText(RunExperimentActivity.this, "The experiment was interupted. Invalid reaction time.", Toast.LENGTH_LONG).show();
//
//		}
        
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click_on_dog_coat);
	    mp.start();
	    //startTime.setToNow();
	    startTime = System.currentTimeMillis();
        
    }

	public void onRunExperimentClick(View v){
    	mTts.speak("This would run the experiment.",
        TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
        null);
    	startActivity(new Intent(this, ManyImagesHearAndClickActivity.class));
    }

	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;

	    public ImageAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(285, 285));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	    }

	    // references to our images
	    private Integer[] mThumbIds = {
//	            R.drawable.sample_2, R.drawable.sample_3,
//	            R.drawable.sample_4, R.drawable.sample_5,
//	            R.drawable.sample_6, R.drawable.sample_7,
//	            R.drawable.sample_0, R.drawable.sample_1,
//	            R.drawable.sample_2, R.drawable.sample_3,
//	            R.drawable.sample_4, R.drawable.sample_5,
//	            R.drawable.sample_6, R.drawable.sample_7,
	            R.drawable.sample_0, R.drawable.sample_1,
	            R.drawable.sample_2, R.drawable.sample_3
//	            R.drawable.sample_4, R.drawable.sample_5,
//	            R.drawable.sample_6, R.drawable.sample_7
	    };
	}
}
