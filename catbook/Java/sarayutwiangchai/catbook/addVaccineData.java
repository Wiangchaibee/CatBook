package sarayutwiangchai.catbook;

import android.provider.BaseColumns;

/**
 * Created by Sarayut Wiangchai on 7/7/2558.
 */
public class addVaccineData {

    private int id;
    private String Name;

    public static final String TABLE = "Add_Vaccine_Name";

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "Name";

    }

    public addVaccineData(int id, String Name) {

        this.id = id;
        this.Name = Name;

    }

    public  addVaccineData(){

    }


}
