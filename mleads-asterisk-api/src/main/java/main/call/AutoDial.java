package main.call;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import main.bean.JNDIBean;
import main.dao.DBConnection;
import main.utils.AsterManager;

import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;

@Path("autodial")
@RequestScoped
public class AutoDial {

	@Context
    private UriInfo context;
	
	public AutoDial(){
	}
	
	@GET
    @Path("{mleadsid}/{msisdn}")
    @Produces("application/json")
	public String call(@PathParam("mleadsid") Integer id, @PathParam("msisdn") String msisdn){
		try{
			String jdbc = JNDIBean.getContextValue("MLeadsAPI", "jdbc-url");
			String managerHostname = JNDIBean.getContextValue("MLeadsAPI", "manager-hostname");
			String managerUsername = JNDIBean.getContextValue("MLeadsAPI", "manager-username");
			String managerPassword = JNDIBean.getContextValue("MLeadsAPI", "manager-password");
			String autodialGroup = JNDIBean.getContextValue("MLeadsAPI", "autodialGroup");
			
			/*
			 * For debug to show flow in database
			 */
//			DBConnection db =new DBConnection(jdbc);
//			db.connect();
//			db.autodialInsertMsisdn(id, msisdn);
//			db.disconnect();
			
			AsterManager manager = new AsterManager(managerHostname, managerUsername, managerPassword);
			if(manager.autodialCall(id, msisdn, autodialGroup)){
				return "{\"id\" : \""+id+"\", \"status\" : \"ok\"}";
			}else{
				return "{\"id\" : \""+id+"\", \"status\" : \"fail\"}";
			}
		}catch(Exception e){
			return "{\"status\" : \"system error\"}";
		}
	}
}
