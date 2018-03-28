package bupt.tasays.DB_Direct;

/**
 * Created by root on 18-3-16.
 */

import java.sql.*;

import static java.lang.System.out;

public class DBManager {

    // 数据库连接常量
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "bupt8421BCD!";
    public static final String URL = "jdbc:mysql://39.107.121.30:3306/Server?useSSL=false";
    //把MySQL-USER和MySQL-PASS改为你的服务器mysql账户和密码

    // 静态成员，支持单态模式
    private static DBManager per = null;
    private Connection conn = null;
    private Statement stmt = null;

    // 单态模式-懒汉模式
    private DBManager() {
    }

    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // 加载驱动
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 连接数据库，获取句柄+对象
    public void connectDB() {
        out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            if(conn!=null)
            {
                out.println("SqlManager:Connect to database successful.");
                stmt = conn.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭数据库 关闭对象，释放句柄
    public void closeDB() {
        out.println("Close connection to database..");
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("Close connection successful");
    }

    // 查询
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // 增添/删除/修改
    public int executeUpdate(String sql) {
        int ret = 0;
        try {
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}