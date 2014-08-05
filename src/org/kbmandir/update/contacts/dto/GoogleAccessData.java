package org.kbmandir.update.contacts.dto;

public class GoogleAccessData {
	public String googleUsername;
	public String googlePassword;
	public String googleDocName;

	public GoogleAccessData(String googleUsername, String googlePassword,
			String googleDocName) {
		super();
		this.googleUsername = googleUsername;
		this.googlePassword = googlePassword;
		this.googleDocName = googleDocName;
	}
}
