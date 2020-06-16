package com.example.marieniel.notes;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText titleEditText,descEditText;
    Button saveBtn, showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        showBtn = (Button) findViewById(R.id.showBtn);
        addData();
        showNotes();
    }

    public void addData(){
        saveBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      boolean isInserted =  myDb.insertDATA(titleEditText.getText().toString(),descEditText.getText().toString());
                        if(isInserted =true)
                            Toast.makeText(MainActivity.this,"Saved!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Unable to save notes.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void showNotes(){
        showBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor result = myDb.getAllData();
                       if (result.getCount()== 0){
                           //show message
                           showMessage("Error","Empty notes");
                           return;
                       }
                       StringBuffer buffer = new StringBuffer();
                       while(result.moveToNext()){
                           buffer.append("ID :"+ result.getString(0)+"\n");
                           buffer.append("TITLE :"+ result.getString(1)+"\n");
                           buffer.append("DESCRIPTION :"+ result.getString(2)+"\n\n");
                       }

                       //show all data
                        showMessage("Notes",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
