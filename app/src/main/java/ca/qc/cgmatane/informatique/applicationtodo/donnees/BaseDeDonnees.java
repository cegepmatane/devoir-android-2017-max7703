package ca.qc.cgmatane.informatique.applicationtodo.donnees;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper {

    private static BaseDeDonnees instance = null;

    public static BaseDeDonnees getInstance(Context contexte)
    {
        if(null == instance) instance = new BaseDeDonnees(contexte);
        return instance;
    }

    public static BaseDeDonnees getInstance()
    {
        return instance;
    }

    public BaseDeDonnees(Context contexte) {
        super(contexte, "applicationtodo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS todo(id_todo INTEGER PRIMARY KEY, titre TEXT, date_de_realisation TEXT, heure TEXT, description TEXT, url TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        //String DELETE = "delete from livre where 1 = 1";
        //db.execSQL(DELETE);
        String CREER_TABLE = "CREATE TABLE IF NOT EXISTS todo(id_todo INTEGER PRIMARY KEY, titre TEXT, date_de_realisation TEXT, heure TEXT, description TEXT, url TEXT)";
        db.execSQL(CREER_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        String DELETE = "delete from todo where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, url) VALUES('1','Regarder une video', 'Demain', '8h', 'Le Java', 'www.youtube.com')";
        String INSERT_2 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, url) VALUES('2','Faire la lessive', 'Aujourdhui', '16h', 'Laver son linge', 'N/A')";
        String INSERT_3 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, url) VALUES('3','Lire un livre', 'Demain', '12h', 'Maria Chapdelaine', 'N/A')";
        String INSERT_4 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, url) VALUES('4','Aller à la fête', 'Aujourdhui', '21h', 'Party de Nico', 'N/A')";
        String INSERT_5 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, url) VALUES('5','Test Titre 5', 'Test date de realisation 5', '8h', 'Test Heure', 'Test URL')";

        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);
        db.execSQL(INSERT_4);
        db.execSQL(INSERT_5);

    }

}