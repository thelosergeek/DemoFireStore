package in.example.notesappusingfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateNotes extends AppCompatActivity {

    private EditText titleEdt, DescriptionEdt;
    private Button buttonUpdate,  buttonDelete;

    private String title, Description;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        Notes notes = (Notes) getIntent().getSerializableExtra("notes");

        db = FirebaseFirestore.getInstance();

        titleEdt = findViewById(R.id.EtTitleField);
        DescriptionEdt = findViewById(R.id.EtDescriptionField);


        buttonUpdate = findViewById(R.id.btnUpdate);
        buttonDelete = findViewById(R.id.btnDelete);

        titleEdt.setText(notes.gettitle());
        DescriptionEdt.setText(notes.getDescription());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleEdt.getText().toString();
                Description = DescriptionEdt.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    titleEdt.setError("Please enter Title");
                } else if (TextUtils.isEmpty(Description)) {
                    DescriptionEdt.setError("Please enter Description");
                } else {
                    updateNotes(notes, title, Description);
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(notes);
            }
        });


    }

    private void deleteNote(Notes notes) {
        db.collection("Notes").document(notes.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateNotes.this, "Note has been deleted .", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateNotes.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(UpdateNotes.this, "Fail to delete the note. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateNotes(Notes notes, String title, String description) {
        Notes updatedNotes = new Notes(title, Description);
        db.collection("Notes").document(notes.getId())
                .set(updatedNotes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateNotes.this, "Note has been updated.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateNotes.this, MainActivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateNotes.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
