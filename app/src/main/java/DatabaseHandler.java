
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


    public class DatabaseHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "usersManager";
        private static final String TABLE_USERS = "users";
        private static final String KEY_ID = "id";
        private static final String KEY_USERNAME = "username";
        private static final String KEY_EMAIL = "email";
        private static final String KEY_PASSWORD = "password";
        private static final String KEY_ISADMIN = "isadmin";


        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //3rd argument to be passed is CursorFactory instance
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = " CREATE TABLE " + TABLE_USERS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                    + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT,"
                    + KEY_ISADMIN + " TEXT" + ")";
            db.execSQL(CREATE_USERS_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

            // Create tables again
            onCreate(db);
        }

        // code to add the new contact
        void addUser(users Users) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, Users.getName()); // Contact Name
            values.put(KEY_EMAIL, Users.getEmail());
            values.put(KEY_PASSWORD, Users.getPassword());
            values.put(KEY_ISADMIN, Users.getStatus());

            // Inserting Row
            db.insert(TABLE_USERS, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
        }

        // code to get the single contact
        users getUsers(int id) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                            KEY_USERNAME, KEY_EMAIL,KEY_PASSWORD,KEY_ISADMIN}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            users contact = new users(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3),Boolean.parseBoolean(cursor.getString(4)));
            // return contact
            return contact;
        }

        // code to get all contacts in a list view
        public List<users> getAllContacts() {
            List<users> contactList = new ArrayList<users>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_USERS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    users contact = new users();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setEmail(cursor.getString(2));
                    contact.setPassword(cursor.getString(3));
                    contact.setStatus(Boolean.parseBoolean(cursor.getString(4)));

                    // Adding contact to list
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            return contactList;
        }

        // code to update the single contact
        public int updateContact(users contact) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, contact.getName());
            values.put(KEY_EMAIL, contact.getEmail());
            values.put(KEY_PASSWORD, contact.getPassword());
            values.put(KEY_ISADMIN, contact.getStatus());


            // updating row
            return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(contact.getID())});
        }

        // Deleting single contact
        public void deleteContact(users contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_USERS, KEY_ID + " = ?",
                    new String[]{String.valueOf(contact.getID())});
            db.close();
        }

        // Getting contacts Count
        public int getContactsCount() {
            String countQuery = "SELECT  * FROM " + TABLE_USERS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }
    }
