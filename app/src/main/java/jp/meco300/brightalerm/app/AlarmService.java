package jp.meco300.brightalerm.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * Created by meco300 on 2014/04/16.
 */
public class AlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        Log.v("AlarmService","onCreate");
        Thread thr = new Thread(null, mTask,"AlermServiceThread");
        thr.start();
        Log.v("AlarmService","スレッド開始");
    }

    Runnable mTask = new Runnable(){
        public void run(){
            // ここにアラーム通知する前の処理を。

            Intent alarmBroadcast = new Intent();
            alarmBroadcast.setAction("AlarmAction");
            sendBroadcast(alarmBroadcast);
            Log.v("AlarmService","通知画面起動メッセージを送った");
            AlarmService.this.stopSelf();
            Log.v("AlarmService","サービス停止");
        }
    };
}
