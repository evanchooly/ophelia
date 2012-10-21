package models;

import com.google.code.morphia.Datastore;
import dao.Finder;
import dao.MongoModel;

public class User extends MongoModel<User> {
    public String name;
    public Boolean admin;

    public User(String name, boolean admin) {
        this.name = name;
        this.admin = admin;
    }

    public static UserFinder find() {
        return new UserFinder();
    }

    public static class UserFinder extends Finder<User> {
        public UserFinder() {
            super(User.class);
        }

        public boolean initialized() {
            return mongo(new Operation<Boolean>() {
                @Override
                public Boolean execute(Datastore ds) {
                    return ds.createQuery(User.class).get() != null;
                }
            });
        }
    }
}
