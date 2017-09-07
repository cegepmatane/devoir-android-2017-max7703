package ca.qc.cgmatane.informatique.applicationtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.applicationtodo.donnees.todoDAO;

import static ca.qc.cgmatane.informatique.applicationtodo.R.id.parent;

public class VueApplicationTODO extends AppCompatActivity implements View.OnClickListener {
    protected todoDAO accesseurTODO = todoDAO.getInstance();
    protected List<HashMap<String, String>> listeTODO;
    protected ListView vueListeTODO;

    Button buttonAdd;

    public void onClick(View v)
    {
        Intent intentionNaviguerAjouterTODO = new Intent(
                VueApplicationTODO.this, VueAjouterTODO.class
        );
        startActivity(intentionNaviguerAjouterTODO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_application_todo);

        //Intialization Button
        buttonAdd = (Button)findViewById(R.id.vue_button_add);
        buttonAdd.setOnClickListener(this);

        vueListeTODO = (ListView)findViewById(R.id.vue_liste_todo);
        listeTODO = accesseurTODO.listerLesTODOEnHashmap();

        SimpleAdapter adapteurVueListeTODO = new SimpleAdapter(
                this,
                listeTODO,
                android.R.layout.two_line_list_item,
                new String[] {"titre", "daterealisation"},
                new int[] {android.R.id.text1, android.R.id.text2});

        vueListeTODO.setAdapter(adapteurVueListeTODO);

        vueListeTODO.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick
                            (AdapterView<?> parent, View vue, int positionDansAdapteur,
                             long positionItem){

                        ListView vueListeTODO = (ListView)vue.getParent();

                        @SuppressWarnings("unchecked")
                        HashMap<String, String> todo =
                                (HashMap<String, String>)
                                        vueListeTODO.getItemAtPosition((int)positionItem);

                        Intent intentionNaviguerModifierTODO = new Intent(
                                VueApplicationTODO.this, VueModifierTODO.class
                        );
                        intentionNaviguerModifierTODO.putExtra("id_todo", todo.get("id_todo"));
                        startActivity(intentionNaviguerModifierTODO);
                    }}
        );
    }

}
