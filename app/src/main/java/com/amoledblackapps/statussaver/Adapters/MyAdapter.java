package com.amoledblackapps.statussaver.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amoledblackapps.statussaver.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import de.mateware.snacky.Snacky;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FileHolder>
{
    private final String DIR_SAVE = "/StatusSaver";
    private ArrayList<File> filesList;
    private Activity activity;


    public MyAdapter(ArrayList<File> filesList, Activity activity){

        this.filesList = filesList;
        this.activity = activity;
        setHasStableIds(false);

    }

    @Override
    public int getItemCount() {

        return filesList.size();
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_item,parent,false);

        return new FileHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileHolder holder, int position)
    {

        File currentFile = filesList.get(position);

        holder.imageDownload.setOnClickListener(this.downloadMediaItem(currentFile));

        holder.videoDownload.setOnClickListener(this.downloadMediaItem(currentFile));

        if (currentFile.getAbsolutePath().endsWith(".mp4")){

            holder.imageCardView.setVisibility(View.GONE);
            holder.videoCardView.setVisibility(View.VISIBLE);

            Uri videoUri = Uri.parse(currentFile.getAbsolutePath());
            holder.videoView.setVideoURI(videoUri);
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(false);
                    holder.videoView.start();
                }
            });
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        }


    }

    private View.OnClickListener downloadMediaItem(final File sourceFile)
    {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Runnable()
                {
                    @Override
                    public void run() {
                        File destFile = new File(Environment.getExternalStorageDirectory().toString()
                        + DIR_SAVE + sourceFile.getName());


                        try {
                            copyFile(sourceFile,destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Snacky.builder().
                                setActivty(activity).
                                setText("Saved to gallery").
                                success().
                                show();
                    }


                    }.run();
                }

            };
        }

    private void copyFile(File sourceFile, File destFile) throws IOException {

        if (!destFile.getParentFile().exists())
        {

            destFile.getParentFile().mkdirs();


    }

        if(!destFile.exists()) {

            destFile.createNewFile();
        }

        FileChannel source;

        source = new FileInputStream(sourceFile).getChannel();
        FileChannel destination = new FileOutputStream(destFile).getChannel();

        destination.transferFrom(source,0,source.size());

        source.close();

        destination.close();


    }

    static class FileHolder extends  RecyclerView.ViewHolder
    {

        ImageView imageView;
        VideoView videoView;
        CardView imageCardView,videoCardView;
        Button videoDownload,imageDownload;
        FileHolder(View itemView) {
            super (itemView);
            imageView = itemView.findViewById(R.id.imageViewMedia);
            videoView = itemView.findViewById(R.id.videoView);
            imageCardView = itemView.findViewById(R.id.cardViewImage);
            videoCardView = itemView.findViewById(R.id.videoCardView);
            videoDownload = itemView.findViewById(R.id.videoDownloadButton);
            imageDownload = itemView.findViewById(R.id.buttonDownloadImage);



        }
    }
}
