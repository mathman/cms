package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.Auth.*;
import models.Characters.Character;
import models.website.*;

import play.Play;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.List;

import play.data.Form;

public class Application extends Controller {

    public static Result index() {
        User user = null;
        String email = ctx().session().get("email");
        if (email != null)
            user = User.findByEmail(email);
        List<Message> messages = Message.find.where()
                .orderBy("timestamp")
                .setMaxRows(3).findList();
        return ok(index.render("cms by mathman", Form.form(Login.class), user, messages));
    }

    /**
     * Login class used by Login Form.
     */
    public static class Login {

        @Constraints.Required
        public String email;
        @Constraints.Required
        public String password;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {

            User user = null;
            try {
                user = User.authenticate(email, password);
            } catch (Exception e) {
                return Messages.get("error.technical");
            }
            if (user == null) {
                return Messages.get("invalid.user.or.password");
            }
            return null;
        }
    }

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

        List<Message> messages = Message.find.where()
                .orderBy("timestamp")
                .setMaxRows(3).findList();
        if (loginForm.hasErrors()) {
            return badRequest(index.render("cms by mathman", loginForm, null, messages));
        } else {
            session("email", loginForm.get().email);
            return redirect("/");
        }
    }

    public static Result disconnect() {
        session().clear();
        return redirect("/");
    }
    public static Result status() {

        List<String> data = new ArrayList<>();
        List<Realm> realms = Realm.find.all();
        for (Realm realm : realms)
        {
            if (realm.isOnline()) {
                data.add("online");

                EbeanServer characters = Ebean.getServer(Play.application().configuration().getString(realm.name));
                Integer ally = characters.find(Character.class).where("online = 1 AND race = 1 OR race = 3 OR race = 4 OR race = 7 OR race = 11").findRowCount();
                Integer horde = characters.find(Character.class).where("online = 1 AND race = 2 OR race = 5 OR race = 6 OR race = 8 OR race = 10").findRowCount();

                Integer total = ally + horde;
                data.add(String.valueOf(total));
                Integer allypct = 0;
                Integer hordepct = 0;
                if (total != 0) {
                    allypct = Math.round((ally * 100) / total);
                    hordepct = Math.round((horde * 100) / total);
                }
                data.add(String.valueOf(allypct));
                data.add(String.valueOf(hordepct));

            }
            else {
                data.add("offline");
                data.add("0");
                data.add("0");
                data.add("0");
            }
        }

        return ok(Json.toJson(data));
    }

    public static Result realms() {
        List<Realm> realms = Realm.find.all();
        return ok(Json.toJson(realms));
    }
}
