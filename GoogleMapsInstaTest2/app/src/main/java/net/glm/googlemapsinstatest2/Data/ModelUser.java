package net.glm.googlemapsinstatest2.Data;

import net.glm.googlemapsinstatest2.R;

import java.util.ArrayList;

/**
 * Created by Michael on 19/02/2018.
 */

public class ModelUser {
    private String name;
    private int imgId;
    private double shiftLat;
    private double shiftLong;
    private int radius;
    private String status;
    private int age;
    private String sex;
    private String orientation;


    private ModelUser(String name, int imgId, double shiftX, double shiftY, int radius) {

        this.name = name;
        this.imgId = imgId;
        this.shiftLat = shiftX;
        this.shiftLong = shiftY;
        this.radius = radius;

    }

    public ModelUser(String name, int imgId, double shiftLat, double shiftLong, int radius, String status, int age, String sex, String orientation) {
        this.name = name;
        this.imgId = imgId;
        this.shiftLat = shiftLat;
        this.shiftLong = shiftLong;
        this.radius = radius;
        this.status = status;
        this.age = age;
        this.sex = sex;
        this.orientation = orientation;
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

    public String getStatus() {
        return status;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getOrientation() {
        return orientation;
    }

    public double getShiftLong() {
        return shiftLong;
    }

    public int getRadius() {
        return radius;
    }

    public static ArrayList<ModelUser> getFakeUsers1() {
        ArrayList<ModelUser> usersList = new ArrayList<>();
        usersList.add(new ModelUser("OnFire", R.drawable.onfireapp, 0, 0, 120,"We will connect people in the world",20,"Male","Bisexual"));
        usersList.add(new ModelUser("Rony", R.drawable.user7, 0.0050d, 0.0070d, 60,"I Am Not Perfect. I Am Limited Editions",21,"Female","Straight"));
        usersList.add(new ModelUser("Mike", R.drawable.user2, -0.0070d, -0.0080d, 140,"I’M Sexy & I Know It",20,"Male","Straight"));
        usersList.add(new ModelUser("Nimrod", R.drawable.user3, 0.01d, 0.0030d, 100,"All Girls Are My Sisters Except You",23,"Male","Straight"));
        usersList.add(new ModelUser("Oleg", R.drawable.user4, -0.010d, 0.0020d, 120,"I speak my mind. I never mind what I speak",18,"Male","Straight"));
        usersList.add(new ModelUser("Shay", R.drawable.user5, 0.010d, -0.0050d, 200,"Totally Available!! Please Disturb Me!!",20,"Male","Bisexual"));
        usersList.add(new ModelUser("Orna", R.drawable.user6, -0.0060d, 0.0080d, 140,"God is really creative, I mean…just look at m!!!",19,"Female","Straight"));

        return usersList;
    }
}
