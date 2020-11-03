package edu.unicauca.main.session;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;
import com.mongodb.client.result.DeleteResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;

public  class SimpleSessionManager {
    /* Esta clase obtiene el usuario de la base de datos que tenga el campo is_authenticated en memoria */
    private static UserModel user;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static String email,password;
    private  static  Context  c;
    public static void init(Context pc){
        c = pc;
        if(user==null) user = new UserModel(c);
        loadUser();
       login();

    }
    private static void login(){

        ModelManager manager = user.getManager();
        manager.addObserver(new Observer() {
            @Override
            public void notify(Object observed) {
                if(email == null || password==null ||( user != null &&  user.isAuthenticated())){
                    return;
                }
                Map<String,Object> fitlerFields = new HashMap<>();
                fitlerFields.put("email",email);
                fitlerFields.put("password",password);

                List<UserModel> users  =user.getManager().filter(fitlerFields, ModelManager.REMOTE_MODE);

                if(!users.isEmpty()) {
                    user = users.get(0);
                    user.authenticate();//si el usuario no esta autenticado devuelve un usario on el atributo is_authenticated = false;
                    user.setUuid(user.getKey());
                    user.authenticate();
                    user.saveLocal(false);

                    try{
                        sync();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        manager.link(ModelManager.REMOTE_MODE);

    }
    private static void loadUser() {
       //TODO Select user from database local
        Map<String,Object> fitlerFields = new HashMap<>();
        fitlerFields.put("isAuthenticated",1);
        user = new UserModel(null);
        List<UserModel> users  =user.getManager().filter(fitlerFields, ModelManager.LOCAL_MODE);

        if(!users.isEmpty()) {
            user = users.get(0);
            user.authenticate();//si el usuario no esta autenticado devuelve un usario on el atributo is_authenticated = false;
        }
    }

    public static  UserModel getLoginUser() {
       if(user==null)
           loadUser();
       return user;
    }
    public  static  void createUser(final String name, final String lastname, final String pemail, final String ppassword, final Observer observer){

        firebaseAuth.createUserWithEmailAndPassword(pemail,ppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                boolean successfull = task.isSuccessful();
                if(successfull) {
                    UserModel user = new UserModel(name, lastname, pemail, ppassword);
                    user.setUuid(firebaseAuth.getCurrentUser().getUid());
                    user.setKey(firebaseAuth.getCurrentUser().getUid());
                    email =pemail;
                    password = ppassword;
                    user.save();
                    login(pemail,ppassword,false,observer);
                }else {
                    observer.notify(successfull);
                }
            }
        });
    }
    public  static  void login(final String pemail, final String ppassword, final boolean save,final Observer o){
        email =pemail;
        password = ppassword;
        if(user != null && user.isAuthenticated()){
            o.notify(false);
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(pemail,ppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(save) {
                        user.setKey(firebaseAuth.getCurrentUser().getUid());
                        user.setUuid(firebaseAuth.getCurrentUser().getUid());

                        user.save();
                        //user.saveLocal(false);
                    }

                }
                o.notify(task.isSuccessful());
            }
        });

    }
    public  static  void logout(){
        if(user != null) {
            user.unauthenticate();
            firebaseAuth.signOut();

        }
    }
    private static void sync(){
        //enviar la info de la base local a la remota

        TaskModel t = new TaskModel(c);
        t.getManager().link(ModelManager.LOCAL_MODE);
        List<TaskModel> all =new ArrayList<>( t.getManager().getAll());
        for(TaskModel task : all){
            if(!task.isSynchronized()) {
                task.setSync(1);
                task.saveLocal();
                task.saveRemote(firebaseAuth.getCurrentUser().getUid());
            }
        }

    }
}
