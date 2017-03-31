package bd.com.tahsin.contactlist;

/**
 * Created by Tahsin on 3/30/2017.
 */
public class User {

    private String number;
    private String name;

    public User(){
      //  this.number = number;
      //  this.name = name;
    }

    //Setter
    
    public void setNumber(String number) {
        this.number = number;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Getter

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}
