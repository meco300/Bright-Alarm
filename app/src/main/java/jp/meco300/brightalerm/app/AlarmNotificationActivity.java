package jp.meco300.brightalerm.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by meco300 on 2014/04/16.
 */
public class AlarmNotificationActivity extends Activity {

    private WakeLock wakelock;

    MediaPlayer mediaPlayer;
    private Camera camera = null;
    private Window window = null;
    private LayoutParams layoutParams = null;
    private Vibrator vibrator = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        Log.v("AlarmNotificationActivity","onCreate");

        //スリープ状態から復帰する
        wakelock = ((PowerManager)getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.ON_AFTER_RELEASE, "disableLock");
        wakelock.acquire();

        // スクリーンロックを解除する。

        window = getWindow();
        layoutParams = new LayoutParams();

        window.addFlags(layoutParams.FLAG_SHOW_WHEN_LOCKED | layoutParams.FLAG_DISMISS_KEYGUARD);
        camera = Camera.open();
        camera.startPreview();
    }

    @Override
    public void onStart(){
        super.onStart();
        // とりあえず音要らない
//        if(mediaPlayer == null){
//            mediaPlayer = MediaPlayer.create(this,R.raw.alarm);
//        }
        layoutParams.screenBrightness = 1.0f;
        window.setAttributes(layoutParams);

        Camera.Parameters param = camera.getParameters();
        try {
            camera.setPreviewTexture(new SurfaceTexture(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(param);

//        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
//        long[] pattern = {0,3000,2000};
//        vibrator.vibrate(pattern,0);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopAndRelease();
    }

    private void stopAndRelease(){
        // 解除
        camera.stopPreview();
        camera.release();
        camera = null;

//        vibrator.cancel();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent){
        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return false;
    }

//    @Override
//    public void onAttachedToWindow(){
//        this.getWindow().setType(LayoutParams.TYPE_SYSTEM_ALERT);
//        super.onAttachedToWindow();
//    }
}
