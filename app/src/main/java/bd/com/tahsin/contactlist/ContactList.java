package bd.com.tahsin.contactlist;

/**
 * Created by Tahsin on 3/30/2017.
 */
public class ContactList {
    private int id;
    private String name;
    private String number;

    public ContactList(String name, String number) {

        this.name = name;
        this.number = number;
    }

    //Setter

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setnumber(String number) {
        this.number = number;
    }


    //Getter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}