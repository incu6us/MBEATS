/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.call;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import main.bean.JNDIBean;
import main.dao.DBConnection;

/**
 *
 * @author vpryimak
 */
@Path("fraud")
@RequestScoped
public class FraudCheck {

    @Context
    private UriInfo context;
    
    public FraudCheck(){
    }
    
    @GET
    @Path("{mleadsid}/{msisdn}")
    @Produces("application/json")
    public String setNumberToCheck(@PathParam("mleadsid") Integer mleadsID, @PathParam("msisdn") String msisdn) {
        String jdbc = JNDIBean.getContextValue("MLeadsAPI", "jdbc-url");

        DBConnection db = new DBConnection(jdbc);
        try {
            db.connect();
            db.fraudInsertMsisdn(mleadsID, msisdn);
            db.disconnect();
            
            return "{\"status\":\"ok\",\"order_id\":\""+mleadsID+"\"}";
        } catch (ClassNotFoundException | SQLException ex) {
            return "{\"status\":\"error\",\"order_id\":\""+mleadsID+"\"}";
        }
        
    }
}
