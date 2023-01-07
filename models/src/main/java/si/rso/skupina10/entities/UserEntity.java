package si.rso.skupina10.entities;

import javax.persistence.*;

@Entity(name = "userentitiy")
@NamedQueries(value = {
        @NamedQuery(name = "User.getAll", query = "SELECT u FROM userentitiy u"),
        @NamedQuery(name = "User.getUserById", query = "SELECT u FROM userentitiy u WHERE u.userId = :userId")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;

    private String password;

    private Integer role; // 0 if user, 1 if delivery

    private Float lat;
    private Float lng;

    public Integer getUserId() {return userId; }

    public void setUserId(Integer userId) {this.userId = userId;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password){this.password = password;}

    public Integer getRole(){return role;}
    public void setRole(Integer role){this.role = role;}

    public Float getLat(){return lat;}
    public void setLat(Float lat){this.lat = lat;}

    public Float getLng(){return lng;}
    public void setLng(Float lng){this.lng = lng;}
}
