package ayds.songinfo.moredetails.fulllogic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

  private TextView textPane1;
  //private JPanel imagePanel;
 // private JLabel posterImageLabel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_other_info);

    textPane1 = findViewById(R.id.textPane1);


    open(getIntent().getStringExtra("artistName"));
  }

  public void getARtistInfo(String artistName) {

    // create
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    LastFMAPI lastFMAPI = retrofit.create(LastFMAPI.class);

    Log.e("TAG","artistName " + artistName);

        new Thread(new Runnable() {
          @Override
          public void run() {

            ArticleEntity article = dataBase.ArticleDao().getArticleByArtistName(artistName);


            String text = "";


            if (article != null) { // exists in db

              text = "[*]" + article.getBiography();

              final String urlString = article.getArticleUrl();
              findViewById(R.id.openUrlButton1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent intent = new Intent(Intent.ACTION_VIEW);
                  intent.setData(Uri.parse(urlString));
                  startActivity(intent);
                }
              });

            } else { // get from service
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


                  // save to DB  <o/
                  final String text2 = text;
                  new Thread(new Runnable() {
                    @Override
                    public void run() {
                      dataBase.ArticleDao().insertArticle(new ArticleEntity(artistName, text2, url.getAsString()));
                    }
                  }).start();



                }


                final String urlString = url.getAsString();
                findViewById(R.id.openUrlButton1).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urlString));
                    startActivity(intent);
                  }
                });

              } catch (IOException e1) {
                Log.e("TAG", "Error " + e1);
                e1.printStackTrace();
              }
            }


            String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png";

            Log.e("TAG","Get Image from " + imageUrl);



            final String finalText = text;

            runOnUiThread( () -> {
              Picasso.get().load(imageUrl).into((ImageView) findViewById(R.id.imageView1));


              textPane1.setText(Html.fromHtml( finalText));


            });



          }
        }).start();

  }

  private ArticleDatabase dataBase = null;

  private void open(String artist) {


    dataBase =    Room.databaseBuilder(this, ArticleDatabase.class, "database-name-thename").build();

    new Thread(new Runnable() {
      @Override
      public void run() {
        dataBase.ArticleDao().insertArticle(new ArticleEntity( "test", "sarasa", "")  );
        Log.e("TAG", ""+ dataBase.ArticleDao().getArticleByArtistName("test"));
        Log.e("TAG", ""+ dataBase.ArticleDao().getArticleByArtistName("nada"));

      }
    }).start();


    getARtistInfo(artist);
  }

  public static String textToHtml(String text, String term) {

    StringBuilder builder = new StringBuilder();

    builder.append("<html><div width=400>");
    builder.append("<font face=\"arial\">");

    String textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replaceAll("(?i)" + term, "<b>" + term.toUpperCase() + "</b>");

    builder.append(textWithBold);

    builder.append("</font></div></html>");

    return builder.toString();
  }

}
