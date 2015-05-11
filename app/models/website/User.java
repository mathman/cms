package models.website;

import javax.persistence.*;

import models.utils.Hash;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.*;

import java.util.Date;
import java.util.List;

/**
 * Created by mathman on 11/02/15.
 */

@Entity
public class User extends Model {

    @Id
    public Integer id;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;

    @Constraints.Required
    @Formats.NonEmpty
    public String name;

    @Constraints.Required
    @Formats.NonEmpty
    public String password;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date subscription_date;

    public int access;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.subscription_date = new Date();
        this.access = 0;
    }

    public static Finder<Long,User> find = new Finder<Long,User>(
            Long.class, User.class
    );

    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static User authenticate(String email, String clearPassword) throws Exception {

        // get the user with email only to keep the salt password
        User user = find.where().eq("email", email).findUnique();
        if (user != null) {
            try {
                // get the hash password from the salt + clear password
                if (Hash.checkPassword(clearPassword, user.password)) {
                    return user;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static List<User> page(int page, int pageSize, String sortBy, String order) {
        return
                find.where()
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page)
                        .getList();
    }
}
