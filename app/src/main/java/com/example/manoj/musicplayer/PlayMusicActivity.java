package com.example.manoj.musicplayer;

import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {
    int Current;
    public float x1,x2;
    final int minDistance = 150;
    public static MediaPlayer mp;
    int flagforsamesong;
    int flag=1;
    int position;
    int islooping=0;
    ImageView ivPhoto;
    SeekBar seekBar;
    TextView tvCurrentTime,tvDuration;
    ImageButton btnseekback,btnpause,btnseekfwd,btnplay;
    ImageButton btnLoopOne,btnLoopNone,btnnextsong,btnprevsong;
    TextView tvTitle;
    Bitmap bitmap;
    Handler seek_Handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN

        );
        setContentView(R.layout.activity_play_music);

        Bundle data = getIntent().getBundleExtra("data");
        flagforsamesong = data.getInt("flag");
        position = data.getInt("position");
        ivPhoto = (ImageView) findViewById(R.id.iv_FullSize);
        btnLoopNone = (ImageButton) findViewById(R.id.btn_loopnone);
        btnLoopOne = (ImageButton) findViewById(R.id.btn_loopone);
        tvCurrentTime = (TextView) findViewById(R.id.tv_CurrentTime);
        tvDuration = (TextView) findViewById(R.id.tv_Duration);
        seekBar = (SeekBar) findViewById(R.id.sb_SeekBar);
        tvTitle = (TextView) findViewById(R.id.tv_Titile);
        tvTitle.setText(MainActivity.songsLists.get(position).getTitle());

        btnplay  = (ImageButton) findViewById(R.id.btn_Play);

        btnpause = (ImageButton) findViewById(R.id.btn_Pause);


        btnnextsong = (ImageButton) findViewById(R.id.btn_nextsong);
        btnprevsong = (ImageButton) findViewById(R.id.btn_prevsong);
        btnseekback = (ImageButton) findViewById(R.id.btn_SeekBack);

        btnseekfwd = (ImageButton) findViewById(R.id.btn_SeekFrwd);


        Log.d("1234567", "onCreate: "+ MainActivity.songsLists.get(position).getSongPath());


        bitmap = MainActivity.songsLists.get(position).getBitmap();

//        if(flagforsamesong==1)
//        {
            //that means the same song is already playing
//        }

//        else
//        {
            mp = new MediaPlayer();

            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);


            try {
                mp.setDataSource(this, Uri.parse(MainActivity.songsLists.get(position).getSongPath()));
                mp.prepareAsync();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        seekBar.setMax(mp.getDuration()/1000);
                        btnpause.setVisibility(View.VISIBLE);
                        btnplay.setVisibility(View.GONE);
                        if(bitmap!=null)
                        {
                            ivPhoto.setImageBitmap(bitmap);
                        }
                        else
                        {
                            ivPhoto.setImageResource(R.drawable.default_play_image);
                        }


                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
//        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                int current = (mp.getCurrentPosition());
//                Log.d("12345", "onProgressChanged: " + current);
//                Log.d("12345", "onProgressChanged:  "+ progress);
//                if (fromUser)
//                {
//                    mp.seekTo(current+(progress*100));
//                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Current = mp.getCurrentPosition();
                seek_Handler.removeCallbacks(run);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int shift;
                seek_Handler.removeCallbacks(run);
                int totalduration = mp.getDuration();
                if(mp.getCurrentPosition()<Current)
                {
                    shift = 1;
                }
                else
                {
                    shift  = 0;
                }
                int currentduration = progressToTimer(seekBar.getProgress(),totalduration,Current,shift);

                mp.seekTo(currentduration);
                seekUpdation();
            }
        });



        tvDuration.setText(MainActivity.songsLists.get(position).getMinlength() + ":"
                + MainActivity.songsLists.get(position).getSeclength());

        seekUpdation();

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                btnpause.setVisibility(View.GONE);
                btnplay.setVisibility(View.VISIBLE);

            }
        });



        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                btnplay.setVisibility(View.GONE);
                btnpause.setVisibility(View.VISIBLE);
            }
        });


        btnseekback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.seekTo(mp.getCurrentPosition()-5000);
            }
        });

        btnseekfwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.seekTo(mp.getCurrentPosition()+5000);
            }
        });
        btnLoopNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoopOne.setVisibility(View.VISIBLE);
                btnLoopNone.setVisibility(View.GONE);
                mp.setLooping(true);
                islooping=1;
            }
        });
        btnLoopOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoopOne.setVisibility(View.GONE);
                btnLoopNone.setVisibility(View.VISIBLE);
                mp.setLooping(false);
                islooping=0;
            }
        });

        btnnextsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = position+1;
                if(position==MainActivity.songsLists.size())
                {
                    position =0;
                }
                StartMediaPlayer(position);
            }
        });
        btnprevsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = position-1;
                if(position<0)
                {
                    position = MainActivity.songsLists.size()-1;
                }
                StartMediaPlayer(position);
            }
        });
        //For Swipe Left Or Right
        ivPhoto.setOnTouchListener(new OnSwipeActionPerform(getApplicationContext(), new OnSwipeActionPerform.OnswipePerformListner() {
            @Override
            public void OnSwipePerformed(int leftoright) {
                if(leftoright==1)
                {
                    if(position==0)
                    {
                        position = MainActivity.songsLists.size()-1;
                    }
                    else
                    {
                        position = position-1;
                    }
                    StartMediaPlayer(position);
                }
                else if(leftoright==0){
                    if(position==MainActivity.songsLists.size()-1)
                    {
                        position = 0;
                    }
                    else
                    {
                        position = position+1;
                    }
                    StartMediaPlayer(position);
                }
            }
        }));

    }


    Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    //for seekbar touch
    public int progressToTimer(int progress, int totalDuration,int Current,int Shift) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        if(Shift==1)
        {
            currentDuration = (int) (double)((progress +Current)/1000);
        }
        else if(Shift==0)
        {
            currentDuration = (int) (Current - (((double)progress) /1000));
        }
        return currentDuration * 1000;
    }

    public void seekUpdation()
    {
        seekBar.setProgress(mp.getCurrentPosition()/1000);
        int Current = mp.getCurrentPosition()/1000;
        int minut = Current/60;
        int se = Current - (minut*60);
        tvCurrentTime.setText(String.valueOf(minut)+":"+String.valueOf(se));  //current time is not getting set
        seek_Handler.postDelayed(run,1000);
        if((mp.getCurrentPosition()==mp.getDuration())&&(islooping==0))
        {
            Log.d("123123", "seekUpdation:  Condition Met Song Finished");
            position=position+1;
            if(position!=(MainActivity.songsLists.size()-1))
            {
                StartMediaPlayer(position);

            }
            else
            {
                StartMediaPlayer(0);
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,MainActivity.class);
//        i.putExtra("CurrentPosition",position);
//        startActivity(i);
    }

    public void StartMediaPlayer(int position)
    {
        try {
            mp.reset();
            mp.setDataSource(getApplicationContext(),Uri.parse(MainActivity.songsLists.get(position).getSongPath()));
            mp.prepareAsync();
            final SongsList thisSong = MainActivity.songsLists.get(position);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    Log.d("123123", "StartMediaPlayer: Song Started Sucessfully");
                    btnpause.setVisibility(View.VISIBLE);
                    btnplay.setVisibility(View.GONE);
                    tvTitle.setText(thisSong.getTitle());
                    tvDuration.setText(thisSong.getMinlength().toString()+":"+thisSong.getSeclength().toString());
                    bitmap = thisSong.getBitmap();
                    if(bitmap!=null)
                    {
                        ivPhoto.setImageBitmap(bitmap);
                    }
                    else
                    {
                        ivPhoto.setImageResource(R.drawable.default_play_image);
                    }
                }
            });




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
