package com.icip.framework.sys.exception;

/**
 * 系统码常量类
 * 
 * @author lenovo
 * 
 */
public class ExceptionConstants {

	/** 默认视图 */
	public final static String SYSTEM_VIEWNAME = "comm/error";

	/** 失败状态 */
	public final static String SYSTEM_FAIL_STATUS = "0";

	/** 成功状态 */
	public final static String SYSTEM_SUCCESS_STATUS = "1";

	/** 默认错误信息 */
	public final static String SYSTEM_MSG = "系统出错拉！";

	/** 系统错误：在无法定义具体错误是使用 */
	public final static String SYSTEM_ERROR = "E90000";

	/** 系统错误：超时 */
	public final static String SYSTEM_TIMEOUT = "E90001";

	/** 系统错误：超限 */
	public final static String SYSTEM_LIMITED = "E90002";

	/** 系统错误：线程调度异常 */
	public final static String SYSTEM_THREAD = "E90003";

	/** 系统错误：资源文件异常 */
	public final static String SYSTEM_RESOURCE = "E90004";

	/** 系统错误，格式转换异常 **/
	public final static String SYSTEM_FORMAT = "E90005";

	/** 系统错误: 任务调用异常 **/
	public final static String SYSTEM_TASK = "E90006";

	/** 系统错误: 生成流水异常 **/
	public final static String BUILE_SERIAL_NO_ERROR = "E90007";

}
