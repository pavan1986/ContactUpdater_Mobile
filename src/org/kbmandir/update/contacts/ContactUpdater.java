package org.kbmandir.update.contacts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.kbmandir.update.contacts.dto.ContactData;
import org.kbmandir.update.contacts.dto.GoogleAccessData;
import org.kbmandir.update.contacts.dto.PostManData;
import org.kbmandir.update.contacts.mail.PostMan;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class ContactUpdater {

	public static void main(String[] args) throws Exception {
		// load properties from properties
		// file:'resources/contact_updater.properties'
		Properties configProperties = new Properties();
		InputStream fileStream = new FileInputStream(new File(
				"resources/contact_updater.properties"));
		configProperties.load(fileStream);

		// prepare google access data
		GoogleAccessData googleAccessData = new GoogleAccessData(
				configProperties.getProperty("google_username"),
				configProperties.getProperty("google_password"),
				configProperties.getProperty("goodle-doc-title"));

		// prepare contact to be added: add contact#1
		ContactData contactData1 = new ContactData();
		contactData1.dateString = "1/1/2014";
		contactData1.firstName = "Sudarshan";
		contactData1.lastName = "Krishna";
		contactData1.email = "sudarshan.krishna@gmail.com";
		contactData1.phone = "4085053069";
		contactData1.likeToVolunteer = true;
		contactData1.comments = "This is a test comment!";
		// add contact#1
		addRow(googleAccessData, contactData1);
		PostMan.sendMail(new PostManData(googleAccessData.googleUsername,
				googleAccessData.googlePassword, contactData1.email));

		// prepare contact to be added: add contact#2
		ContactData contactData2 = new ContactData();
		contactData2.dateString = "1/1/2014";
		contactData2.firstName = "abhay";
		contactData2.lastName = "charan";
		contactData2.email = "abhay.charan@gmail.com";
		contactData2.phone = "4081234567";
		contactData2.likeToVolunteer = true;
		contactData2.comments = "This is another test comment!";

		// add contact#2
		addRow(googleAccessData, contactData2);
		PostMan.sendMail(new PostManData(googleAccessData.googleUsername,
				googleAccessData.googlePassword, contactData2.email));

		// prepare contact to be added: add contact#3
		ContactData contactData3 = new ContactData();
		contactData3.dateString = "1/1/2014";
		contactData3.firstName = "Seshu";
		contactData3.lastName = "Cl";
		contactData3.email = "seshucl@gmail.com";
		contactData3.phone = "1324567890";
		contactData3.likeToVolunteer = true;
		contactData3.comments = "This is seshu's comments";
		// add contact#3
		addRow(googleAccessData, contactData3);
		PostMan.sendMail(new PostManData(googleAccessData.googleUsername,
				googleAccessData.googlePassword, contactData3.email));

		// prepare contact to be added: add contact#4
		ContactData contactData4 = new ContactData();
		contactData4.dateString = "4/8/2014";
		contactData4.firstName = "vamshidhar";
		contactData4.lastName = "boda";
		contactData4.email = "vgd@ihf-usa.org";
		contactData4.phone = "1324567980";
		contactData4.likeToVolunteer = true;
		contactData4.comments = "This is vamshi's comments";
		// add contact#4
		addRow(googleAccessData, contactData4);
		PostMan.sendMail(new PostManData(googleAccessData.googleUsername,
				googleAccessData.googlePassword, contactData4.email));

	}

	public static boolean addRow(GoogleAccessData googleAccessData,
			ContactData contactData) throws AuthenticationException,
			MalformedURLException, IOException, ServiceException {
		System.out
				.format("Adding the row\n "
						+ "[[[ Date:%s, firstname:%s, lastname:%s, Email:%s, Phone:%s, volunteer:%s, comments:%s ]]]\n",
						contactData.dateString, contactData.firstName,
						contactData.lastName, contactData.email,
						contactData.phone,
						String.valueOf(contactData.likeToVolunteer),
						contactData.comments);
		try {
			SpreadsheetService service = new SpreadsheetService(
					"Contacts-MobileApp");
			service.setUserCredentials(googleAccessData.googleUsername,
					googleAccessData.googlePassword);

			// Define the URL to request. This should never change.
			URL SPREADSHEET_FEED_URL = new URL(
					"https://spreadsheets.google.com/feeds/spreadsheets/private/full");

			// Make a request to the API and get all spreadsheets.
			SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
					SpreadsheetFeed.class);
			List<SpreadsheetEntry> spreadsheets = feed.getEntries();

			if (spreadsheets.size() == 0) {
				// TODO: There were no spreadsheets, act accordingly.
			}

			// TODO: Choose a spreadsheet more intelligently based on your
			// app's needs.

			for (SpreadsheetEntry spreadsheetEntry : spreadsheets) {
				if (spreadsheetEntry.getTitle().getPlainText()
						.equals(googleAccessData.googleDocName)) {
					addRow(service, spreadsheetEntry, contactData);
					break;
				}
			}

		} catch (Exception e) {
			System.err.println("\nException while adding the row:"
					+ e.getMessage());

			e.printStackTrace();
			return false;
		}

		return true;

	}

	private static void addRow(SpreadsheetService service,
			SpreadsheetEntry spreadsheetEntry, ContactData contactData)
			throws Exception {
		// Get the first worksheet of the first spreadsheet.
		// TODO: Choose a worksheet more intelligently based on your
		// app's needs.
		WorksheetFeed worksheetFeed = service.getFeed(
				spreadsheetEntry.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		WorksheetEntry worksheet = worksheets.get(0);

		// Fetch the list feed of the worksheet.
		URL listFeedUrl = worksheet.getListFeedUrl();
		ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

		// Create a local representation of the new row.
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("Date", contactData.dateString);
		row.getCustomElements().setValueLocal("firstname",
				contactData.firstName);
		row.getCustomElements().setValueLocal("lastname", contactData.lastName);
		row.getCustomElements().setValueLocal("Email", contactData.email);
		row.getCustomElements().setValueLocal("Phone", contactData.phone);
		row.getCustomElements().setValueLocal("volunteer",
				(contactData.likeToVolunteer) ? "Yes" : "No");
		row.getCustomElements().setValueLocal("Comments", contactData.comments);
		// Send the new row to the API for insertion.
		row = service.insert(listFeedUrl, row);

	}
}