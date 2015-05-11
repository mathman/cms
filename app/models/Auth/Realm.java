package models.Auth;

import javax.persistence.*;

import play.db.ebean.*;

import java.net.Socket;

/**
 * Created by mathman on 12/02/15.
 */

@Entity
@Table(name = "realmlist")
public class Realm extends Model {

    @Id
    public Integer id;

    public String name;
    public String address;
    public Integer port;

    public static Finder<Long,Realm> find = new Finder<Long,Realm>(
            "Auth", Long.class, Realm.class
    );

    public Boolean isOnline() {
        try {
            Socket s = new Socket(this.address, this.port);
            s.close();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
