package com.example.manoj.musicplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Manoj on 7/24/2017.
 */


public class SongsListRVAdapter extends RecyclerView.Adapter<SongsListRVAdapter.SongListHolder> {

    public interface OnviewClickedListner{
        void onViewCiclked(int position);
    }


    ArrayList<SongsList> songs;
    Context context;
    OnviewClickedListner onviewClickedListner;

    public SongsListRVAdapter(ArrayList<SongsList> songs, Context context,OnviewClickedListner onviewClickedListner) {
        this.songs = songs;
        this.context = context;
        this.onviewClickedListner = onviewClickedListner;
    }

    @Override
    public SongListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = li.inflate(R.layout.singview,parent,false);
        return new SongListHolder(view);

    }

    @Override
    public void onBindViewHolder(SongListHolder holder, final int position) {

        SongsList thisSong = songs.get(position);
        holder.tvTitle.setText(thisSong.getTitle());
        holder.tvSinger.setText(thisSong.getArtist());
        holder.tvDuration.setText(thisSong.getDuration().toString());


        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("23456", "onClick:  View is Clicked");
                onviewClickedListner.onViewCiclked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongListHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle,tvSinger,tvDuration;
        View thisView;

        public SongListHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_SongTitle);
            tvSinger = (TextView) itemView.findViewById(R.id.tv_Singer);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_Duration);
            thisView = itemView;

        }
    }

}
