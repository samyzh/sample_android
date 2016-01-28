package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart.helps;

import java.text.DecimalFormat;
/**
 */
public class MoneyShowTool {
	private static DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置 
	private static DecimalFormat twolastDF = new DecimalFormat("#0.00");
	
	private static DecimalFormat nolastDF = new DecimalFormat("#0");
	private static DecimalFormat formatnolastDF = new DecimalFormat("#,##0");
	
	/**单独后面两位小数点*/

	public static String format(double d){
		return decimalFormat.format(d);
	}
	
	public static String twolastDF(double d){
		return twolastDF.format(d);
	}
	
	public static String nolastDF(double d){
		return nolastDF.format(d);
	}
	public static String formatNolastDF(double d){
		return formatnolastDF.format(d);
	}
}
