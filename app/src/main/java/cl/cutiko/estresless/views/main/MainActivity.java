package cl.cutiko.estresless.views.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cl.cutiko.estresless.R;
import cl.cutiko.estresless.models.Pending;
import cl.cutiko.estresless.views.main.pendings.PendingListFragment;
import cl.cutiko.estresless.views.main.search.SearchListener;

public class MainActivity extends AppCompatActivity implements PendingCallback, SearchListener {

    private PendingListFragment pendingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pendingListFragment = (PendingListFragment) getSupportFragmentManager().findFragmentById(R.id.pendingListFragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_pending);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                ImageButton saveBtn = (ImageButton) dialog.findViewById(R.id.savePendingBtn);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText input = (EditText) dialog.findViewById(R.id.pendingEt);
                        String name = input.getText().toString();
                        CreatePending createPending = new CreatePending(MainActivity.this);
                        createPending.validation(name);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void created(Pending pending) {
        pendingListFragment.addPending(pending);
    }

    @Override
    public void noName() {
        Toast.makeText(this, "Un nombre por favor", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searched(String name) {
        pendingListFragment.updateList(name);
    }
}
