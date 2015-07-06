package sarayutwiangchai.catbook;

import android.provider.BaseColumns;

/**
 * Created by Sarayut Wiangchai on 6/7/2558.
 */
public class veterinarianData {

    private int id;
    private String Name;
    private String Last_name;
    private String Address;
    private String Phone_Number;
    private String Email;

    public static final String DATABASE_NAME = "Veterinarian";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Veterinarian";




    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String Name = "Name";
        public static final String Last_name = "Last_name";
        public static final String Address = " Address";
        public static final String Phone_Number = "Phone_Number";
        public static final String Email = "Email";

    }

    public veterinarianData(){

    }

    public veterinarianData(int id, String Name, String Last_name, String Address,
                     String Phone_Number, String Email ) {

        this.id = id;
        this.Name = Name;
        this.Last_name = Last_name;
        this.Address = Address;
        this.Phone_Number = Phone_Number;
        this.Email = Email;

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
