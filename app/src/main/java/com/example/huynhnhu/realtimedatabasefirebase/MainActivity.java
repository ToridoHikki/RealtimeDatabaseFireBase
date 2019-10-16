package com.example.huynhnhu.realtimedatabasefirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    ArrayAdapter<String> adapter;
    String TAG ="FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        lvContact = findViewById(R.id.lvContact);
        lvContact.setAdapter(adapter);
        //lấy đối tượng firebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Kết nối tới node có tên là contacts(node này do ta định nghĩa trong CSDL firebase)
        DatabaseReference myRef = database.getReference("contacts");
        //truy xuất và lắng nghe sự thay đổi dữ liệu
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                //Vỏng lặp lấy sự thay đổi dữ liệu khi có sự thay đỏi trong Firebase
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    //lấy key dữ liệu
                    String key = data.getKey();
                    // lấy giá trị của key( nội dung)
                    String value = data.getValue().toString();
                    adapter.add(key+"\n"+value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost.onCancelled", databaseError.toException());

            }
        });
    }
}
