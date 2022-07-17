package com.example.wechating.ui.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechating.MainActivity;
import com.example.wechating.MakeMomentPage;
import com.example.wechating.R;
import com.example.wechating.component.NewsAdapter;
import com.example.wechating.domain.Friends;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsFragment extends Fragment implements View.OnClickListener {

    private NewsViewModel newsViewModel;
    private List<Friends> list;
    private static NewsAdapter adapter;
    private ImageView goMakeMoment;
    private List<Integer> profiles;
    private List<String> nickname;
    private List<List<Integer>> itemPictures;
    private List<Integer> itemPicture;
    private List<Integer> itemPicture2;
    private List<String> copyWriting;
    private List<Integer> times;
    private static boolean flag=false;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });



        AppBarLayout appBarLayout = root.findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolbarLayout collapsingToolbar = root.findViewById(R.id.collapsing_toolbar);
                int color = Color.argb(200,0,0,0);
                collapsingToolbar.setCollapsedTitleTextColor(color);
                ImageView imageView = root.findViewById(R.id.image1);
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // 折叠状态
                    collapsingToolbar.setTitle("< 返回");
                    //requestWindowFeature(Window.FEATURE_NO_TITLE);
                    imageView.setVisibility(View.GONE);
                } else { // 非折叠状态
                    collapsingToolbar.setTitle("");
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
        initFriends();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(list,profiles,nickname,itemPictures,copyWriting,times);
        recyclerView.setAdapter(adapter);


        goMakeMoment=root.findViewById(R.id.camera_id);
        goMakeMoment.setOnClickListener(this);

        return root;
    }



    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), MakeMomentPage.class);
        startActivity(intent);

    }



    private void initFriends() {
        list=new ArrayList<>();
        if(flag)
            list.add(new Friends("陈冠希"));
        list.add(new Friends("聪明彬"));
        list.add(new Friends("树树伟"));
        list.add(new Friends("美羊羊"));
        list.add(new Friends("勤勤恳恳老实彬"));
        list.add(new Friends("垃圾嘉"));
        list.add(new Friends("蔡坤坤"));
        list.add(new Friends("丁丁鱼"));
        list.add(new Friends("大笨婷"));

        nickname=new ArrayList<>();
        if(flag)
            nickname.add("邱聪明");
        nickname.add("聪明彬");
        nickname.add("树树伟");
        nickname.add("美羊羊");
        nickname.add("勤勤恳恳老实彬");
        nickname.add("垃圾嘉");
        nickname.add("蔡坤坤");
        nickname.add("丁丁鱼");
        nickname.add("大笨婷");

        profiles=new ArrayList<>();//朋友圈好友的头像
        if(flag)
            profiles.add(R.drawable.p16);
        profiles.add(R.drawable.p12);
        profiles.add(R.drawable.p13);
        profiles.add(R.drawable.p8);
        profiles.add(R.drawable.p10);
        profiles.add(R.drawable.p7);
        profiles.add(R.drawable.p2);
        profiles.add(R.drawable.p4);
        profiles.add(R.drawable.p3);

        times=new ArrayList<>();//发布动态距离现在的时间
        if(flag)
            times.add(0);
        times.add(25);
        times.add(35);
        times.add(50);
        times.add(1);
        times.add(2);
        times.add(5);
        times.add(7);
        times.add(8);



        itemPictures=new ArrayList<>();
        itemPicture=new ArrayList<>();
        if(flag){
            itemPictures.add(itemPicture);
        }

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq1);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.sun);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq3_2);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>(10000);

        itemPicture.add(R.drawable.pyq4_3);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq5_2);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq6);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq7);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq8_2);
        itemPictures.add(itemPicture);


        copyWriting=new ArrayList<>();
        if(flag)
            copyWriting.add("Hello World");
        copyWriting.add("期末了，作业考试都好多...");
        copyWriting.add("昨天夕阳真美");
        copyWriting.add("给大家推荐部好看的电影，真心不错");
        copyWriting.add("蓝天白云");
        copyWriting.add("两只都好可爱");
        copyWriting.add("楼下两个凑一起不就好了");
        copyWriting.add("有一起疯狂星期四的吗？");
        copyWriting.add("有一起疯狂星期四的吗？没有我一会再来问问");

    }

    public static void update(){
        flag=true;
        adapter.notifyDataSetChanged();
    }

}




