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
        String CREATE_TABLE = "CREATE TABLE todo(id_todo INTEGER PRIMARY KEY, titre TEXT, " +
                "date_de_realisation TEXT, heure TEXT, description TEXT, url TEXT, fini TINYINT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        String CREER_TABLE = "CREATE TABLE todo(id_todo INTEGER PRIMARY KEY, titre TEXT, " +
                "date_de_realisation TEXT, heure TEXT, description TEXT, url TEXT, fini TINYINT)";
        db.execSQL(CREER_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        String DELETE = "delete from todo where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, " +
                "url, fini) VALUES('1','Regarder une video', '30/09/2017', '10:30', 'Le Java', 'www.youtube.com', '0')";
        String INSERT_2 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, " +
                "url, fini) VALUES('2','Faire la lessive', '20/09/2017', '08:30', 'Laver son linge', 'N/A', '0')";
        String INSERT_3 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, " +
                "url, fini) VALUES('3','Lire un livre', '20/09/2017', '04:30', 'Maria Chapdelaine', 'N/A', '0')";
        String INSERT_4 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, " +
                "url, fini) VALUES('4','Aller à la fête', '18/09/2017', '18:07', 'Party de Nico', 'N/A', '0')";
        String INSERT_5 = "INSERT INTO todo(id_todo, titre, date_de_realisation, heure, description, " +
                "url, fini) VALUES('5','Test Titre 5', '18/09/2017', '18:06', 'Test description', 'Test URL', '0')";

        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);
        db.execSQL(INSERT_4);
        db.execSQL(INSERT_5);

    }

}