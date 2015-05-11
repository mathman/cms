package models.website;

import javax.persistence.*;
import play.db.ebean.*;

/**
 * Created by mathman on 12/02/15.
 */

@Entity
public class Session extends Model {

    @Id
    public Integer id;

    public Integer user;
    public String last_login;
    public String last_ip;

    public Session(String last_login, String last_ip, Integer user) {
        this.last_login = last_login;
        this.last_ip = last_ip;
        this.user = user;
    }

    public static Finder<Long,Session> find = new Finder<Long,Session>(
            Long.class, Session.class
    );
}
