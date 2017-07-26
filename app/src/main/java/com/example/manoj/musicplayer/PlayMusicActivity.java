package com.example.manoj.musicplayer;

import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {

    public static MediaPlayer mp;
    int flag=1;

    int islooping=0;
    int position;
    SeekBar seekBar;
    TextView tvCurrentTime,tvDuration;
    ImageButton btnseekback,btnpause,btnseekfwd,btnplay;
    ImageButton btnLoopOne,btnLoopNone,btnnextsong,btnprevsong;
    TextView tvTitle;
    Handler seek_Handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

//        Bundle data = getIntent().getBundleExtra("bundle");

//        int position = data.getInt("position",0);

//        ArrayList<SongsList> songslist = (ArrayList<SongsList>)data.getSerializable("data");

//        Bundle info =  getIntent().getBundleExtra("Info");

//        String SongPath = info.getString("SongPath");

//        String title = info.getString("Title");

//        String min = info.getString("Minute");
//        String sec = info.getString("Second");
        position = getIntent().getIntExtra("position",0);


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
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

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
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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


    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

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
    }

    public void StartMediaPlayer(int position)
    {
        try {
            mp.reset();
            mp.setDataSource(getApplicationContext(),Uri.parse(MainActivity.songsLists.get(position).getSongPath()));
            mp.prepare();
            mp.start();
            Log.d("123123", "StartMediaPlayer: Song Started Sucessfully");
            SongsList thisSong = MainActivity.songsLists.get(position);
            tvTitle.setText(thisSong.getTitle());
            Log.d("123123", "StartMediaPlayer: Title Set Ho gya");
            tvDuration.setText(thisSong.getMinlength().toString()+":"+thisSong.getSeclength().toString());
            Log.d("123123", "StartMediaPlayer: Duration bhi set ho gya");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
