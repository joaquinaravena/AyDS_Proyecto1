package ayds.songinfo.moredetails.fulllogic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.room.Room;

import ayds.songinfo.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;


import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class OtherInfoWindow extends Activity {

  public final static String ARTIST_NAME_EXTRA = "artistName";
  public static final String BASE_URL = "https://ws.audioscrobbler.com/2.0/";
  public static final String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png";
  private TextView textPane1;
  private ArticleDatabase dataBase = null;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_other_info);

    textPane1 = findViewById(R.id.textPane1);

    open(getIntent().getStringExtra(ARTIST_NAME_EXTRA));
  }

  public void getArtistInfo(String artistName) {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    LastFMAPI lastFMAPI = retrofit.create(LastFMAPI.class);

    Log.e("TAG","artistName " + artistName);

        new Thread(() -> {

          ArticleEntity article = dataBase.ArticleDao().getArticleByArtistName(artistName);

          String text = "";

          if (article != null) {
            text = "[*]" + article.getBiography();
            addListenerToOpenUrlButton1(article.getArticleUrl());

          } else {
            Response<String> callResponse;
            try {
              callResponse = lastFMAPI.getArtistInfo(artistName).execute();

              Log.e("TAG","JSON " + callResponse.body());

              Gson gson = new Gson();
              JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
              JsonObject artist = jobj.get("artist").getAsJsonObject();
              JsonObject bio = artist.get("bio").getAsJsonObject();
              JsonElement extract = bio.get("content");
              JsonElement url = artist.get("url");


              if (extract == null) {
                text = "No Results";
              } else {
                text = extract.getAsString().replace("\\n", "\n");

                text = textToHtml(text, artistName);

                final String text2 = text;
                new Thread(() ->
                        dataBase.ArticleDao().insertArticle(new ArticleEntity(artistName, text2, url.getAsString()))).start();
              }
              addListenerToOpenUrlButton1(url.getAsString());

            } catch (IOException e1) {
              Log.e("TAG", "Error " + e1);
            }
          }

          Log.e("TAG","Get Image from " + IMAGE_URL);

          String finalText = text;
          runOnUiThread( () -> {
            Picasso.get().load(IMAGE_URL).into((ImageView) findViewById(R.id.imageView1));

            textPane1.setText(Html.fromHtml(finalText, Html.FROM_HTML_MODE_LEGACY));
          });
        }).start();
  }

  private void addListenerToOpenUrlButton1(String urlArticle) {
    findViewById(R.id.openUrlButton1).setOnClickListener(viewOnClick -> {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(urlArticle));
      startActivity(intent);
    });
  }

  private void open(String artist) {
    dataBase = Room.databaseBuilder(this, ArticleDatabase.class, "database-name-thename").build();
    getArtistInfo(artist);
  }

  public static String textToHtml(String text, String term) {

    String textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replaceAll("(?i)" + term, "<b>" + term.toUpperCase() + "</b>");

    return "<html><div width=400>" + "<font face=\"arial\">" +
            textWithBold + "</font></div></html>";
  }

}
