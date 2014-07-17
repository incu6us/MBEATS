import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ua.mbeats.asterisk.sip.dao.SipAccountsDAO;
import ua.mbeats.asterisk.sip.utils.PostToManager;

public class Test {

	public static void main(String[] args) throws IOException {
		/*
		 * Create Sip Account
		 */
		String username = "testuser";
		String password = "testpassword";
		String nameid = "testnameid";

		Map<String, String> params = new HashMap<String, String>();

		String register = username.trim() + ":" + password.trim()
				+ "@sip.zadarma.com/" + nameid.trim();

		String config = "type=friend\r\n" + "username=" + username.trim()
				+ "\r\n" + "defaultuser=" + username.trim() + "\r\n"
				+ "secret=" + password.trim() + "\r\n" + "fromuser="
				+ username.trim() + "\r\n" + "fromdomain=sip.zadarma.com\r\n"
				+ "host=sip.zadarma.com\r\n" + "qualify=yes\r\n"
				+ "nat=yes\r\n" + "dtmfmode=rfc2833\r\n"
				+ "insecure=invite\r\n" + "context=\r\n" + "canreinvite=no\r\n"
				+ ";srvlookup=no";

		params.put("c_sip_name_a", nameid.trim());
		params.put("c_sip_register_a", register);
		params.put("advanced_sip_config", config);

		PostToManager.doPost(params);
		params.clear();

		/*
		 * Create incoming route
		 */
		String sipId = null;
		SipAccountsDAO sips = new SipAccountsDAO();
		sips.connect();
		sipId = sips.fetchSipId(nameid.trim());
		sips.disconnect();
		
		params.put("sr_name", nameid.trim());
		params.put("groups", "0001");
		params.put("exten", "1"); // Custom extension (not Default: s);
		params.put("ext_number", nameid.trim());
		params.put("sips", sipId); // Put ID of Zadarma trunk (sip_custom.id)

		PostToManager.doPost(params);
		
		/*
		 * List sip accounts
		 */

//		SipAccountsDAO sips = new SipAccountsDAO();
//		sips.connect();
//		
//		System.out.println(sips.showSipList());
//		
//		sips.disconnect();
		
		/*
		 * Fetch Sip id
		 */
		
//		SipAccountsDAO sips = new SipAccountsDAO();
//		sips.connect();
//		
//		System.out.println(sips.fetchSipId("testnameid"));
//		
//		sips.disconnect();
		
		
		/*
		 * Delete Sip Account
		 */
//		SipAccountsDAO sips = new SipAccountsDAO();
//		sips.connect();
//		String sipId = sips.fetchSipId("testnameid");
//		sips.disconnect();
//	
//		Map<String, String> params = new HashMap<String, String>();
//		
//		params.put("q", "cr_s");
//		params.put("sub", "list");
//		params.put("d_id", sipId);
//		
//		if(PostToManager.doGet(params) == 200){
//			System.out.println("Ok");
//		}
		
	}
}
