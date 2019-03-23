package com.technoweird.camprep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technoweird.camprep.Model.Model;

public class AddWordActivity extends AppCompatActivity {
    private EditText startwith,word,definitions,synonym,antonym,example;
    private Button addword;
    DatabaseReference dbwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        startwith=findViewById(R.id.startWith);
        word=findViewById(R.id.word);
        synonym=findViewById(R.id.synonym);
        antonym=findViewById(R.id.antonym);
        example=findViewById(R.id.example);
        addword=findViewById(R.id.addbtn);
        definitions=findViewById(R.id.definition);
        dbwords= FirebaseDatabase.getInstance().getReference("words");



        addword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start=startwith.getText().toString();
                String words=word.getText().toString();
                String definition=definitions.getText().toString();
                String synonyms=synonym.getText().toString();
                String antonyms=antonym.getText().toString();
                String sentence=example.getText().toString();
                if(!TextUtils.isEmpty(start)&&!TextUtils.isEmpty(words)&&!TextUtils.isEmpty(definition) &&!TextUtils.isEmpty(synonyms)&&!TextUtils.isEmpty(antonyms)&&!TextUtils.isEmpty(sentence)){
                    String id=dbwords.push().getKey();

                    Model model=new Model(id,start,words,definition,synonyms,antonyms,sentence);

                    dbwords.child(id).setValue(model);
                    Toast.makeText(getApplicationContext(),"Word added successful",Toast.LENGTH_LONG).show();
                    startwith.setText("");
                    word.setText("");
                    definitions.setText("");
                    synonym.setText("");
                    antonym.setText("");
                    example.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
