package ca.qc.cgmatane.informatique.applicationtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.applicationtodo.donnees.todoDAO;
import ca.qc.cgmatane.informatique.applicationtodo.modele.todo;

import static ca.qc.cgmatane.informatique.applicationtodo.VueApplicationTODO.adapteurVueListeTODO;
import static ca.qc.cgmatane.informatique.applicationtodo.donnees.todoDAO.listeTODO;
import ca.qc.cgmatane.informatique.applicationtodo.donnees.BaseDeDonnees;
/**
 * Created by max77 on 06/09/2017.
 */

public class VueAjouterTODO extends AppCompatActivity implements View.OnClickListener{
    Button buttonSend;
    EditText champTitre;
    EditText champDateRealisation;
    EditText champHeure;
    EditText champDescription;
    EditText champURL;
    private BaseDeDonnees accesseurBaseDeDonnees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_todo);

        //Intialization Button
        buttonSend = (Button)findViewById(R.id.action_ajouter_todo);
        buttonSend.setOnClickListener(this);

        this.accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
    }
    public void onClick(View v)
    {
        Toast test = Toast.makeText(getApplicationContext(),
                "Nouveau TODO ajouté",
                Toast.LENGTH_LONG);
        test.show();

        champTitre = (EditText)findViewById(R.id.champ_titre_ajouter_todo);
        champDateRealisation = (EditText)findViewById(R.id.champ_date_realisation_ajouter_todo);
        champHeure = (EditText)findViewById(R.id.champ_heure_ajouter_todo);
        champDescription = (EditText)findViewById(R.id.champ_description_ajouter_todo);
        champURL = (EditText)findViewById(R.id.champ_url_ajouter_todo);

        //todo todo;
        int new_id = adapteurVueListeTODO.getCount() + 1;
        /*todo = new todo(new_id, champTitre.getText().toString(),
                champDateRealisation.getText().toString(),
                champHeure.getText().toString(),
                champDescription.getText().toString(),
                champURL.getText().toString());


        listeTODO.add(todo);*/

        String INSERT_DATA = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, " +
                "description, url) VALUES('" + new_id + "','" + champTitre.getText().toString() +
                "', '" + champDateRealisation.getText().toString() + "', " +
                "'" + champHeure.getText().toString() + "', " +
                "'" + champDescription.getText().toString() + "', " +
                "'" + champURL.getText().toString() + "')";

        Log.d("INSERT", INSERT_DATA);
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(INSERT_DATA);

        naviguerRetourTODO();
    }
    public void naviguerRetourTODO(){
        this.finish();
    }
}
