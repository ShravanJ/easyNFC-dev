//Copyright 2014-2015 Shravan Jambukesan. Contains re-used code by Akash Kakkilaya.
//This feature has not yet been tested on a device yet
//Code re-used from WriteUrlActivity.java modified to handle opening of app bundle IDs


/*someone pls test this out and tell me if it works so i can either keep it or throw it out. its literally a crap
re-implementation of the url writter that i put together in like 10 minutes. or help me fix it as i dont have much 
time for this project anymore. thanks ~shravanj
*/

package com.pixel.nfcsettings;

import java.io.IOException;
import java.nio.charset.Charset;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteApp extends Activity {

	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private String urlAddress="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.writeurl);
        final EditText urlEditText = (EditText)this.findViewById(R.id.urltext);

        Button writeUrlButton = (Button)this.findViewById(R.id.writeurlbutton);
        writeUrlButton.setOnClickListener(new View.OnClickListener() 
        {
        	public void onClick(View view) 
        	{
        		urlAddress = urlEditText.getText().toString();
         		TextView messageText = (TextView)findViewById(R.id.URL);
         		messageText.setText("Touch NFC Tag to write app to open " +urlAddress);
        		        		
        	}
        });
    
       
     	 mNfcAdapter = NfcAdapter.getDefaultAdapter(this); 
     	 mPendingIntent = PendingIntent.getActivity(this, 0,
         new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    	 IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
    	 	//ndef.addDataScheme("http");
	 	 mFilters = new IntentFilter[] {ndef,};
         mTechLists = new String[][] { new String[] { Ndef.class.getName() },
    		   new String[] { NdefFormatable.class.getName() }};

     	
    }
    
    @Override
    public void onResume() 
    {
        super.onResume();
        if (mNfcAdapter != null) mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                mTechLists);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);  
        byte[] uriField = urlAddress.getBytes(Charset.forName("US-ASCII"));
        byte[] payload = new byte[uriField.length + 1];              //add 1 for the URI Prefix
        payload[0] = 0x0;                                      
        System.arraycopy(uriField, 0, payload, 1, uriField.length);  //appends URI to payload
        NdefRecord URIRecord  = new NdefRecord(
            NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, new byte[0], payload);
        NdefMessage newMessage= new NdefMessage(new NdefRecord[] { URIRecord });
        writeNdefMessageToTag(newMessage, tag);     
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }
    
    boolean writeNdefMessageToTag(NdefMessage message, Tag detectedTag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                	Toast.makeText(this, "Tag is read-only.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                	Toast.makeText(this, "The data cannot written to tag, Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + size + " bytes.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                ndef.writeNdefMessage(message);
                ndef.close();                
                Toast.makeText(this, "Message has been written to tag.", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                NdefFormatable ndefFormat = NdefFormatable.get(detectedTag);
                if (ndefFormat != null) {
                    try {
                    	ndefFormat.connect();
                    	ndefFormat.format(message);
                    	ndefFormat.close();
                        Toast.makeText(this, "The data has been written to the tag ", Toast.LENGTH_SHORT).show();
                        return true;
                    } catch (IOException e) {
                    	 Toast.makeText(this, "Failed to format tag", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                	 Toast.makeText(this, "NDEF is not supported", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch (Exception e) {
        	Toast.makeText(this, "Write opreation has failed", Toast.LENGTH_SHORT).show();
        }
        return false;
        
        
    }
}
    
    




