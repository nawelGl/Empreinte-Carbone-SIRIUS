package edu.ezip.ing1.pds.business.dto;




import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Users {


    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public final Users add(final User user) {
        users.add(user);
        return this;
    }

    @Override
    public String toString() {
        return "Users{" +
                "users=" + users +
                '}';
    }
}