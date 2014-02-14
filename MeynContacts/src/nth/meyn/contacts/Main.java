//https://forums.oracle.com/forums/thread.jspa?threadID=2246809

package nth.meyn.contacts;

/* 
 * This file is part of jwordpress. 
 * 
 * jwordpress is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * jwordpress is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License 
 * along with jwordpress.  If not, see <http://www.gnu.org/licenses/>. 
 */

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/**
 * 
 * Example implementation
 * 
 * Run this as java net.bican.wordpress.example.Main &lt;username&gt; &lt;password&gt; &lt;xmlrpc-url&gt; for your blog.
 * 
 * @author Can Bican
 * 
 */
public class Main {

	
	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws XmlRpcFault
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		MeynConnectService.readContacts("nilsth","MeyN66^");
	}



}
