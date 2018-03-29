package net.glm.googlemapsinstatest2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.glm.googlemapsinstatest2.Data.ModelUser;

import java.util.ArrayList;

import static net.glm.googlemapsinstatest2.Utility.Utility.getPicturesList;
import static net.glm.googlemapsinstatest2.Utility.Utility.getResizebleCircleBitmap;

public class ProfileActivity extends AppCompatActivity {

    public static final String USER_NUMBER = "User number";

    private ImageView userImage;

    private TextView userName;
    private TextView userStatus;
    private TextView userAge;
    private TextView userSex;
    private TextView userOrientation;
    private TextView userNumberOfFollowers;

    private RecyclerView recyclerViewPictures;
    private RecyclerViewPicturesAdapter picturesAdapter;

    int position;
    ModelUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userImage = findViewById(R.id.iv_profile_headline_user_image);
        userName = findViewById(R.id.tv_profile_headline_name_data);
        userStatus = findViewById(R.id.tv_profile_headline_status_data);
        userAge = findViewById(R.id.tv_profile_age_data);
        userSex = findViewById(R.id.tv_profile_sex_data);
        userOrientation = findViewById(R.id.tv_profile_orientation_data);
        userNumberOfFollowers = findViewById(R.id.tv_profile_followers_data);

        Intent intent = getIntent();
        position = intent.getIntExtra(USER_NUMBER,0);
        user = getUserWithPosition(position);
        bindUserData(user);

        recyclerViewPictures = findViewById(R.id.rv_in_profile_activity);
        recyclerViewPictures.setLayoutManager(new GridLayoutManager(ProfileActivity.this,3));
        picturesAdapter = new RecyclerViewPicturesAdapter(getPicturesList());
        recyclerViewPictures.setAdapter(picturesAdapter);






    }

    private ModelUser getUserWithPosition(int position){
        ArrayList<ModelUser> users;
        users = ModelUser.getFakeUsers1();
        return  users.get(position);
    }

    private void bindUserData(ModelUser user){

        if (user == null){
            return;
        }

        Bitmap circleBitmap =  getResizebleCircleBitmap(BitmapFactory.decodeResource(ProfileActivity.this.getResources(), user.getImgId()),(int)  30);
        userImage.setImageBitmap(circleBitmap);
        userName.setText(user.getName());
        userStatus.setText(user.getStatus());
        userAge.setText(String.valueOf(user.getAge()));
        userSex.setText(user.getSex());
        userOrientation.setText(user.getOrientation());
        userNumberOfFollowers.setText(String.valueOf(user.getRadius()));


    }
}
