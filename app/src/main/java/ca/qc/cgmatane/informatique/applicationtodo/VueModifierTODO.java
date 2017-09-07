package ca.qc.cgmatane.informatique.applicationtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.qc.cgmatane.informatique.applicationtodo.donnees.todoDAO;
import ca.qc.cgmatane.informatique.applicationtodo.modele.todo;

/**
 * Created by max77 on 06/09/2017.
 */

public class VueModifierTODO extends AppCompatActivity implements View.OnClickListener {
    protected todoDAO accesseurTODO = todoDAO.getInstance();
    todo todo;
    EditText champTitre;
    EditText champDateRealisation;
    EditText champHeure;
    EditText champDescription;
    EditText champURL;
    Button buttonModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_todo);

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id_todo = (String) parametres.get("id_todo");
        int id_todo = Integer.parseInt(parametre_id_todo);

        /*Toast test = Toast.makeText(getApplicationContext(),
                "Valeur reçue " + id_todo,
                Toast.LENGTH_LONG);
        test.show();*/

        todo = accesseurTODO.trouverTODO(id_todo);
        champTitre = (EditText)findViewById(R.id.champ_titre_modifier_todo);
        champDateRealisation = (EditText)findViewById(R.id.champ_date_realisation_modifier_todo);
        champHeure = (EditText)findViewById(R.id.champ_heure_modifier_todo);
        champDescription = (EditText)findViewById(R.id.champ_description_modifier_todo);
        champURL = (EditText)findViewById(R.id.champ_url_modifier_todo);

        champTitre.setText(todo.getTitre());
        champDateRealisation.setText(todo.getDaterealisation());
        champHeure.setText(todo.getHeure());
        champDescription.setText(todo.getDescription());
        champURL.setText(todo.getUrl());

        //Intialization Button
        buttonModify = (Button) findViewById(R.id.action_modifier_todo);
        buttonModify.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        todo.setTitre(champTitre.getText().toString());
        todo.setDaterealisation(champDateRealisation.getText().toString());
        todo.setHeure(champHeure.getText().toString());
        todo.setDescription(champDescription.getText().toString());
        todo.setUrl(champURL.getText().toString());
        accesseurTODO.modifierTODO(todo);
        naviguerRetourTODO();
    }
    public void naviguerRetourTODO(){
        this.finish();
    }
}
