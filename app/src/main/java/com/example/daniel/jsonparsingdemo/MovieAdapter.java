package com.example.daniel.jsonparsingdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Daniel on 1/10/2016.
 */
public class MovieAdapter extends ArrayAdapter {


    private List<Movie> movieList;
    private int resource;
    private LayoutInflater inflater;
    private Context c;
    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        movieList = objects;
        c = context.getApplicationContext();
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    Context getC(){ return c; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = inflater.inflate(resource, null);
        }

        ImageView moviePoster;
        String imageURL;
        TextView movieTitle;
        TextView movieDate;
        TextView moviePlot;
        TextView movieReview;

        moviePoster = (ImageView)convertView.findViewById(R.id.moviePoster);
        movieTitle = (TextView)convertView.findViewById(R.id.title);
        movieDate = (TextView)convertView.findViewById(R.id.date);
        moviePlot = (TextView)convertView.findViewById(R.id.plot);
        movieReview = (TextView)convertView.findViewById(R.id.rating);

        // MOVIE POSTER GOES HERE
        imageURL = movieList.get(position).getImage();
        Glide.with(this.getC()).load("http://image.tmdb.org/t/p/w500/" + imageURL).into(moviePoster);


        movieTitle.setText(movieList.get(position).getTitle());
        movieDate.setText("Release Date: " + movieList.get(position).getDate());
        moviePlot.setText(movieList.get(position).getPlot());
        movieReview.setText("Rating: " + movieList.get(position).getRating());

        return convertView;
    }
}
