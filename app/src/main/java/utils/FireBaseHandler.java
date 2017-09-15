package utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Aisha on 8/20/2017.
 */

public class FireBaseHandler {


    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mFirebaseDatabase;


    public FireBaseHandler() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();

    }


    public void uploadStory(final Story story, final OnStorylistener OnStorylistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("shortStory/");

        story.setStoryID(mDatabaseRef.push().getKey());

        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("shortStory/" + story.getStoryID());


        mDatabaseRef1.setValue(story).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                OnStorylistener.onStoryDownLoad(story, true);
                OnStorylistener.onStoryUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                OnStorylistener.onStoryUpload(false);
                OnStorylistener.onStoryDownLoad(null, false);
            }
        });


    }

    public interface OnStorylistener {


        public void onStoryDownLoad(Story story, boolean isSuccessful);

        public void onStoryListDownLoad(ArrayList<Story> storyList, boolean isSuccessful);


        public void onStoryUpload(boolean isSuccessful);
    }

}
