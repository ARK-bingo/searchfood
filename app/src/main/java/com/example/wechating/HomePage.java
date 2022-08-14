package com.example.wechating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechating.component.LoopViewAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//定义选项卡内卡片中承载的数据的类
class TestData {
    public int imageId;
    public String text;

    public TestData(int imageId, String text) {
        this.imageId = imageId;
        this.text = text;
    }
}

//自定义一个适配器来进行创建itemView以及绑定数据
class TestRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    List<TestData> list;

    public TestRecyclerAdapter(HomePage homePage, List<TestData> dataList) {
        this.list = dataList;
    }

    @Override
    public void onClick(View view) {
        int position = view.getVerticalScrollbarPosition();
        TestData testData = list.get(position);
        Toast.makeText(view.getContext(), "you click item"+ position, Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        Context context;
        public ViewHolder(Context context, View view) {
            super(view);
            imageView = view.findViewById(R.id.imageview1);
            textView = view.findViewById(R.id.textview1);
            this.context = context;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(parent.getContext(), view);
        viewHolder.itemView.setOnClickListener(this);
        viewHolder.imageView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, position);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        TestData testData = list.get(position);
        holder.imageView.setImageResource(testData.imageId);
        holder.textView.setText(testData.text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

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

    //recyclerView: 首页中选项卡页面的变量
    private RecyclerView homepage_recyclerView;
    private RecyclerView.Adapter homepage_adapter;
    private GridLayoutManager homepage_layoutManager;
    List<TestData> testList = new LinkedList<>();


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

        //recyclerView的初始化及配置
        initTestData(); //初始化测试数据
        homepage_recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        //设置固定大小
        homepage_recyclerView.setHasFixedSize(true);
        //创建线性布局
        homepage_layoutManager = new GridLayoutManager(this, 2);
        homepage_layoutManager.setOrientation(RecyclerView.VERTICAL);
        //给recyclerView设置布局管理器
        homepage_recyclerView.setLayoutManager(homepage_layoutManager);
        //创建并设置适配器
        homepage_adapter = new TestRecyclerAdapter(this, testList);
        homepage_recyclerView.setAdapter(homepage_adapter);
    }

    //轮播图片区域设置
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

    //初始化测试数据
    private void initTestData() {
        for (int i = 0; i < 25; i++) {
            testList.add(new TestData(R.drawable.home_icon0, "item"+i));
        }
    }
}










