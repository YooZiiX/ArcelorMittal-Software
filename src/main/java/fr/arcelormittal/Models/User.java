package fr.arcelormittal.Models;

public class User {

    private int id, token;
    private String name, email, hPassword;
    private String role;

    public User(int id, String name, String email, String hPassword, int roleToken){
        this.id = id;
        this.name = name;
        this.email = email;
        this.hPassword = hPassword;
        if (roleToken == 1) this.role = "Administrateur";
        if (roleToken == 2) this.role = "Worker";
        if (roleToken == 3) this.role = "Process Engineer";
    }

    @Override
    public String toString() {
        return "User : {ID : " + id +
                " | Name : " + name +
                " | E-Mail : " + email +
                " | Password : " + hPassword +
                " | Rôle : " + role + " }";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String gethPassword() {
        return hPassword;
    }

    public void sethPassword(String hPassword) {
        this.hPassword = hPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }
}
