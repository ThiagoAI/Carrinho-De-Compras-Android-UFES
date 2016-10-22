import android.provider.BaseColumns;

/**
 * Created by thiago on 22/10/16.
 */
public class ProductContract {

    public ProductContract() {}

    public static abstract class Product implements BaseColumns{
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
    }
}
