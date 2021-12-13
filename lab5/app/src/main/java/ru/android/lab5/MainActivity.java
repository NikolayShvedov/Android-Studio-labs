package ru.android.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.android.lab5.journals.JournalAPI;

public class MainActivity extends AppCompatActivity {

    public String fileName;
    private Button btnDownload, btnRead, btnDelete;
    private EditText idJournal;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Documents/Journals/";
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.popup_activity);
        dialog.show();

        if (getDialogStatus()) {
            dialog.hide();
        } else {
            dialog.show();
        }

        File filesDir = new File(path);
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }

        idJournal = findViewById(R.id.idJournal);
        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setBackgroundColor(Color.GREEN);
        btnRead = findViewById(R.id.btnRead);
        btnDelete = findViewById(R.id.btnDelete);
        disableBtn();
        idJournal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                File file = new File(path  + idJournal.getText().toString() + ".pdf");
                if (file.exists()) {
                    enableBtn();
                } else {
                    disableBtn();
                }
            }
        });
    }

    public void disableBtn(){
        btnDelete.setClickable(false);
        btnRead.setClickable(false);
        btnDelete.setBackgroundColor(Color.BLUE);
        btnRead.setBackgroundColor(Color.BLUE);
    }
    public void enableBtn(){
        btnDelete.setClickable(true);
        btnRead.setClickable(true);
        btnDelete.setBackgroundColor(Color.GREEN);
        btnRead.setBackgroundColor(Color.GREEN);
    }

    public void deletePDFFile(View v) throws IOException {

        File file = new File(path  + idJournal.getText() + ".pdf");
        file.delete();
        disableBtn();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Файл успешно удален!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void getPDF(View v) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://ntv.ifmo.ru/file/journal/").
                build();

        JournalAPI journalFile = retrofit.create(JournalAPI.class);
        fileName = idJournal.getText() + ".pdf";
        Call<ResponseBody> call = journalFile.downloadJournalFile(fileName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if( response.code() == 404){
                        Toast.makeText(getApplicationContext(), "Файл с таким id не найден!", Toast.LENGTH_SHORT).show();
                    } else {
                        btnDownload.setClickable(false);
                        Toast.makeText(getApplicationContext(),
                                "Началась загрузка файла!", Toast.LENGTH_SHORT).show();
                        File file = new File(path, fileName);
                        file.createNewFile();
                        Files.asByteSink(file).write(response.body().bytes());
                        Toast.makeText(getApplicationContext(),
                                "Файл успешно загружен в папку" + path +"!", Toast.LENGTH_SHORT).show();
                        enableBtn();
                        Log.e("TAG", "File Path= " + file.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Отсутствует интернет!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }

    public void closeDialog(View view) {
        dialog.dismiss();
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            storeDialogStatus(true);
        } else {
            storeDialogStatus(false);
        }
    }

    public void readPDF(View v) {

        File file = new File(path + idJournal.getText().toString() + ".pdf");
        Uri uri = FileProvider.getUriForFile(this,
                "ru.android.lab5.fileprovider", file);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(uri, "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = Intent.createChooser(target, "Open PDF using");
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No Applications found to open pdf", Toast.LENGTH_SHORT).show();
        }
    }
}