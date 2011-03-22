package util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class PageSplit {

	private org.apache.log4j.Logger logger = Logger.getLogger(PageSplit.class);

	/**
	 * 简单的分页,只有上一页,下一页
	 * 
	 * @param pageCount
	 * @param toPage
	 * @param url
	 * @param map
	 * @return
	 */
	public static String simpleSeacherSplit(int pageCount, int toPage,
			String url, Map map) {

		StringBuffer sb = new StringBuffer();
		String params = "";
		if (toPage < 1) {
			toPage = 1;
		}
		if (pageCount < 1)
			return "";
		if (null != map) {
			Set keys = map.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext()) {
				String temp = it.next().toString();
				params += temp + "=" + map.get(temp) + "&";
			}
		}
		params = params + "toPage=";
		url = url + "?" + params;

		if (toPage == 1) {
			sb.append(" <a href=\"" + url + (toPage) + "\">上一页</a>");

		} else {
			sb.append(" <a href=\"" + url + (toPage - 1) + "\">上一页</a>");

		}

		if (toPage != pageCount) {
			sb.append(" <a href=\"" + url + (toPage + 1) + "\">下一页</a>");
		} else if (toPage == pageCount) {
			sb.append(" <a href=\"" + url + pageCount + "\">下一页</a>");

		}

		return sb.toString();
	}

	/**
	 * 分页
	 * 
	 * @param pageCount
	 *            总页数
	 * @param currentPageNum
	 *            当前是第几页
	 * @param url
	 *            URL链接
	 * @param map
	 *            url 问号后面的参数.
	 * @param currentCss
	 *            当前页的css
	 * @return
	 */
	public static String seacherSplitByClass(int pageCount, int currentPageNum,
			String url, Map<String, Object> map, String currentCss) {

		StringBuffer sb = new StringBuffer();
		String params = "";
		if (currentPageNum < 1) {
			currentPageNum = 1;
		}

		int beindex = currentPageNum - 6;
		int endindex = currentPageNum + 5;
		if (beindex < 1)
			beindex = 1;
		if (endindex > pageCount)
			endindex = pageCount;
		if (pageCount < 1)
			return "";

		if (null != map) {
			Set keys = map.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext()) {
				String temp = it.next().toString();
				params += temp + "=" + map.get(temp) + "&";
			}
		}
		params = params + "toPage=";
		url = url + "?" + params;

		sb.append(" <a href=\"" + url + 1 + "\">首页</a>");
		if (currentPageNum == 1) {
			sb.append(" <a href=\"" + url + (currentPageNum) + "\">上一页</a>");

		} else {
			sb
					.append(" <a href=\"" + url + (currentPageNum - 1)
							+ "\">上一页</a>");

		}
		for (int i = beindex; i <= endindex; i++) {
			if (i == currentPageNum) {
				String temp = currentCss.replace("{toPage}", i + "");
				sb.append("&nbsp;" + i);
			} else {
				sb.append(" <a href=\"" + url + i + "\">" + i + "</a>");
			}
		}

		if (currentPageNum != pageCount) {
			sb
					.append(" <a href=\"" + url + (currentPageNum + 1)
							+ "\">下一页</a>");
		} else if (currentPageNum == pageCount) {
			sb.append(" <a href=\"" + url + pageCount + "\">下一页</a>");

		}
		sb.append(" <a href=\"" + url + pageCount + "\">末页</a>");
		return sb.toString();
	}

}
