package in.example.notesappusingfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    private RecyclerView notesRV;
    private ArrayList<Notes> notesArrayList;
    private NotesAdapter notesAdapter;
    private FirebaseFirestore db;

    ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRV = findViewById(R.id.idRVCourses);

        floatingActionButton = findViewById(R.id.floating_action_button);
        loadingPB = findViewById(R.id.idProgressBar);


        db = FirebaseFirestore.getInstance();

        notesArrayList = new ArrayList<>();
        notesRV.setHasFixedSize(true);
        notesRV.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(notesArrayList,this);

        notesRV.setAdapter(notesAdapter);

        db.collection("Notes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                Notes n = d.toObject(Notes.class);
                                n.setId(d.getId());

                                notesArrayList.add(n);
                            }

                            notesAdapter.notifyDataSetChanged();
                        } else {

                            Toast.makeText(MainActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
                startActivity(intent);
            }
        });

    }
}