package ua.mbeats.asterisk.sip.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ua.mbeats.asterisk.sip.dao.SipAccountsDAO;

@Path("/zadarma")
public class ZadarmaSip {

	@GET
	@Path("/add/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public SipAccountsDAO addSip(@PathParam("username") String username,
			@PathParam("password") String password) {

		SipAccountsDAO sipAcc = new SipAccountsDAO();
		
		sipAcc.connect();
		sipAcc.addSipAcc(username, password, "zadarma");
		sipAcc.disconnect();
		
		return sipAcc;
	}
	
	@GET
	@Path("/del/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public SipAccountsDAO delSip(@PathParam("username") String username){
		SipAccountsDAO sipAcc = new SipAccountsDAO();
		
		sipAcc.connect();
		sipAcc.deleteSipAcc(username);
		sipAcc.disconnect();
		
		return sipAcc;
	}
	
	@GET
	@Path("/list")
	public String list(){
		String list;
		SipAccountsDAO sipAcc = new SipAccountsDAO();
		
		sipAcc.connect();
		list = sipAcc.showSipList();
		sipAcc.disconnect();
		
		return "{ \"list\": \""+list+"\" }";
	}
	
}
