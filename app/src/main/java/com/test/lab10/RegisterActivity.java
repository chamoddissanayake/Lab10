package com.test.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    CheckBox chkStudent, chkTeacher;
    EditText etUsername, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        chkStudent = (CheckBox) findViewById(R.id.chkStudent);
        chkTeacher = (CheckBox) findViewById(R.id.chkTeacher);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        chkStudent.setChecked(true);

        chkStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chkTeacher.setChecked(false);
                chkStudent.setChecked(true);
            }
        });

        chkTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chkStudent.setChecked(false);
                chkTeacher.setChecked(true);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etUsername.getText().toString().trim()) ){
                    Toast.makeText(getApplicationContext(),"Please enter username",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etPassword.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
                }else if(chkTeacher.isChecked()==false && chkStudent.isChecked()==false){
                    Toast.makeText(getApplicationContext(),"Please select type",Toast.LENGTH_SHORT).show();
                }else{
                    String type="";
                    if(chkTeacher.isChecked()==true && chkStudent.isChecked()==false){
                        type = "Teacher";
                    }else if(chkStudent.isChecked()==true && chkTeacher.isChecked()==false){
                        type = "Student";
                    }

                    RegisterUser(etUsername.getText().toString().trim(),   etPassword.getText().toString().trim(),  type, getApplicationContext());
                }


            }
        });







    }

    private void RegisterUser(String name, String password, String type, Context applicationContext) {
        //Register start

        try{

            DbHelper dbHelper = new DbHelper(getApplicationContext());
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ReaderContract.UserEntry.COLUMN_NAME_NAME, name);
            values.put(ReaderContract.UserEntry.COLUMN_NAME_PASSWORD , password);
            values.put(ReaderContract.UserEntry.COLUMN_NAME_TYPE , type);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(ReaderContract.UserEntry.TABLE_NAME_USER, null, values);

            if (newRowId >0){
                etUsername.setText("");
                etPassword.setText("");
                Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Error Occurred.",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Error Occurred."+ex,Toast.LENGTH_SHORT).show();
        }finally {

        }




        //Register end
    }
}
