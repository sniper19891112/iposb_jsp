package com.iposb.page;

import org.apache.log4j.Logger;

/*
 *一个分页管理器，
 *用于处理在网页分页系统中出现的问题
 */
public class Paging {
	//private Object[] data = null; // 要管理的分页数据，这些数据存放于数组当中(条目数组)
	private int total = 0;
	private int perLen = 1;// 每页的内容长度(条目数)
	private int ptr = 0; // 当前指针(页码下标)
	private int max = 0;// 最大的页数
	
	static Logger logger = Logger.getLogger(Paging.class);

	public Paging() {
	};

	/*
	 * 设置页长
	 */
	public int setPerLen(int i) {
		int r = perLen;
		perLen = i;
		if (perLen <= 0)
			perLen = 1;
		return r;
	}

	/*
	 * 获得单位页所能保存的内容数(条目数)
	 */
	public int getPerLen() {
		perLen = perLen <= 0 ? 1 : perLen;
		return perLen;
	}

	/*
	 * 获得最大的页数
	 */
	public int getMaxPage() {
		if (total == 0)
			return max = 1;
		perLen = perLen <= 0 ? 1 : perLen;
		return max = (total + perLen) / perLen;
	}

	/*
	 * 下一页,得到的是++ptr
	 */
	public int nextPage() {
		++ptr;
		ptr = ptr >= max ? max - 1 : ptr;
		return ptr;
	}

	/*
	 * 上一页
	 */
	public int prePage() {
		--ptr;
		ptr = ptr < 0 ? 0 : ptr;
		return ptr;
	}

	/*
	 * 得到当前页码
	 */
	public int getPageNo() {
		int m = getMaxPage();
		ptr = ptr < 0 ? 0 : (ptr > m ? m : ptr);
		return ptr;
	}

	/*
	 * 设置当前页码
	 */
	public int setPageNo(int i) {
		int r = ptr;
		ptr = i;
		ptr = ptr >= getMaxPage() ? max - 1 : (ptr < 0 ? 0 : ptr);
		return r;
	}

	/*
	 * 设置需要分页的数据
	 */
	public int setData(int t) {
		int r = t;
		total = t - 1;
		getMaxPage();// 最大的页数
		return r;
	}

	/*
	 * 获得当前页,得到的是数据内容(当前页的条目数组)
	 */
//	public Object[] getNowPage(){
//		getPerLen();
//		Object[] page = new Object[perLen];
//		for(int i=0;i<perLen && i+ptr*perLen<total;i++)
//			page[i]=data[ptr*perLen+i];
//		return page;
//}

	/*
	 * 得到数据所在页范围的条目数据下界
	 */
	public int getNowPageLow() {
		getPerLen();
		getPageNo();
		return ptr * perLen;// 下界
	}

	/*
	 * 获得所在页范围的条目数据上界
	 */
	public int getNowPageHigh() {
		try {
			getPerLen();
			getPageNo();
			int h = ptr * perLen + perLen - 1;// 上界
			h = h >= total ? total - 1 : h;
			return h;
		} catch (Exception e) {
			return 0;
		}
	}

	/*
	 * 获得某一区间内的页码 包括首未页,返回的是页码 int range;为页码呈现的区间。如 【3】 【4】 【5】 这个区间长度为3
	 */
	public int[] getRange(int range){
		getPageNo();
		int[] ans = null;
		int m = getMaxPage();// 得到最大的页码
		range = range <= 0 ? 1 :(range > m ? m : range);
		ans = new int[range]; // 得到区间页码存储数组
		int left = ptr - range/2 ; // 得到最左侧的页码
		left = left < 0 ? 0 : (left+range >= m ? m-range : left); // 游动、修正
		for(int i=0; i<range; i++, left++) 
			ans[i]=left;// 得到页标范围的结果
		return ans;
	}
}