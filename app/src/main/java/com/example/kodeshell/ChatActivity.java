package com.example.kodeshell;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    String receiverUID, receiverName, SenderUID;

    int receiverAvatarId;
    TextView receiver;
    ImageView profilePic;
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
        receiverAvatarId = getIntent().getIntExtra("AvatarId", 0);

        messagesArrayList = new ArrayList<>();

        sendButton = findViewById(R.id.sendbtnn);
        textMessage = findViewById(R.id.textmsg);
        receiver = findViewById(R.id.recivername);
        profilePic = findViewById(R.id.profileimgg);

        recycleView = findViewById(R.id.msgadpter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);

        recycleView.setLayoutManager(linearLayoutManager);
        msgAdapter = new MessageAdapter(ChatActivity.this, messagesArrayList);
        recycleView.setAdapter(msgAdapter);

        receiver.setText(receiverName);
        Picasso.get().load(loadImage(receiverAvatarId)).fit().centerInside().into(profilePic);

        SenderUID = auth.getUid();

        senderRoom = SenderUID + receiverUID;
        receiverRoom = receiverUID + SenderUID;

        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

//        int newPosition = msgAdapter.getItemCount() - 1;
//        recycleView.scrollToPosition(newPosition);
        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessegeModel messages = dataSnapshot.getValue(MessegeModel.class);
                    messagesArrayList.add(messages);
                }
                msgAdapter.notifyDataSetChanged();
                int newPosition = msgAdapter.getItemCount();
                recycleView.smoothScrollToPosition(newPosition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recycleView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int heightDiff = oldBottom - bottom;
                if (heightDiff > 200) {
                    int keyboardHeight = heightDiff;
                    recycleView.smoothScrollBy(0, keyboardHeight);
                }
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

    private int loadImage(int i){
        if(i == 0) {
            return R.drawable.avatar1;
        }
        else if(i == 1) {
            return R.drawable.avatar2;
        }
        else if(i == 2) {
            return R.drawable.avatar3;
        }
        else if(i == 3) {
            return R.drawable.avatar4;
        }
        else if(i == 4) {
            return R.drawable.avatar5;
        }
        else if(i == 5) {
            return R.drawable.avatar6;
        }
        else if(i == 6) {
            return R.drawable.avatar7;
        }
        else if(i == 7) {
            return R.drawable.avatar8;
        }
        else if(i == 8) {
            return R.drawable.avatar9;
        }
        else if(i == 9) {
            return R.drawable.avatar10;
        }
        else if(i == 10) {
            return R.drawable.avatar11;
        }
        return R.drawable.avatar12;
    }
}