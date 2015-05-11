package models.website;

import javax.persistence.*;

import play.data.format.Formats;
import play.db.ebean.*;

import java.util.Date;
import java.util.List;

/**
 * Created by mathman on 12/02/15.
 */

@Entity
public class Comment extends Model {

    @Id
    public Integer id;

    public String content;
    public Integer author;
    public Integer message;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date timestamp;

    public Comment(String content, Integer author, Integer message) {
        this.content = content;
        this.author = author;
        this.message = message;
        this.timestamp = new Date();
    }

    public static Finder<Long,Comment> find = new Finder<Long,Comment>(
            Long.class, Comment.class
    );

    public static List<Comment> page(int page, int pageSize, String sortBy, String order) {
        return
                find.where()
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page)
                        .getList();
    }
}
