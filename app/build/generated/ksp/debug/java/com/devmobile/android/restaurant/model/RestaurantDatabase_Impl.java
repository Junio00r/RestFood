package com.devmobile.android.restaurant.model;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.devmobile.android.restaurant.model.repository.RestaurantDatabase;
import com.devmobile.android.restaurant.model.repository.dao.IFoodDao;
import com.devmobile.android.restaurant.viewmodel.dao.IFoodDao_Impl;
import com.devmobile.android.restaurant.model.repository.dao.IUserDao;
import com.devmobile.android.restaurant.viewmodel.dao.IUserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RestaurantDatabase_Impl extends RestaurantDatabase {
  private volatile IFoodDao _iFoodDao;

  private volatile IUserDao _iUserDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(17) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `foods` (`mId` INTEGER NOT NULL, `name` TEXT NOT NULL, `foodPrice` REAL NOT NULL, `section` TEXT NOT NULL, `imageId` INTEGER NOT NULL, `timeIconId` INTEGER NOT NULL, `timeToPrepare` TEXT NOT NULL, `description` TEXT, PRIMARY KEY(`mId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd4b8ee17bcee1728ef34c2d7636bfc21')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `foods`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsFoods = new HashMap<String, TableInfo.Column>(8);
        _columnsFoods.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("foodPrice", new TableInfo.Column("foodPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("section", new TableInfo.Column("section", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("imageId", new TableInfo.Column("imageId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("timeIconId", new TableInfo.Column("timeIconId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("timeToPrepare", new TableInfo.Column("timeToPrepare", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoods.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFoods = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFoods = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFoods = new TableInfo("foods", _columnsFoods, _foreignKeysFoods, _indicesFoods);
        final TableInfo _existingFoods = TableInfo.read(db, "foods");
        if (!_infoFoods.equals(_existingFoods)) {
          return new RoomOpenHelper.ValidationResult(false, "foods(com.devmobile.android.restaurant.model.Food).\n"
                  + " Expected:\n" + _infoFoods + "\n"
                  + " Found:\n" + _existingFoods);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(2);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.devmobile.android.restaurant.model.repository.localdata.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d4b8ee17bcee1728ef34c2d7636bfc21", "e20a52e213a4b7cd59e45308f2230275");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "foods","users");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `foods`");
      _db.execSQL("DELETE FROM `users`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(IFoodDao.class, IFoodDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(IUserDao.class, IUserDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public IFoodDao getFoodDao() {
    if (_iFoodDao != null) {
      return _iFoodDao;
    } else {
      synchronized(this) {
        if(_iFoodDao == null) {
          _iFoodDao = new IFoodDao_Impl(this);
        }
        return _iFoodDao;
      }
    }
  }

  @Override
  public IUserDao getUserDao() {
    if (_iUserDao != null) {
      return _iUserDao;
    } else {
      synchronized(this) {
        if(_iUserDao == null) {
          _iUserDao = new IUserDao_Impl(this);
        }
        return _iUserDao;
      }
    }
  }
}
