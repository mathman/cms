package controllers;


import models.utils.Hash;
import models.website.User;
import play.Logger;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.*;

import play.data.Form;
import views.html.howtoconnect;
import views.html.register;

public class HowToConnect extends Controller {

    public static Result index() {
        User user = null;
        String email = ctx().session().get("email");
        if (email != null)
            user = User.findByEmail(email);
        return ok(howtoconnect.render("Onlines Players", Form.form(Application.Login.class), user));
    }

    public static class Register {

        @Constraints.Required
        public String email;

        @Constraints.Required
        public String fullname;

        @Constraints.Required
        public String password;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {
            if (isBlank(email)) {
                return "Email is required";
            }

            if (isBlank(fullname)) {
                return "Full name is required";
            }

            if (isBlank(password)) {
                return "Password is required";
            }

            return null;
        }

        private boolean isBlank(String input) {
            return input == null || input.isEmpty() || input.trim().isEmpty();
        }
    }

    public static Result register() {
        User user = null;
        String email = ctx().session().get("email");
        if (email != null)
            user = User.findByEmail(email);
        return ok(register.render("Register", Form.form(Application.Login.class), Form.form(Register.class), user));
    }

    /**
     * Save the new user.
     *
     * @return Successfull page or created form if bad
     */
    public static Result save() {
        Form<Register> registerForm = Form.form(Register.class).bindFromRequest();

        if (registerForm.hasErrors()) {
            return badRequest(register.render("Register", Form.form(Application.Login.class), registerForm, null));
        }

        Register registerResult = registerForm.get();
        Result resultError = checkBeforeSave(registerForm, registerResult.email);

        if (resultError != null) {
            return resultError;
        }

        try {
            User user = new User(registerResult.email, registerResult.fullname, Hash.createPassword(registerResult.email, registerResult.password));
            user.save();

            return redirect("/");
        } catch (Exception e) {
            Logger.error("Signup.save error", e);
            flash("error", Messages.get("error.technical"));
        }
        return badRequest(register.render("Register", Form.form(Application.Login.class), registerForm, null));
    }

    /**
     * Check if the email already exists.
     *
     * @param registerForm User Form submitted
     * @param email email address
     * @return Index if there was a problem, null otherwise
     */
    private static Result checkBeforeSave(Form<Register> registerForm, String email) {
        // Check unique email
        if (User.findByEmail(email) != null) {
            flash("error", Messages.get("error.email.already.exist"));
            return badRequest(register.render("Register", Form.form(Application.Login.class), registerForm, null));
        }

        return null;
    }
}
