package br.com.iurimenin.gochat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.iurimenin.gochat.R;

public class ChatActivity extends AppCompatActivity {

    private MenuItem mBtnLogout;
    protected FirebaseDatabase database;
    private ListView listView;
    private DatabaseReference myRefMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);

        final List<String[]> messagens = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, messagens) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(messagens.get(position)[0]);
                text2.setText(messagens.get(position)[1]);
                return view;
            }
        };

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        FirebaseAuth.getInstance().signInAnonymously();
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

        myRefMessages = database.getReference("messages");
        myRefMessages.keepSynced(true);

        ChildEventListener childEventListener = myRefMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String, Object> array = (Map<String, Object>) dataSnapshot.getValue();
                String[] mensagem = new String[2];
                mensagem[0] = (String) array.get("text");
                mensagem[1] = (String) array.get("senderName");
                messagens.add(mensagem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);

        super.onCreateOptionsMenu(menu);

        mBtnLogout = menu.findItem(R.id.action_logout);
        mBtnLogout.setVisible(true);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:


                return logout();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private boolean logout() {

        FirebaseAuth.getInstance().signOut();

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
        return true;
    }
}
