package ca.qc.cgmatane.informatique.applicationtodo.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.applicationtodo.modele.todo;

/**
 * Created by max77 on 06/09/2017.
 */

public class todoDAO {
    private static todoDAO instance = null;

    protected List<todo> listeTODO;

    public static todoDAO getInstance() {
        if(null == instance){
            instance = new todoDAO();
        }
        return instance;
    }

    private todoDAO()
    {
        super();
        listeTODO = new ArrayList<todo>();

        todo todo;
        todo = new todo(1, "One Piece", "Eiichirō Oda", "Test Heure", "Test Description", "Test URL");
        listeTODO.add(todo);
        todo = new todo(2, "Naruto", "Masashi Kishimoto", "Test Heure", "Test Description", "Test URL");
        listeTODO.add(todo);
        todo = new todo(3, "Harry Potter", "J.K. Rowling", "Test Heure", "Test Description", "Test URL");
        listeTODO.add(todo);
        todo = new todo(4, "The Hobbit", "Tolkien", "Test Heure", "Test Description", "Test URL");
        listeTODO.add(todo);
        todo = new todo(5, "Test Titre 5", "Test date de realisation 5", "Test Heure", "Test Description", "Test URL");
        listeTODO.add(todo);
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

        List<HashMap<String, String>> listeTODOHashmap = new ArrayList<HashMap<String, String>>();

        for(todo todo : this.listeTODO){

            listeTODOHashmap.add(todo.exporterHashmap());
        }
        return listeTODOHashmap;
    }
}
