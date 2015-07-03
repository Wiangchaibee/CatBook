package sarayutwiangchai.catbook;

import android.provider.BaseColumns;


/**
 * Created by Sarayut Wiangchai on 19/6/2558.
 */
public class expensesData {
    private int id;
    private String Name;
    private String Amount;
    private String Detail;
    private String Date;
    private String Type;

    public static final String TABLE = "Expenses";




    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "Name";
        public static final String AMOUNT = "Amount";
        public static final String DETAIL = "Detail";
        public static final String DATE = "Date";
        public static final String TYPE = "Type";
    }

    public expensesData(){

    }

    public expensesData(int id, String Name, String Amount, String Detail,
                        String Date, String Type) {

        this.id = id;
        this.Name = Name;
        this.Amount = Amount;
        this.Detail = Detail;
        this.Date = Date;
        this.Type = Type;

    }

    public String getName() {
        return Name;
    }

    public String getAmount() {
        return Amount;
    }

    public String getDetail() {
        return Detail;
    }

    public String getDate() {
        return Date;
    }

    public String getType() {
        return Type;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public void setDetail(String detail) {
        this.Detail = detail;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public void setType(String type) {
        this.Type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}

