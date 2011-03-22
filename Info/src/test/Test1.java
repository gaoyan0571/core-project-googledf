package test;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class Test1 {

	
	public void test1()
	{
		
	}
	// 定义MySQL的数据库驱动程序
	public static final String DBDRIVER = "org.gjt.mm.mysql.Driver" ;
	// 定义MySQL数据库的连接地址
	public static final String DBURL = "jdbc:mysql://localhost:3306/test" ;
	// MySQL数据库的连接用户名
	public static final String DBUSER = "root" ;
	// MySQL数据库的连接密码
	public static final String DBPASS = "123456789" ;
	public static void main(String args[]) throws Exception{	// 所有异常抛出
		Connection conn = null ;		// 数据库连接
		PreparedStatement pstmt = null ;	// 数据库操作
		String keyWord = "李" ;	 // 设置查询关键字
		ResultSet rs = null ;	// 接收查询结果
		String sql = "select count(*) from admin_log";
		Class.forName(DBDRIVER) ;	// 加载驱动程序
		conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
		pstmt = conn.prepareStatement(sql) ;	// 实例化PreapredStatement对象
		rs = pstmt.executeQuery() ;	// 执行查询
		while(rs.next()){
			
			
			System.out.println(rs.getRow());
			System.out.println(rs.getInt(1));
		}
		
		while(rs.next()){
			
			
			System.out.println(rs.getRow());
			System.out.println(rs.getInt(1));
		}
		rs.close() ;
		pstmt.close() ;
		conn.close() ;			// 数据库关闭
	}
}
