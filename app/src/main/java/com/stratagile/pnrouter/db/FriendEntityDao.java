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
 * DAO for table "FRIEND_ENTITY".
*/
public class FriendEntityDao extends AbstractDao<FriendEntity, Long> {

    public static final String TABLENAME = "FRIEND_ENTITY";

    /**
     * Properties of entity FriendEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property FriendId = new Property(2, String.class, "friendId", false, "FRIEND_ID");
        public final static Property FriendLocalStatus = new Property(3, int.class, "friendLocalStatus", false, "FRIEND_LOCAL_STATUS");
        public final static Property AddFromMe = new Property(4, boolean.class, "addFromMe", false, "ADD_FROM_ME");
        public final static Property Timestamp = new Property(5, long.class, "timestamp", false, "TIMESTAMP");
    }


    public FriendEntityDao(DaoConfig config) {
        super(config);
    }
    
    public FriendEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FRIEND_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"FRIEND_ID\" TEXT," + // 2: friendId
                "\"FRIEND_LOCAL_STATUS\" INTEGER NOT NULL ," + // 3: friendLocalStatus
                "\"ADD_FROM_ME\" INTEGER NOT NULL ," + // 4: addFromMe
                "\"TIMESTAMP\" INTEGER NOT NULL );"); // 5: timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FRIEND_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FriendEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String friendId = entity.getFriendId();
        if (friendId != null) {
            stmt.bindString(3, friendId);
        }
        stmt.bindLong(4, entity.getFriendLocalStatus());
        stmt.bindLong(5, entity.getAddFromMe() ? 1L: 0L);
        stmt.bindLong(6, entity.getTimestamp());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FriendEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String friendId = entity.getFriendId();
        if (friendId != null) {
            stmt.bindString(3, friendId);
        }
        stmt.bindLong(4, entity.getFriendLocalStatus());
        stmt.bindLong(5, entity.getAddFromMe() ? 1L: 0L);
        stmt.bindLong(6, entity.getTimestamp());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FriendEntity readEntity(Cursor cursor, int offset) {
        FriendEntity entity = new FriendEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // friendId
            cursor.getInt(offset + 3), // friendLocalStatus
            cursor.getShort(offset + 4) != 0, // addFromMe
            cursor.getLong(offset + 5) // timestamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FriendEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFriendId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFriendLocalStatus(cursor.getInt(offset + 3));
        entity.setAddFromMe(cursor.getShort(offset + 4) != 0);
        entity.setTimestamp(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FriendEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FriendEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FriendEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
