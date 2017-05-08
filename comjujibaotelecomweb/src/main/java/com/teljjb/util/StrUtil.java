package com.teljjb.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class StrUtil {
	
	private final static Log log = LogFactory.getLog( StrUtil.class );
	private StrUtil(){
		
	}
	
	public static String trim(String pValue){
		if ( pValue == null ){
			return null;
		} else {
			return pValue.trim();
		}
	}
	
	public static final String PKG_SPLITER = ".";
	public static String getParentPath(String pPathName){
		return getParentPath(pPathName,  PKG_SPLITER);
	}
	/**
	 * 
	 * @param pPathName
	 * @return
	 */
	public static String getParentPath(String pPathName, String pSpliter){
   	 if ( pPathName == null ){
   		 return null;
   	 }
   	 int lastIndex = pPathName.lastIndexOf(pSpliter);
   	 if ( lastIndex == -1 ){
   		 return null;
   	 }
   	 return pPathName.substring(0, lastIndex);
    }
	

	
	/**
	 * 判断字符串内容是否为空
	 * 
	 * 
	 * @param pValue
	 * @return
	 */
	public static  boolean isEmpty(String pValue){
		if ( pValue == null){
			return true;
		}
		if ( "null".equalsIgnoreCase(pValue.trim()) ){
			return true;
		}
		
		if ( "".equalsIgnoreCase(pValue.trim()) ){
			return true;
		}
       return false;		
	}
	
	 private static final String DATE_SPLIT = "-";
	  /**
	   * 获取日期字符串
	   * 说明：页面上通过控件选择日期时，日期格式是yyyy-mm-dd,但我们数据库
	   * 记录的日期是字符串类型，不需要分隔符"-",所以提供这样一个函数，将分隔
	   * 符删除.
	   * @param pDate
	   * @return
	   */
	  public static String getDateStr(String pDate){
		 if ( pDate == null){
			 return null;
		 }	 
		 return pDate.replaceAll(DATE_SPLIT,"");
	  }	
	  
	  public static String getFormatDate(String pDate){
			 if ( pDate == null){
				 return null;
			 }
			 String year = pDate.substring(0,4);
			 String month = pDate.substring(4,6);
			 String day = pDate.substring(6,8);
			 return year + DATE_SPLIT + month + DATE_SPLIT + day;
		  
	  }
	  public static void main(String[] args) {
		  if ( log.isDebugEnabled() ){
		      log.debug( getSQLParam("%dd_fdf") );
		      log.debug( getJsMessage("%dd_fd'f\"\'dfs\ndd") );
			double d1 = 2563522252.21111;
			String strValue = StrUtil.double2ChineseNumber(d1);
			log.debug(strValue);
			BigDecimal dec = new BigDecimal("1112563522252.21");
			strValue = StrUtil.bigDec2ChineseNumber(dec);
			log.debug(strValue);
		  }
		 
		 
	}
	  

		/**
		 * Check if a String has length.
		 * <p><pre>
		 * StringUtils.hasLength(null) = false
		 * StringUtils.hasLength("") = false
		 * StringUtils.hasLength(" ") = true
		 * StringUtils.hasLength("Hello") = true
		 * </pre>
		 * @param str the String to check, may be <code>null</code>
		 * @return <code>true</code> if the String is not null and has length
		 */
		public static boolean hasLength(String str) {
			return (str != null && str.length() > 0);
		}

		/**
		 * Check if a String has text. More specifically, returns <code>true</code>
		 * if the string not <code>null<code>, it's <code>length is > 0</code>, and
		 * it has at least one non-whitespace character.
		 * <p><pre>
		 * StringUtils.hasText(null) = false
		 * StringUtils.hasText("") = false
		 * StringUtils.hasText(" ") = false
		 * StringUtils.hasText("12345") = true
		 * StringUtils.hasText(" 12345 ") = true
		 * </pre>
		 * @param str the String to check, may be <code>null</code>
		 * @return <code>true</code> if the String is not null, length > 0,
		 *         and not whitespace only
		 * @see Character#isWhitespace
		 */
		public static boolean hasText(String str) {
			
			if ( str == null ){
				return false;
			}
			int  strLen = str.length();
			if (strLen == 0) {
				return false;
			}
			for (int i = 0; i < strLen; i++) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return true;
				}
			}
			return false;
		}
		
		public static String getSQLParam(String pSQLParam){
			if ( pSQLParam == null ){
				return null;
			}
			return pSQLParam.replaceAll("%","\\\\%").replaceAll("_","\\\\_");
			
		}
		
		public static String getJsMessage(String pJsString){
			if ( pJsString == null ){
			   return null;
			}
			return pJsString.replaceAll("\"","\\\\\"").
			       replaceAll("'","\\\\\'").
			       replaceAll("\n","\\\\\\n");
			
			
			
		}

		/*
		 * 将小写的人民币转化成大写
		 */
		public static String double2ChineseNumber(double number) {
			StringBuffer chineseNumber = new StringBuffer();
			String[] num = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
			String[] unit = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟",
					"亿", "拾", "佰", "仟", "万" };
			String tempNumber = String.valueOf(Math.round((number * 100)));
			int tempNumberLength = tempNumber.length();
			if ("0".equals(tempNumber)) {
				return "零圆整";
			}
			if (tempNumberLength > 15) {
				try {
					throw new Exception("超出转化范围.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			boolean preReadZero = true; // 前面的字符是否读零
			for (int i = tempNumberLength; i > 0; i--) {
				if ((tempNumberLength - i + 2) % 4 == 0) {
					// 如果在（圆，万，亿，万）位上的四个数都为零,如果标志为未读零，则只读零，如果标志为已读零，则略过这四位
					if (i - 4 >= 0 && "0000".equals(tempNumber.substring(i - 4, i))) {
						if (!preReadZero) {
							chineseNumber.insert(0, "零");
							preReadZero = true;
						}
						i -= 3; // 下面还有一个i--
						continue;
					}
					// 如果当前位在（圆，万，亿，万）位上，则设置标志为已读零（即重置读零标志）
					preReadZero = true;
				}
				int digit = Integer.parseInt(tempNumber.substring(i - 1, i));
				if (digit == 0) {
					// 如果当前位是零并且标志为未读零，则读零，并设置标志为已读零
					if (!preReadZero) {
						chineseNumber.insert(0, "零");
						preReadZero = true;
					}
					// 如果当前位是零并且在（圆，万，亿，万）位上，则读出（圆，万，亿，万）
					if ((tempNumberLength - i + 2) % 4 == 0) {
						chineseNumber.insert(0, unit[tempNumberLength - i]);
					}
				}
				// 如果当前位不为零，则读出此位，并且设置标志为未读零
				else {
					chineseNumber
							.insert(0, num[digit] + unit[tempNumberLength - i]);
					preReadZero = false;
				}
			}
			// 如果分角两位上的值都为零，则添加一个“整”字
			if (tempNumberLength - 2 >= 0
					&& "00".equals(tempNumber.substring(tempNumberLength - 2,
							tempNumberLength))) {
				chineseNumber.append("整");
			}
			return chineseNumber.toString();
		}

		private String number2BigRMB(double sum) {
			String[] numeral = { "十", "百", "千" };
			String[] bigNumeral = { "亿", "万" };
			String[] measure = { "元", "角", "分" };
			String[] numbers = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
			StringBuffer result = new StringBuffer();
			int digit = 0;
			for (double temp = sum; temp >= 1; temp /= 10) {
				digit++;
			}
			int loop = 0;
			for (int temp = digit - 4; temp > 0; temp -= 4) {
				loop++;
			}
			int srcLoop = loop;

			boolean acrossLoopZeroFlag = true;

			while (loop >= 0) {
				int doubleBigNumber1 = (int) (sum / Math.pow(10000, loop));
				sum %= Math.pow(10000, loop);

				int bigDigit = 0;
				for (double temp = doubleBigNumber1; temp >= 1; temp /= 10) {
					bigDigit++;
				}
				if (bigDigit < 4 && srcLoop != loop && acrossLoopZeroFlag) {
					result.append(numbers[0]);
					acrossLoopZeroFlag = false;
				} else {
					acrossLoopZeroFlag = true;
				}

				boolean flag = true;
				if (loop > 0) {
					flag = false;
				}
				boolean innerFlag = true;
				if (doubleBigNumber1 == 0) {
					innerFlag = false;
				}
				boolean addZeroFlag = false;

				int i = (int) Math.pow(10, bigDigit - 1);
				for (; i >= 1; i = i / 10, bigDigit--) {
					int doubleNumber = doubleBigNumber1 / i;
					if (doubleNumber > 0) {
						if (addZeroFlag) {
							result.append(numbers[0]);
							addZeroFlag = false;
						}
						result.append(numbers[doubleNumber]);
						if (bigDigit == 1) {
							if (flag) {
								result.append(measure[0]);
							} else {
								result.append(bigNumeral[loop % 2]);
							}
						} else {
							result.append(numeral[bigDigit - 2]);
						}
					} else if (bigDigit == 1) {
						if (flag) {
							result.append(measure[0]);
						} else {
							if (innerFlag) {
								result.append(bigNumeral[loop % 2]);
							}
						}
					} else {
						addZeroFlag = true;
					}
					doubleBigNumber1 %= i;
				}
				loop--;
			}

			DecimalFormat df = new DecimalFormat("#.##");
			String floatStr = df.format(sum);
			floatStr = floatStr.substring(floatStr.indexOf(".") + 1);
			int floatPart = Integer.parseInt(floatStr);
			int jiao = floatPart / 10;
			int fen = floatPart % 10;
			if (jiao > 0) {
				result.append(numbers[jiao]);
				result.append(measure[1]);
			}
			if (fen > 0) {
				result.append(numbers[fen]);
				result.append(measure[2]);
			}
			return result.toString();
		}
		
		public static String bigDec2ChineseNumber(BigDecimal dec){
			double d1 = dec.doubleValue();
			return double2ChineseNumber(d1);
		}
		



}
