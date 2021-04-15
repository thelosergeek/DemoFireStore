package in.example.notesappusingfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    private TextInputEditText titleEdt, descriptionEdt;
    private MaterialButton btnSave;
    private String title, desciption;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);


        titleEdt = findViewById(R.id.titleTextField);
        descriptionEdt = findViewById(R.id.DescriptionTextField);
        btnSave = findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = titleEdt.getText().toString();
                desciption = descriptionEdt.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    titleEdt.setError("Title cannot be empty");
                } else if (TextUtils.isEmpty(desciption)) {
                    descriptionEdt.setError("Description cannot be empty");
                } else {
                    uploadToFirestore(title, desciption);
                }
            }

        });
    }

    private void uploadToFirestore(String title, String desciption) {

        CollectionReference dbNotes = db.collection("Notes");
        Notes notes = new Notes(title, desciption);
        dbNotes.add(notes).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NewNoteActivity.this, "New Note Added", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewNoteActivity.this, MainActivity.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewNoteActivity.this, "Error \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}