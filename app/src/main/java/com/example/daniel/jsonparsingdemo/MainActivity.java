package com.example.daniel.jsonparsingdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public TextView tvData;
    public ListView Movies;
    public ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setMessage("Calm ur tits. Loading the fucking data.");

        Movies = (ListView) findViewById(R.id.Movies);
        new JSONTask().execute("http://api.themoviedb.org/3/movie/now_playing?api_key=449e22ac533022d9b3387f104d2621c2");
   }

    public class JSONTask extends AsyncTask<String, String, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            HttpURLConnection connection=null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line  =  "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parent = new JSONObject(finalJson);
                JSONArray mainArray = parent.getJSONArray("results");
                StringBuffer finalBuffer = new StringBuffer("");

                List<Movie> movieList = new ArrayList<>();

                for (int i = 0; i < mainArray.length(); ++i) {
                    JSONObject ob1 = mainArray.getJSONObject(i);
                    Movie movie = new Movie(
                            ob1.getString("poster_path"),
                            ob1.getString("overview"),
                            ob1.getString("release_date"),
                            ob1.getString("title"),
                            ob1.getString("vote_average")
                            );
//                    String name = ob1.getString("title");
//                    String date = ob1.getString("release_date");
//                    finalBuffer.append(name + ": " + date + '\n');

                    movieList.add(movie);

                }

                return movieList;

                /*
                JSONObject parentObject = null;

                try {
                    parentObject = new JSONObject(finalJson);
                    JSONArray parentArray = parentObject.getJSONArray("results");
                    StringBuffer finalBuffer = new StringBuffer();
                    for (int i = 0; i < parentArray.length(); ++i){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        String movieName = finalObject.getString("title");
                        int movieDate = finalObject.getInt("release_date");
                        finalBuffer.append(movieName + ": " + movieDate + '\n');
                    }
                    return finalBuffer.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                */
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e("MYAPP", "Failed to create JSON object");
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> result){
            super.onPostExecute(result);
            progress.dismiss();
            // TODO: set movie data
            MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.movie_entry, result);
            Movies.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new JSONTask().execute("http://api.themoviedb.org/3/movie/now_playing?api_key=449e22ac533022d9b3387f104d2621c2");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
