package util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 四则运算
 * @author h
 *
 */
public class NumUtil {

	/**
	 * 
	 * 提供精确的加法运算�??
	 * 
	 * @param v1
	 *            被加�?
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 
	 * 提供精确的减法运算�??
	 * 
	 * @param v1
	 *            被减�?
	 * 
	 * @param v2
	 *            减数
	 * 
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 
	 * 提供精确的乘法运算�??
	 * 
	 * @param v1
	 *            被乘�?
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	public static double mul(double v1, int v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}
	
	
	public static double mul(double v1, double v2,double v3) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		
		return b1.multiply(b2).doubleValue();

	}

	
	public static double round(double num) {
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}

	/**
	 * double类型的四舍五�?,取小数点后两�?
	 * 
	 * @param double num
	 * @return double num
	 */
	public static double myRound(double num) {

		num = Math.round(num * 100) / 100.00;

		return num;

	}
	
	
	/**
	 * 判断是否为整�?
	 * @param num
	 * @return
	 */
	public static boolean isAllNum(String num)
	{
		String regex = "[1-9][0-9]*";
		
		if(StringUtils.isEmpty(num))
		{
			return false;
		}
		if(num.matches(regex))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(	"The scale must be a positive integer or zero");

		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}  
		    
	public static void main(String[] args) {
		
		System.out.println(NumUtil.isAllNum("01"));
	}
}
