package com.wf.dubbo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @Description: <P>字符串工具类, 继承org.apache.commons.lang3.StringUtils类</P>
 *
 * @version: v1.0.0
 * @author: wangpf
 * @date: 2018年11月24日 下午10:46:37 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年11月24日     wang           v1.0.0               修改原因
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的<a target="_blank" style="color: #0000F0; display:inline; position:static; background:none;" href="http://www.so.com/s?q=%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F&ie=utf-8&src=se_lighten_f">正则表达式</a>
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\\t|\\r|\\n";//定义空格回车 和& 符号
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }
    
    
    /**
     * <P>删除Html标签</P>
     * 
     * @param htmlStr
     * @return 文本字符串
     */
    public static String delHTMLTag(String htmlStr) {
    	if(htmlStr==null){
    		return " ";
    	}
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr= m_space.replaceAll(""); // 过滤空格回车标签
        //替换掉&符号，产生的word生成的bug
        htmlStr=htmlStr.replaceAll("&", "");  
        
        return htmlStr.trim(); // 返回文本字符串
    }
	
    /**
     * <P>字符串第一个字符小写</P>
     * 
     * @param str
     * @return 字符串
     */
	public static String lowerFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toLowerCase() + str.substring(1);
		}
	}
	
	/**
     * <P>字符串第一个字符大写</P>
     * 
     * @param str
     * @return 字符串
     */
	public static String upperFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * <P>替换掉HTML标签方法</P>
	 * 
	 * @param html 
	 * @return 文本字符串
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * <P>缩略字符串（不区分中英文字符）</P>
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return 
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * <P>缩略字符串（替换html）</P>
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
	}
		
	
	/**
	 * <P>转换为Double类型</P>
	 * 
	 * @param val 目标对象
	 * @return Double
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * <P>转换为Float类型</P>
	 * 
	 * @param val 目标对象
	 * @return Float
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * <P>转换为Long类型</P>
	 * 
	 * @param val 目标对象
	 * @return Long
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * <P>转换为Integer类型</P>
	 * 
	 * @param val 目标对象
	 * @return Integer
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * <P>获得i18n字符串，本地化字符串</P>
	 * 
	 * @param code 目标字符串
	 * @param args 目标字符串参数
	 * @return 
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = SpringContextHolder.getBean(LocaleResolver.class);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Locale localLocale = localLocaleResolver.resolveLocale(request);
		return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
	}
	
	/**
	 * <P>获得用户远程地址</P>
	 * 
	 * @param request 用户请求
	 * @return 用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
	
	  public static void main(String[] args) {
//		  String sss={"weektype":"2","reportDatas":[{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"15kW乘用车充电模块","enddate":"2014-10-26"},{"progress":"50","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"讨论、调整站内监控Android终端界面风格，包括主界面和各列表风格","createperson":"王朋飞","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"90","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"站内监控android客户端界面美化","createperson":"位苏","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"100","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"LEV2000,历史数据存储bug一处。\r\nLEV2000,前置解析bug一处。\r\nLEV2000,统一linux与windows规约名称。\r\nLEV1000,天津充电站电话支持。","createperson":"丁积德","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"100","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"站内监控系统使用手册作成","createperson":"葛松","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"100","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"站内监控系统改进（新建测点修改)","createperson":"葛松","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"20","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"冀北电动汽车运营管理系统维护：接入营销基础数据平台工作中的数据比对工作","createperson":"邵宏强","donedate":"","projectname":"运营监控系统实施运维","enddate":"2014-10-26"},{"progress":"50","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"参加营销数据基础平台实施项目建设启动会议；冀北电力公司电动汽车系统营销数据基础平台实施项目编码核对、数据比对；","createperson":"张尔斌","donedate":"","projectname":"运营监控系统实施运维","enddate":"2014-10-26"},{"progress":"50","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"蒙东电力公司电动汽车运营管理系统营销基础数据平台编码核对、数据比对","createperson":"张尔斌","donedate":"","projectname":"运营监控系统实施运维","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"10整车充电机","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"电池管理系统","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"换电项目","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"上海交流桩","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"60kW一体化充电","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"长岛微网","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"分体式充电项目","enddate":"2014-10-26"},{"progress":"100","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"鲁能运营服务平台需求评审及修订","createperson":"李伟","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"50","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"电动汽车服务平台门户及手机端界面设计","createperson":"李伟","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"50","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"熟悉嵌入式开发平台使用","createperson":"邵宏强","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"100","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"LNOA协同办公系统（日报、周报、月报数据统计；根据模板生成word文档）；genWeb自动生成程序修改。","createperson":"严娜","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"10","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"运营监控系统需求页面","createperson":"严娜","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"90","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"开发充电桩上操作界面、运营监控系统界面、微信界面","createperson":"张宗慧","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"100","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"负责开发了微信wap网站的新闻管理,留言板功能.","createperson":"赵庆辉","donedate":"","projectname":"软件开发","enddate":"2014-10-26"},{"progress":"","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"硬件开发","enddate":"2014-10-26"},{"progress":"","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"产品工艺","enddate":"2014-10-26"},{"progress":"","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"换电技术","enddate":"2014-10-26"},{"progress":"","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"储能、电池技术","enddate":"2014-10-26"},{"progress":"","modulename":"技术研究","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"充电技术","enddate":"2014-10-26"},{"progress":"","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"技术支持","enddate":"2014-10-26"},{"progress":"100","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"lnoa办公协同系统-在线办公模块测试，测试权限控制,人员角色用户初始化,hb二级缓存处理bug","createperson":"王磊","donedate":"","projectname":"其他","enddate":"2014-10-26"},{"progress":"100","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"在153服务器上重新安装系统运行环境，部署站内监控系统和浙江互动平台","createperson":"王朋飞","donedate":"","projectname":"其他","enddate":"2014-10-26"},{"progress":"100","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"运营监控、服务平台产品需求讨论","createperson":"王朋飞","donedate":"","projectname":"其他","enddate":"2014-10-26"},{"progress":"100","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"给济南局新编专利6篇","createperson":"张宗慧","donedate":"","projectname":"其他","enddate":"2014-10-26"},{"progress":"20","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"部门宣传资料整理、制作。","createperson":"张宗慧","donedate":"","projectname":"其他","enddate":"2014-10-26"},{"progress":"","modulename":"左侧空白","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"领导关注事宜","enddate":"2014-10-26"}],"weeklogid":"de35208133f6418eac183603c6963290","reportDatas2":[{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"15kW乘用车充电模块","enddate":"2014-10-26"},{"progress":"10","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"站内监控系统-电动汽车服务平台门户网站界面设计，设计评审","createperson":"王磊","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"0","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"1、车载终端监控页美化\r\n2、充电站运营监控平台需求界面设计","createperson":"位苏","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"0","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"LEV2000测试\r\nLEV2000,windows安装包制作，给工程部试用。跟踪工程部使用反馈。","createperson":"丁积德","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"0","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"windows服务器安装包作成","createperson":"葛松","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"0","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"站内监控系统测点管理，location4值设定。","createperson":"葛松","donedate":"","projectname":"电动汽车运营监控系统","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"运营监控系统实施运维","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"10整车充电机","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"电池管理系统","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"换电项目","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"上海交流桩","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":"60kW一体化充电","enddate":"2014-10-26"},{"progress":"","modulename":"产品研发","costdays":"","startdate":"2014-10-20","officename":"软件研发室","logdetail":"","createperson":"","donedate":"","projectname":...
		  
		 } 
	  /**
	     * 获取工程路径
	     * @return
	     */
	    public static String getProjectPath(){
			String projectPath = "";
			try {
				File file = new DefaultResourceLoader().getResource("").getFile();
				if (file != null){
					while(true){
						File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
						if (f == null || f.exists()){
							break;
						}
						if (file.getParentFile() != null){
							file = file.getParentFile();
						}else{
							break;
						}
					}
					projectPath = file.toString();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return projectPath;
	    }
	    /**
		 * 驼峰命名法工具
		 * @return
		 * 		toCamelCase("hello_world") == "helloWorld" 
		 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
		 * 		toUnderScoreCase("helloWorld") = "hello_world"
		 */
	    public static String toCapitalizeCamelCase(String s) {
	        if (s == null) {
	            return null;
	        }
	        s = toCamelCase(s);
	        return s.substring(0, 1).toUpperCase() + s.substring(1);
	    }
	    
	    /**
		 * 驼峰命名法工具
		 * @return
		 * 		toCamelCase("hello_world") == "helloWorld" 
		 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
		 * 		toUnderScoreCase("helloWorld") = "hello_world"
		 */
	    public static String toUnderScoreCase(String s) {
	        if (s == null) {
	            return null;
	        }

	        StringBuilder sb = new StringBuilder();
	        boolean upperCase = false;
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);

	            boolean nextUpperCase = true;

	            if (i < (s.length() - 1)) {
	                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
	            }

	            if ((i > 0) && Character.isUpperCase(c)) {
	                if (!upperCase || !nextUpperCase) {
	                    sb.append(SEPARATOR);
	                }
	                upperCase = true;
	            } else {
	                upperCase = false;
	            }

	            sb.append(Character.toLowerCase(c));
	        }

	        return sb.toString();
	    }
	    /**
		 * 驼峰命名法工具
		 * @return
		 * 		toCamelCase("hello_world") == "helloWorld" 
		 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
		 * 		toUnderScoreCase("helloWorld") = "hello_world"
		 */
	    public static String toCamelCase(String s) {
	        if (s == null) {
	            return null;
	        }

	        s = s.toLowerCase();

	        StringBuilder sb = new StringBuilder(s.length());
	        boolean upperCase = false;
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);

	            if (c == SEPARATOR) {
	                upperCase = true;
	            } else if (upperCase) {
	                sb.append(Character.toUpperCase(c));
	                upperCase = false;
	            } else {
	                sb.append(c);
	            }
	        }

	        return sb.toString();
	    }
	    public static Object depthClone(Object srcObj){ 
	    	 Object cloneObj = null; 
	    	 try { 
	    	 ByteArrayOutputStream out = new ByteArrayOutputStream(); 
	    	 ObjectOutputStream oo = new ObjectOutputStream(out); 
	    	 oo.writeObject(srcObj); 
	    	 
	    	 ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray()); 
	    	 ObjectInputStream oi = new ObjectInputStream(in); 
	    	 cloneObj = oi.readObject(); 
	    	 } catch (IOException e) { 
	    	 e.printStackTrace(); 
	    	 } catch (ClassNotFoundException e) { 
	    	 e.printStackTrace(); 
	    	 } 
	    	 return cloneObj; 
	    	 }
	    
	    /**
		 * 去掉字符串前面的0
		 * @return
		 * 		delStringFrontZero("00000132000414") == "132000414" 
		 */
	    public static String delStringFrontZero(String srcObj){ 
		    String newStr = srcObj.replaceAll("^(0+)", "");
	    	return newStr; 
   	 	}
}
