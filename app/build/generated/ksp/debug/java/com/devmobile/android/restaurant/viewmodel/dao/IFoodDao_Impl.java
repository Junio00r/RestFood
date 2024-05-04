package com.devmobile.android.restaurant.viewmodel.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.devmobile.android.restaurant.model.Food;
import com.devmobile.android.restaurant.model.repository.dao.IFoodDao;
import com.devmobile.android.restaurant.model.enums.FoodSection;
import com.devmobile.android.restaurant.model.enums.TempoPreparo;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class IFoodDao_Impl implements IFoodDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Food> __insertionAdapterOfFood;

  private final EntityDeletionOrUpdateAdapter<Food> __deletionAdapterOfFood;

  private final EntityDeletionOrUpdateAdapter<Food> __updateAdapterOfFood;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTable;

  public IFoodDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFood = new EntityInsertionAdapter<Food>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `foods` (`mId`,`name`,`foodPrice`,`section`,`imageId`,`timeIconId`,`timeToPrepare`,`description`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Food entity) {
        statement.bindLong(1, entity.getMId());
        statement.bindString(2, entity.getMName());
        statement.bindDouble(3, entity.getMFoodPrice());
        statement.bindString(4, __FoodSection_enumToString(entity.getMSection()));
        statement.bindLong(5, entity.getMImageId());
        statement.bindLong(6, entity.getMTimeIconId());
        statement.bindString(7, __TempoPreparo_enumToString(entity.getMTimeToPrepare()));
        if (entity.getMDescription() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMDescription());
        }
      }
    };
    this.__deletionAdapterOfFood = new EntityDeletionOrUpdateAdapter<Food>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `foods` WHERE `mId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Food entity) {
        statement.bindLong(1, entity.getMId());
      }
    };
    this.__updateAdapterOfFood = new EntityDeletionOrUpdateAdapter<Food>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `foods` SET `mId` = ?,`name` = ?,`foodPrice` = ?,`section` = ?,`imageId` = ?,`timeIconId` = ?,`timeToPrepare` = ?,`description` = ? WHERE `mId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Food entity) {
        statement.bindLong(1, entity.getMId());
        statement.bindString(2, entity.getMName());
        statement.bindDouble(3, entity.getMFoodPrice());
        statement.bindString(4, __FoodSection_enumToString(entity.getMSection()));
        statement.bindLong(5, entity.getMImageId());
        statement.bindLong(6, entity.getMTimeIconId());
        statement.bindString(7, __TempoPreparo_enumToString(entity.getMTimeToPrepare()));
        if (entity.getMDescription() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMDescription());
        }
        statement.bindLong(9, entity.getMId());
      }
    };
    this.__preparedStmtOfDeleteAllTable = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM foods";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<Food> foods) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFood.insert(foods);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll(final List<Food> foods) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFood.handleMultiple(foods);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFood(final List<Food> foods) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFood.handleMultiple(foods);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTable.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllTable.release(_stmt);
    }
  }

  @Override
  public List<Food> getFoodsBySection(final FoodSection foodSection) {
    final String _sql = "SELECT * FROM foods WHERE section = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __FoodSection_enumToString(foodSection));
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "mId");
      final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfMFoodPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "foodPrice");
      final int _cursorIndexOfMSection = CursorUtil.getColumnIndexOrThrow(_cursor, "section");
      final int _cursorIndexOfMImageId = CursorUtil.getColumnIndexOrThrow(_cursor, "imageId");
      final int _cursorIndexOfMTimeIconId = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIconId");
      final int _cursorIndexOfMTimeToPrepare = CursorUtil.getColumnIndexOrThrow(_cursor, "timeToPrepare");
      final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final List<Food> _result = new ArrayList<Food>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Food _item;
        final long _tmpMId;
        _tmpMId = _cursor.getLong(_cursorIndexOfMId);
        final String _tmpMName;
        _tmpMName = _cursor.getString(_cursorIndexOfMName);
        final float _tmpMFoodPrice;
        _tmpMFoodPrice = _cursor.getFloat(_cursorIndexOfMFoodPrice);
        final FoodSection _tmpMSection;
        _tmpMSection = __FoodSection_stringToEnum(_cursor.getString(_cursorIndexOfMSection));
        final int _tmpMImageId;
        _tmpMImageId = _cursor.getInt(_cursorIndexOfMImageId);
        final int _tmpMTimeIconId;
        _tmpMTimeIconId = _cursor.getInt(_cursorIndexOfMTimeIconId);
        final TempoPreparo _tmpMTimeToPrepare;
        _tmpMTimeToPrepare = __TempoPreparo_stringToEnum(_cursor.getString(_cursorIndexOfMTimeToPrepare));
        final String _tmpMDescription;
        if (_cursor.isNull(_cursorIndexOfMDescription)) {
          _tmpMDescription = null;
        } else {
          _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
        }
        _item = new Food(_tmpMId,_tmpMName,_tmpMFoodPrice,_tmpMSection,_tmpMImageId,_tmpMTimeIconId,_tmpMTimeToPrepare,_tmpMDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Food getFoodById(final long foodId) {
    final String _sql = "SELECT * FROM foods WHERE mId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, foodId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "mId");
      final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfMFoodPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "foodPrice");
      final int _cursorIndexOfMSection = CursorUtil.getColumnIndexOrThrow(_cursor, "section");
      final int _cursorIndexOfMImageId = CursorUtil.getColumnIndexOrThrow(_cursor, "imageId");
      final int _cursorIndexOfMTimeIconId = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIconId");
      final int _cursorIndexOfMTimeToPrepare = CursorUtil.getColumnIndexOrThrow(_cursor, "timeToPrepare");
      final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final Food _result;
      if (_cursor.moveToFirst()) {
        final long _tmpMId;
        _tmpMId = _cursor.getLong(_cursorIndexOfMId);
        final String _tmpMName;
        _tmpMName = _cursor.getString(_cursorIndexOfMName);
        final float _tmpMFoodPrice;
        _tmpMFoodPrice = _cursor.getFloat(_cursorIndexOfMFoodPrice);
        final FoodSection _tmpMSection;
        _tmpMSection = __FoodSection_stringToEnum(_cursor.getString(_cursorIndexOfMSection));
        final int _tmpMImageId;
        _tmpMImageId = _cursor.getInt(_cursorIndexOfMImageId);
        final int _tmpMTimeIconId;
        _tmpMTimeIconId = _cursor.getInt(_cursorIndexOfMTimeIconId);
        final TempoPreparo _tmpMTimeToPrepare;
        _tmpMTimeToPrepare = __TempoPreparo_stringToEnum(_cursor.getString(_cursorIndexOfMTimeToPrepare));
        final String _tmpMDescription;
        if (_cursor.isNull(_cursorIndexOfMDescription)) {
          _tmpMDescription = null;
        } else {
          _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
        }
        _result = new Food(_tmpMId,_tmpMName,_tmpMFoodPrice,_tmpMSection,_tmpMImageId,_tmpMTimeIconId,_tmpMTimeToPrepare,_tmpMDescription);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getFoodsSize() {
    final String _sql = "SELECT COUNT(mId) FROM foods";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Food> getAllFoods() {
    final String _sql = "SELECT * FROM foods";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "mId");
      final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfMFoodPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "foodPrice");
      final int _cursorIndexOfMSection = CursorUtil.getColumnIndexOrThrow(_cursor, "section");
      final int _cursorIndexOfMImageId = CursorUtil.getColumnIndexOrThrow(_cursor, "imageId");
      final int _cursorIndexOfMTimeIconId = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIconId");
      final int _cursorIndexOfMTimeToPrepare = CursorUtil.getColumnIndexOrThrow(_cursor, "timeToPrepare");
      final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final List<Food> _result = new ArrayList<Food>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Food _item;
        final long _tmpMId;
        _tmpMId = _cursor.getLong(_cursorIndexOfMId);
        final String _tmpMName;
        _tmpMName = _cursor.getString(_cursorIndexOfMName);
        final float _tmpMFoodPrice;
        _tmpMFoodPrice = _cursor.getFloat(_cursorIndexOfMFoodPrice);
        final FoodSection _tmpMSection;
        _tmpMSection = __FoodSection_stringToEnum(_cursor.getString(_cursorIndexOfMSection));
        final int _tmpMImageId;
        _tmpMImageId = _cursor.getInt(_cursorIndexOfMImageId);
        final int _tmpMTimeIconId;
        _tmpMTimeIconId = _cursor.getInt(_cursorIndexOfMTimeIconId);
        final TempoPreparo _tmpMTimeToPrepare;
        _tmpMTimeToPrepare = __TempoPreparo_stringToEnum(_cursor.getString(_cursorIndexOfMTimeToPrepare));
        final String _tmpMDescription;
        if (_cursor.isNull(_cursorIndexOfMDescription)) {
          _tmpMDescription = null;
        } else {
          _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
        }
        _item = new Food(_tmpMId,_tmpMName,_tmpMFoodPrice,_tmpMSection,_tmpMImageId,_tmpMTimeIconId,_tmpMTimeToPrepare,_tmpMDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __FoodSection_enumToString(@NonNull final FoodSection _value) {
    switch (_value) {
      case ENTRADA: return "ENTRADA";
      case PRINCIPAL: return "PRINCIPAL";
      case BEBIDA: return "BEBIDA";
      case SOBREMESA: return "SOBREMESA";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __TempoPreparo_enumToString(@NonNull final TempoPreparo _value) {
    switch (_value) {
      case LENTO: return "LENTO";
      case NORMAL: return "NORMAL";
      case RAPIDO: return "RAPIDO";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private FoodSection __FoodSection_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "ENTRADA": return FoodSection.ENTRADA;
      case "PRINCIPAL": return FoodSection.PRINCIPAL;
      case "BEBIDA": return FoodSection.BEBIDA;
      case "SOBREMESA": return FoodSection.SOBREMESA;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private TempoPreparo __TempoPreparo_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "LENTO": return TempoPreparo.LENTO;
      case "NORMAL": return TempoPreparo.NORMAL;
      case "RAPIDO": return TempoPreparo.RAPIDO;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
