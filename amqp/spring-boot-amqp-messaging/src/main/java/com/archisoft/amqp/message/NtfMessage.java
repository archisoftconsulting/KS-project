package com.archisoft.amqp.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NtfMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean email;
	private boolean inbox = true;
	private boolean sms;
	
	private String from;
	private String to;
	private String cc;
	private String subject;
//	private String message;	
	private boolean html;
	
	private String ntdId;
	private Map<String, String> subjectParam;
	private Map<String, String> contentParam;
	
    // Default constructor is needed to deserialize JSON
    public NtfMessage() {
//		this.to = new ArrayList<String>();
//		this.cc = new ArrayList<String>();
    	contentParam = new HashMap<String, String>();
    	subjectParam = new HashMap<String, String>();
    }
    
	public NtfMessage(String ntfId, boolean inbox, boolean email, boolean sms, String from, String toList,
			String ccList, String subject, boolean html, Map<String, String> subjectParam, Map<String, String> contentParam) {

		this();
		this.inbox = inbox;
		this.email = email;
		this.sms = sms;
		this.from = from;
		this.to = toList;
		this.cc = ccList;
		this.subject = subject;
//		this.message = message;
		this.html = html;
		this.subjectParam = subjectParam;
		this.contentParam = contentParam;
	}

	private String[] splitByComma(String toMultiple) {
		String[] toSplit = toMultiple.split(",");
		return toSplit;
	}
	
		
    public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isInbox() {
		return inbox;
	}

	public void setInbox(boolean inbox) {
		this.inbox = inbox;
	}

	public boolean isSms() {
		return sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/*public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}*/

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public String getNtdId() {
		return ntdId;
	}

	public void setNtdId(String ntdId) {
		this.ntdId = ntdId;
	}

	public Map<String, String> getSubjectParam() {
		return subjectParam;
	}

	public void setSubjectParam(Map<String, String> subjectParam) {
		this.subjectParam = subjectParam;
	}

	public Map<String, String> getContentParam() {
		return contentParam;
	}

	public void setContentParam(Map<String, String> contentParam) {
		this.contentParam = contentParam;
	}
	
}
