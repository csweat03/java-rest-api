package me.christian.application.implementation;

import com.mongodb.client.model.Filters;
import me.christian.App;
import me.christian.application.Application;
import me.christian.utility.CryptographyUtility;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.LinkedHashMap;
import java.util.Objects;

public class UserApplication extends Application {

    public UserApplication() {
        super("User Application");
    }

    private final class User {
        private final String username, email, pin, password;
        private RoleType roleType;
        private final String apiKey;

        public User(String apiKey) {
            User temporaryUserObject = findUser(apiKey);
            this.username = temporaryUserObject.username;
            this.email = temporaryUserObject.email;
            this.pin = temporaryUserObject.pin;
            this.password = temporaryUserObject.password;
            this.roleType = temporaryUserObject.roleType;
            this.apiKey = temporaryUserObject.apiKey;
        }

        /**
         * This constructor should only be used internally, all other cases should use the User(apiKey) constructor or the findUser() function.
         * This will invoke the generateNewApiKey() function from the {@link CryptographyUtility}. This returns a cryptographically secure 64-char string.
         * This will create a LinkedHashMap with all the generated data, and add this into the MongoDB database as a new Document.
         * This will invoke the insertOne(document) function from the Collection 'users' under the Database 'userdb'
         * TODO: Create a filter to ensure validity of provided username, email, pin, and password parameters.
         *
         * @param username  Provided, any String is valid.
         * @param email     Provided, any String is valid.
         * @param pin       Provided, any String is valid.
         * @param password  Provided, any String is valid.
         */
        private User(String username, String email, String pin, String password) {
            this.username = CryptographyUtility.Base64.encrypt(username);
            this.email = CryptographyUtility.Base64.encrypt(email);
            this.pin = CryptographyUtility.Base64.encrypt(pin);
            this.password = CryptographyUtility.encryptionMethod1(this.username, this.email, this.pin).encrypt(password);
            this.roleType = RoleType.USER;
            this.apiKey = CryptographyUtility.generateNewApiKey();

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();

            data.put("username", this.username);
            data.put("email", this.email);
            data.put("pin", this.pin);
            data.put("password", this.password);
            data.put("roleType", this.roleType);
            data.put("apiKey", this.apiKey);

            App.getUserDatabase().getCollection("users").insertOne(new Document(data));
        }

        private User(String username, String email, String pin, String password, RoleType roleType, String apiKey) {
            this.username = username;
            this.email = email;
            this.pin = pin;
            this.password = password;
            this.roleType = roleType;
            this.apiKey = apiKey;
        }

        private void promoteUser(User elevatedUser) {
            if (elevatedUser.roleType != RoleType.ADMINISTRATOR) {
                System.err.println("\nWARNING: Provided user does not appear to be an administrator.\n");
                return;
            }
            if (this.roleType == RoleType.ADMINISTRATOR) {
                System.err.println("\nWARNING: " + username + " already appears to be an administrator.\n");
                return;
            }
            this.roleType = RoleType.ADMINISTRATOR;
            System.out.println(this.username + " has been successfully promoted.");
        }

        private enum RoleType {
            USER, ADMINISTRATOR;

            public String toString() {
                return this.name().toLowerCase();
            }
        }
    }

    protected final User findUser(String username, String email, String pin, String password) {
        username = CryptographyUtility.Base64.encrypt(username);
        email = CryptographyUtility.Base64.encrypt(email);
        pin = CryptographyUtility.Base64.encrypt(pin);

        CryptographyUtility.AES256 cryptographyUtility = CryptographyUtility.encryptionMethod1(username, email, pin);

        password = cryptographyUtility.encrypt(password);

        Bson usernameFilter = Filters.eq("username", username);
        Bson emailFilter = Filters.eq("email", email);
        Bson pinFilter = Filters.eq("pin", pin);
        Bson passwordFilter = Filters.eq("password", password);

        return App.getGson().fromJson(Objects.requireNonNull(App.getUserDatabase().getCollection("users").find()
                .filter(usernameFilter).filter(emailFilter).filter(pinFilter).filter(passwordFilter)
                .first()).toJson(), User.class);
    }

    protected final User findUser(String apiKey) {
        Bson apiKeyFilter = Filters.eq("apiKey", apiKey);

        return App.getGson().fromJson(Objects.requireNonNull(App.getUserDatabase().getCollection("users").find()
                .filter(apiKeyFilter)
                .first()).toJson(), User.class);
    }

    protected final User createUser(String username, String email, String password, String pin) {
        return new User(username, email, password, pin);
    }


}
