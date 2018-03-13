package net.glm.googlemapsinstatest2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static net.glm.googlemapsinstatest2.Utility.Utility.*;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MapsTest";
    private static final int ERROR_DIALOG_REQUEST = 901;


    private Button btnMap;
    ImageView imageView;
    ImageView imageView2;

    private RecyclerView usersRecyclerView;
    private LinearLayoutManager horizontalLinearLayoutManager;
    private RecyclerviewUsersOnMapAdapter mapRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.onfireapp);
        Bitmap catBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cat);
        imageView.setImageBitmap(getCircledBitmap2(catBitmap, (int) ( 30*getResources().getDisplayMetrics().density)));
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setImageBitmap(getCircledBitmap2(catBitmap, (int) ( 100*getResources().getDisplayMetrics().density)));

        if (isServicesAvailable()){
            init();

        }

    }

    private void init(){
        btnMap = findViewById(R.id.btn_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });


    }

    public boolean isServicesAvailable(){
        Log.d(LOG_TAG," isServicesAvailable:  Checking Google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(available == ConnectionResult.SUCCESS){
            Log.d(LOG_TAG," isServicesAvailable: Google Services ia working");
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(LOG_TAG," isServicesAvailable: On error accured but User can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Log.d(LOG_TAG," isServicesAvailable: On UNFIXED error accured ");
            Toast.makeText(this,"You can't make mao request",Toast.LENGTH_SHORT);

        }
        return false;
    }


}
