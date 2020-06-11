package com.byted.chapter5;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class VideoPlayer extends AppCompatActivity {

    private CustomVideoView videoView;
    private ProgressBar progressBar;
    private AnimatorSet set;
    private  String feedurl;
    private String _description;
    private Context mContext;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        setTitle(_description);

        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar3);
        ImageView avator = findViewById(R.id.avator2);
        ImageView good = findViewById(R.id.good2);
        TextView nickname = findViewById(R.id.nickname2);
        TextView likecount = findViewById(R.id.likecount2);
        TextView description = findViewById(R.id.description);


        resetTargetAnimation();
        Intent intent = getIntent();

        String _avator=intent.getStringExtra("avator");
        String _nickname=intent.getStringExtra("nickname");
        String _likecount=intent.getStringExtra("likecount");
        _description=intent.getStringExtra("description");
        feedurl= intent.getStringExtra("feedurl");
        Log.d("dong", _description);

        String url ="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3875642194,2749727726&fm=11&gp=0.jpg";//点赞的图片地址

        mContext=this.getApplicationContext();
        Glide.with(this.getApplicationContext()).load(_avator).into(avator);
        nickname.setText(_nickname);
        Glide.with(this.getApplicationContext()).load(url).into(good);
        likecount.setText(_likecount);
        description.setText(_description);

        videoView.setVideoURI(Uri.parse(feedurl));
        Log.d("dong", String.valueOf(Uri.parse(feedurl)));



        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                else {
                    videoView.start();
                }
                return false;
            }
        });

        good.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url2 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1507278842,1601195391&fm=26&gp=0.jpg";
                Glide.with(mContext).load(url2).into(good);
                likecount.setText(_likecount+1);
            }
        });


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.setMediaController(new MediaController(VideoPlayer.this));//默认的视频控制器
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.download:
                Log.d("xiazai","hello");
                //下面这种方法提示没有写权限，但在mainfest中已经添加了，没有找到解决方法
////                Toast.makeText(this,"正在下载视频",Toast.LENGTH_SHORT).show();
////                //创建下载任务,downloadUrl就是下载链接
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(feedurl));
//////指定下载路径和下载文件名
//                request.setDestinationInExternalPublicDir("/download/", _description+".mp4");
//                Log.d("xiazai","place2");
//////获取下载管理器
//                DownloadManager downloadManager= (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
//                Log.d("xiazai","place3");
//////将下载任务加入下载队列，否则不会进行下载
//                downloadManager.enqueue(request);
//                Log.d("xiazai","place4");

                //跳转到浏览器，用浏览器自带的下载功能
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(feedurl));
                startActivity(intent);
                break;
        }
        return true;
    }

    private void resetTargetAnimation() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ObjectAnimator alpha1 = ObjectAnimator.ofFloat(progressBar,
                        "alpha",1f,0f);
                alpha1.setDuration(1000);
            set =new AnimatorSet();
            set.playTogether(alpha1);
            set.start();
            }
        }, 2000);
    }

    @Override//重载onConfigurationChanged需要在mainfests中设置权限和捕获事件类型
    public void onConfigurationChanged(Configuration newConfig) {//监听屏幕方向改变
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){//横屏
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            videoView.setLayoutParams(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){//竖屏
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip2px(this,515f));
            videoView.setLayoutParams(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    public static int dip2px(Context context, float dpValue) {//常用的把dip值转换为pix像素值的方法
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);    }
}
