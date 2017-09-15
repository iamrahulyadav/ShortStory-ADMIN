package app.story.craftystudio.shortstory_admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import utils.FireBaseHandler;
import utils.Story;

import static java.text.DateFormat.getDateTimeInstance;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FireBaseHandler fireBaseHandler;

    EditText storyTitle;
    EditText storyFull;
    EditText storyAuthorName;
    EditText storyBookNAme;
    EditText storyTag;
    EditText storyGenre;
    EditText storyBookLink;
    EditText storyLikes;

    CheckBox pushNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //declaration
        storyTitle = (EditText) findViewById(R.id.admin_story_title_edittext);
        storyFull = (EditText) findViewById(R.id.admin_story_full_edittext);
        storyAuthorName = (EditText) findViewById(R.id.admin_story_authorname_edittext);
        storyBookNAme = (EditText) findViewById(R.id.admin_story_bookName_edittext);
        storyTag = (EditText) findViewById(R.id.admin_story_tag_edittext);
        storyGenre = (EditText) findViewById(R.id.admin_story_genre_edittext);
        storyBookLink = (EditText) findViewById(R.id.admin_story_Link_edittext);
        storyLikes = (EditText) findViewById(R.id.admin_story_likes_edittext);

        pushNotification = (CheckBox) findViewById(R.id.admin_story_pushnotification_checkbox);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void uploadStory(View view) {


        //creting story class object
        Story story = new Story();

        if (isEmpty(storyTitle)) {
            storyTitle.setError("Title cannot be null");
            return;
        } else {
            story.setStoryTitle(storyTitle.getText().toString());
        }

        if (isEmpty(storyFull)) {
            storyFull.setError("Story cannot be null");
            return;

        } else {
            story.setStoryFull(storyFull.getText().toString());
        }
        if (isEmpty(storyAuthorName)) {
            storyAuthorName.setError("AuthorNAme cannot be null");
            return;

        } else {
            story.setStoryAuthorNAme(storyAuthorName.getText().toString());
        }
        if (isEmpty(storyBookNAme)) {
            story.setStoryBookName("");
        } else {
            story.setStoryBookName(storyBookNAme.getText().toString());
        }
        if (isEmpty(storyBookLink)) {
            story.setStoryBookLink("");
        } else {
            story.setStoryBookLink(storyBookLink.getText().toString());
        }
        if (isEmpty(storyGenre)) {
            storyGenre.setError("Genre cant be null");
            return;

        } else {
            story.setStoryGenre(storyGenre.getText().toString());
        }
        if (isEmpty(storyTag)) {
            storyTag.setError("Tag cant be null");
            return;

        } else {
            story.setStoryTag(storyTag.getText().toString());
        }
        if (isEmpty(storyLikes)) {

        } else {
            story.setStoryLikes(Integer.parseInt(storyLikes.getText().toString()));
        }


        story.setPushNotification(pushNotification.isChecked());

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        story.setStoryDate(mydate);


        fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadStory(story, new FireBaseHandler.OnStorylistener() {
            @Override
            public void onStoryDownLoad(Story story, boolean isSuccessful) {
                if (isSuccessful) {
                    Toast.makeText(MainActivity.this, "Uploaded SuccessFull", Toast.LENGTH_SHORT).show();

                    clearData();

                } else {
                    Toast.makeText(MainActivity.this, " Story Not Uploaded SuccessFully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onStoryListDownLoad(ArrayList<Story> storyList, boolean isSuccessful) {

            }

            @Override
            public void onStoryUpload(boolean isSuccessful) {

            }
        });


    }

    private void clearData() {

        storyTitle.setText("");
        storyFull.setText("");
        storyAuthorName.setText("");
        storyBookNAme.setText("");
        storyTag.setText("");
        storyGenre.setText("");
        storyBookLink.setText("");
        storyLikes.setText("");

        pushNotification.setChecked(false);
    }

    public boolean isEmpty(EditText edittext) {

        if (TextUtils.isEmpty(edittext.getText().toString())) {
            return true;
        } else {
            return false;
        }

    }
}
