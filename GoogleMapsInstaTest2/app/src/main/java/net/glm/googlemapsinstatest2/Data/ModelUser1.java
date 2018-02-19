package net.glm.googlemapsinstatest2.Data;

import net.glm.googlemapsinstatest2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 19/02/2018.
 */

public class ModelUser1 {
    private String name;
    private int imgId;
    private double shiftLat;
    private double shiftLong;
    private int radius;


    private ModelUser1(String name, int imgId, double shiftX, double shiftY, int radius) {

        this.name = name;
        this.imgId = imgId;
        this.shiftLat = shiftX;
        this.shiftLong = shiftY;
        this.radius = radius;

    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }

    public double getShiftLat() {
        return shiftLat;
    }

    public double getShiftLong() {
        return shiftLong;
    }

    public int getRadius() {
        return radius;
    }

    public static ArrayList<ModelUser1> getFakeUsers1(){
        ArrayList<ModelUser1> usersList = new ArrayList<>();
        usersList.add(new ModelUser1("OnFire", R.drawable.onfireapp,0,0,100));
        usersList.add(new ModelUser1("Lion", R.drawable.lion,0.0070d,0.0050d,60));
        usersList.add(new ModelUser1("Tiger", R.drawable.tiger,-0.0070d,-0.0080d,140));
        usersList.add(new ModelUser1("Dog", R.drawable.dog,0.01d,0d,100));
        usersList.add(new ModelUser1("Cat", R.drawable.cat,-0.012d,0.0020d,100));
        usersList.add(new ModelUser1("Fine", R.drawable.fine,0.012d,-0.0040d,200));

        return usersList;
    }
}
