package com.example.manoj.musicplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class PlayMusicActivity extends AppCompatActivity {

    public static MediaPlayer mp;
    int flag=1;

    ImageButton btnseekback,btnpause,btnseekfwd,btnplay;

    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        Bundle info =  getIntent().getBundleExtra("Info");

        String SongPath = info.getString("SongPath");

        String title = info.getString("Title");

        tvTitle = (TextView) findViewById(R.id.tv_Titile);
        tvTitle.setText(title);

        btnplay  = (ImageButton) findViewById(R.id.btn_Play);

        btnpause = (ImageButton) findViewById(R.id.btn_Pause);


        btnseekback = (ImageButton) findViewById(R.id.btn_SeekBack);

        btnseekfwd = (ImageButton) findViewById(R.id.btn_SeekFrwd);

        Log.d("1234567", "onCreate: "+ SongPath);


        mp = new MediaPlayer();

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mp.setDataSource(this, Uri.parse(SongPath));
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

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
                mp.seekTo(0);
            }
        });

        btnseekfwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.seekTo(mp.getCurrentPosition()+5000);
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,MainActivity.class);
    }
}
