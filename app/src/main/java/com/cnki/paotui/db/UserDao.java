package com.cnki.paotui.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/15
 * Time: 13:00
 * 此类的作用为：
 */
public class UserDao {
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型

    private Dao<User, Integer> dao;

        public UserDao(Context context) {
            try {
                this.dao = DatabaseHelper.getInstance(context).getDao(User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * 创建数据
         *
         * @param data
         */
        public void create(User data) {
            try {
                dao.createOrUpdate(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * 创建数据集合
         *
         * @param datas
         */
        public void createList(List<User> datas) {
            try {
                dao.create(datas);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * 向user表中添加一条数据
         * <p>
         * create:插入一条数据或集合
         * <p>
         * createIfNotExists:如果不存在则插入
         * <p>
         * createOrUpdate:如果指定id则更新
         *
         * @param data
         */
        public void insert(User data) {
            try {
                dao.createIfNotExists(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 通过id删除指定数据
        public void delete(int id) {
            try {
                dao.deleteById(id);
            } catch (SQLException e) {
            }
        }

        // 删除表中的一条数据
        public void delete(User data) {
            try {
                dao.delete(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 删除数据集合
        public void deleteList(List<User> datas) {
            try {
                dao.delete(datas);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //清空数据
        public void deleteAll() {
            try {
                dao.delete(dao.queryForAll());
            } catch (Exception e) {
            }
        }

        // 修改表中的一条数据
        public void update(User data) {
            try {
                dao.update(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 查询表中的所有数据
        public List<User> queryAll() {
            List<User> users = null;
            try {
                users = dao.queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
        }

        // 根据ID取出用户信息
        public User queryById(int id) {
            User user = null;
            try {
                user = dao.queryForId(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }

        // 通过条件查询集合（例如：通过messageId和title）
        public List<User> queryByMessageIdAndTitle(int messageId, String title) {
            try {
                QueryBuilder<User, Integer> builder = dao.queryBuilder();
                builder
                        .where()
                        .eq("messageId", messageId)
                        .and()
                        .eq("title", title);
                builder.orderBy("messageId", false);
                return builder.query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 通过条件查询集合（例如：通过content）
        public List<User> queryByContent(String content) {
            try {
                QueryBuilder<User, Integer> builder = dao.queryBuilder();
                builder
                        .where()
                        .eq("content", content);
                builder.orderBy("title", false);
                return builder.query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        // 通过条件查询集合（例如：通过content）
        public User queryByUserNameAndPassword(String username,String password) {
            try {
                QueryBuilder<User, Integer> builder = dao.queryBuilder();
                        builder
                        .where()
                        .eq("username", username).and().eq("password",password);
                return builder.queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}

