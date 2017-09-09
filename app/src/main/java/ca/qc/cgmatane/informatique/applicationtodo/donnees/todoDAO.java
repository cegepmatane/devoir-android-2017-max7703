package ca.qc.cgmatane.informatique.applicationtodo.donnees;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.applicationtodo.modele.todo;

/**
 * Created by max77 on 06/09/2017.
 */

public class todoDAO {
    private static todoDAO instance = null;
    private BaseDeDonnees accesseurBaseDeDonnees;

    public static List<todo> listeTODO;

    public static todoDAO getInstance() {
        if(null == instance){
            instance = new todoDAO();
        }
        return instance;
    }

    private todoDAO()
    {
        super();
        this.accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        Log.d("DAO", "instance bdd : " + this.accesseurBaseDeDonnees);
        listeTODO = new ArrayList<todo>();

        /*todo todo;
        todo = new todo(1, "Regarder une video", "Demain", "8h", "Le Java", "www.youtube.com");
        listeTODO.add(todo);
        todo = new todo(2, "Faire la lessive", "Aujourd'hui", "16h", "Laver son linge", "N/A");
        listeTODO.add(todo);
        todo = new todo(3, "Lire un livre", "Demain", "12h", "Maria Chapdelaine", "N/A");
        listeTODO.add(todo);
        todo = new todo(4, "Aller à la fête", "Aujourd'hui", "21h", "Party de Nico", "N/A");
        listeTODO.add(todo);
        todo = new todo(5, "Test Titre 5", "Test date de realisation 5", "Test Heure", "Test Description", "Test URL");
        listeTODO.add(todo);*/
    }
    public void modifierTODO(todo todo){
        for(todo TODOTeste : this.listeTODO){

            if(TODOTeste.getId() == todo.getId()) {

                TODOTeste.setUrl(todo.getUrl());
                TODOTeste.setDescription(todo.getDescription());
                TODOTeste.setHeure(todo.getHeure());
                TODOTeste.setDaterealisation(todo.getDaterealisation());
                TODOTeste.setTitre(todo.getTitre());
                return;
            }
        }
    }
    public todo trouverTODO(int id)
    {
        for(todo todo : this.listeTODO)
        {
            if(todo.getId() == id) return todo;
        }
        return null;
    }
    public List<HashMap<String, String>> listerLesTODOEnHashmap(){
        this.listerLesTODO();
        List<HashMap<String, String>> listeTODOHashmap = new ArrayList<HashMap<String, String>>();

        for(todo todo : this.listeTODO){

            listeTODOHashmap.add(todo.exporterHashmap());
        }
        return listeTODOHashmap;
    }

    public List<todo> listerLesTODO(){
        String LISTER_TODO = "SELECT * FROM todo";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_TODO, null);

        this.listeTODO.clear();
        todo todo;

        int indexId = curseur.getColumnIndex("id_todo");
        int indexDateRealisation = curseur.getColumnIndex("date_de_realisation");
        int indexTitre = curseur.getColumnIndex("titre");
        int indexHeure = curseur.getColumnIndex("heure");
        int indexUrl = curseur.getColumnIndex("url");
        int indexDescription = curseur.getColumnIndex("description");

        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()) {
            int id = curseur.getInt(indexId);
            String datederealisation = curseur.getString(indexDateRealisation);
            String titre = curseur.getString(indexTitre);
            String heure = curseur.getString(indexHeure);
            String url = curseur.getString(indexUrl);
            String description = curseur.getString(indexDescription);

            todo = new todo(id, titre, datederealisation, heure, description, url);
            this.listeTODO.add(todo);
        }
        return listeTODO;
    }

}
