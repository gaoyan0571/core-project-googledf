package core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vo.InfoVO;

/**
 * 
 * 
 * @author 谢锦楠
 */
@SuppressWarnings("serial")
public class MapRecord extends HashMap<String, Object> {

	/**
	 * 默认构造方法，调用父类构造方法完成初始化
	 */
	public MapRecord() {
		super();
	}

	/**
	 * 指定初始容量的构造方法，调用父类构造方法完成初始化，初始容量会根据参数值扩展为2的幂值
	 * 
	 * @param initialCapacity
	 *            Record 的初始容量
	 */
	public MapRecord(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 指定复制源 Map 对象的构造方法，调用父类构造方法完成初始化，将参数内容复制到本对象中
	 * 
	 * @param record
	 *            需要复制到新对象中的源对象
	 */
	public MapRecord(Map<String, Object> record) {
		super(record);
	}

	/**
	 * 在 Record 中增加一个 key-value 的对
	 * 
	 * @param key
	 *            元素名
	 * @param value
	 *            元素值
	 */
	public void set(String key, Object value) {

		super.put(key, value);
	}

	/**
	 * 根据元素名称查找元素值，并返回
	 * 
	 * @param key
	 *            元素名
	 * @return Object 类型的元素值
	 */
	public Object get(String key) {

		if (key == null) {
			return null;
		}

		return super.get(key);
	}

	/**
	 * 根据元素名查找对象值，并以 Integer 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return Integer 类型的元素值
	 */
	public Integer getInt(String key) {

		if (key == null) {
			return 0;
		}

		Object o = get(key);
		if (o instanceof Integer) {
			return (Integer) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.Integer。");
		}
	}

	/**
	 * 根据元素名查找对象值，并以 String 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return String 类型的元素值
	 */
	public String getString(String key) {

		if (key == null) {
			return "";
		}

		Object o = get(key);
		if (o instanceof String) {
			return (String) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.String。");
		}
	}

	/**
	 * 根据元素名查找对象值，并以 Long 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return Long 类型的元素值
	 */
	public Long getLong(String key) {

		if (key == null) {
			return 0L;
		}

		Object o = get(key);
		if (o instanceof Long) {
			return (Long) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.Long。");
		}
	}

	/**
	 * 根据元素名查找对象值，并以 Double 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return Double 类型的元素值
	 */
	public Double getDouble(String key) {

		if (key == null) {
			return 0.0;
		}

		Object o = get(key);
		if (o instanceof Double) {
			return (Double) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.Double。");
		}
	}

	/**
	 * 根据元素名查找对象值，并以 Float 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return Float 类型的元素值
	 */
	public Float getFloat(String key) {

		if (key == null) {
			return 0.0f;
		}

		Object o = get(key);
		if (o instanceof Float) {
			return (Float) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.Float。");
		}
	}

	/**
	 * 根据元素名查找对象值，并以 Character 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return Character 类型的元素值
	 */
	public Character getChar(String key) {

		if (key == null) {
			return '0';
		}

		Object o = get(key);
		if (o instanceof Character) {
			return (Character) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.Character。");
		}
	}

	/**
	 * 根据元素名查找对象值，并以 Boolean 类型返回
	 * 
	 * @param key
	 *            元素名
	 * @return Boolean 类型的元素值
	 */
	public Boolean getBoolean(String key) {

		if (key == null) {
			return false;
		}

		Object o = get(key);
		if (o instanceof Boolean) {
			return (Boolean) o;
		} else {
			throw new ClassCastException(key + " 的类型为 " + o.getClass()
					+ ", 要求的是 java.lang.Boolean。");
		}
	}

	/**
	 * 返回 Record 对象中的元素个数
	 * 
	 * @return 元素个数
	 */
	public int getSize() {
		return keySet().size();
	}

	/**
	 * 以字符串形式返回 Record 对象的元素信息
	 * 
	 * @return 所有元素的 名称、值和类名 构成的字符串
	 */
	public String getInfoAsString() {

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Record = ").append("{");

		Iterator<String> it = keySet().iterator();
		String key;
		boolean isFirstKey = true;
		while (it.hasNext()) {
			key = it.next();
			if (!isFirstKey) {
				stringBuffer.append(", ");
			} else {
				isFirstKey = false;
			}
			stringBuffer.append("[").append(key).append("=").append(get(key))
					.append("; ").append(get(key).getClass()).append("]");
		}

		stringBuffer.append(", Count = ").append(keySet().size()).append("}");

		return stringBuffer.toString();
	}

	/**
	 * 将 PO 表示的对象转换成 Record 对象
	 * 
	 * @param o
	 *            数据转换的源对象
	 * @return 转换后的 Record 对象
	 * @throws Exception
	 *             Field 对象的存取性为 false 时，对其操作会产生 IllegalAccessException
	 */
	public static MapRecord toRecord(Object o) throws Exception {

		Field[] fields = o.getClass().getDeclaredFields();
		MapRecord record = new MapRecord(fields.length);
		for (int i = 0; i < fields.length; i++) {

			// 将可存取性设置为 true
			fields[i].setAccessible(true);
			record.set(fields[i].getName(), fields[i].get(o));
		}

		return record;
	}

	/**
	 * 将 Record 表示的对象转换成 VO 对象
	 * 
	 * @param record
	 *            数据转换的源对象
	 * @param vo
	 *            数据转换的目标对象
	 * @return 数据转换后的 VO 对象
	 * @throws Exception
	 *             Field 对象的存取性为 false 时，对其操作会产生 IllegalAccessException
	 */
	public static Object toVo(MapRecord record, Object vo) throws Exception {


		// 如果传递进来的 VO 没有初始化，对其进行初始化
		if (vo == null) {
			vo = vo.getClass().newInstance();
		}
		
		Object fieldValue;
		Field[] fields = vo.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			fieldValue = record.get(fields[i].getName());
			if (fieldValue != null) {
				fields[i].set(vo, fieldValue);
			}
		}

		return vo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MapRecord record = new MapRecord();
		record.set("id", 1233L);
		record.set("content", "内容是什么");
		record.set("add_time", "123");
		record.set("intNumber", 1);
		System.out.println(record.get("intNumber").getClass());
		System.out.println(record.get("id"));
		InfoVO vo = new InfoVO();
		vo = (InfoVO) MapRecord.toVo(record, vo);
		System.out.println("vo = " + vo);

		InfoVO infoVo = new InfoVO();
		infoVo.setId(123L);
		infoVo.setContent("内容");
		record = MapRecord.toRecord(infoVo);
		// System.out.println(record.getInfoAsString());
	}

}
