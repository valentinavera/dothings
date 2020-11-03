package edu.unicauca.main.session;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;
import com.mongodb.client.result.DeleteResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.UserModel;

public  class SimpleSessionManager {
    /* Esta clase obtiene el usuario de la base de datos que tenga el campo is_authenticated en memoria */
    private static UserModel user;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private static void loadUser() {
       //TODO Select user from database local
        Map<String,Object> fitlerFields = new HashMap<>();
        fitlerFields.put("isAuthenticated",true);
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
    public  static  void createUser(final String name, final String lastname, final String email, final String password, final Observer observer){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                boolean successfull = task.isSuccessful();
                if(successfull) {
                    UserModel user = new UserModel(name, lastname, email, password);
                    user.saveLocal();
                    login(email,password,false,observer);
                }else {
                    observer.notify(successfull);
                }
            }
        });
    }
    public  static  void login(final String email, final String password, final boolean save,final Observer o){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = new UserModel(email,password);
                    if(save) user.saveLocal();
                    user.authenticate();
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
}
