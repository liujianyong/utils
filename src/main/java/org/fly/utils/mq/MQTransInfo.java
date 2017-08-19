package org.fly.utils.mq;

import java.io.Serializable;
import java.util.Map;
 
public class MQTransInfo implements Serializable{

	private String message;

	private Map<String,String> attachments;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String,String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String,String> attachments) {
		this.attachments = attachments;
	}
	
}
