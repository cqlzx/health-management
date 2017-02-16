package com.along.android.healthmanagement.helpers;

import com.orm.SugarRecord;
import java.util.List;

public class EntityManager {
    public static <T> List<T> find(Class<T> type, String whereClause, String... whereArgs) {
        return SugarRecord.find(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> List<T> find(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
        return SugarRecord.find(type, whereClause, whereArgs, groupBy, orderBy, limit);
    }

    public static <T> List<T> findWithQuery(Class<T> type, String query, String... arguments) {
        return SugarRecord.findWithQuery(type, query, arguments);
    }

    public static <T> T findById(Class<T> type, Long id) {
        return SugarRecord.findById(type, id);
    }

    public static <T> T findById(Class<T> type, Integer id) {
        return SugarRecord.findById(type, id);
    }

    public static <T> List<T> findByIds(Class<T> type, String[] ids) {
        return SugarRecord.findById(type, ids);
    }

    public static <T> long count(Class<?> type) {
        return SugarRecord.count(type);
    }

    public static <T> long count(Class<?> type, String whereClause, String[] whereArgs) {
        return SugarRecord.count(type, whereClause, whereArgs);
    }

    public static <T> List<T> listAll(Class<T> type) {
        return SugarRecord.find(type, null, null, null, null, null);
    }

    public static <T> List<T> listAll(Class<T> type, String orderBy) {
        return SugarRecord.find(type, null, null, null, orderBy, null);
    }

    public static <T> int deleteAll(Class<T> type) {
        return SugarRecord.deleteAll(type, null);
    }

    public static <T> int deleteAll(Class<T> type, String whereClause, String... whereArgs) {
        return SugarRecord.deleteAll(type, whereClause, whereArgs);
    }

    public static <T> T first(Class<T>type) {
        return SugarRecord.first(type);
    }

    public static <T> T last(Class<T>type) {
        return SugarRecord.last(type);
    }
}
