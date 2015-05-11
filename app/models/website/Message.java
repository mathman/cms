package models.website;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.format.Formats;
import play.db.ebean.*;

/**
 * Created by mathman on 12/02/15.
 */

@Entity
public class Message extends Model {

    @Id
    public Integer id;

    public String title;
    public String content;
    public Integer author;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date timestamp;

    public Message(String title, String content, Integer author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = new Date();
    }

    public static Finder<Long,Message> find = new Finder<Long,Message>(
            Long.class, Message.class
    );

    public static List<Message> page(int page, int pageSize, String sortBy, String order) {
        return
                find.where()
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page)
                        .getList();
    }
}
