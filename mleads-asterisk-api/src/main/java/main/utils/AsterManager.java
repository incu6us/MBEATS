package main.utils;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

public class AsterManager {
	
	private ManagerConnection managerConnection;
	private String hostname;
	private int port;
	private String username;
	private String password;
	
	public AsterManager(){
	}
	
	public AsterManager(String hostname, String username, String password){
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		
		try{
			if(this.managerConnection.getState() == this.managerConnection.getState().CONNECTED){
				this.managerConnection.logoff();
			}
		}catch(NullPointerException e){
		}
		
		ManagerConnectionFactory factory = new ManagerConnectionFactory(this.hostname, this.username, this.password);
		this.managerConnection = factory.createManagerConnection();
	}
	
	public AsterManager(String hostname, int port, String username, String password){
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		
		try{
			if(this.managerConnection.getState() == this.managerConnection.getState().CONNECTED){
				this.managerConnection.logoff();
			}
		}catch(NullPointerException e){
		}
		
		ManagerConnectionFactory factory = new ManagerConnectionFactory(this.hostname, this.port, this.username, this.password);
		this.managerConnection = factory.createManagerConnection();
	}
	
	
	@SuppressWarnings("deprecation")
	public Boolean autodialCall(Integer id, String msisdn, String autodialGroup){
		OriginateAction originateAction;
        ManagerResponse originateResponse;
        
        originateAction = new OriginateAction();
        originateAction.setChannel("Local/" + msisdn + "@gsm-out");
        originateAction.setCallerId(msisdn);
        originateAction.setContext("autodial");
        originateAction.setExten(msisdn);
        originateAction.setVariable("extID", id.toString());
        originateAction.setVariable("autodialGroup", autodialGroup);
        originateAction.setPriority(1);
        originateAction.setAsync(true);
 
        try {
        	// connect to Asterisk and log in
			managerConnection.login();
			
			// send the originate action and wait for a maximum of 30 seconds for Asterisk to send a reply
			originateResponse = managerConnection.sendAction(originateAction, 60000);
			
			// and finally log off and disconnect
			managerConnection.logoff();
		} catch (IllegalStateException | IOException
				| AuthenticationFailedException | TimeoutException e) {
			e.printStackTrace();
			return false;
		}
        
        return true;
	}
}
