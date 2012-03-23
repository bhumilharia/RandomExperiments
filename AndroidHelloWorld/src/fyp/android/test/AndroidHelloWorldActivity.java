package fyp.android.test;

import android.app.Activity;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidHelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tw = new TextView(this);  
        tw.setText("Hello World");
        setContentView(tw);
    }
}