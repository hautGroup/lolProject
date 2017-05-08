package com.teljjb.util;

import java.util.Collection;
import java.util.Map;

/**
 * 此类记录了所有关于Assert的工具类方法
 * @author 中间业务组OWK开发小组
 *
 */
public class AssertUtils {
	
	private AssertUtils(){
		
	}
	/**
	 * 判断表达式是否为真,为假时抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 使用示例:
	 *     int i= 2
	 *     Assert.isTrue(i > 0, "值必须大于0") 输出：void
	 *     Assert.isTrue(i > 3, "值必须大于3") 输出：抛出异常 IllegalArgumentException:值必须大于3
	 * </font></pre>
	 * @param expression 布尔表达式
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException 当表达式为false时
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断表达式是否为真，为假时抛出异常 . <br>
	 * <pre><font size="3" color="bb"> 
	 * 使用示例：	
	 *     int i = 1
	 *     Assert.isTrue(i > 0) 输出： void
	 *     Assert.isTrue(i>1)   输出：抛出异常IllegalArgumentException：[Assertion failed] - 表达式必须为真
	 * </font></pre>
	 * @param expression 布尔表达式
	 * @throws IllegalArgumentException 当表达式为false时
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - 表达式必须为真");
	}

	/**
	 * 断言对象非空,为空时抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当object为空null时，抛出IllegalArgumentException异常
	 * 使用示例：	
	 *     Object clazz = null;    
	 *     Assert.notNull(clazz, "对象不能为空") 输出：抛出IllegalArgumentException异常：对象不能为空
	 * </font></pre>
	 * @param object 检查的对象
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException 当对象为null时
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言对象非空，为空时抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当object为空null时，抛出IllegalArgumentException异常
	 * 使用示例：	
	 *     Object clazz = null;    
	 *     Assert.notNull(clazz)输出：抛出IllegalArgumentException异常：[Assertion failed] - 输入参数不能为空
	 * </font></pre>
	 * @param object 检查的对象
	 * @throws IllegalArgumentException 当对象为null时
	 */
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - 输入参数不能为空");
	}

	/**
	 * 断言输入字符串不为空并且长度不能为0，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当text为空null时，抛出IllegalArgumentException异常
	 * 使用示例：	
	 *     String name = null;    
	 *     Assert.hasLength(name, "Name 不能为空");输出：抛出IllegalArgumentException异常：Name 不能为空
	 * </font></pre>
	 * @param String text 待检查字符串
	 * @param String message 抛出异常时的说明
	 * @throws IllegalArgumentException 当text为null时
	 */
	public static void hasLength(String text, String message) {
		if (!StrUtil.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 断言输入字符串不为空并且长度不能为0，否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当text为空null时，抛出IllegalArgumentException异常
	 * 使用示例：	
	 *     String name = null;    
	 *     Assert.hasLength(name);输出：抛出IllegalArgumentException异常：[Assertion failed] - 输入参数长度不能为0，并且不能为空
	 * </font></pre>
	 * @param String text 待检查字符串	
	 * @throws IllegalArgumentException 当text为null时
	 */	
	public static void hasLength(String text) {
		hasLength(text, "[Assertion failed] - 输入参数长度不能为0，并且不能为空");
	}
	/**
	 * 断言输入字符串是否有字符（不包括空格），否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当text为空null时，抛出IllegalArgumentException异常
	 * 使用示例：	
	 *     String name = "";    
	 *     Assert.hasText(name, "Name 不能为空") 输出：抛出IllegalArgumentException异常：Name 不能为空
	 *     AssertUtils.hasText(" ","输入参数不能没有字符") 输出：抛出IllegalArgumentException异常：输入参数不能没有字符
	 *</font></pre>
	 * @param String text 待检查字符串	
	 * @param String message 抛出异常时的说明
	 * @throws IllegalArgumentException 当text为null时
	 */	
	public static void hasText(String text, String message) {
		if (!StrUtil.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 断言输入字符串是否有字符（不包括空格），否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当text为空null时，抛出IllegalArgumentException异常
	 * 使用示例：	
	 *     String name = "";    
	 *     Assert.hasText(name) 输出：抛出IllegalArgumentException异常：[Assertion failed] - 输入参数长度不能为0;并且不能为空,或只有空格
	 *     AssertUtils.hasText(" ") 输出：抛出IllegalArgumentException异常：[Assertion failed] - 输入参数长度不能为0;并且不能为空,或只有空格
	 * </font></pre>
	 * @param String text 待检查字符串	
	 * @throws IllegalArgumentException 当text为null时
	 */		
	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - 输入参数长度不能为0;并且不能为空,或只有空格");
	}

	/**
	 * 断言给定的文本不包含指定的字符串，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当textToSearch为空null时，抛出IllegalArgumentException异常
	 *     当substring为空null时，抛出IllegalArgumentException异常
	 * 使用示例： 
	 *     String name = "Jack rod "   
	 *     Assert.doesNotContain(name, "rod", "Name 不能包含 'rod'") 输出：抛出IllegalArgumentException异常 Name 不能包含 'rod'
	 * </font></pre>
	 * @param textToSearch 被查找的字符串
	 * @param substring 查找的内容
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException textToSearch或substring为null
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言给定的文本不包含指定的字符串，否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当textToSearch为空null时，抛出IllegalArgumentException异常
	 *     当substring为空null时，抛出IllegalArgumentException异常
	 * 使用示例： 
	 *     String name = "Jack rod "   
	 *     Assert.doesNotContain(name, "rod") 输出：抛出IllegalArgumentException异常 [Assertion failed] - 给定的字符串不能包含 [rod]
	 * </font></pre>
	 * @param textToSearch 被查找的字符串
	 * @param substring 查找的内容
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException textToSearch或substring为null
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - 给定的字符串不能包含 [" + substring + "]");
	}


	/**
	 * 断言对象数组不为空并且至少有一个元素，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当array为null空时，抛出IllegalArgumentException异常
	 *  使用示例：
	 *     String[] array = null;   
	 *     Assert.notEmpty(array, "数组不能为空") 输出：抛出IllegalArgumentException异常 数组不能为空
	 * </font></pre>
	 * @param array 检查的数组
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException 当数组为空时
	 */
	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 断言对象数组不为空并且至少有一个元素，否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当array为null空时，抛出IllegalArgumentException异常
	 *  使用示例：
	 *     String[] array = null;   
	 *     Assert.notEmpty(array) 输出：抛出IllegalArgumentException异常 [Assertion failed] - 数组不为空并且至少有一个元素
	 * </font></pre>
	 * @param array 检查的数组
	 * @throws IllegalArgumentException 当数组为空时
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - 数组不为空并且至少有一个元素");
	}
	/**
	 * 断言Collection不为空并且至少有一个元素，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当collection为null空时，抛出IllegalArgumentException异常
	 *  使用示例：
	 *     Collection collection = null;   
	 *     Assert.notEmpty(collection,"Collection内必须有元素") 输出：抛出IllegalArgumentException异常 Collection内必须有元素
	 * </font></pre>
	 * @param array 检查的Collection
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException 当Collection为空时
	 */
	public static void notEmpty(Collection collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 断言Collection不为空并且至少有一个元素，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当collection为null空时，抛出IllegalArgumentException异常
	 *  使用示例：
	 *     Collection collection = null;   
	 *     Assert.notEmpty(collection) 输出：抛出IllegalArgumentException异常 [Assertion failed] - Collection不为空并且至少有一个元素
	 * </font></pre>
	 * @param array 检查的Collection
	 * @throws IllegalArgumentException 当Collection为空时
	 */
	
	public static void notEmpty(Collection collection) {
		notEmpty(collection,
				"[Assertion failed] - Collection不为空并且至少有一个元素");
	}
	/**
	 * 断言Map不为空并且至少有一个元素，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当map为null空时，抛出IllegalArgumentException异常
	 *  使用示例：
	 *     Map map = null;   
	 *     Assert.notEmpty(map,"Map 必须有元素") 输出：抛出IllegalArgumentException异常 Map 必须有元素
	 * </font></pre>
	 * @param array 检查的Map
	 * @param message 抛出异常时的说明
	 * @throws IllegalArgumentException 当Map为空时
	 */
	public static void notEmpty(Map map, String message) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 
	 * 断言Map不为空并且至少有一个元素，否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值处理：
	 *     当map为null空时，抛出IllegalArgumentException异常
	 * 使用示例：
	 *     Map map = null;   
	 *     Assert.notEmpty(map) 输出：抛出IllegalArgumentException异常 [Assertion failed] - Map不为空并且至少有一个元素
	 * </font></pre>
	 * @param array 检查的Map
	 * @throws IllegalArgumentException 当Map为空时
	 */
	public static void notEmpty(Map map) {
		notEmpty(map, "[Assertion failed] - Map不为空并且至少有一个元素");
	}


	/**
	 * 断言输入对象是指定类的实例，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值判断：
	 *    当clazz为空null时，抛出IllegalArgumentException异常
	 * 使用示例： 
	 *     Foo foo = new Foo();
	 *     Too too = new Too();  
	 *     Assert.instanceOf(Foo.class, foo)
	 *     Assert.instanceOf(Foo.class, too) 抛出IllegalArgumentException异常:对象'Too' 必须是 'Foo的实例'
	 * </font></pre>
	 * @param clazz 检查对象的类型
	 * @param obj 检查的对象
	 * @throws IllegalArgumentException 当obj不是clazz的实例时
	 */
	public static void isInstanceOf(Class clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	/**
	 * 断言输入对象是指定类的实例，否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 空值判断：
	 *    当clazz为空null时，抛出IllegalArgumentException异常
	 * 使用示例： 
	 *     Foo foo = new Foo();
	 *     Too too = new Too();  
	 *     Assert.instanceOf(Foo.class, foo,"")
	 *     Assert.instanceOf(Foo.class, too,"") 抛出IllegalArgumentException异常:对象'Too' 必须是 'Foo的实例'
	 * </font></pre>
	 * @param clazz 检查对象的类型
	 * @param obj 检查的对象
	 * @param 异常时的说明
	 * @throws IllegalArgumentException 当obj不是clazz的实例时
	 */
	public static void isInstanceOf(Class clazz, Object obj, String message) {
		Assert.notNull(clazz, "Class类不能为空");
		Assert.isTrue(clazz.isInstance(obj), message +
				"对象'" + (obj != null ? obj.getClass().getName() : "[null]") +
				"' 必须是 '" + clazz.getName() + "的实例'");
	}


	/**
	 * 断言布尔表达式为真，否则抛出异常信息message. <br>
	 * <pre><font size="3" color="bb"> 
	 * 使用示例：
	 *     String id = "";       
	 *     Assert.state(id == null, "id 不为null空") 抛出IllegalArgumentException异常:id 不为null空
	 * </font></pre>
	 * @param expression 布尔表达式
	 * @param message 异常时的说明
	 * @throws IllegalStateException 表达式为真时
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}
	
	/**
	 * 断言布尔表达式为真，否则抛出异常. <br>
	 * <pre><font size="3" color="bb"> 
	 * 使用示例：
	 *     String id = null;       
	 *     Assert.state(id == null) 抛出IllegalArgumentException异常:[Assertion failed] - 布尔表达式必须为真
	 * </font></pre>
	 * @param expression 布尔表达式
	 * @throws IllegalStateException 表达式为真时
	 */
	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - 布尔表达式必须为真");
	}

}
