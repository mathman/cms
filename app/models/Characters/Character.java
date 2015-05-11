package models.Characters;

import javax.persistence.*;
import play.db.ebean.*;

/**
 * Created by mathman on 12/02/15.
 */

@Entity
@Table(name = "characters")
public class Character extends Model {

    @Id
    public Integer guid;

    public String name;
    public Integer online;
    public Integer race;

    @Column(name = "class")
    public Integer Class;

    public Integer level;

    public Integer gender;
}
