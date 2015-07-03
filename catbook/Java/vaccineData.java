package sarayutwiangchai.catbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
/**
 * Created by Sarayut Wiangchai on 19/6/2558.
 */

public class vaccineData {
    private int id;
    private String Name;
    private String Cat;
    private String Date;
    private String Inject;

    public static final String TABLE = "Vaccine";


    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "Name";
        public static final String CAT = "Cat";
        public static final String DATE = "Date";
        public static final String INJECT = "Inject";

    }


    public vaccineData(){

    }

    public vaccineData(int id, String Name, String Cat, String Date,
                       String Inject) {

        this.id = id;
        this.Name = Name;
        this.Cat = Cat;
        this.Date = Date;
        this.Inject = Inject;



    }



    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setCat(String cat) {
        this.Cat = cat;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public void setInject(String inject) {
        this.Inject = inject;
    }

    public String getCat() {
        return Cat;
    }

    public String getDate() {
        return Date;
    }

    public String getInject() {
        return Inject;
    }


}

