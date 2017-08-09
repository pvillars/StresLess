package cl.cutiko.estresless.views.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import cl.cutiko.estresless.R;
import cl.cutiko.estresless.models.Pending;

public class DetailsActivity extends AppCompatActivity {

    private Pending pending;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long id = getIntent().getLongExtra("id", 0);
        pending = Pending.findById(Pending.class, id);

        /*pending = (Pending) getIntent().getSerializableExtra(PendingListFragment.PENDING);
        Log.d("ID", String.valueOf(pending.getId()));*/
        getSupportActionBar().setTitle(pending.getName());
        editText = (EditText) findViewById(R.id.descriptionEt);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("LIFECYCLE", "onResume: ");
        String description = pending.getDescription();
        if (description != null) {
            editText.setText(description);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("LIFECYCLE", "onPause: ");
        String description = editText.getText().toString();
        pending.setDescription(description);
        pending.save();
    }
}
