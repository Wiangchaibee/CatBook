package sarayutwiangchai.catbook;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.provider.BaseColumns;

/**
 * Created by Sarayut Wiangchai on 19/6/2558.
 */
public class myCatData {

    private int id;
    private String Name;
    private String Gender;
    private String Breed;
    private String Birth;
    private String Weight;

    private byte[] imageByteArray;
    //Database
    public static final String DATABASE_NAME = "CatBook";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Cat_Data";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "Name";
        public static final String GENDER = "Gender";
        public static final String BREED = "Breed";
        public static final String BIRTH = "Birth";
        public static final String WEIGHT = "Weight";
        public static final String PICTURE = "Picture";
        public static final String REFERENCES_MYCATDATA_ID = "Cat_Data(_ID)";
    }



    public myCatData(){

    }

    public myCatData(int id, String Name, String Gender, String Breed,
                     String Birth, String Weight ,byte[] imageByteArray) {

        this.id = id;
        this.Name = Name;
        this.Gender = Gender;
        this.Breed = Breed;
        this.Birth = Birth;
        this.Weight = Weight;
        this.imageByteArray = imageByteArray;


    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public String getBreed() {
        return Breed;
    }

    public String getBirth() {
        return Birth;
    }

    public String getWeight() {
        return Weight;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public void setBreed(String breed) {
        this.Breed = breed;
    }

    public void setBirth(String birth) {
        this.Birth = birth;
    }

    public void setWeight(String weight) {
        this.Weight = weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }
    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }






}
