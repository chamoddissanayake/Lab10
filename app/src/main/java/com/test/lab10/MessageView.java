package com.test.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageView extends AppCompatActivity {

    TextView tvSubject, tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        Intent intent = getIntent();
        String Subject=intent.getStringExtra("Subject");
        String Message=intent.getStringExtra("Message");

        tvSubject.setText(Subject);
        tvMessage.setText(Message);


    }
}
