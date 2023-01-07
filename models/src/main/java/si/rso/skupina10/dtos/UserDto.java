package si.rso.skupina10.dtos;

public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private Integer role;
    private Float lat;
    private Float lng;

    public Integer getId() {return id; }

    public void setId(Integer userId) {this.id = userId;}

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

    public String toString(){ return "User id: "+ id; }
}
