package com.example.daniel.jsonparsingdemo;

/**
 * Created by Daniel on 1/4/2016.
 */
public class Movie {
    private String image, plot, release_date, title, rating;

    // CONSTRUCTOR //
    public Movie (String imageIn, String plotIn, String dateIn, String titleIn, String ratingIn){
        this.image = imageIn;
        this.plot = plotIn;
        this.release_date = dateIn;
        this.title = titleIn;
        rating = ratingIn;
    }


    // GETTERS //
    String getImage(){ return image; }
    String getPlot(){ return plot; }
    String getDate() { return release_date; }
    String getTitle() { return title; }
    String getRating() { return rating; }

}
