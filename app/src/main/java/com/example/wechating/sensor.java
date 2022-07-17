package com.example.wechating;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class sensor extends Activity implements SensorEventListener,AnimationListener{

    private SensorManager  sensorManager ;
    private Sensor sensor ;
    private Animation out_top_Annotation = null;//上半部退出动画
    private Animation out_bottom_Annotation = null;//下半部退出动画
    private Animation in_top_Annotation = null;//上半部进入动画
    private Animation in_bottom_Annotation = null;//下半部进入动画
    private int duration = 450;//动画效果时长，可以自己设置，此处为0.45秒
    private ImageView imageView_top = null;
    private ImageView imageView_bottom = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        Log.d("sabi", "onCreate:haha ");

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.main, null);

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_GAME);

        /**
         * 设置动画效果
         */
        this.out_top_Annotation = new TranslateAnimation(0,0,0,-(float)getWindowManager().getDefaultDisplay().getHeight()/3);
        this.out_top_Annotation.setDuration(duration);


        this.out_bottom_Annotation = new TranslateAnimation(0,0,0,(float)getWindowManager().getDefaultDisplay().getHeight()/3);
        this.out_bottom_Annotation.setDuration(duration);

        this.in_top_Annotation = new TranslateAnimation(0,0,-(float)getWindowManager().getDefaultDisplay().getHeight()/3,0);
        this.in_top_Annotation.setDuration(duration);

        this.in_bottom_Annotation = new TranslateAnimation(0,0,(float)getWindowManager().getDefaultDisplay().getHeight()/3,0);
        this.in_bottom_Annotation.setDuration(duration);

        this.out_bottom_Annotation.setAnimationListener(this);
        this.in_bottom_Annotation.setAnimationListener(this);

        /**
         * 新建两个ImageView用于展示摇的效果
         */
        this.imageView_top = new ImageView(this);
        this.imageView_top.setLayoutParams(new LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight()/2));

        this.imageView_bottom = new ImageView(this);
        this.imageView_bottom.setLayoutParams(new LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight()/2));

        this.imageView_top.setImageResource(R.drawable.up);
        this.imageView_bottom.setImageResource(R.drawable.down);
        //this.imageView_top.setBackgroundColor(Color.BLUE);//设置上半部分背景色，实际中用图来表示
        //this.imageView_bottom.setBackgroundColor(Color.RED);//设置下班部分背景，实际中用图来表示
        //实际应用中上半部分和下半部分是一张图切成两部分的
        ll.addView(imageView_top);
        ll.addView(imageView_bottom);

        setContentView(ll);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[SensorManager.DATA_X];
        float y = event.values[SensorManager.DATA_Y];
        float z = event.values[SensorManager.DATA_Z];
        if(Math.abs(x)>=14||Math.abs(y)>=14||Math.abs(z)>=14){//判断加速度>14时，这个值是可以修改的。
            Toast.makeText(this,"搜寻成功!!!", LENGTH_SHORT).show();
            imageView_bottom.startAnimation(out_bottom_Annotation);//开始动画，记住是startAnimation，不是setAnimation
            imageView_top.startAnimation(out_top_Annotation);
            sensorManager.unregisterListener(this);//取消监听加速感应器，如果不取消的话会有问题，同学们可以自己去掉，狂摇试试
            //Toast.makeText(this,"搜寻成功!!!",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        if(animation == this.out_bottom_Annotation){
            imageView_bottom.startAnimation(in_bottom_Annotation);
            imageView_top.startAnimation(in_top_Annotation);
        }else if(animation.equals(this.in_bottom_Annotation)){
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_GAME);//完成动画后再次监听加速感应器
        }
    }


    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

}