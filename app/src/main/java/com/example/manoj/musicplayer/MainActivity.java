package com.example.manoj.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    int count =0;
    Cursor cursor;
    public static final String TAG = "Main Activity";
    SongsListRVAdapter songsListRVAdapter;
    RecyclerView rvSongsList;

    public static ArrayList<SongsList> songsLists = new ArrayList<SongsList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSongList();
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

//                ArrayList<String> Paths = new ArrayList<>();
//                SongsList thisSong = songsLists.get(position);
//                Bundle thisBumdle = new Bundle();
//                thisBumdle.putString("Title",thisSong.Title);
//                thisBumdle.putString("SongPath",thisSong.songPath);
//                thisBumdle.putString("ArtistName",thisSong.Artist);
//                thisBumdle.putString("Minute",thisSong.getMinlength().toString());
//                thisBumdle.putString("Second",thisSong.getSeclength().toString());
                Intent i = new Intent(MainActivity.this,PlayMusicActivity.class);

//               i.putExtra("Info",thisBumdle);

//                Bundle data = new Bundle();
  //              data.putSerializable("data",(Serializable)songsLists);
//                data.putInt("position",position);
//                i.putExtra("bundle",data);
//                startActivity(i);
                if(count==0)
                {
                    count =1;
                }
                else
                {
                    PlayMusicActivity.mp.release();
                }
                i.putExtra("position",position);
                startActivity(i);




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



                    Integer Dur = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))/1000;
                    int min = Dur/60;
                    int remSec = Dur - min*60;
                    String x = (String.valueOf(remSec));
                    if(x.length()==1)
                    {
                        x = "0"+x;
                    }
                    songsLists.add(new SongsList(name,artistName,songPath,albumName,artistId,albumId,min,x));

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
        


    }
}
