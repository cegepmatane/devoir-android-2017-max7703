package ca.qc.cgmatane.informatique.applicationtodo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import ca.qc.cgmatane.informatique.applicationtodo.donnees.todoDAO;
import ca.qc.cgmatane.informatique.applicationtodo.modele.todo;

public class VueAlarme extends AppCompatActivity {
    private MediaPlayer player;
    protected todoDAO accesseurTODO = todoDAO.getInstance();
    private todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_alarme);

        Bundle parametres = this.getIntent().getExtras();
        final String parametre_id_todo = (String) parametres.get("id_todo");
        final int id_todo = Integer.parseInt(parametre_id_todo);

        TextView nom_todo = (TextView) findViewById(R.id.nom_todo);
        Button stop = (Button) findViewById(R.id.action_stop_alarme);
        Button modifier = (Button) findViewById(R.id.action_modifier_todo);
        Button todo_fait = (Button) findViewById(R.id.action_todo_fait);

        todo = accesseurTODO.trouverTODO(id_todo);
        nom_todo.setText(todo.getTitre());

        stop.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                player.stop();
                naviguerRetourTODO();
            }
        });
        modifier.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                player.stop();
                Intent intentionNaviguerModifierTODO = new Intent(
                        VueAlarme.this, VueModifierTODO.class
                );
                intentionNaviguerModifierTODO.putExtra("id_todo", parametre_id_todo);
                startActivity(intentionNaviguerModifierTODO);
                naviguerRetourTODO();
            }
        });
        todo_fait.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                player.stop();
                accesseurTODO.todoTermine(parametre_id_todo);
                naviguerRetourTODO();
            }
        });
        play(this, getAlarmSound());
    }
    private void play(Context context, Uri alert) {

        player = new MediaPlayer();
        try {
            player.setDataSource(context, alert);
            final AudioManager audio = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audio.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                player.setAudioStreamType(AudioManager.STREAM_ALARM);
                player.prepare();
                player.start();
            }
        } catch (IOException e) {
        }
    }

    private Uri getAlarmSound() {

        Uri alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alertSound == null) {
            alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alertSound == null) {
                alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alertSound;
    }
    public void naviguerRetourTODO(){
        this.finish();
    }
}
