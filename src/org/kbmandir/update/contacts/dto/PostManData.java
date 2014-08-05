package org.kbmandir.update.contacts.dto;

public class PostManData {
	public String senderEmailId = "";
	public String senderPassword = "";
	public String toRecipient;

	public PostManData(String senderEmailId, String senderPassword,
			String toRecipient) {
		super();
		this.senderEmailId = senderEmailId;
		this.senderPassword = senderPassword;
		this.toRecipient = toRecipient;
	}

	@Override
	public String toString() {
		return "PostManData [senderEmailId=" + senderEmailId
				+ ", senderPassword=" + senderPassword + ", toRecipients="
				+ toRecipient + "]";
	}
}