package com.along.android.healthmanagement.helpers;


import com.orm.SugarRecord;
import java.util.List;

public class EntityManager {
    public static <T> List<T> find(Class<T> type, String whereClause, String... whereArgs) {
        return SugarRecord.find(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> List<T> findWithQuery(Class<T> type, String query, String... arguments) {
        return SugarRecord.findWithQuery(type, query, arguments);
    }


}
