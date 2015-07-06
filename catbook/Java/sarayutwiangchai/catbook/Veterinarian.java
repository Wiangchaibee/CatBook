package sarayutwiangchai.catbook;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Veterinarian extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private EditText edtName;
    private EditText edtLast_name;
    private EditText edtAddress;
    private EditText edtPhone_Number;
    private EditText edtEmail;
    private Button Save;

    private ConnectDB connect;
    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarian);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        ////////////////////////////////////////////////////////////////////////////////////////////
        connect = new ConnectDB(this);

        edtName = (EditText) findViewById(R.id.VEDT1);
        edtLast_name = (EditText) findViewById(R.id.VEDT2);
        edtAddress = (EditText) findViewById(R.id.VEDT3);
        edtPhone_Number = (EditText) findViewById(R.id.VEDT4);
        edtEmail = (EditText) findViewById(R.id.VEDT5);

        Save = (Button) findViewById(R.id.VBUTTON);

        ////////////////////////////////////////////////////////////////////////////////////////////
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ID = bundle.getInt(veterinarianData.Column.ID);
            String name = bundle.getString(veterinarianData.Column.Name);
            String last_name = bundle.getString(veterinarianData.Column.Last_name);
            String address = bundle.getString(veterinarianData.Column.Address);
            String phone = bundle.getString(veterinarianData.Column.Phone_Number);
            String email = bundle.getString(veterinarianData.Column.Email);


            edtName.setText(name);
            edtLast_name.setText(last_name);
            edtAddress.setText(address);
            edtPhone_Number.setText(phone);
            edtEmail.setText(email);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(Veterinarian.this);
                builder.setTitle(getString(R.string.add_data_message_veterinarian));
                builder.setMessage(getString(R.string.add_data_title_veterinarian));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               veterinarianData veterinarian = new veterinarianData();
                                veterinarian.setName(edtName.getText().toString());
                                veterinarian.setLast_name(edtLast_name.getText().toString());
                                veterinarian.setAddress(edtAddress.getText().toString());
                                veterinarian.setPhone_Number(edtPhone_Number.getText().toString());
                                veterinarian.setEmail(edtEmail.getText().toString());


                                if (ID == -1) {
                                    connect.addVeterinarian(veterinarian);
                                    finish();

                                } else {
                                    veterinarian.setId(ID);
                                    connect.updateVeterinarian(veterinarian);
                                    finish();
                                    Intent Update = new Intent(Veterinarian.this, CatDetail.class);
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
            getMenuInflater().inflate(R.menu.veterinarian, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_veterinarian, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Veterinarian) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
