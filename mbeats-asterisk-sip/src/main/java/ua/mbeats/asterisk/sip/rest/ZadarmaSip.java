package ua.mbeats.asterisk.sip.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ua.mbeats.asterisk.sip.dao.SipAccountsDAO;
import ua.mbeats.asterisk.sip.utils.PostToManager;

@Path("/")
public class ZadarmaSip {

	@GET
	@Path("/add/{nameid}/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addSip(@PathParam("nameid") String nameid,
			@PathParam("username") String username,
			@PathParam("password") String password) throws IOException {

		/*
		 * Create Sip Account
		 */
		
		String status = "{\"status\": \"fail\"}";
		
		Map<String, String> params = new HashMap<String, String>();

		String register = username.trim() + ":" + password.trim()
				+ "@sip.zadarma.com/" + nameid.trim();

		String config = "type=friend\n\r" + "username="+username.trim()+"\n\r"
				+ "defaultuser="+username.trim()+"\n\r" + "secret="+password.trim()+"\n\r"
				+ "fromuser="+username.trim()+"\n\r" + "fromdomain=sip.zadarma.com\n\r"
				+ "host=sip.zadarma.com\n\r" + "qualify=yes\n\r"
				+ "nat=yes\n\r" + "dtmfmode=rfc2833\n\r"
				+ "insecure=invite\n\r" + "context="+nameid.trim()+"\r\n"
				+ "canreinvite=no\n\r" + ";srvlookup=no";

		params.put("c_sip_name_a", nameid.trim());
		params.put("c_sip_register_a", register);
		params.put("advanced_sip_config", config);

		int createSipAccount = PostToManager.doPost(params);
		params.clear();
		
		/*
		 * Create incoming route
		 */
		String sipId = null;
		SipAccountsDAO sips = new SipAccountsDAO();
		sips.connect();
		sipId = sips.fetchSipId(nameid.trim());
//		sipId = sips.fetchSipId("Zadarma");
		sips.disconnect();

		params.put("sr_name", nameid.trim());
		params.put("groups", "0001");
		params.put("exten", "1"); // Custom extension (not Default: s);
		params.put("ext_number", nameid.trim());
		params.put("sips", sipId);
		
		int createIncomingRoute = PostToManager.doPost(params);
		if(createSipAccount == 200 && createIncomingRoute == 200){
			status = "{\"status\": \"ok\"}";
		}
		
		return status;
	}

	@GET
	@Path("/del/{nameid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delSip(@PathParam("nameid") String nameid) throws IOException {
		String status = "{\"status\": \"fail\"}";
		
		SipAccountsDAO sips = new SipAccountsDAO();
		sips.connect();
		String sipId = sips.fetchSipId(nameid);
		sips.disconnect();
	
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("q", "cr_s");
		params.put("sub", "list");
		params.put("d_id", sipId);
		
		int deleteSipAccountResult = PostToManager.doGet(params);
		if(deleteSipAccountResult == 200){
			status = "{\"status\": \"ok\"}";
		}

		return status;
	}

	@GET
	@Path("/list")
	public String list() {
		String list;
		SipAccountsDAO sipAcc = new SipAccountsDAO();

		sipAcc.connect();
		list = sipAcc.showSipList();
		sipAcc.disconnect();

		return "{\"list\": [" + list + "]}";
	}

}
