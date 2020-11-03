package edu.unicauca.main.session;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.auth.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.UserModel;

public  class SimpleSessionManager {
    /* Esta clase obtiene el usuario de la base de datos que tenga el campo is_authenticated en memoria */
    private static UserModel user;


    private static void loadUser() {
       //TODO Select user from database local
        Map<String,Object> fitlerFields = new HashMap<>();
        fitlerFields.put("isAuthenticated",true);
        user = new UserModel(null);
        List<UserModel> users  =user.getManager().filter(fitlerFields, ModelManager.LOCAL_MODE);

        if(!users.isEmpty())
            user.authenticate();//si el usuario no esta autenticado devuelve un usario on el atributo is_authenticated = false;
    }

    public static  UserModel getLoginUser() {
       if(user==null)
           loadUser();
       return user;
    }
    public  static  boolean login(String username,String password){
        Map<String,Object> fitlerFields = new HashMap<>();
        fitlerFields.put("username",username);
        fitlerFields.put("username",password);
        user = new UserModel(username,password);
        List<UserModel> users  =user.getManager().filter(fitlerFields, ModelManager.REMOTE_MODE);
        if(users.isEmpty())
            return false;
        user = users.get(0);
        user.saveLocal();
        user.authenticate();
        return true;
    }
    public  static  void logout(){
        if(user != null)
            user.unauthenticate();
    }
}
