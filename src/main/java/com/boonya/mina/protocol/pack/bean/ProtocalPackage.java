package com.boonya.mina.protocol.pack.bean;

public class ProtocalPackage {

	private int length; // 报头 显示整个报文长度
	private byte flag;// 标记符
	private String content; // 报文内容

	// 构造方法
	public ProtocalPackage(byte flag, String content) {
		super();
		this.flag = flag;
		this.content = content;
		int temLenth = (content == null ? 0 : content.getBytes().length);
		this.length = 5 + temLenth; // int 型的 length 占4个字节 版本号 byte flag占一个字节
									// 一共5个字节
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// 重写toString方法
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("length:").append(length);
		sb.append("flag:").append(flag);
		sb.append("content:").append(content);
		return sb.toString();
	}

}
