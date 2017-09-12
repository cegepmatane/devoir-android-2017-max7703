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
    public void ajouterTODO(todo todo)
    {
        String INSERT_DATA = "INSERT INTO todo(titre, date_de_realisation, heure, " +
                "description, url, fini) VALUES('" + todo.getTitre() +
                "', '" + todo.getDaterealisation() + "', " +
                "'" + todo.getHeure() + "', " +
                "'" + todo.getDescription() + "', " +
                "'" + todo.getUrl() + "', '0')";

        Log.d("INSERT", INSERT_DATA);
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(INSERT_DATA);
    }
    public void todoTermine(String id)
    {
        String TODO_FINI = "UPDATE todo SET fini = '1' WHERE id_todo = '" + id + "'";
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(TODO_FINI);
        Log.d("TODO FINI", TODO_FINI);
    }
    public void modifierTODO(todo todo){

        for(todo TODOTeste : this.listeTODO){

            if(TODOTeste.getId() == todo.getId()) {
                String UPDATE_TODO = "UPDATE todo SET titre = '" + todo.getTitre() +
                        "', date_de_realisation = '" + todo.getDaterealisation() +
                        "', heure = '" + todo.getHeure() +
                        "', description = '" + todo.getDescription() +
                        "', url = '" + todo.getUrl() +
                        "' WHERE " + todo.getId() + " = id_todo";
                Log.d("UPDATE", UPDATE_TODO);

                accesseurBaseDeDonnees.getWritableDatabase().execSQL(UPDATE_TODO);

                /*TODOTeste.setUrl(todo.getUrl());
                TODOTeste.setDescription(todo.getDescription());
                TODOTeste.setHeure(todo.getHeure());
                TODOTeste.setDaterealisation(todo.getDaterealisation());
                TODOTeste.setTitre(todo.getTitre());*/
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
        String LISTER_TODO = "SELECT * FROM todo WHERE fini = '0'";
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
