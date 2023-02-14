package src.com.lc.demo.utils;

public class LcResp {
	
	private String bContent;
	private String charset;
	private String format;
	private String timestamp;
	private String version;
	private String sign;
	private String signType;
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("--response-body-start------------------\n");
		sb.append("bContent=").append(this.bContent+"\n");
		sb.append("charset=").append(this.charset+"\n");
		sb.append("format=").append(this.format+"\n");
		sb.append("timestamp=").append(this.timestamp+"\n");
		sb.append("version=").append(this.version+"\n");
		sb.append("sign=").append(this.sign+"\n");
		sb.append("signType=").append(this.signType+"\n");
		sb.append("--end----------------------- ----------\n");
		return sb.toString();
	}
	
}
