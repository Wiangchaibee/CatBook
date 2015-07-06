package sarayutwiangchai.catbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CatDetail extends Activity
       /* implements NavigationDrawerFragment.NavigationDrawerCallbacks*/ {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    ConnectDB connect;

    private TextView txtName;
    private TextView txtGender;
    private TextView txtBreed;
    private TextView txtBirth;
    private TextView txtWeight;
    private String id = "";

    private Button ButtonEdit;
    private Button ButtonDelete;

    private myCatData mCat;

    private ImageView imageView;

    List <String> cats;
    List <String> cat_veterinarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //mNavigationDrawerFragment = (NavigationDrawerFragment)
        //        getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // mTitle = getTitle();

        // Set up the drawer.
        //  mNavigationDrawerFragment.setUp(
        //           R.id.navigation_drawer,
        //         (DrawerLayout) findViewById(R.id.drawer_layout));


        connect = new ConnectDB(this);
        cats = connect.CatList();
        cat_veterinarian = connect.CatList_veterinarian();


        //////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////////////////////////

        final Bundle bundle  = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString(myCatData.Column.NAME);
        }

        setContentView(R.layout.activity_cat_detail);
        txtName = (TextView) findViewById(R.id.detail_name);
        txtGender = (TextView) findViewById(R.id.detail_gender);
        txtBreed = (TextView) findViewById(R.id.detail_breed);
        txtBirth = (TextView) findViewById(R.id.detail_birth);
        txtWeight = (TextView) findViewById(R.id.detail_weight);
        ButtonDelete = (Button) findViewById(R.id.button_delete);
        ButtonEdit = (Button) findViewById(R.id.button_edit);

        ButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(CatDetail.this,
                        AddCat.class);

                updateIntent.putExtra(myCatData.Column.ID, mCat.getId());
                updateIntent.putExtra(myCatData.Column.NAME, mCat.getName());
                updateIntent.putExtra(myCatData.Column.GENDER, mCat.getGender());
                updateIntent.putExtra(myCatData.Column.BREED, mCat.getBreed());
                updateIntent.putExtra(myCatData.Column.BIRTH, mCat.getBirth());
                updateIntent.putExtra(myCatData.Column.WEIGHT, mCat.getWeight());
                updateIntent.putExtra(myCatData.Column.PICTURE,mCat.getImageByteArray());

                startActivity(updateIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        });
        initialData();


        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(CatDetail.this);
                builder.setTitle(getString(R.string.alert_title));
                builder.setMessage(getString(R.string.alert_message));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                connect.deleteCat(id);

                                Toast.makeText(getApplication(),
                                        "Deleted", Toast.LENGTH_LONG).show();
                                finish();
                                Intent Delete = new Intent(CatDetail.this, CatList.class);
                                startActivity(Delete);
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel), null);

                builder.show();

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////

        final Button IBT6 = (Button) findViewById(R.id.button_add);
        IBT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent veterinarian = new Intent(CatDetail.this,Veterinarian.class);
                startActivity(veterinarian);

                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);

            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////

    }


    private void initialData() {
        mCat = connect.getCat(id);
        txtName.setText(mCat.getName());
        txtGender.setText(mCat.getGender());
        txtBreed.setText(mCat.getBreed());
        txtBirth.setText(mCat.getBirth());
        txtWeight.setText(mCat.getWeight());

    }
    private void setUpImage(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
    }




    // @Override
    //  public void onNavigationDrawerItemSelected(int position) {
    //     // update the main content by replacing fragments
    //     FragmentManager fragmentManager = getSupportFragmentManager();
    //     fragmentManager.beginTransaction()
    //             .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
    //             .commit();
    //  }

    // public void onSectionAttached(int number) {
    //  switch (number) {
    //     case 1:
    //        mTitle = getString(R.string.title_section1);
    //       break;
    //   case 2:
    //     mTitle = getString(R.string.title_section2);
    //     break;
    // case 3:
    //     mTitle = getString(R.string.title_section3);
    ///     break;
    // }
    //  }

    public void restoreActionBar() {
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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

    private class ImageAdapter extends BaseAdapter {
        public ImageAdapter(CatDetail catDetail, List<String> cats) {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */





}
