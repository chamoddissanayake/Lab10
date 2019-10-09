package com.test.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to Register Activity
                Intent intentReg = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentReg);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etUsername.getText().toString().trim()) ){
                    Toast.makeText(getApplicationContext(),"Please enter username",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etPassword.getText().toString().trim()) ){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
                }else{
                    //validate User
                    String inputUsername = etUsername.getText().toString();
                    String inputPassword = etPassword.getText().toString();
                    validateUser(inputUsername,inputPassword, getApplicationContext() );
                }
            }
        });


    }

    private void validateUser(String inputUsername, String inputPassword, Context applicationContext) {
        //validate user - start
        DbHelper dbHelper = new DbHelper(getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ReaderContract.UserEntry.COLUMN_NAME_NAME,
                ReaderContract.UserEntry.COLUMN_NAME_TYPE,
                ReaderContract.UserEntry.COLUMN_NAME_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = ReaderContract.UserEntry.COLUMN_NAME_NAME  + " = ? AND "+ ReaderContract.UserEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = { inputUsername, inputPassword };

// How you want the results sorted in the resulting Cursor
//        String sortOrder = ReaderContract.UserEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                ReaderContract.UserEntry.TABLE_NAME_USER,   // The table to query
                projection,                                 // The array of columns to return (pass null to get all)
                selection,                                  // The columns for the WHERE clause
                selectionArgs,                              // The values for the WHERE clause
                null,                               // don't group the rows
                null,   null                  // don't filter by row groups
               // sortOrder               // The sort order
        );

        List<User> itemList = new ArrayList<>();
        while(cursor.moveToNext()) {
            User userObj = new User();

            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ReaderContract.UserEntry._ID ));
            String nameFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.UserEntry.COLUMN_NAME_NAME));
            String passwordFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.UserEntry.COLUMN_NAME_PASSWORD));
            String typeFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.UserEntry.COLUMN_NAME_TYPE));

            userObj.setItemId(itemId);
            userObj.setNameFromDB(nameFromDB);
            userObj.setPasswordFromDB(passwordFromDB);
            userObj.setTypeFromDB(typeFromDB);

            itemList.add(userObj);
        }
        cursor.close();

        if(itemList.size()>1){
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }else if(itemList.size()<1){
            Toast.makeText(getApplicationContext(),"Invalid Username of Password",Toast.LENGTH_SHORT).show();
        }else{
            //Correct Username and Password -Found
            User foundUser = itemList.get(0);
            String userType = foundUser.getTypeFromDB();
            long userID =foundUser.getItemId();
            String userName = foundUser.getNameFromDB();

            if(userType.equals("Student")){
                //Load Student Interface with details
                Intent intentStd = new Intent(getApplicationContext(), StudentActivity.class);
                intentStd.putExtra("userType",userType);
                intentStd.putExtra("userID",userID);
                intentStd.putExtra("userName",userName);
                startActivity(intentStd);
            }else  if(userType.equals("Teacher")){
                //Load Teacher Interface with details
                Intent intentTea = new Intent(getApplicationContext(), TeacherActivity.class);
                intentTea.putExtra("userType",userType);
                intentTea.putExtra("userID",userID);
                intentTea.putExtra("userName",userName);
                startActivity(intentTea);
            }

        }


        //validate user - end
    }
}
