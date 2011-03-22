package excel;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import util.Constant;
import dao.BaseDAO;

public class ExcelDemo extends BaseDAO {

	
	String tables[]={"game","game_merchant_area","game_server","tb_bank_info","tb_cogamemapping","tb_cogamemapping_bak","tb_cogamemapping_bak1117","tb_exchange_password","tb_game_get_hd_score_log","tb_game_hd_score","tb_game_hd_sdo","tb_game_hd_sdo_vote","tb_game_login_conf","tb_game_login_get_score_log","tb_game_module","tb_game_newplayercard","tb_game_newplayercard_activity","tb_game_score","tb_game_user_login","tb_game_user_login_1129","tb_game_user_lottery","tb_game_user_score","tb_id_card","tb_interface_conf","tb_pay_num","tb_tg_admin_log","tb_tg_back_order","tb_tg_game_percent","tb_tg_player_consume_info","tb_tg_player_info","tb_tg_spreader_base","tb_tg_spreader_exchange_info","tb_tg_spreader_game_map","tb_tg_spreader_info","tb_tg_spreader_performance","tb_tg_spreader_register","tb_tg_web_info","tb_user_newplayercard_ref"};
	
	public ExcelDemo()
	{
		
		this.project=Constant.Project_name;
		this.db_master_id=Constant.Db_master_id;
		this.db_slave_id=Constant.DB_slave_id;
		
		
		String path =this.getClass().getResource("/config/log4j.properties").toString();
		path=path.replaceAll("file:/", "");
		
		PropertyConfigurator.configure(path);
		
        
    	try {
			InputStream is = this.getClass().getResourceAsStream("/config/250_proxool.xml");
			JAXPConfigurator.configure(new InputStreamReader(is), false);
			System.out.println(is.toString());
		} catch (Exception e) {
			logger.error("初始化数据库连接池失败！", e);
		}
	}
	
	/**
	 * 导出数据库的表结构
	 */
	public List<Map<String,Object>> dumpTableStructure(String dumpTable)
	{
		
		String sql ="select column_name,column_comment,column_type,column_default from information_schema.columns where table_name='"+dumpTable+"'";
		List<Map<String,Object>> listMap=super.selectAll(sql);
		return listMap;
	}
	
	
	public void createExcel()
	{
		HSSFWorkbook workbook=new HSSFWorkbook();
		
		//建sheet工作表
		for(int i=0;i<tables.length;i++)
		{
			HSSFSheet sheet=workbook.createSheet(tables[i]);
			
			List<Map<String,Object>> data =this.dumpTableStructure(tables[i]);
			
			//在sheet工作表里写数据
			for(int rowNum=0;rowNum<data.size()+1;rowNum++)
			{
				HSSFRow row=sheet.createRow((short)rowNum);
				
				int cellNum =0;
				
				if (rowNum==0)
				{
					
					row.createCell((short) cellNum++).setCellValue("名称");
					row.createCell((short) cellNum++).setCellValue("字段");
					row.createCell((short) cellNum++).setCellValue("类型");
					row.createCell((short) cellNum++).setCellValue("说明");
					continue;
					
				}
				System.out.println(tables[i]);
				
				
				Map<String,Object> map =data.get(rowNum-1);
				
				row.createCell((short) cellNum++).setCellValue(map.get("column_name").toString());
				row.createCell((short) cellNum++).setCellValue(map.get("column_comment").toString());
				row.createCell((short) cellNum++).setCellValue(map.get("column_type").toString());
				row.createCell((short) cellNum++).setCellValue(map.get("column_comment").toString());
			}
			
			
			
		}
		
		try {
			FileOutputStream fileout = new FileOutputStream("D:/temp/data_structure.xls");
			workbook.write(fileout);
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	public HSSFSheet  createSheet(HSSFWorkbook workbook ,String sheetName)
	{
		return workbook.createSheet(sheetName);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExcelDemo demo =new ExcelDemo();
		demo.createExcel();
		
	}
	
	
	

}
