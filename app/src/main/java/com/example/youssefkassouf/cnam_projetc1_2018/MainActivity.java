package com.example.youssefkassouf.cnam_projetc1_2018;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView messageTextview;

    private DocumentReference documentReference = FirebaseFirestore.getInstance().document("Messages/SharedMessage");

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    String message = documentSnapshot.getString("message");
                    messageTextview.setText(message);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextview = (TextView) findViewById(R.id.message);
    }

    public void saveMessage(View view) {
        EditText messageEdit = (EditText) findViewById(R.id.messageEdit);
        String message = messageEdit.getText().toString();

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("message", message);
        documentReference.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Message successfully saved!",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error: Message was not saved!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
