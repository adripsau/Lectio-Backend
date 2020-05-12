package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "userlists")
public class UserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long list_id;

    @Column(name = "user_id")
    private long user_id;

    @Size(min = 3)
    @Column(name = "list_name", nullable = false)
    private String list_name;

    @Column(name = "list_description", nullable = true)
    private String list_description;

    public UserList() {
    }

    public UserList(long list_id, long user_id, String list_name, String list_description) {
        this.list_id = list_id;
        this.user_id = user_id;
        this.list_name = list_name;
        this.list_description = list_description;
    }

    public long getList_id() {
        return list_id;
    }

    public void setList_id(long list_id) {
        this.list_id = list_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getList_description() {
        return list_description;
    }

    public void setList_description(String list_description) {
        this.list_description = list_description;
    }
}
