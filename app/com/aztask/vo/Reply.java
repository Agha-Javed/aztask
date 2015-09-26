package com.aztask.vo;

public class Reply {

	private String code;
	private String message;

	public Reply() {
	}

	public Reply(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isSuccess(){
		return (this.code!=null && this.code.equals("200")) ? true : false;
	}

}
