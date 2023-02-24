package com.cnki.paotui.utils;

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
import java.util.ArrayList;
import java.util.List;

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
                    User user=new User();
                    user.id=id;
                    user.username=name;
                    user.password=pass;
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
     * @return 查询所有攻略
     */
    public List<Travell> queryAllTrall(){
        String sql = "select * from travel";
        List<Travell> list=new ArrayList<Travell>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                int id=resultSet.getInt(1);
                String title=resultSet.getString(2);
                String avatar=resultSet.getString(3);
                String name=resultSet.getString(4);
                String content=resultSet.getString(5);
                String pictureNumber=resultSet.getString(6);
                String commentNumber=resultSet.getString(7);
                String viewNumber=resultSet.getString(8);
                String imageUrl=resultSet.getString(9);
                String jumpUrl=resultSet.getString(10);
                Travell travel=new Travell();
                travel.setId(id);
                travel.setTitle(title);
                author author=new author();
                author.setAvatar(avatar);
                author.setName(name);
                travel.setAuthor(author);
                travel.setContent(content);
                travel.setPictureNumber(pictureNumber);
                travel.setCommentNumber(commentNumber);
                travel.setViewNumber(viewNumber);
                travel.setImageUrl(imageUrl);
                travel.setJumpUrl(jumpUrl);
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
     * @return 查询所有攻略
     */
    public List<Travell> queryAllCollentTrall(){
        String sql = "SELECT A.* FROM travel A INNER JOIN travel_user b on A.id = b.travelid where b.userid= "+App.user.id;
        List<Travell> list=new ArrayList<Travell>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                int id=resultSet.getInt(1);
                String title=resultSet.getString(2);
                String avatar=resultSet.getString(3);
                String name=resultSet.getString(4);
                String content=resultSet.getString(5);
                String pictureNumber=resultSet.getString(6);
                String commentNumber=resultSet.getString(7);
                String viewNumber=resultSet.getString(8);
                String imageUrl=resultSet.getString(9);
                String jumpUrl=resultSet.getString(10);
                Travell travel=new Travell();
                travel.setId(id);
                travel.setTitle(title);
                author author=new author();
                author.setAvatar(avatar);
                author.setName(name);
                travel.setAuthor(author);
                travel.setContent(content);
                travel.setPictureNumber(pictureNumber);
                travel.setCommentNumber(commentNumber);
                travel.setViewNumber(viewNumber);
                travel.setImageUrl(imageUrl);
                travel.setJumpUrl(jumpUrl);
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
     * @return 查询所有攻略
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
     * @return 是否收藏这个攻略
     */
    public boolean queryTrallISCollent(int trallid){
        String sql = "SELECT * FROM travel_user where travelid = "+trallid+" and userid= "+App.user.id;
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
     * @return 是否收藏这个攻略
     */
    public boolean queryBookISCollent(String trallid){
        String sql = "SELECT * FROM book where id = "+trallid;
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
     * @return 进行删除这个攻略
     */
    public boolean deleteTrall(int trallid){
        String sql = "delete FROM travel_user where travelid = "+trallid+" and userid= "+App.user.id;
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
    /**
     * @return 进行删除这个攻略
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
    /**
     * @return 进行删除这个攻略
     */
    public void addTrall(int trallid){
        String sql="INSERT INTO travel_user(userid,travelid) VALUES(?,?)";
        try {
            connection=getConnection();
            preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
            preparedStatement.setInt(1,App.user.id);
            preparedStatement.setInt(2,trallid);
             preparedStatement.executeUpdate();
            //  System.out.println("已经成功添加创建了一条新的数据了");
        } catch (SQLException e) {
            //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
            e.printStackTrace();

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

    /*
     * 1、执行静态SQL语句。通常通过Statement实例实现。    这应该是某个指定的sql语句
     * 2、执行动态SQL语句。通常通过PreparedStatement实例实现。    但这应该是多条（much manty）sql语句
     * 3、执行数据库存储过程。通常通过CallableStatement实例实现。
     */
    public int insertUser(String username,String password){
            String sql="INSERT INTO user(username,password) VALUES(?,?)";
            try {
                connection=getConnection();
                preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
                preparedStatement.setString(1,username);
                preparedStatement.setString(2, password);
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
     * 插入一条行程安排
     * @param myTravell
     * @return
     */
    public int insertMyTrall(MyTravell myTravell){
            String sql="INSERT INTO mytravel(userid,starttime,fromlocation,tolocation,hotal,fromlat,fromlon,tolat,tolon) VALUES(?,?,?,?,?,?,?,?,?)";
            try {
                connection=getConnection();
                preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
                preparedStatement.setString(1, App.user.username);
                preparedStatement.setString(2, myTravell.getStarttime());
                preparedStatement.setString(3, myTravell.getFromlocation());
                preparedStatement.setString(4, myTravell.getTolocation());
                preparedStatement.setString(5, myTravell.getHotal());
                preparedStatement.setString(6, myTravell.getFromlat());
                preparedStatement.setString(7, myTravell.getFromlon());
                preparedStatement.setString(8, myTravell.getTolat());
                preparedStatement.setString(9, myTravell.getTolon());
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
     * 插入一条行程安排
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
     * @return 查询所有的行程安排
     */
    public List<MyTravell> queryAllMyTrall(){
        String sql = "select * from mytravel where userid = '"+App.user.username+"'";
        List<MyTravell> list=new ArrayList<MyTravell>();
        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(sql); //获取预编译的sql语句
            resultSet =preparedStatement.executeQuery(); //执行sql语句
            while(resultSet.next()){
                int id=resultSet.getInt(1);
                String userid=resultSet.getString(2);
                String starttime=resultSet.getString(3);
                String fromlocation=resultSet.getString(4);
                String tolocation=resultSet.getString(5);
                String hotal=resultSet.getString(6);
                String fromlat=resultSet.getString(7);
                String fromlon=resultSet.getString(8);
                String tolat=resultSet.getString(9);
                String tolon=resultSet.getString(10);
                MyTravell myTravell=new MyTravell();
                myTravell.setId(id);
                myTravell.setUserid(userid);
                myTravell.setStarttime(starttime);
                myTravell.setFromlocation(fromlocation);
                myTravell.setTolocation(tolocation);
                myTravell.setHotal(hotal);
                myTravell.setFromlat(fromlat);
                myTravell.setFromlon(fromlon);
                myTravell.setTolat(tolat);
                myTravell.setTolon(tolon);
                list.add(myTravell);
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

    public void insertAllTrall(List<Travell> travells){
        String sql = "INSERT INTO travel(id,title,avatar,name,content,pictureNumber,commentNumber,viewNumber,imageUrl,jumpUrl) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            connection=getConnection();
            for (Travell travell:travells){
                    preparedStatement =connection.prepareStatement(sql);
                    preparedStatement.setInt(1,travell.getId());
                    preparedStatement.setString(2,travell.getTitle());
                    preparedStatement.setString(3,travell.getAuthor().getAvatar());
                    preparedStatement.setString(4,travell.getAuthor().getName());
                    preparedStatement.setString(5,travell.getContent());
                    preparedStatement.setString(6,travell.getPictureNumber());
                    preparedStatement.setString(7,travell.getCommentNumber());
                    preparedStatement.setString(8,travell.getViewNumber());
                    preparedStatement.setString(9,travell.getImageUrl());
                    preparedStatement.setString(10,travell.getJumpUrl());
                     preparedStatement.executeUpdate();
                      preparedStatement.close();
            }




        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
//        public void queryStudentById(int id){
//             //  System.out.println("------------这究竟是一个怎样的方法！-----------------");
//            String sql = "select name,age from student where id=?";
//            connection =getConnection();
//            try {
//                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1, id);   //设置占位符对应的值
//                resultSet = preparedStatement.executeQuery(); //这里为什么不能添加参数sql
//                while(resultSet.next()){
//                    Student student = new Student();
//                    student.setId(id);
//                    student.setName(resultSet.getString(1));  //这里的resultSet.getString()是个什么鬼
//                    student.setAge(resultSet.getInt(2));
//                     //  System.out.println(student);
//                }
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        public void addSql(){
//            String sql="INSERT INTO family.student(id,name,age) VALUES(?,?,?)";
//            connection =getConnection();
//            try {
//                preparedStatement =connection.prepareStatement(sql); //错误： Duplicate entry '3' for key 'PRIMARY'---》主键重复输入“3”  因为当我看看表的时候才发现我的3、肖文飞、15已经在数据库被创建了
//                preparedStatement.setString(1,"6");
//                preparedStatement.setString(2, "周楠");
//                preparedStatement.setString(3,"22");
//                preparedStatement.executeUpdate();
//                 //  System.out.println("已经成功添加创建了一条新的数据了");
//            } catch (SQLException e) {
//                 //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
//                e.printStackTrace();
//            }finally {
//                try {
//                    preparedStatement.close();
//                    connection.close();
//                } catch (SQLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        /*
//         * 通过传参数的形式 为value 赋值
//         */
//        public void addSql1(Student student){
//            String sql="INSERT INTO family.student(id,name,age) VALUES(?,?,?)";
//            connection =getConnection();
//            try {
//                preparedStatement =connection.prepareStatement(sql);
//                preparedStatement.setInt(1,student.getId());
//                preparedStatement.setString(2, student.getName());
//                preparedStatement.setInt(3,student.getAge());
//                preparedStatement.executeUpdate();  //处理以上结果集
//                 //  System.out.println("已经成功添加创建了一条新的数据了");
//            } catch (SQLException e) {
//                 //  System.out.println("重复添加一条数据，即已经被添加或被创建了");
//                e.printStackTrace();
//            }finally {
//                try {
//                    preparedStatement.close();
//                    connection.close();
//                } catch (SQLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//        public void deleteSql(){
//            String sql="DELETE FROM `family`.`student` WHERE `id`='7'";
//            connection = getConnection();
//            try {
//                preparedStatement =connection.prepareStatement(sql);
//                preparedStatement.executeUpdate(sql);
//                 //  System.out.println("成功删除了id为7的 数据");
//            } catch (SQLException e) {
//                 //  System.out.println("删除操作失败，这是为什么呢");
//                e.printStackTrace();
//            }
//        }
//
//        public void updateSql(){
//            String sql="UPDATE `family`.`student` SET `name`='肖肖肖' WHERE `id`='7'";
//            connection =getConnection();
//            try {
//                preparedStatement=connection.prepareStatement(sql);
//                preparedStatement.executeUpdate(sql);
//                 //  System.out.println("更新修改成功");
//            } catch (SQLException e) {
//                 //  System.out.println("这是一首简单的小情歌，但是你还是测试失败了，这怪谁呢");
//                e.printStackTrace();
//            }
//        }
    }

