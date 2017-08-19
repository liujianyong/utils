package org.fly.utils.ftp;


/**
 * ftp 相关配置
 * 
 */
 
public class FtpConfig {
	
    private String host;
    private int port;
    private String username;
    private String password;
    private String controlencoding;
    
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getControlencoding() {
		return controlencoding;
	}
	public void setControlencoding(String controlencoding) {
		this.controlencoding = controlencoding;
	}


}
