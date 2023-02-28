package com.cnki.paotui.utils;

import androidx.collection.ArraySet;

import com.cnki.paotui.App;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.db.MyTravell;
import com.cnki.paotui.db.Travell;
import com.cnki.paotui.db.User;
import com.cnki.paotui.db.author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class JDBC {
        Connection connection;               //连接数据库的连接对象
        PreparedStatement preparedStatement;  //预编译语句
        ResultSet resultSet;                  //获得的结果集
    private  static JDBC jdbc;
    public static JDBC getInstance() {
        if (jdbc == null) {
            jdbc =new JDBC();
            jdbc.getConnection();
        }
        return jdbc;
    }
    private JDBC() {
       // getConnection();
    }
        //编辑数据库连接池：mysql数据库连接驱动（com.mysql.jdbc.Driver）、JDBC url地址、登录名和密码
        public Connection getConnection(){
            String url="jdbc:mysql://47.95.15.65:3306/test";
            String userName="root";
            String password="root";
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                 //  System.out.println("找不到驱动");
                e.printStackTrace();
            }

            try {
                connection= DriverManager.getConnection(url,userName,password);
                if (connection!=null) {
                    System.err.println("数据库连接成功");
                }
            } catch (SQLException e) {
                 //  System.out.println("数据库连接失败");
                e.printStackTrace();
            }
            return connection;
        }


    /**
     * 查询用户
     * @param username
     * @param password
     * @return
     */
        public User queryuser(String username,String password){
            String sql = "select * from user where username=? and password=?";

            try {
                connection=getConnection();
                preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                resultSet =preparedStatement.executeQuery(); //执行sql语句
                while(resultSet.next()){
                    int id=resultSet.getInt(1);
                    String name=resultSet.getString(2);
                    String pass=resultSet.getString(3);
                    String pass1=resultSet.getString(5);
                    User user=new User();
                    user.id=id;
                    user.username=name;
                    user.password=pass;
                    user.password1=pass1;
                    return  user;
                }
                return null;
            } catch (SQLException e) {

                e.printStackTrace();
                return null;
            }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
                try {
                    if(resultSet!=null)
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }


    /**
     * @return 查询所有收藏的图书
     */
    public List<Book> queryAllCollentBook(){
        String sql = "SELECT * FROM book" ;
        List<Book> list=new ArrayList<Book>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                String id=resultSet.getString(1);
                String title=resultSet.getString(2);
                String url=resultSet.getString(3);
                String cover=resultSet.getString(4);
                String type=resultSet.getString(5);
                String content=resultSet.getString(6);
                String auther=resultSet.getString(7);
                String time=resultSet.getString(8);
                String newchaapter=resultSet.getString(9);
               Book travel=new Book();
                travel.id=id+"";
                travel.title=title;
                travel.url=url;
                travel.cover=cover;
                travel.type=type;
                travel.content=content;
                travel.auther=auther;
                travel.time=time;
                travel.newchapter=newchaapter;
                list.add(travel);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return list;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    /**
     * @return 查询所有历史浏览的书籍
     */
    public List<Book> queryAllHistoryBook(){
        String sql = "SELECT * FROM book_history" ;
        List<Book> list=new ArrayList<Book>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                String id=resultSet.getString(1);
                String title=resultSet.getString(2);
                String url=resultSet.getString(3);
                String cover=resultSet.getString(4);
                String type=resultSet.getString(5);
                String content=resultSet.getString(6);
                String auther=resultSet.getString(7);
                String time=resultSet.getString(8);
                String newchaapter=resultSet.getString(9);
                Book travel=new Book();
                travel.id=id+"";
                travel.title=title;
                travel.url=url;
                travel.cover=cover;
                travel.type=type;
                travel.content=content;
                travel.auther=auther;
                travel.time=time;
                travel.newchapter=newchaapter;
                list.add(travel);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return list;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * @return 是否收藏这个图书
     */
    public boolean queryBookISCollent(String trallid){
        String sql = "SELECT * FROM book where id = "+trallid;
        System.out.println("查询语句"+sql);
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * @return 进行删除这个图书
     */
    public boolean deleteBook(String trallid){
        String sql = "delete FROM book where id = "+trallid;
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            int a =preparedStatement.executeUpdate(); //执行sql语句
            if(a>0){
                return true;
            }
            return false;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


    /*
     * 1、执行静态SQL语句。通常通过Statement实例实现。    这应该是某个指定的sql语句
     * 2、执行动态SQL语句。通常通过PreparedStatement实例实现。    但这应该是多条（much manty）sql语句
     * 3、执行数据库存储过程。通常通过CallableStatement实例实现。
     */
    public int insertUser(String username,String password,String password1){
            String sql="INSERT INTO user(username,password,password1) VALUES(?,?,?)";
            try {
                connection=getConnection();
                preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
                preparedStatement.setString(1,username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, password1);
              return   preparedStatement.executeUpdate();
                 //  System.out.println("已经成功添加创建了一条新的数据了");
            } catch (SQLException e) {
                 //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
                e.printStackTrace();
                return -1;
            }finally {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }


    /**
     * 插入一条图书
     * @param myTravell
     * @return
     */
    public int insertBook(Book myTravell){
        String sql="INSERT INTO book(id,title,url,cover,type,content,auther,time,newchapter) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            connection=getConnection();
            preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
            preparedStatement.setString(1,myTravell.id);
            preparedStatement.setString(2, myTravell.title);
            preparedStatement.setString(3, myTravell.url);
            preparedStatement.setString(4, myTravell.cover);
            preparedStatement.setString(5, myTravell.type);
            preparedStatement.setString(6, myTravell.content);
            preparedStatement.setString(7, myTravell.auther);
            preparedStatement.setString(8, myTravell.time);
            preparedStatement.setString(9, myTravell.newchapter);
            return   preparedStatement.executeUpdate();
            //  System.out.println("已经成功添加创建了一条新的数据了");
        } catch (SQLException e) {
            //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
            e.printStackTrace();
            return -1;
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 插入一条历史图书
     * @param myTravell
     * @return
     */
    public int insertHistoryBook(Book myTravell){
        String sql="INSERT INTO book_history(id,title,url,cover,type,content,auther,time,newchapter) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            connection=getConnection();
            preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
            preparedStatement.setString(1,myTravell.id);
            preparedStatement.setString(2, myTravell.title);
            preparedStatement.setString(3, myTravell.url);
            preparedStatement.setString(4, myTravell.cover);
            preparedStatement.setString(5, myTravell.type);
            preparedStatement.setString(6, myTravell.content);
            preparedStatement.setString(7, myTravell.auther);
            preparedStatement.setString(8, myTravell.time);
            preparedStatement.setString(9, myTravell.newchapter);
            return   preparedStatement.executeUpdate();
            //  System.out.println("已经成功添加创建了一条新的数据了");
        } catch (SQLException e) {
            //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
            e.printStackTrace();
            return -1;
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 插入一条搜索
     * @param myTravell
     * @return
     */
    public int insertSearch(String myTravell){
        String sql="INSERT INTO search(text,time) VALUES(?,?)";
        try {
            connection=getConnection();
            preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  String format = sdf.format(new Date());
            preparedStatement.setString(1,myTravell);
            preparedStatement.setString(2,format);
            return   preparedStatement.executeUpdate();
            //  System.out.println("已经成功添加创建了一条新的数据了");
        } catch (SQLException e) {
            //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
            e.printStackTrace();
            return -1;
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取所有搜索
     * @return
     */
    public Set<String> getAllSearch(){
        String sql="select * from search";
        Set<String> list=new ArraySet<>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                String title=resultSet.getString(1);

                list.add(title);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return list;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    /**
     * 是否开启
     * @return
     */
    public boolean isOPen(){
        String sql="select * from open";
        Set<String> list=new ArraySet<>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * @return 搜索图书
     */
    public List<Book> queryAllMyBook(String content,int start,int lengty){
        String sql = "select * from allbook where title like '%"+content+"%' limit "+start+","+lengty;
        System.out.println(sql);
        List<Book> list=new ArrayList<Book>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                String title=resultSet.getString(1);
                String url=resultSet.getString(2);
                String auther=resultSet.getString(3);
                String time=resultSet.getString(4);
                Book book=new Book();
                book.title=title;
                book.url=url;
                book.id=book.url.split("/")[book.url.split("/").length-1];

                book.auther=auther;
                book.time=time;
                list.add(book);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return list;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    /**
     * @return 搜素作者
     */
    public List<Book> queryAllMyBookByAuther(String auther1){
        String sql = "select * from allbook where auther = '"+auther1+"'";
        System.out.println(sql);
        List<Book> list=new ArrayList<Book>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                String title=resultSet.getString(1);
                String url=resultSet.getString(2);
                String auther=resultSet.getString(3);
                String time=resultSet.getString(4);
                Book book=new Book();
                book.title=title;
                book.url=url;
                book.id=book.url.split("/")[book.url.split("/").length-1];
                book.auther=auther;
                book.time=time;
                list.add(book);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return list;
        }finally {
//            停止执行sql语句，关闭生成sql语句的进程以及关掉与数据库交互的连接，进而达到释放资源内存的作用
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    }

