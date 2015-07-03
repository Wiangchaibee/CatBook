package sarayutwiangchai.catbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ExpensesDetail extends ActionBarActivity {

    ConnectDB connect;

    private TextView txtName;
    private TextView txtAmount;
    private TextView txtDetail;
    private TextView txtDate;
    private TextView txtType;
    private String id = "";

    private Button ButtonEdit;
    private Button ButtonDelete;

    private expensesData mExpenses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        connect = new ConnectDB(this);

        final Bundle bundle  = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString(expensesData.Column.ID);
        }

        setContentView(R.layout.activity_expenses_detail);

        txtName = (TextView) findViewById(R.id.EDTV1);
        txtAmount = (TextView) findViewById(R.id.EDTV2);
        txtDetail = (TextView) findViewById(R.id.EDTV3);
        txtDate = (TextView) findViewById(R.id.EDTV4);
        txtType = (TextView) findViewById(R.id.EDTV5);
        ButtonDelete = (Button) findViewById(R.id.button_delete);
        ButtonEdit = (Button) findViewById(R.id.button_edit);

        ButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(ExpensesDetail.this,
                        AddExpenses.class);

                updateIntent.putExtra(expensesData.Column.ID, mExpenses.getId());
                updateIntent.putExtra(expensesData.Column.NAME, mExpenses.getName());
                updateIntent.putExtra(expensesData.Column.AMOUNT, mExpenses.getAmount());
                updateIntent.putExtra(expensesData.Column.DETAIL, mExpenses.getDetail());
                updateIntent.putExtra(expensesData.Column.DATE, mExpenses.getDate());
                updateIntent.putExtra(expensesData.Column.TYPE, mExpenses.getType());

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
                        new AlertDialog.Builder(ExpensesDetail.this);
                builder.setTitle(getString(R.string.alert_title_expenses));
                builder.setMessage(getString(R.string.alert_message_expenses));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                connect.deleteExpenses(id);

                                Toast.makeText(getApplication(),
                                        "Deleted", Toast.LENGTH_LONG).show();
                                finish();
                                Intent Delete = new Intent(ExpensesDetail.this, ExpensesList.class);
                                startActivity(Delete);
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel), null);

                builder.show();

            }
        });
    }

    private void initialData() {
        mExpenses = connect.getExpenses(id);
        txtName.setText(mExpenses.getName());
        txtAmount.setText(mExpenses.getAmount());
        txtDetail.setText(mExpenses.getDetail());
        txtDate.setText(mExpenses.getDate());
        txtType.setText(mExpenses.getType());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expenses_detail, menu);
        return true;
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
}
