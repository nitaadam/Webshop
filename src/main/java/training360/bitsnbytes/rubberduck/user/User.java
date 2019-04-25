package training360.bitsnbytes.rubberduck.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {

    private long id;
    private String name;
    private String userName;
    private String password;
    private int enabled = 1;
    private UserRole userRole;


    public User(long id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

    public User(){
    }

    public User(long id, String name, String userName, String password, int enabled, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getEnabled() {
        return enabled;
    }

}
