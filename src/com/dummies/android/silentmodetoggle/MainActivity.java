package com.dummies.android.silentmodetoggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	private AudioManager mAudioManager;
	private boolean mPhoneIsSilent;
	private Button increaseButton;
    private Button decreaseButton;
    private TextView volumevView;
    private Button toggleButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        increaseButton=(Button)findViewById(R.id.increase);
        decreaseButton=(Button)findViewById(R.id.decrese);
        volumevView=(TextView)findViewById(R.id.volume);
        checkIfPhoneIsSilent();
        toggleButton=(Button)findViewById(R.id.toggleButton);
      
        
        if(mPhoneIsSilent){
        	toggleButton.setText(R.string.On);
        	increaseButton.setEnabled(false);
        	decreaseButton.setEnabled(false);
        	volumevView.setText(R.string.None);
        }else {
			toggleButton.setText(R.string.off);
			increaseButton.setEnabled(true);
        	decreaseButton.setEnabled(true);
        	volumevView.setText(String.valueOf(mAudioManager.getStreamVolume(2)));
        }
        
        toggleButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		
        			if (mPhoneIsSilent) { 
        			// Change back to normal mode
        			mAudioManager
        			.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        			mPhoneIsSilent = false;      			
        			} else {
        			// Change to silent mode
        			mAudioManager
        			.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        			mPhoneIsSilent = true;      
        			
        			}
        			toggleUi();
        			}
        	});
        
        decreaseButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				mAudioManager.adjustSuggestedStreamVolume(-1, 2,0);
				int temp=mAudioManager.getStreamVolume(2);
				if(temp!=0){
					volumevView.setText(String.valueOf(temp));
				}else {
					mPhoneIsSilent=true;
					toggleUi();
				}
			}
		});
        
        increaseButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				mAudioManager.adjustSuggestedStreamVolume(1, 2,0);
				volumevView.setText(String.valueOf(mAudioManager.getStreamVolume(2)));
			}
		});
    }
    
    	private void checkIfPhoneIsSilent() { 
	    	int ringerMode = mAudioManager.getRingerMode();
	    	if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
	    	mPhoneIsSilent = true;
	    	} else {
	    	mPhoneIsSilent = false;
	    	}
    	}
    	
    	private void toggleUi() { 
	    		ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
	    		Drawable newPhoneImage;
	    		if (mPhoneIsSilent) {
	    		newPhoneImage =
	    		getResources().getDrawable(R.drawable.off);
	    		increaseButton.setEnabled(false);
	        	decreaseButton.setEnabled(false);
	        	volumevView.setText(R.string.None);	        	
	        	toggleButton.setText(R.string.On);
	    		} else {
	    		newPhoneImage =
	    		getResources().getDrawable(R.drawable.on);
	    		increaseButton.setEnabled(true);
	        	decreaseButton.setEnabled(true);
	        	volumevView.setText(String.valueOf(mAudioManager.getStreamVolume(2)));
	        	toggleButton.setText(R.string.off);
	    		}
	    		imageView.setImageDrawable(newPhoneImage);
		}
    	
    	protected void onResume() {
    		super.onResume();
    		checkIfPhoneIsSilent();
    		toggleUi();
    	}
}