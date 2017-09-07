package ca.qc.cgmatane.informatique.applicationtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by max77 on 06/09/2017.
 */

public class VueAjouterTODO extends AppCompatActivity implements View.OnClickListener{

    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_todo);

        //Intialization Button
        buttonSend = (Button)findViewById(R.id.action_ajouter_todo);
        buttonSend.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        Toast test = Toast.makeText(getApplicationContext(),
                "added",
                Toast.LENGTH_LONG);
        test.show();



        this.finish();
    }
}
