package sarayutwiangchai.catbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.*;


public class AddExpenses extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AdapterView.OnItemSelectedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private EditText edtName;
    private EditText edtAmount;
    private EditText edtDetail;
    private EditText edtDate;
    private Spinner edtType;
    private Button Save;

    private ConnectDB connect;
    private int ID = -1;

    static final int DATE_DIALOG_ID = 0;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ////////////////////////////////////////////////////////////////////////////////////////////
        edtName = (EditText) findViewById(R.id.ADEET1);
        edtAmount = (EditText) findViewById(R.id.ADEET2);
        edtDetail = (EditText) findViewById(R.id.ADEET3);
        edtDate = (EditText) findViewById(R.id.ADEET4);
        edtType = (Spinner) findViewById(R.id.spinner);
        Save = (Button) findViewById(R.id.ADEBT1);

        ////////////////////////////////////////////////////////////////////////////////////////////
        connect = new ConnectDB(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ID = bundle.getInt(expensesData.Column.ID);
            String name = bundle.getString(expensesData.Column.NAME);
            String amount = bundle.getString(expensesData.Column.AMOUNT);
            String detail = bundle.getString(expensesData.Column.DETAIL);
            String date = bundle.getString(expensesData.Column.DATE);
            String type = bundle.getString(expensesData.Column.TYPE);

            edtName.setText(name);
            edtAmount.setText(amount);
            edtDetail.setText(detail);
            edtDate.setText(date);
            edtType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    loadSpinnerExpensesType();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }



        ////////////////////////////////////////////////////////////////////////////////////////////

        edtType.setOnItemSelectedListener(this);
        loadSpinnerExpensesType();

        /////////////////////////////////////////////////////////////////////////////////////

        edtDate = (EditText) findViewById(R.id.ADEET4);
        edtDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        final java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH);
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        updateCurrentDate();

        /////////////////////////////////////////////////////////////////////////////////////

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(AddExpenses.this);
                builder.setTitle(getString(R.string.add_data_title_expenses));
                builder.setMessage(getString(R.string.add_data_message_expenses));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                expensesData expenses = new expensesData();
                                expenses.setName(edtName.getText().toString());
                                expenses.setAmount(edtAmount.getText().toString());
                                expenses.setDetail(edtDetail.getText().toString());
                                expenses.setDate(edtDate.getText().toString());
                                expenses.setType(edtType.getSelectedItem().toString());

                                if (ID == -1) {
                                    connect.addExpenses(expenses);
                                    finish();

                                } else {
                                    expenses.setId(ID);
                                    connect.updateExpense(expenses);
                                    finish();
                                    Intent Update = new Intent(AddExpenses.this, ExpensesList.class);
                                    startActivity(Update);
                                }


                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }

                        });

                builder.show();
            }
        });




    }

    private void loadSpinnerExpensesType() {
        String[] expense = {"อาหาร","รักษาพยาบาล","อุปกรณ์","อื่นๆ"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, expense);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edtType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                switch(position){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        final Intent  intent = new Intent(AddExpenses.this, AddType.class);
                        startActivity(intent);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // attaching data adapter to spinner
        edtType.setAdapter(dataAdapter);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:

                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    private void updateCurrentDate() {
        edtDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateCurrentDate();
                }
            };



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.add_expenses, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_expenses, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((AddExpenses) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
