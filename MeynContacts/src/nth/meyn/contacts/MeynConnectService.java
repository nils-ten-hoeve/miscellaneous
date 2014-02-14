package nth.meyn.contacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.swing.text.html.HTML;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.html.HTMLUListElement;

public class MeynConnectService {

	private static final String MEYN_POLAND = "Meyn Poland";
	private static final String MEYN_WORMERVEER = "Meyn Wormerveer";
	private static final String MEYN_OOSTZAAN_AMBACHT = "Meyn Oostzaan Ambacht";
	private static final String MEYN_BEIJING = "Meyn Beijing";
	private static final String MEYN_BRAZIL = "Meyn Brazil";
	private static final String MEYN_MEXICO = "Meyn Mexico";
	private static final String MEYN_BALLGROUND = "Meyn Ballground";
	private static final String MEYN_NUMANSDORP = "Meyn Numansdorp";
	private static final String MEYN_OOSTZAAN = "Meyn Oostzaan H.O.";
	private static final String END_CONTACTS = "<div id=\"pag-bottom\" class=\"pagination\">";
	private static final String START_PAGINATION = "<a class='page-numbers' href='/connections/?upage=";
	private static final String START_PHONE = "<div class=\"profile-field-loop\" id=\"profile-phone\">";
	private static final String START_LOCATION = "<div class=\"profile-field-loop\" id=\"profile-location\">";
	private static final String START_JOB_TITLE = "<div class=\"profile-field-loop\" id=\"profile-job-title\">";
	private static final String START_NAME = "<div class=\"item-title\">";
	private static final String START_CONTACT = "<div class=\"item\">";
	private static final String START_OF_NEXT_ELEMENT = "<";
	private static final String END_OF_NEXT_ELEMENT = ">";
	private static final String HTTP_WWW_MEYNCONNECT_COM = "https://www.meynconnect.com/";
	private static final String HTTP_WWW_MEYNCONNECT_COM_LOGIN = HTTP_WWW_MEYNCONNECT_COM + "wp-login.php";
	private static final String HTTP_WWW_MEYNCONNECT_COM_CONTACTS = HTTP_WWW_MEYNCONNECT_COM + "connections/";;

	public static void readContacts(String userName, String password) throws ClientProtocolException, IOException {
		// java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		// java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
		// System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		// System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		// System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
		// System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
		// System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");

		// try {
		DefaultHttpClient httpclient = HttpClientIgnoreInvalidSSLCertificate.createClient();

		// Create a local instance of cookie store
		CookieStore cookieStore = new BasicCookieStore();
		httpclient.setCookieStore(cookieStore);

		// Override CookieSpecFactory because WordPress sends cookies that do not comply with standard
		CookieSpecFactory csf = new CookieSpecFactory() {
			public CookieSpec newInstance(HttpParams params) {
				return new BrowserCompatSpec() {
					@Override
					public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
						// allow all cookies
					}
				};
			}
		};
		httpclient.getCookieSpecs().register("easy", csf);
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");

		// get login page with cookies
		HttpGet httpget = new HttpGet(HTTP_WWW_MEYNCONNECT_COM_LOGIN);

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine());
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("No connection to: " + HTTP_WWW_MEYNCONNECT_COM_LOGIN);
		}
		if (entity != null) {
			entity.consumeContent();
		}

		System.out.println("Initial set of cookies:");
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {
			for (Cookie cooky : cookies) {
				System.out.println("- " + cooky.toString());
			}
		}

		// post response with login details (and previously received cooky)
		HttpPost httpost = new HttpPost(HTTP_WWW_MEYNCONNECT_COM_LOGIN);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("log", userName));
		nvps.add(new BasicNameValuePair("pwd", password));
		nvps.add(new BasicNameValuePair("rememberme", "forever"));
		nvps.add(new BasicNameValuePair("redirect_to", HTTP_WWW_MEYNCONNECT_COM));
		nvps.add(new BasicNameValuePair("testcookie", "1"));
		nvps.add(new BasicNameValuePair("wp-submit", "Log In"));

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		entity = httpost.getEntity();

		response = httpclient.execute(httpost);
		entity = response.getEntity();

		System.out.println("Login form post: " + response.getStatusLine());
		if (response.getStatusLine().getStatusCode() != 302) {
			throw new RuntimeException("Could not login on: " + HTTP_WWW_MEYNCONNECT_COM_LOGIN);
		}

		if (entity != null) {
			entity.consumeContent();
		}

		System.out.println("Post logon cookies:");
		cookies = httpclient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {
			for (Cookie cooky : cookies) {
				System.out.println("- " + cooky.toString());
			}
		}

		// get contents
		HttpGet httpGet = new HttpGet(HTTP_WWW_MEYNCONNECT_COM_CONTACTS);
		response = httpclient.execute(httpGet);
		entity = response.getEntity();
		String pageContent = EntityUtils.toString(entity);
		System.out.println("Page Contents: " + pageContent);

		System.out.println("Content form get: " + response.getStatusLine());
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Could not retrieve contact details from: " + HTTP_WWW_MEYNCONNECT_COM_CONTACTS);
		}

		if (entity != null) {
			entity.consumeContent();
		}

		// System.out.println("Post get cookies:");
		// cookies = httpclient.getCookieStore().getCookies();
		// if (cookies.isEmpty()) {
		// System.out.println("None");
		// } else {
		// for (Cookie cooky : cookies) {
		// System.out.println("- " + cooky.toString());
		// }
		// }

		int nrOfPages = parseNrOfPages(pageContent);
		for (int pageNr = 1; pageNr < nrOfPages; pageNr++) {
			String url = HTTP_WWW_MEYNCONNECT_COM_CONTACTS + "?upage=" + pageNr;
			httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			entity = response.getEntity();
			pageContent = EntityUtils.toString(entity);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Could not retrieve contact details from: " + url);
			}
			if (entity != null) {
				entity.consumeContent();
			}
			parse(pageContent);
		}

		httpclient.getConnectionManager().shutdown();

		// } catch (Exception e) {
		// System.out.println(e);
		//
		// }

	}

	private static int parseNrOfPages(String html) {
		// this only works with the first page!
		int currentPos = html.indexOf(START_PAGINATION) + START_PAGINATION.length();// position of second page
		currentPos = html.indexOf(START_PAGINATION, currentPos) + START_PAGINATION.length();// position of last page
		currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
		int endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
		return Integer.parseInt(html.substring(currentPos, endPos));
	}

	private static void parse(String html) {
		int currentPos = 0;
		int endPos = 0;

		// nrOfPages=

		boolean endOfPage = false;
		while (!endOfPage) {
			// name
			currentPos = html.indexOf(START_CONTACT, currentPos) + START_CONTACT.length();
			currentPos = html.indexOf(START_NAME, currentPos) + START_NAME.length();
			currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
			endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
			String name = html.substring(currentPos, endPos).trim();// TODO HTMLtotext

			// job title
			currentPos = html.indexOf(START_JOB_TITLE, currentPos) + START_JOB_TITLE.length();
			currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
			endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
			String jobTitle = html.substring(currentPos, endPos);// TODO HTMLtotext
			int beginAppendix = jobTitle.indexOf("(");
			if (beginAppendix > 0) {
				jobTitle = jobTitle.substring(0, beginAppendix);
			}
			jobTitle = jobTitle.trim();

			// location
			currentPos = html.indexOf(START_LOCATION, currentPos) + START_JOB_TITLE.length();
			currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
			endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
			String location = html.substring(currentPos, endPos).trim();// TODO HTMLtotext

			// phone
			currentPos = html.indexOf(START_PHONE, currentPos) + START_PHONE.length();
			currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
			endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
			String phone = normilizePhoneNumber(html.substring(currentPos, endPos), location);// TODO HTMLtotext

			// mobile
			currentPos = html.indexOf(START_PHONE, currentPos) + START_PHONE.length();
			currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
			endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
			String mobile = normilizePhoneNumber(html.substring(currentPos, endPos), location);// TODO HTMLtotext

			// email
			currentPos = html.indexOf(START_PHONE, currentPos) + START_PHONE.length();
			currentPos = html.indexOf(END_OF_NEXT_ELEMENT, currentPos) + END_OF_NEXT_ELEMENT.length();
			endPos = html.indexOf(START_OF_NEXT_ELEMENT, currentPos);
			String email = html.substring(currentPos, endPos).trim();// TODO HTMLtotext

			int endOfContacts = html.indexOf(END_CONTACTS);
			int nextContact = html.indexOf(START_CONTACT, currentPos);
			System.out.println("Name: " + name + "   Job title: " + jobTitle + "   Location: " + location + "   Phone: " + phone + "   Mobile: " + mobile + "   email: " + email);

			endOfPage = nextContact > endOfContacts;

		}

	}

	private static String normilizePhoneNumber(String number, String location) {
		number = number.replace("-", " ");
		number = number.replace("(", " ");
		number = number.replace(")", " ");
		number = number.trim();
		if (MEYN_OOSTZAAN.equals(location) || MEYN_NUMANSDORP.equals(location) || MEYN_OOSTZAAN_AMBACHT.equals(location) || MEYN_WORMERVEER.equals(location)) {
			if (number.length() == 4) {
				number = "+31 20 204 " + number; // convert internal to external number
			}
			if ("9".equals(number)) {
				number = "+31 20 204 5992"; // reception
			}
			if ("**310".equals(number)) {
				number = "+31 75 640 6338";
			}
			if ("**350".equals(number)) {
				number = "+31 75 614 5381";
			}

			if (number.startsWith("0")) {
				number = "+31 " + number.substring(1);// add country code NL
			}
		} else if (location.toLowerCase().contains("ball") && location.toLowerCase().contains("ground") || location.toLowerCase().contains("canada")) {
			if (number.length() > 0 && !number.startsWith("+1")) {
				number = "+1 " + number;// add country code US
			}
		} else if (MEYN_MEXICO.equals(location)) {
			if (number.length() > 0 && !number.startsWith("+") && number.startsWith("52")) {
				number = "+" + number;// add + before country code
			}
		} else if (MEYN_BRAZIL.equals(location)) {
			if (number.length() > 0 && !number.startsWith("+") && number.startsWith("55")) {
				number = "+" + number;// add + before country code
			}
		} else if (MEYN_BEIJING.equals(location)) {
			if (number.length() > 0 && !number.startsWith("+") && number.startsWith("86")) {
				number = "+" + number;// add + before country code
			}
		} else if (MEYN_POLAND.equals(location)) {
			if (number.length() > 0 && !number.startsWith("+") && number.startsWith("48")) {
				number = "+" + number;// add + before country code
			}
			if (number.length() > 0 && !number.startsWith("+48")) {
				number = "+48 " + number;// add + before country code
			}
		}

		// Separate country code with space
		if (number.startsWith("+31")) {
			number = "+31 " + number.substring(3);
		}
		if (number.startsWith("+48")) {
			number = "+48 " + number.substring(3);
		}
		if (number.startsWith("+52")) {
			number = "+52 " + number.substring(3);
		}
		if (number.startsWith("+55")) {
			number = "+55 " + number.substring(3);
		}
		if (number.startsWith("+86")) {
			number = "+86 " + number.substring(3);
		}
		if (number.startsWith("+1")) {
			number = "+1 " + number.substring(2);
		}

		number = number.replace("  ", " ");

		// if (number.length() > 4 && !number.startsWith("+") && !number.startsWith("**")) {
		// number = "!!!!" + number;
		// }
		// TODO no more than 4 numbers in a row
		return number;
	}
}
