package com.example.firebasetask;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nameEdit;
    EditText domainEdit;
    Button save;
  private   RecyclerView recyclerView;
   private RecyclerView.Adapter adapter;
    private List<UserData> userDataa;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference("User Data");
        nameEdit = findViewById(R.id.nameEditText);
        domainEdit = findViewById(R.id.domainEditText);
        save = findViewById(R.id.saveBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userDataa = new ArrayList<>();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDataa.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    UserData userData = userSnapshot.getValue(UserData.class);
                    userDataa.add(userData);
                    adapter = new MyAdapter(userDataa,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addUser() {
        String name = nameEdit.getText().toString().trim();
        String domain = domainEdit.getText().toString().trim();
        if((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(domain))){
            String id = databaseReference.push().getKey();
            UserData userData = new UserData(id,name,domain);
            databaseReference.child(id).setValue(userData);
            Toast.makeText(this, "Data Added!", Toast.LENGTH_SHORT).show();
            nameEdit.setText("");
            domainEdit.setText("");

        }else {
            Toast.makeText(this, "please enter the name!", Toast.LENGTH_SHORT).show();
        }
    }
}