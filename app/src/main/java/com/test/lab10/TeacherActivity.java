package com.test.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity {

    TextView welcomeMsg;
    EditText etSubject, etMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        etSubject = (EditText) findViewById(R.id.etSubject);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        final String userType=intent.getStringExtra("userType");
        final String userName=intent.getStringExtra("userName");
        final String dsf = intent.getStringExtra("userID");

        welcomeMsg.setText("Welcome "+userName);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(TextUtils.isEmpty(etSubject.getText().toString().trim())  ){
                    
                }else if(TextUtils.isEmpty(etMessage.getText().toString().trim())  ){

                }else{
                    String subject = etSubject.getText().toString().trim();
                    String message = etMessage.getText().toString().trim();
                    addNewMessage(subject,message, userName,getApplicationContext());
                }
                
                
            }
        });
    }

    private void addNewMessage(String subject, String message,String userName,  Context applicationContext) {
        //Add new message - start
        try{

            DbHelper dbHelper = new DbHelper(getApplicationContext());
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ReaderContract.MessageEntry.COLUMN_NAME_USER, userName);
            values.put(ReaderContract.MessageEntry.COLUMN_NAME_SUBJECT , subject);
            values.put(ReaderContract.MessageEntry.TABLE_NAME_MESSAGE , message);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(ReaderContract.MessageEntry.TABLE_NAME_MESSAGE, null, values);

            if (newRowId >0){
                etMessage.setText("");
                etSubject.setText("");
                Toast.makeText(getApplicationContext(),"Message Added Successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Error Occurred.",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Error Occurred."+ex,Toast.LENGTH_SHORT).show();
        }finally {

        }
        //Add new message - end
    }
}
