package ca.qc.cgmatane.informatique.applicationtodo.donnees;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.applicationtodo.VueAlarme;
import ca.qc.cgmatane.informatique.applicationtodo.modele.todo;

/**
 * Created by max77 on 06/09/2017.
 */

public class todoDAO {
    private static todoDAO instance = null;
    private BaseDeDonnees accesseurBaseDeDonnees;
    private Context context;

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
        //Log.d("DAO", "instance bdd : " + this.accesseurBaseDeDonnees);
        listeTODO = new ArrayList<todo>();
    }
    public void getApplicationTODOContext(Context cont)
    {
        context = cont;
    }
    public void ajouterTODO(todo todo)
    {
        String INSERT_DATA = "INSERT INTO todo(titre, date_de_realisation, heure, " +
                "description, url, fini) VALUES('" + todo.getTitre() +
                "', '" + todo.getDaterealisation() + "', " +
                "'" + todo.getHeure() + "', " +
                "'" + todo.getDescription() + "', " +
                "'" + todo.getUrl() + "', '0')";

        //Log.d("INSERT", INSERT_DATA);
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(INSERT_DATA);

        ajouterAlarm(todo.getTitre());
    }

    public void todoTermine(String id)
    {
        String TODO_FINI = "UPDATE todo SET fini = '1' WHERE id_todo = '" + id + "'";
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(TODO_FINI);
        //Log.d("TODO FINI", TODO_FINI);
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
                //Log.d("UPDATE", UPDATE_TODO);

                accesseurBaseDeDonnees.getWritableDatabase().execSQL(UPDATE_TODO);
                modifierAlarm(todo.getId());

            }
        }
    }
    public void ajouterAlarm(String titre)
    {
        String LISTER_TODO = "SELECT * FROM todo WHERE fini = '0' AND titre = '" + titre + "'";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_TODO, null);

        int indexId = curseur.getColumnIndex("id_todo");
        int indexDateRealisation = curseur.getColumnIndex("date_de_realisation");
        int indexHeure = curseur.getColumnIndex("heure");

        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext())
        {
            String id = curseur.getString(indexId);
            String jour = curseur.getString(indexDateRealisation).substring(0,2);
            String mois = curseur.getString(indexDateRealisation).substring(3,5);
            String annee = curseur.getString(indexDateRealisation).substring(6,10);

            String heure = curseur.getString(indexHeure).substring(0,2);
            String minute = curseur.getString(indexHeure).substring(3,5);

            //Log.d("DATE", Integer.parseInt(jour) + "   " + Integer.parseInt(mois) + "   " + Integer.parseInt(annee));
            //Log.d("DATE", Integer.parseInt(heure) + "   " + Integer.parseInt(minute));

            int calculmois = Integer.parseInt(mois) - 1;

            Calendar temps = Calendar.getInstance();
            temps.setTimeInMillis(System.currentTimeMillis());
            temps.clear();
            temps.set(Integer.parseInt(annee),calculmois,Integer.parseInt(jour),Integer.parseInt(heure),Integer.parseInt(minute));

            //Log.d("DATE", temps.toString());

            Intent intentAlarm = new Intent(context, VueAlarme.class);
            intentAlarm.putExtra("id_todo", id);
            PendingIntent pending = PendingIntent.getActivity(context,Integer.parseInt(id), intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, temps.getTimeInMillis(),pending);
        }
    }
    public void mettreAlarm()
    {
        String LISTER_TODO = "SELECT * FROM todo WHERE fini = '0'";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_TODO, null);

        int indexId = curseur.getColumnIndex("id_todo");
        int indexDateRealisation = curseur.getColumnIndex("date_de_realisation");
        int indexHeure = curseur.getColumnIndex("heure");

        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext())
        {
            String id = curseur.getString(indexId);
            String jour = curseur.getString(indexDateRealisation).substring(0,2);
            String mois = curseur.getString(indexDateRealisation).substring(3,5);
            String annee = curseur.getString(indexDateRealisation).substring(6,10);

            String heure = curseur.getString(indexHeure).substring(0,2);
            String minute = curseur.getString(indexHeure).substring(3,5);

            //Log.d("DATE", Integer.parseInt(jour) + "   " + Integer.parseInt(mois) + "   " + Integer.parseInt(annee));
            //Log.d("DATE", Integer.parseInt(heure) + "   " + Integer.parseInt(minute));

            int calculmois = Integer.parseInt(mois) - 1;

            Calendar temps = Calendar.getInstance();
            temps.setTimeInMillis(System.currentTimeMillis());
            temps.clear();
            temps.set(Integer.parseInt(annee),calculmois,Integer.parseInt(jour),Integer.parseInt(heure),Integer.parseInt(minute));

            //Log.d("DATE", temps.toString());

            Intent intentAlarm = new Intent(context, VueAlarme.class);
            intentAlarm.putExtra("id_todo", id);
            PendingIntent pending = PendingIntent.getActivity(context,Integer.parseInt(id), intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, temps.getTimeInMillis(),pending);
        }
    }
    public void modifierAlarm(int id_todo_modif)
    {
        String LISTER_TODO = "SELECT * FROM todo WHERE fini = '0' AND id_todo = '" + id_todo_modif + "'";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_TODO, null);

        int indexId = curseur.getColumnIndex("id_todo");
        int indexDateRealisation = curseur.getColumnIndex("date_de_realisation");
        int indexHeure = curseur.getColumnIndex("heure");

        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext())
        {
            String id = curseur.getString(indexId);
            String jour = curseur.getString(indexDateRealisation).substring(0,2);
            String mois = curseur.getString(indexDateRealisation).substring(3,5);
            String annee = curseur.getString(indexDateRealisation).substring(6,10);

            String heure = curseur.getString(indexHeure).substring(0,2);
            String minute = curseur.getString(indexHeure).substring(3,5);

            //Log.d("DATE", Integer.parseInt(jour) + "   " + Integer.parseInt(mois) + "   " + Integer.parseInt(annee));
            //Log.d("DATE", Integer.parseInt(heure) + "   " + Integer.parseInt(minute));

            int calculmois = Integer.parseInt(mois) - 1;

            Calendar temps = Calendar.getInstance();
            temps.setTimeInMillis(System.currentTimeMillis());
            temps.clear();
            temps.set(Integer.parseInt(annee),calculmois,Integer.parseInt(jour),Integer.parseInt(heure),Integer.parseInt(minute));

            //Log.d("DATE", temps.toString());

            Intent intentAlarm = new Intent(context, VueAlarme.class);
            intentAlarm.putExtra("id_todo", id);
            PendingIntent pending = PendingIntent.getActivity(context,Integer.parseInt(id), intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, temps.getTimeInMillis(),pending);
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
        curseur.close();
        return listeTODO;
    }

}
