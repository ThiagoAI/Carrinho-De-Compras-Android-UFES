import android.provider.BaseColumns;

/**
 * Created by thiago on 22/10/16.
 */
public class UserContract {

    public UserContract() {}

    public static abstract class User implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_STATE = "state";
    }
}
