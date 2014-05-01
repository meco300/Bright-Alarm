package jp.meco300.brightalerm.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by meco300 on 2014/04/16.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context,Intent intent){
        Log.v("AlarmNotificationReceiver", "action:" + intent.getAction());



        Intent notification = new Intent(context,AlarmNotificationActivity.class);
        notification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notification);

    }
}
