package com.example.manoj.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    int count =0;
    Cursor cursor;
    public static final String TAG = "Main Activity";
    SongsListRVAdapter songsListRVAdapter;
    RecyclerView rvSongsList;

    ArrayList<SongsList> songsLists = new ArrayList<SongsList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int premcode;
        premcode = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);



//        getSongList();
        Log.d(TAG, "onCreate: Songs Fetched");
        rvSongsList = (RecyclerView) findViewById(R.id.rv_SongsList);
        Log.d(TAG, "onCreate: sharuu");

        rvSongsList.setLayoutManager(new LinearLayoutManager(this));



        songsListRVAdapter = new SongsListRVAdapter(songsLists, this, new SongsListRVAdapter.OnviewClickedListner() {
            @Override
            public void onViewCiclked(int position) {

                SongsList thisSong = songsLists.get(position);
                Bundle thisBumdle = new Bundle();
                thisBumdle.putString("Title",thisSong.Title);
                thisBumdle.putString("SongPath",thisSong.songPath);
                thisBumdle.putString("ArtistName",thisSong.Artist);
                Intent i = new Intent(MainActivity.this,PlayMusicActivity.class);

                i.putExtra("Info",thisBumdle);

                startActivity(i);
                if(count==0)
                {
                    count =1;
                }
                else
                {
                    PlayMusicActivity.mp.release();
                }

            }
        });

        rvSongsList.setAdapter(songsListRVAdapter);





    }

    public void getSongList()
    {
        List<String> fullsongpath = new ArrayList<>();
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";


        cursor = managedQuery(allsongsuri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

                    String songPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    fullsongpath.add(songPath);
                    String artistName = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    int artistId = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                    String albumName = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    int albumId = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    int Duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                    songsLists.add(new SongsList(name,artistName,songPath,albumName,artistId,albumId,Duration));

                } while (cursor.moveToNext());
            }
//            cursor.close();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: Yes Function Will Be Called");
        
        getSongList();

    }
}
