package com.example.liulongchang.myandroidapp0;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{

            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e

    };
    //存放图片的标题
    private String[] titles = new  String[]{

            "巩俐不低俗，我就不能低俗",
            "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"

    };


    private TextView titleTextView;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mViewPaper = (ViewPager)findViewById(R.id.vp);

                //显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0;i < imageIds.length;i++){

            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);

        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        titleTextView = (TextView)findViewById(R.id.title);
        titleTextView.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position){

                titleTextView.setText(titles[position]);

                dots.get(position).setBackgroundResource(R.color.colorAccent);
                dots.get(oldPosition).setBackgroundResource(R.color.colorDarkgray);


                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0,float arg1,int arg2){



            }

            @Override
            public void onPageScrollStateChanged(int arg0){


            }


        });






    }






    private class ViewPagerAdapter extends PagerAdapter {



        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
//			view.removeView(view.getChildAt(position));
//			view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }





    }





    @Override
    protected void onStart(){
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };

//    private Handler mHandler = new Handler(){
//        public void handleMessage(android.os.Message msg) {
//            mViewPaper.setCurrentItem(currentItem);
//        };
//    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }


}
