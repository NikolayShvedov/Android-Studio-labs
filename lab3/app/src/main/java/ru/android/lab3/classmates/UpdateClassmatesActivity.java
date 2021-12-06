package ru.android.lab3.classmates;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.android.lab3.R;
import ru.android.lab3.db_helper.DBHelper;
import ru.android.lab3.model.Classmate;

public class UpdateClassmatesActivity extends AppCompatActivity {

    private EditText editId;
    private EditText editName;
    private EditText editSurname;
    private EditText editPatronymic;
    private String editDateCreate;
    private Button buttonUpdate;
    private Button buttonLastUpdate;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_classmates);

        editId = findViewById(R.id.editText_id);
        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editPatronymic = (EditText) findViewById(R.id.editText_Patronymic);
        editDateCreate = convertNowDateToString();
        buttonUpdate = (Button) findViewById(R.id.button_update);
        buttonLastUpdate = (Button) findViewById(R.id.button_last_update);

        dbHelper = new DBHelper(this);
        UpdateData();
        UpdateLastData();
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData() {
        buttonUpdate.setOnClickListener(
                v -> {
                    boolean isUpdate = dbHelper.updateData(editId.getText().toString(),
                            editName.getText().toString(),
                            editSurname.getText().toString(), editPatronymic.getText().toString(),
                            editDateCreate);
                    if(isUpdate)
                        Toast.makeText(UpdateClassmatesActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(UpdateClassmatesActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                }
        );
    }

    public void UpdateLastData() {
        buttonLastUpdate.setOnClickListener(
                v -> {
                    boolean isUpdate = dbHelper.updateLastData(new Classmate());
                    if(isUpdate)
                        Toast.makeText(UpdateClassmatesActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(UpdateClassmatesActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                }
        );
    }
    public String convertNowDateToString() {

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        return todayAsString;
    }

}