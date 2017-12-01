package main.java.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity User
 *
 * @author : Gilles Andrieu [Atecna](http://www.atecna.fr/)
 * @version : 1.0
 */

@XmlRootElement
public class Users {
    // Properties
    private String users_id;
    private String login;
    private String password;

    // Constructor
    public Users() { }

    public Users(String users_id, String login, String password){
        this.users_id = users_id;
        this.login = login;
        this.password = password;
    }

    // Getters / Setters
    public Users(String login){ this.login = login; }

    public String getUsers_id() { return users_id; }

    public void setUsers_id(String users_id) { this.users_id = users_id; }

    public String getLogin() { return login; }

    public void setLogin(String nom) { this.login = nom; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Users{" +
                "users_id='" + users_id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
