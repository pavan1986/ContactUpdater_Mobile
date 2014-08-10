package org.kbmandir.update.contacts.dto;

public class PostManData {
	public String senderEmailId = "";
	public String senderPassword = "";
	public ContactData contactData;

	public PostManData(String senderEmailId, String senderPassword,
			ContactData contactData) {
		super();
		this.senderEmailId = senderEmailId;
		this.senderPassword = senderPassword;
		this.contactData = contactData;
	}

	@Override
	public String toString() {
		return "PostManData [senderEmailId=" + senderEmailId
				+ ", senderPassword=" + senderPassword + ", toRecipients="
				+ contactData.toString() + "]";
	}
}