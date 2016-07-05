import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.icip.framework.message.MessageService;
import com.icip.framework.message.bean.Mail;
import com.icip.framework.message.bean.MailAttachment;
import com.icip.framework.message.impl.EmailSenderImpl;
import com.icip.utils.AppUtil;

/**
 * 邮箱短信
 * 
 * @author lenovo
 * 
 */
public class MessageTest extends JUnitActionBase {

	/**
	 * 测试邮件
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testMail() throws Exception {
		MessageService messageService = (MessageService) AppUtil
				.getBean("messageService");
		Map map = new HashMap();
		map.put("name", "代志伟");
		String[] to = { "327586498@qq.com" };
		messageService.sendMessage("1", map, to);
	}

	/**
	 * 测试邮件
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMailAtt() throws Exception {
		EmailSenderImpl emailSender = (EmailSenderImpl) AppUtil
				.getBean("emailSender");
		Mail mail = new Mail();
		mail.setFrom("649095437@qq.com");
		mail.setFromName("缴啊管理员");
		mail.setHtmlFormat(true);
		mail.setContent("<html><head><meta charset='UTF-8'><title>缴啊主页</title></head><body><a href='http://baidu.com'>缴啊主页</a></body></html>");
		String[] to = { "327586498@qq.com" };
		mail.setTo(to);
		// 抄送
		String[] cc = { "327586498@qq.com" };
		mail.setCc(cc);
		// 秘密抄送
		String[] bcc = { "renmianyijiuzai@163.com" };
		mail.setBcc(bcc);
		mail.setSubject("缴啊主页");
		MailAttachment ma = new MailAttachment();
		ma.setFileName("bdyjy.png");
		ma.setFilePath("E:\\1.png");
		MailAttachment[] attachments = { ma };
		mail.setAttachments(attachments);
		emailSender.sendEmail(mail);
	}

	/**
	 * 测试手机短信
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testPhone() throws Exception {
		MessageService messageService = (MessageService) AppUtil
				.getBean("messageService");
		Map map = new HashMap();
		map.put("name", "代志伟");
		String[] to = { "18664964225", "13405207717" };
		messageService.sendMessage("3", map, to);
	}
	
	
	/**
	 * 测试微信
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testWx() throws Exception {
		MessageService messageService = (MessageService) AppUtil
				.getBean("messageService");
		Map map = new HashMap();
		map.put("name", "代志伟");
		String[] to = { "oKOV2wG5jxA1jKOouTJYq3ew4iNo" };
		messageService.sendMessage("2", map, to);
	}

}
