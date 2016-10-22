import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thiago on 22/10/16.
 */
public class ProductDAO {

    SQLiteDatabase db;
    ProductHelper mDbHelper;

    public ProductDAO(Context context){
        mDbHelper = new ProductHelper(context);
    }

    public void open(String mode){
        if(mode.equals("write")) {
            db = mDbHelper.getWritableDatabase();
        }
        else{
            db = mDbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db.close();
    }

    public void putProduct(String name,String price){
        ContentValues values = new ContentValues();
        values.put(ProductContract.Product.COLUMN_NAME, name);
        values.put(ProductContract.Product.COLUMN_PRICE, price);

        long newRowId;
        newRowId = db.insert(
                ProductContract.Product.TABLE_NAME,
                null,
                values);

    }

    public Cursor getProducts(){
        String[] projection = {
                ProductContract.Product._ID,
                ProductContract.Product.COLUMN_NAME,
                ProductContract.Product.COLUMN_PRICE
        };

        //SORTING
        String sortOrder = ProductContract.Product.TABLE_NAME + " ASC";

        Cursor c = db.query(
                ProductContract.Product.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return c;
        }

    public void updateProduct(int rowId,String name, String price){
        ContentValues values = new ContentValues();
        values.put(ProductContract.Product.COLUMN_NAME, name);
        values.put(ProductContract.Product.COLUMN_PRICE, price);

        String selection = ProductContract.Product._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(rowId) };

        int count = db.update(
                ProductContract.Product.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void clean(){
        db.execSQL("delete from " + ProductContract.Product.TABLE_NAME);
    }

    }

