package com.omellete.calminapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omellete.calminapp.adapter.MessageAdapter;
import com.omellete.calminapp.databinding.ActivityGroupSharingBinding;
import com.omellete.calminapp.databinding.ActivityLoginBinding;
import com.omellete.calminapp.model.AllMethods;
import com.omellete.calminapp.model.Message;
import com.omellete.calminapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupSharingActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    MessageAdapter adapter;
    User u;
    List<Message> messages;
    ActivityGroupSharingBinding binding;
    String userDname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupSharingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Public Chat");
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F78CBD68"));
        actionBar.setBackgroundDrawable(colorDrawable);

        init();

    }

    private void init (){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        u = new User();
        binding.btnSend.setOnClickListener(this);
        messages = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(binding.etMessage.getText ().toString())) {
            Message message = new Message(binding.etMessage.getText().toString(), userDname);
            binding.etMessage. setText ("");
            reference.push().setValue(message);
        }else{
            Toast.makeText(getApplicationContext(), "Kamu tidak bisa mengirim pesan kosong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        u.setuid(currentUser.getUid());
        u.setName(currentUser.getDisplayName());

        ProgressDialog pd = new ProgressDialog(GroupSharingActivity.this);
        pd.setMessage("Mohon Tunggu sebentar...");
        pd.show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();
        ref.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDname = dataSnapshot.child("username").getValue(String.class);
                Log.d(TAG, "Name: " + userDname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        firebaseDatabase.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u= snapshot.getValue (User.class);
                u.setuid(currentUser.getUid());
                AllMethods.name = userDname ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference = firebaseDatabase.getReference("messages");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                messages.add(message);
                displayMessages(messages);
                pd.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());

                List<Message> newMessage = new ArrayList<Message>();

                for (Message m: messages){
                    if(m.getKey().equals(message.getKey())){
                        newMessage.add(message);
                    }else{
                        newMessage.add(m);
                    }
                }

                messages = newMessage;
                displayMessages(messages);
                pd.dismiss();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());

                List<Message> newMessage = new ArrayList<Message>();

                for(Message m: messages){
                    if (!m.getKey().equals(message.getKey())){
                        newMessage.add(m);
                    }
                }

                messages = newMessage;
                displayMessages(messages);
                pd.dismiss();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = new ArrayList<>();
    }

    private void displayMessages(List<Message> messages) {
        binding.rvMessage.setLayoutManager(new LinearLayoutManager(GroupSharingActivity.this));
        adapter = new MessageAdapter(GroupSharingActivity.this, messages, reference);
        binding.rvMessage.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(GroupSharingActivity.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}