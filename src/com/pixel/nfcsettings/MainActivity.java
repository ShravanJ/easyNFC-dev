//Copyright 2013-2015 Shravan Jambukesan and Akash Kakkilaya
package com.pixel.nfcsettings;


import com.pixel.nfcsettings.R;
import com.pixel.nfcsettings.Writephoneactivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button writePhoneButton = (Button) findViewById(R.id.phone);
        writePhoneButton.setOnClickListener(new View.OnClickListener() 
        {
           public void onClick(View view)
           {
                Intent myIntent = new Intent( view.getContext(), Writephoneactivity.class);
                startActivityForResult(myIntent, 0);
           }
        });
        

        Button writeUrlButton = (Button) findViewById(R.id.url1);
        writeUrlButton.setOnClickListener(new View.OnClickListener() 
        {
           public void onClick(View view)
           {
                Intent myIntent = new Intent( view.getContext(), WriteUrlActivity.class);
                startActivityForResult(myIntent, 0);
           }
        });
        
        Button writeMapButton = (Button) findViewById(R.id.mapmain);
        writeMapButton.setOnClickListener(new View.OnClickListener() 
        {
           public void onClick(View view)
           {
                Intent myIntent = new Intent(view.getContext(), WriteMapActivity.class);
                startActivityForResult(myIntent, 0);
           }
        });      
        
        Button writeSmsButton = (Button) findViewById(R.id.sms);
        writeSmsButton.setOnClickListener(new View.OnClickListener() 
        {
           public void onClick(View view)
           {
                Intent myIntent = new Intent( view.getContext(), WriteSmsActivity.class);
                startActivityForResult(myIntent, 0);
           }
        });     
        
        Button writeMailButton = (Button) findViewById(R.id.mailmain);
        writeMailButton.setOnClickListener(new View.OnClickListener() 
        {
           public void onClick(View view)
           {
                Intent myIntent = new Intent(view.getContext(), WriteMailActivity.class);
                startActivityForResult(myIntent, 0);
           }
        });
        
        Button writeAppButton = (Button) findViewById(R.id.app);
        writeAppButton.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick (View view) 
        	{
        		Intent myIntent = new Intent(view.getContext(), WriteApp.class );
        		startActivityForResult(myIntent, 0);
        	}
        });
        
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
		{
		// Currently unused. Use this block to add options to the menu.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		}

}
