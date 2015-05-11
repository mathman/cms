package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Auth.Realm;
import models.Characters.Character;

import models.website.User;
import play.Play;
import play.libs.Json;
import play.mvc.*;

import views.html.onlines;

import java.util.List;
import java.util.Map;

import play.data.Form;

public class Onlines extends Controller {

    public static Result index() {
        User user = null;
        String email = ctx().session().get("email");
        if (email != null)
            user = User.findByEmail(email);

        List<Realm> realms = Realm.find.all();

        return ok(onlines.render("Onlines Players", Form.form(Application.Login.class), user, realms));
    }

    public static Result list(String name) {
        /**
         * Get needed params
         */
        Map<String, String[]> params = request().queryString();

        EbeanServer charactersServer = Ebean.getServer(Play.application().configuration().getString(name));
        Integer iTotalRecords = charactersServer.find(Character.class).findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "name";
        String order = params.get("sSortDir_0")[0];

        switch(Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0 : sortBy = "name"; break;
            case 1 : sortBy = "level"; break;
            case 2 : sortBy = "race"; break;
            case 3 : sortBy = "class"; break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<Character> charactersPage = charactersServer.find(Character.class).where(
                Expr.ilike("name", "%" + filter + "%")
        )
                .where("online = 1")
                .orderBy(sortBy + " " + order + ", guid " + order)
                .findPagingList(pageSize).setFetchAhead(false)
                .getPage(page);

        Integer iTotalDisplayRecords = charactersPage.getTotalRowCount();

        /**
         * Construct the JSON to return
         */
        ObjectNode result = Json.newObject();

        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode an = result.putArray("aaData");

        for(Character c : charactersPage.getList()) {
            ObjectNode row = Json.newObject();
            row.put("0", c.name);
            row.put("1", c.level);
            row.put("2", c.race + "-" + c.gender);
            row.put("3", c.Class);
            an.add(row);
        }

        return ok(result);
    }
}
