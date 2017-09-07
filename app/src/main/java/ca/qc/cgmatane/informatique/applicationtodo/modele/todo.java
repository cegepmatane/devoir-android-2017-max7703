package ca.qc.cgmatane.informatique.applicationtodo.modele;

import java.util.HashMap;

/**
 * Created by max77 on 06/09/2017.
 */

public class todo {
    protected int id;
    protected String titre;
    protected String daterealisation;
    protected String heure;
    protected String description;
    protected String url;

    public todo(int id, String titre, String daterealisation, String heure, String description, String url) {
        super();
        this.id = id;
        this.titre = titre;
        this.daterealisation = daterealisation;
        this.heure = heure;
        this.description = description;
        this.url = url;
    }

    public todo(String titre, String daterealisation, String heure, String description, String url) {
        super();
        this.titre = titre;
        this.daterealisation = daterealisation;
        this.heure = heure;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDaterealisation() {
        return daterealisation;
    }

    public void setDaterealisation(String daterealisation) {
        this.daterealisation = daterealisation;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> exporterHashmap()
    {
        HashMap<String, String> todo = new HashMap<String, String>();
        todo.put("id_todo", String.valueOf(this.id));
        todo.put("titre", this.titre);
        todo.put("daterealisation", this.daterealisation);
        todo.put("heure", this.heure);
        todo.put("description", this.description);
        todo.put("url", this.url);

        return todo;
    }
}
