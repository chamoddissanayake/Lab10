package com.test.lab10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    TextView welcomeMsg;
    ArrayList<String> Subject;
    ArrayList<String> Message;
    ListView lvMsgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        lvMsgList = (ListView) findViewById(R.id.lvMsgList);

        Intent intent = getIntent();
        String userType=intent.getStringExtra("userType");
        String userName=intent.getStringExtra("userName");
        String dsf = intent.getStringExtra("userID");

        welcomeMsg.setText("Welcome "+userName);

        Subject = new ArrayList<>();
        Message = new ArrayList<>();


        fetchDataFromDB();

//        Subject.add("Maths");Subject.add("Science");Subject.add("Maths");Subject.add("Science");Subject.add("Maths");Subject.add("Science");Subject.add("Science");Subject.add("Science");
//        Message.add("Good");Message.add("Bad");Message.add("Good");Message.add("Bad");Message.add("Good");Message.add("Bad");Message.add("Bad");Message.add("Bad");

        MyAdapter adapter = new MyAdapter(getApplicationContext(),Subject,Message);
        lvMsgList.setAdapter(adapter);

        notificationDisplay();

        lvMsgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvSub= (TextView)view.findViewById(R.id.tvSubject);
                TextView tvMsg = (TextView)view.findViewById(R.id.tvMessage);

                String Subject = tvSub.getText().toString();
                String Message =  tvMsg.getText().toString();


                Intent intentMsgView = new Intent(getApplicationContext(), MessageView.class);
                intentMsgView.putExtra("Subject",Subject);
                intentMsgView.putExtra("Message",Message);
                startActivity(intentMsgView);

            }
        });




    }

    private void notificationDisplay() {
        NotificationChannel notificationChannel = new NotificationChannel("channel_id" , "channel_name", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                .setContentTitle("Messages")
                .setContentText("You have new Messages")
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }

    private void fetchDataFromDB() {
        DbHelper dbHelper = new DbHelper(getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ReaderContract.MessageEntry.COLUMN_NAME_USER,
                ReaderContract.MessageEntry.COLUMN_NAME_SUBJECT,
                ReaderContract.MessageEntry.COLUMN_NAME_MESSAGE
        };

// Filter results WHERE "title" = 'My Title'
//        String selection = ReaderContract.MessageEntry. UserEntry.COLUMN_NAME_NAME  + " = ?";
//        String[] selectionArgs = { inputUsername };

// How you want the results sorted in the resulting Cursor
       String sortOrder = ReaderContract.MessageEntry._ID + " DESC";

        Cursor cursor = db.query(
                ReaderContract.MessageEntry.TABLE_NAME_MESSAGE,   // The table to query
                projection,                                 // The array of columns to return (pass null to get all)
             //   selection,                                  // The columns for the WHERE clause
              //  selectionArgs,                              // The values for the WHERE clause
                null,null,
                null,                               // don't group the rows
                null,                  // don't filter by row groups
                 sortOrder               // The sort order
        );

        List<Message> msgList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Message msgObj = new Message();

            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ReaderContract.MessageEntry._ID ));
            String subjectFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.MessageEntry.COLUMN_NAME_SUBJECT));
            String messageFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.MessageEntry.COLUMN_NAME_MESSAGE));

            msgObj.setSubject(subjectFromDB);
            msgObj.setMessage(messageFromDB);


            msgList.add(msgObj);
        }
        cursor.close();

        if(msgList.size()>0){

            for (Message msgTempObj :  msgList   ) {
                 Subject.add(msgTempObj.getSubject());
                 Message.add(msgTempObj.getMessage());

            }

        }else if(msgList.size()==0){
            Toast.makeText(getApplicationContext(),"Empty Messages",Toast.LENGTH_SHORT).show();
        }else if(msgList.size()<0){
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }

    }
}


class MyAdapter extends ArrayAdapter<String> {
    Context context;

    ArrayList Subject;
    ArrayList Message;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.message_row,parent,false);

        TextView tvSubject = row.findViewById(R.id.tvSubject);
        TextView tvMessage = row.findViewById(R.id.tvMessage);

        tvSubject.setText(Subject.get(position).toString());
        tvMessage.setText(Message.get(position).toString());

        return row;
    }

    MyAdapter(Context c,ArrayList Subject, ArrayList Message){
        super(c, R.layout.message_row, R.id.tvSubject, Subject);
        this.context = c;
        this.Subject = Subject;
        this.Message = Message;
    }
}
