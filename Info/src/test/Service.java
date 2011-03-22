package test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import vo.InfoVO;
import dao.InfoDAO;

public class Service {

	private static Logger logger = Logger.getLogger(Service.class);

	static {
		try {
			String path = Service.class.getResource(
					"/config/log4j.properties").toString();
			path = path.replaceAll("file:/", "");

			PropertyConfigurator.configure(path);

			InputStream is = Service.class.getResourceAsStream(
					"/config/xp_proxool.xml");
			JAXPConfigurator.configure(new InputStreamReader(is), false);

		} catch (Exception e) {
			logger.error("初始化数据库连接池失败！", e);
		}
	}
	
	
	public Service() {
		

	}
	
	public static void main(String[] args) {
		
		InfoDAO dao =InfoDAO.getInstance();
		List list2 =dao.selectAllReturnBeans("select * from info",InfoVO.class);
		
		String sql ="select * from info";
	
			for(int i =0;i<list2.size();i++)
			{
				InfoVO vo =(InfoVO)list2.get(i);
				System.out.println(vo);
			}
		
	}
	

}
