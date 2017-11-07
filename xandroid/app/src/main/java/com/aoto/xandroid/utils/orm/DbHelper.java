package com.aoto.xandroid.utils.orm;

import android.database.Cursor;
import android.os.Environment;

import com.aoto.xandroid.Constants;
import com.aoto.xandroid.utils.Tools;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据库基本操作
 */

public class DbHelper {

    /**
     * 创建数据库管理工具（默认,根据配配置文件）
     *
     * @return
     */
    public static DbManager initDB() {
        DbManager db = null;
        if (Tools.isNotBlank(Constants.ORM_DBNAME) && Constants.ORM_DBVERSION != null && Tools.isNotBlank(Constants.ORM_DBPATH)) {
            File dbFile = null;
            if (Tools.isNotBlank(Constants.OSROOTPATH)) {
                dbFile = new File(Constants.OSROOTPATH + Constants.ORM_DBPATH);
                if (dbFile != null) {
                    DbManager.DaoConfig dbConfig = new DbManager.DaoConfig()
                            .setDbName(Constants.ORM_DBNAME) //设置数据库名
                            .setDbVersion(Constants.ORM_DBVERSION) //设置数据库版本,每次启动应用时将会检查该版本号, 发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                            .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                            .setTableCreateListener(new DbManager.TableCreateListener() {
                                @Override
                                public void onTableCreated(DbManager db, TableEntity<?> table) {

                                }
                            })
                            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                                @Override
                                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                                }
                            })//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
                            .setDbDir(dbFile);
                    db = x.getDb(dbConfig);
                }
            }
        }
        return db;
    }

    /**
     * 创建数据库管理工具（创建指定名称和版本并保存到指定位置）
     *
     * @return
     */
    public static DbManager initDB(String dBName, Integer dBVersion, String dBPath) {
        DbManager db = null;
        if (Tools.isNotBlank(dBName) && dBVersion != null && Tools.isNotBlank(dBPath)) {
            File dbFile = null;
            if (Tools.isNotBlank(Constants.OSROOTPATH)) {
                dbFile = new File(Constants.OSROOTPATH + dBPath);
                if (dbFile != null) {
                    DbManager.DaoConfig dbConfig = new DbManager.DaoConfig()
                            .setDbName(dBName) //设置数据库名
                            .setDbVersion(dBVersion) //设置数据库版本,每次启动应用时将会检查该版本号, 发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                            .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                            .setTableCreateListener(new DbManager.TableCreateListener() {
                                @Override
                                public void onTableCreated(DbManager db, TableEntity<?> table) {

                                }
                            })
                            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                                @Override
                                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                                }
                            })//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
                            .setDbDir(dbFile);
                    db = x.getDb(dbConfig);
                }
            }
        }
        return db;
    }

    /**
     * 使用sql直接返回实体
     *
     * @param db
     * @param sql
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntitys(DbManager db, String sql, Class<T> clazz) {
        List<T> result = null;
        if (db != null && Tools.isNotBlank(sql) && clazz != null) {
            try {
                Cursor cursor = db.execQuery(sql);
                if (cursor != null) {
                    result = new ArrayList<T>();
                    while (cursor.moveToNext()) {
                        T entity = getEntity(clazz, cursor);
                        if (entity != null) {
                            result.add(entity);
                        }
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询单个实体
     *
     * @param db
     * @param sql
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(DbManager db, String sql, Class<T> clazz) {
        T result = null;
        if (db != null && Tools.isNotBlank(sql) && clazz != null) {
            try {
                Cursor cursor = db.execQuery(sql);
                if (cursor != null) {
                    if(cursor.moveToFirst()){
                        result = getEntity(clazz, cursor);
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Map
     *
     * @param db
     * @param sql
     * @return
     */
    public static List<Map<String, String>> queryMaps(DbManager db, String sql) {
        List<Map<String, String>> result = new ArrayList<>();
        try {
            List<DbModel> dbModelAll = db.findDbModelAll(new SqlInfo(sql));
            if (dbModelAll != null && dbModelAll.size() > 0) {
                for (DbModel dbModel : dbModelAll) {
                    if (dbModel != null) {
                        result.add(dbModel.getDataMap());
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询Map
     *
     * @param db
     * @param sql
     * @return
     */
    public static Map<String, String> queryMap(DbManager db, String sql) {
        Map<String, String> result = null;
        try {
            DbModel dbModel = db.findDbModelFirst(new SqlInfo(sql));
            if (dbModel != null) {
                result = dbModel.getDataMap();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 得到实体类
     *
     * @param clazz
     * @param cursor
     * @param <T>
     * @return
     */
    private static <T> T getEntity(Class<T> clazz, Cursor cursor) {
        T result = null;
        try {
            result = clazz.newInstance();
            int columnCount = cursor.getColumnCount();
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    String fieldName = field.toString().substring(field.toString().lastIndexOf(".") + 1);
                    String capitalize = capitalize(fieldName);
                    String methodName = "set" + capitalize;
                    Class<?> fieldType = field.getType();
                    Method method = null;
                    Column anno = field.getAnnotation(Column.class);
                    if (anno != null) {
                        String annoname = anno.name();
                        int columnIndex = cursor.getColumnIndex(annoname);
                        if (fieldType == int.class) {
                            method = clazz.getDeclaredMethod(methodName, int.class);
                            method.invoke(result, cursor.getInt(columnIndex));
                            continue;
                        }
                        if (fieldType == Integer.class) {
                            method = clazz.getDeclaredMethod(methodName, Integer.class);
                            method.invoke(result, cursor.getInt(columnIndex));
                            continue;
                        }
                        if (fieldType == String.class) {
                            method = clazz.getDeclaredMethod(methodName, String.class);
                            method.invoke(result, cursor.getString(columnIndex));
                            continue;
                        }
                        if (fieldType == double.class) {
                            method = clazz.getDeclaredMethod(methodName, double.class);
                            method.invoke(result, cursor.getDouble(columnIndex));
                            continue;
                        }
                        if (fieldType == Double.class) {
                            method = clazz.getDeclaredMethod(methodName, Double.class);
                            method.invoke(result, cursor.getDouble(columnIndex));
                            continue;
                        }
                        if (fieldType == long.class) {
                            method = clazz.getDeclaredMethod(methodName, long.class);
                            method.invoke(result, cursor.getLong(columnIndex));
                            continue;
                        }
                        if (fieldType == Long.class) {
                            method = clazz.getDeclaredMethod(methodName, Long.class);
                            method.invoke(result, cursor.getLong(columnIndex));
                            continue;
                        }
                        if (fieldType == short.class) {
                            method = clazz.getDeclaredMethod(methodName, short.class);
                            method.invoke(result, cursor.getShort(columnIndex));
                            continue;
                        }
                        if (fieldType == Short.class) {
                            method = clazz.getDeclaredMethod(methodName, Short.class);
                            method.invoke(result, cursor.getShort(columnIndex));
                            continue;
                        }
                        if (fieldType == boolean.class) {
                            method = clazz.getDeclaredMethod(methodName, boolean.class);
                            int rs = cursor.getInt(columnIndex);
                            if (rs == 1) {
                                method.invoke(result, true);
                            } else {
                                method.invoke(result, false);
                            }
                            continue;
                        }
                        if (fieldType == Boolean.class) {
                            method = clazz.getDeclaredMethod(methodName, Boolean.class);
                            int rs = cursor.getInt(columnIndex);
                            if (rs == 1) {
                                method.invoke(result, true);
                            } else {
                                method.invoke(result, false);
                            }
                            continue;
                        }
                        if (fieldType == Date.class) {
                            method = clazz.getDeclaredMethod(methodName, Date.class);
                            method.invoke(result, new Date(cursor.getLong(columnIndex)));
                            continue;
                        }
                        if (fieldType == Timestamp.class) {
                            method = clazz.getDeclaredMethod(methodName, Timestamp.class);
                            method.invoke(result, new Date(cursor.getLong(columnIndex)));
                            continue;
                        }
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 构件查询条件(字符串)
     *
     * @param object
     * @return
     */
    public static String buildWhereExpr(Object object) {
        StringBuffer where = new StringBuffer();
        where.append(" 1=1 ");
        if (object != null) {
            Class<?> clazz = object.getClass();
            Field[] Fields = clazz.getDeclaredFields();
            for (int i = 0; i < Fields.length; i++) {
                String fieldName = Fields[i].toString().substring(Fields[i].toString().lastIndexOf(".") + 1);
                String capitalize = capitalize(fieldName);
                String methodName = "get" + capitalize;
                Method method = null;
                Object invoke = null;
                try {
                    method = clazz.getDeclaredMethod(methodName);
                    invoke = method.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (invoke != null) {
                    if (Fields[i].getType() == Integer.class) {
                        where.append(" and " + fieldName + "=" + Integer.valueOf(String.valueOf(invoke)));
                    }
                    if (Fields[i].getType() == String.class) {
                        where.append(" and " + fieldName + " like '%" + String.valueOf(invoke) + "%'");
                    }
                    if (Fields[i].getType() == Double.class) {
                        where.append(" and " + fieldName + "=" + Double.valueOf(String.valueOf(invoke)));
                    }
                    if (Fields[i].getType() == Long.class) {
                        where.append(" and " + fieldName + "=" + Long.valueOf(String.valueOf(invoke)));
                    }
                    if (Fields[i].getType() == Short.class) {
                        where.append(" and " + fieldName + "=" + Short.valueOf(String.valueOf(invoke)));
                        if (Fields[i].getType() == Date.class) {
                            where.append(" and " + fieldName + "=" + (Date) invoke);
                        }
                        if (Fields[i].getType() == Timestamp.class) {
                            where.append(" and " + fieldName + "=" + (Timestamp) invoke);
                        }
                    }
                }
            }
        }
        return where.toString();
    }

    /**
     * 构件查询条件(WhereBuilder)
     *
     * @param object
     * @return
     */
    public static WhereBuilder buildWhere(Object object) {
        String whereExpr = buildWhereExpr(object);
        if (Tools.isNotBlank(whereExpr)) {
            return WhereBuilder.b().expr(whereExpr);
        }
        return null;
    }

    /**
     * 方法描述：将属性首字母大写
     *
     * @param fieldName
     * @return String
     */
    public static String capitalize(String fieldName) {
        String newfieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return newfieldName;
    }
}
