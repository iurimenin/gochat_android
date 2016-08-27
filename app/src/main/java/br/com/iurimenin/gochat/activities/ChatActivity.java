package br.com.iurimenin.gochat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import br.com.iurimenin.gochat.R;

public class ChatActivity extends AppCompatActivity {

    private MenuItem mBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);
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
