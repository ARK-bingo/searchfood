package com.example.wechating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wechating.component.LoopViewAdapter;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    private Spinner spinner;    //定义一个下拉列表
    //定义一个String数组来存取地址
    private String[] location = new String[]{"潮安区","湘桥区","饶平区","枫溪区"};

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg; //图片资源id数组
    private String[] mDec; //图片描述
    private int[] mImg_id; //几个轮播图的id
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoopView(); //初始话轮播图模块

        //定义一个数组适配器。第一个参数为一个上下文对象；
        //第二个参数为android自带的下拉列表框的样式资源；
        //第三个参数为存放选项内容的Strings[]
        ArrayAdapter adapter=new ArrayAdapter(HomePage.this,android.R.layout.simple_spinner_item,location);
        //设置下拉列表框的下拉格式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //获取下拉列表框
        spinner=findViewById(R.id.spinner);
        //将adapter设置为spinner的适配器
        spinner.setAdapter(adapter);


    }

    private void initLoopView() {
        viewPager = (ViewPager)findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout)findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView)findViewById(R.id.loop_dec);

        // 图片资源id数组
        mImg = new int[]{
                R.drawable.test,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test
        };

        // 文本描述
        mDec = new String[]{
                "Test1",
                "Test2",
                "Test3",
                "Test4",
                "Test5"
        };

        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
        };

        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new pagerOnClickListener(getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            dotView = new View(this);
            dotView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(10,10);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }

        ll_dots_container.getChildAt(0).setEnabled(true);
        loop_dec.setText(mDec[0]);
        previousSelectedPosition=0;
        //设置适配器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) %mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                loop_dec.setText(mDec[newPosition]);
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 开启轮询
        new Thread(){
            public void run(){
                isRunning = true;
                while(isRunning){
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();
    }
}