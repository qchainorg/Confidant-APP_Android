package com.stratagile.pnrouter.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECENT_FILE".
*/
public class RecentFileDao extends AbstractDao<RecentFile, Long> {

    public static final String TABLENAME = "RECENT_FILE";

    /**
     * Properties of entity RecentFile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TimeStamp = new Property(1, long.class, "timeStamp", false, "TIME_STAMP");
        public final static Property FileName = new Property(2, String.class, "fileName", false, "FILE_NAME");
        public final static Property FileType = new Property(3, int.class, "fileType", false, "FILE_TYPE");
        public final static Property OpreateType = new Property(4, int.class, "opreateType", false, "OPREATE_TYPE");
        public final static Property FriendName = new Property(5, String.class, "friendName", false, "FRIEND_NAME");
    }


    public RecentFileDao(DaoConfig config) {
        super(config);
    }
    
    public RecentFileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECENT_FILE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TIME_STAMP\" INTEGER NOT NULL ," + // 1: timeStamp
                "\"FILE_NAME\" TEXT," + // 2: fileName
                "\"FILE_TYPE\" INTEGER NOT NULL ," + // 3: fileType
                "\"OPREATE_TYPE\" INTEGER NOT NULL ," + // 4: opreateType
                "\"FRIEND_NAME\" TEXT);"); // 5: friendName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECENT_FILE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RecentFile entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTimeStamp());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(3, fileName);
        }
        stmt.bindLong(4, entity.getFileType());
        stmt.bindLong(5, entity.getOpreateType());
 
        String friendName = entity.getFriendName();
        if (friendName != null) {
            stmt.bindString(6, friendName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RecentFile entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTimeStamp());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(3, fileName);
        }
        stmt.bindLong(4, entity.getFileType());
        stmt.bindLong(5, entity.getOpreateType());
 
        String friendName = entity.getFriendName();
        if (friendName != null) {
            stmt.bindString(6, friendName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public RecentFile readEntity(Cursor cursor, int offset) {
        RecentFile entity = new RecentFile( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // timeStamp
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fileName
            cursor.getInt(offset + 3), // fileType
            cursor.getInt(offset + 4), // opreateType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // friendName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RecentFile entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTimeStamp(cursor.getLong(offset + 1));
        entity.setFileName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFileType(cursor.getInt(offset + 3));
        entity.setOpreateType(cursor.getInt(offset + 4));
        entity.setFriendName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RecentFile entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RecentFile entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RecentFile entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
