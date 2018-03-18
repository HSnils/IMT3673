package com.ntnu.henrik.lab2;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RSSview extends AppCompatActivity {

    // variables
    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    int numberOfItems;
    int refreshTimer;
    String Url;
    Handler h = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssview);

        final int userNumberOfItems = UserPreferences.getNumberOfItems(this);
        final int refresh = UserPreferences.getRefreshTime(this);
        String urlFromPrefrences = UserPreferences.getSelectedRSSFeed(this);

        //Values
        numberOfItems = userNumberOfItems;
        refreshTimer = refresh * 60000;
        Url = urlFromPrefrences;

        lvRss = findViewById(R.id.RSSfeed);

        titles = new ArrayList<String>();
        links = new ArrayList<String>();


        new ProcessInBackground().execute();

        // Goes to article on user click
        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //stores what link user clicks on
                Uri uri = Uri.parse(links.get(i));

                //Turns uri into String
                String stringUri = uri.toString();

                //starts new intent and passes url as a String
                Intent intent = new Intent(RSSview.this, ArticleContent.class);
                intent.putExtra("WORD",stringUri);
                startActivity(intent);
            }
        });
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    @Override
    protected void onResume() {
        //when activity becomes visible


        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Henter RSS innhold på nytt.");
                new ProcessInBackground().execute();

                runnable = this;
                h.postDelayed(runnable, refreshTimer);
            }
        }, refreshTimer);

        super.onResume();
    }

    @Override
    protected void onPause(){
        //stops handler activity when not showing
        h.removeCallbacks(runnable);
        super.onPause();
    }

    //do this in the background
    @SuppressLint("StaticFieldLeak")
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {

        ProgressDialog progressDialog = new ProgressDialog(RSSview.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loader RSS-feed, vent litt...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {

            try {

                titles = new ArrayList<String>();
                links = new ArrayList<String>();

                String rssUrl = Url;
                int selectedNumberOfItems = numberOfItems;
                URL url = new URL(rssUrl);


                /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RSSview.this);
                String rssUrl = preferences.getString("rssUrl", "http://feeds.news24.com/articles/fin24/tech/rss");
                int maxItems = preferences.getInt ("maxItems", numberOfItems)*/


                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using the currently configured
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
			         * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
			         * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
			         * We should skip the "<title>" tag which is a child of "<channel>" tag,
			         * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
			         *
			         * In order to achieve this, we will make use of a boolean variable called "insideItem".
                 */
                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                int eventType = xpp.getEventType(); //loop control variable

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG) {
                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item")) {

                            insideItem = true;
                        }

                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {

                                if (titles.size() < selectedNumberOfItems) {
                                    // extract the text between <title> and </title>
                                    titles.add(xpp.nextText());
                                }

                            }
                        }
                        //if the tag is called "link"
                        else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {

                                if (links.size() < selectedNumberOfItems) {
                                    // extract the text between <link> and </link>
                                    links.add(xpp.nextText());
                                }
                            }
                        }
                    }
                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }

            }

            //checks if something fails with url
            catch (MalformedURLException e) {
                exception = e;
            }
            catch (XmlPullParserException e) {
                exception = e;
            }
            catch (IOException e) {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RSSview.this, android.R.layout.simple_list_item_1, titles);

            lvRss.setAdapter(adapter);


            progressDialog.dismiss();
        }

    }

    //onclick function on button User Preferences to go to UserPreferences
    public void onClickButtonPreferences(View view) {

        //creates intent
        Intent intent = new Intent(this, UserPreferences.class);


        //starts UserPreferences
        startActivity(intent);
    }



    @Override
    public void onBackPressed()
    {
        finish();
        moveTaskToBack(true);
    }

}
