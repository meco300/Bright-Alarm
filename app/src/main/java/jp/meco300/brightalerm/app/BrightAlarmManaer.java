package jp.meco300.brightalerm.app;

import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by meco300 on 2014/04/16.
 */
public class BrightAlarmManaer {
    Context context;
    AlarmManager alarmManager;
    private PendingIntent mAlarmSender;

    public BrightAlarmManaer(Context context){
        this.context = context;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Log.v("AlarmManager", "初期化完了");
    }

    public void addAlarm(int hour, int minute){
        mAlarmSender = PendingIntent.getService(context,
                -1,
                new Intent(context, AlarmService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // アラーム時間の設定
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTimeInMillis(System.currentTimeMillis());
        int currentYear = currentCal.get(Calendar.YEAR);
        int currentMonth = currentCal.get(Calendar.MONTH);
        int currentDate = currentCal.get(Calendar.DATE);
        int currentHour = currentCal.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCal.get(Calendar.MINUTE);

        Log.i("Current Time", String.valueOf(currentYear) + " " + String.valueOf(currentMonth) + " " + String.valueOf(currentDate) + " " + String.valueOf(currentHour) + " " + String.valueOf(currentMinute));

        Calendar setCal = Calendar.getInstance();
        setCal.set(currentYear,currentMonth,currentDate,hour,minute);

        if(currentCal.compareTo(setCal) > 0){
            Log.i("addAlarm","set time is smaller than current time");
            setCal.add(Calendar.DATE,1);
        }

        Log.i("set Time", String.valueOf(setCal.get(Calendar.YEAR)) + " " + String.valueOf(setCal.get(Calendar.MONTH)) + " " + String.valueOf(setCal.get(Calendar.DATE)) + " " + String.valueOf(setCal.get(Calendar.HOUR_OF_DAY)) + " " + String.valueOf(setCal.get(Calendar.MINUTE)));

        currentCal.add(Calendar.SECOND, 10);
        currentCal.set(Calendar.MILLISECOND, 0);
        Log.v("AlermManager", currentCal.getTimeInMillis() + "ms");

        alarmManager.set(AlarmManager.RTC_WAKEUP, currentCal.getTimeInMillis(), mAlarmSender);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, setCal.getTimeInMillis(), mAlarmSender);
        Log.v("AlermManager", "アラームセット完了");
    }
}
