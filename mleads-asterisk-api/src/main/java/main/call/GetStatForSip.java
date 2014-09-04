/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.call;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import main.bean.JNDIBean;
import main.dao.DBConnection;
import main.utils.SysExec;

/**
 * REST Web Service
 *
 * @author vpryimak
 */
@Path("sipinfo")
@RequestScoped
public class GetStatForSip {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SipStat
     */
    public GetStatForSip() {
    }

    /**
     * Retrieves representation of an instance of main.call.GetStatForSip
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{groupForStat}")
    @Produces("application/json")
    public String getXml(@PathParam("groupForStat") String group) {
        List<String> sipsOnline = new ArrayList<>();
        int numElements = 0;
        int inAction = 0;
        int standby = 0;
        String opersArr = "";
        
        try {
            String jdbc = JNDIBean.getContextValue("MLeadsAPI", "jdbc-url");
            String script = JNDIBean.getContextValue("MLeadsAPI", "GetStatForSip");
            
            DBConnection db = new DBConnection(jdbc);
            db.connect();
            sipsOnline = db.execSipInfo(group);
            db.disconnect();

            numElements = sipsOnline.size();
            
            for(int i = 0; i<numElements; i++){
                if(i == 0){
                    opersArr += sipsOnline.get(i);
                }else{
                    opersArr += "|"+sipsOnline.get(i);
                }
            }
            
            try{
                inAction = Integer.parseInt(SysExec.execute(script+" inaction \""+opersArr+"\"").replace("\n", ""));
            }catch(NumberFormatException e){
                inAction = 0;
                java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.INFO, null, e);
            }
            
            try{
                standby = numElements - inAction;
            }catch(Exception e){
                standby = 0;
                java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.INFO, null, e);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetStatForSip.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetStatForSip.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "{\"online\": \""+numElements+"\", \"inaction\" : \""+inAction+"\", \"standby\" : \""+standby+"\"}";
    }

}
