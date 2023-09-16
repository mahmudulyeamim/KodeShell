package com.example.kodeshell;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    String receiverUID, receiverName, SenderUID;
    TextView receiver;
    FirebaseDatabase database;
    FirebaseAuth auth;
    CardView sendButton;
    EditText textMessage;

    String senderRoom, receiverRoom;
    RecyclerView recycleView;
    ArrayList<MessegeModel> messagesArrayList;
    MessageAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        receiverName = getIntent().getStringExtra("Name");
        receiverUID = getIntent().getStringExtra("ID");

        messagesArrayList = new ArrayList<>();

        sendButton = findViewById(R.id.sendbtnn);
        textMessage = findViewById(R.id.textmsg);
        receiver = findViewById(R.id.recivername);
        recycleView = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recycleView.setLayoutManager(linearLayoutManager);
        msgAdapter = new MessageAdapter(ChatActivity.this, messagesArrayList);
        recycleView.setAdapter(msgAdapter);

        receiver.setText(receiverName);

        SenderUID = auth.getUid();

        senderRoom = SenderUID + receiverUID;
        receiverRoom = receiverUID + SenderUID;

        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessegeModel messages = dataSnapshot.getValue(MessegeModel.class);
                    messagesArrayList.add(messages);
                }
                msgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textMessage.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                    return;
                }
                textMessage.setText("");
                Date date = new Date();
                MessegeModel messagess = new MessegeModel(message, SenderUID, date.getTime());

                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .child("messages")
                                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
            }
        });

    }
}