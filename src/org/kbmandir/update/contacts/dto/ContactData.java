package org.kbmandir.update.contacts.dto;

public class ContactData {
	public String dateString;
	public String firstName;
	public String lastName;
	public String email;
	public String phone;
	public Boolean likeToVolunteer;
	public String comments;

	@Override
	public String toString() {
		return "ContactData [dateString=" + dateString + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phone=" + phone + ", likeToVolunteer=" + likeToVolunteer
				+ ", comments=" + comments + "]";
	}

}
