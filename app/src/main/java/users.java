

public class users {
    int _id;
    String _username;
    String _email;
    String _password;
    boolean _isAdmin;
    public users(){   }
    public users(int id, String username, String _email,String password,boolean isAdmin){
        this._id = id;
        this._username = username;
        this._email = _email;
        this._password = password;
        this._isAdmin = isAdmin;
    }

    public users(String username, String _email,String password,boolean isAdmin){
        this._username = username;
        this._email = _email;
        this._password = password;
        this._isAdmin = isAdmin;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._username;
    }

    public void setName(String username){
        this._username = username;
    }

    public String getEmail(){
        return this._email;
    }

    public void setEmail(String email){
        this._email = email;
    }


    public String getPassword(){
        return this._password;
    }

    public void setPassword(String password){
        this._password = password;
    }

    public boolean getStatus(){
        return this._isAdmin;
    }

    public void setStatus(boolean isAdmin){
        this._isAdmin = isAdmin;
    }
}
