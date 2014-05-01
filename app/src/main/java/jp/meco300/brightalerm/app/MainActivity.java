package jp.meco300.brightalerm.app;

import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    TextView hourText = null;
    TextView minuteText = null;
    boolean isDefaultTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hourText = (TextView)findViewById(R.id.hourText);
        hourText.setText("00");

        minuteText = (TextView)findViewById(R.id.minuteText);
        minuteText.setText("00");

        Button showTimePicker = (Button)findViewById(R.id.showTimePicker);
        showTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int hour;
                int minute;

                if(isDefaultTime){
                    hour = calendar.get(Calendar.HOUR_OF_DAY);
                    minute = calendar.get(Calendar.MINUTE);

                }else{
                    hour = Integer.parseInt(hourText.getText().toString());
                    minute = Integer.parseInt(minuteText.getText().toString());
                }

                final TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                    new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hour, int minute){
                            Log.d("MainActivity","setTimePicker");

                            hourText.setText(String.valueOf(hour));
                            minuteText.setText(String.valueOf(minute));
                            isDefaultTime = false;
                        }
                    },hour, minute, true);
                timePickerDialog.show();
            }
        });

        Button setAlarmBtn = (Button)findViewById(R.id.setAlarm);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button)view;
                Toast.makeText(MainActivity.this,"onClick",Toast.LENGTH_SHORT).show();

                BrightAlarmManaer bAlarmManager = new BrightAlarmManaer(MainActivity.this);
                bAlarmManager.addAlarm(Integer.parseInt(hourText.getText().toString()),Integer.parseInt(minuteText.getText().toString()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent){
        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return false;
    }
}
