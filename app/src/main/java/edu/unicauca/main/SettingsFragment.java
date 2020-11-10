package edu.unicauca.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements Observer {
    private static final String CHANNEL_ID ="NOTIFICACION" ;
      private TaskModel taskModel;
    private UserModel userModel;
    private Button btnManage;
    private Button btnSychonize;
    private Button btnNotifications;
    private Button btnLogout;
    private Button btnHelp;
    private TextView infouser;
    private PendingIntent pendingIntent;
    private final static int NOTIFICACION_ID=0;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }
    public static  SettingsFragment newInstance(UserModel us){
        SettingsFragment fragment = new SettingsFragment ();
        fragment.userModel=us;
        us.getManager().addObserver(fragment);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista = inflater.inflate (R.layout.fragment_settings, container, false);
        btnManage = vista.findViewById (R.id.buttonManage);
        btnSychonize = vista.findViewById (R.id.buttonSynchronize);
        btnNotifications = vista.findViewById (R.id.buttonNotify);
        btnLogout= vista.findViewById (R.id.buttonLogout);
        btnHelp = vista.findViewById (R.id.buttonHelp);
        btnSychonize.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                userModel = SimpleSessionManager.getLoginUser();
                if(!userModel.isAuthenticated()){
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                }else {
                    Toast.makeText (getContext (), "Tiene un usuario activo", Toast.LENGTH_LONG).show ();
                }
            }
        });
        btnNotifications.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                createNotification();
                createNotificationChannel();
            }
        });
        btnLogout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                userModel = SimpleSessionManager.getLoginUser();
                if(userModel.isAuthenticated()){
                    SimpleSessionManager.logout();
                    Toast.makeText (getContext (),"Sincronización local." ,Toast.LENGTH_LONG).show ();
                    Intent userMenu = new Intent(getActivity(), MenuActivity.class);
                    startActivity(userMenu);

                }

            }
        });
        btnManage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                userModel = SimpleSessionManager.getLoginUser();
                if(!userModel.isAuthenticated()){
                    Intent manage = new Intent(getActivity(), UserActivity.class);
                    startActivity(manage);
                }else{
                    Toast.makeText (getContext (),"Ya hay un usuario activo." ,Toast.LENGTH_LONG).show ();
                }
            }
        });
        infouser = vista.findViewById(R.id.usuarioNombre);
        userModel = SimpleSessionManager.getLoginUser();
        if(userModel.isAuthenticated()){
           infouser.setText (userModel.getName());
        }

        return vista;
    }

    private void createNotification(){
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(getContext (), MenuActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext ());
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext (), CHANNEL_ID)
                .setSmallIcon(R.drawable.icono)
                .setContentTitle("Do-things")
                .setContentText("Tienes tareas pendientes")
                .setColor(Color.BLUE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Tienes tareas pendientes..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationCompatManager = NotificationManagerCompat.from (getContext ());
        notificationCompatManager.notify (NOTIFICACION_ID, builder.build ());
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificación";
            String description = "Tareas pendientes para hoy";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel (CHANNEL_ID, name, importance);
            channel.setDescription (description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getContext ().getSystemService (NotificationManager.class);
            notificationManager.createNotificationChannel (channel);
        }
    }
    @Override
    public void notify(Object observed) {

    }
}