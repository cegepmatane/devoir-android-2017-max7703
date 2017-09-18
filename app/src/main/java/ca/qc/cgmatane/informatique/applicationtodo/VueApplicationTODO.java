package ca.qc.cgmatane.informatique.applicationtodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.applicationtodo.donnees.BaseDeDonnees;
import ca.qc.cgmatane.informatique.applicationtodo.donnees.todoDAO;

public class VueApplicationTODO extends AppCompatActivity implements View.OnClickListener {

    public static final int ACTIVITY_ALARM_TODO = 3;
    protected todoDAO accesseurTODO;
    public static SimpleAdapter adapteurVueListeTODO;
    protected List<HashMap<String, String>> listeTODO;
    protected ListView vueListeTODO;
    static final int ACTIVITY_MODIFIER_TODO = 1;
    static final int ACTIVITY_AJOUTER_TODO = 2;
    private BaseDeDonnees accesseurBaseDeDonnees;
    Intent intentionNaviguerModifierTODO;
    private final Handler handler = new Handler();

    Button buttonAdd;

    public void onClick(View v)
    {
        Intent intentionNaviguerAjouterTODO = new Intent(
                VueApplicationTODO.this, VueAjouterTODO.class
        );
        startActivityForResult(intentionNaviguerAjouterTODO, ACTIVITY_AJOUTER_TODO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_application_todo);

        //Intialization Button
        buttonAdd = (Button)findViewById(R.id.vue_button_add);
        buttonAdd.setOnClickListener(this);

        vueListeTODO = (ListView)findViewById(R.id.vue_liste_todo);
        vueListeTODO.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        BaseDeDonnees.getInstance(getApplicationContext());
        this.accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        accesseurTODO = todoDAO.getInstance();
        listeTODO = accesseurTODO.listerLesTODOEnHashmap();

        accesseurTODO.getApplicationTODOContext(VueApplicationTODO.this);

        accesseurTODO.mettreAlarm();

        vueListeTODO.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue, int positionDansAdapteur, long positionItem)
                    {
                        ListView vueListeTODO = (ListView)vue.getParent();

                        @SuppressWarnings("unchecked")
                        HashMap<String, String> todo =
                                (HashMap<String, String>)
                                        vueListeTODO.getItemAtPosition((int)positionItem);

                        intentionNaviguerModifierTODO = new Intent(
                                VueApplicationTODO.this, VueModifierTODO.class
                        );
                        intentionNaviguerModifierTODO.putExtra("id_todo", todo.get("id_todo"));
                        startActivityForResult(intentionNaviguerModifierTODO, ACTIVITY_MODIFIER_TODO);
                    }}
        );
        afficherTousLesTodo();
        AutoRefresh();
    }
    protected void onActivityResult(int activite, int resultat, Intent donnees){
        switch(activite){
            case ACTIVITY_MODIFIER_TODO:
                afficherTousLesTodo();
                break;
            case ACTIVITY_AJOUTER_TODO:
                afficherTousLesTodo();
                break;
        }
    }
    private void AutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                afficherTousLesTodo();
                AutoRefresh();
            }
        }, 1000);
    }
    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_B:
                afficherTousLesTodo();
                Log.d("Button B", "Pressed");
                return true;
        }
        return false;
    }*/
     void afficherTousLesTodo() {
        listeTODO = accesseurTODO.listerLesTODOEnHashmap();
        String[] values = new String[] { "titre", "daterealisation", "heure"};
        adapteurVueListeTODO = new SimpleAdapter(
                VueApplicationTODO.this,
                listeTODO,
                R.layout.layout_listview,
                values,
                new int[] {R.id.text1, R.id.text2, R.id.text3});

        vueListeTODO.setAdapter(adapteurVueListeTODO);
    }
}
