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
import main.response.GetInfoXmlResponse;

/**
 * REST Web Service
 *
 * @author vpryimak
 */
@Path("{mleadsid}")
@RequestScoped
public class MLeadsIDResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MLeadsID
     */
    public MLeadsIDResource() {
    }

    /**
     * Retrieves representation of an instance of main.call.MLeadsIDResource
     *
     * @param mleadsId
     * @return an instance of java.lang.String
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    @GET
    @Path("xml")
    @Produces("application/xml")
    public String getXml(@PathParam("mleadsid") String mleadsId) throws ClassNotFoundException, SQLException {
        //TODO return proper representation object
        StringBuffer res = new StringBuffer();

        try {
            String location = JNDIBean.getContextValue("MLeadsAPI", "CallFileLocation");
            String jdbc = JNDIBean.getContextValue("MLeadsAPI", "jdbc-url");
            String audioUrl = JNDIBean.getContextValue("MLeadsAPI", "audioURL");
            String audioPath = JNDIBean.getContextValue("MLeadsAPI", "audioPath");
            String audioExt = JNDIBean.getContextValue("MLeadsAPI", "audioExtension");

            DBConnection db = new DBConnection(jdbc);
            db.connect();

            List<String[]> result = db.exec(mleadsId);

            db.disconnect();

            GetInfoXmlResponse getInfo = new GetInfoXmlResponse();

            List<String> retVal = getInfo.infoXml(result, audioPath, audioUrl, audioExt);

            for (String s : retVal) {
                res.append(s);
            }

        } catch (java.lang.IndexOutOfBoundsException e) {
//            XML out
            res.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                    + "<response>"
                    + "<id>null</id>"
                    + "</response>");
        }

        return res.toString();
    }

    @GET
    @Path("json")
    @Produces("application/json")
    public String getJson(@PathParam("mleadsid") String mleadsId) throws ClassNotFoundException, SQLException {
        //TODO return proper representation object
        StringBuffer res = new StringBuffer();

        try {
            String location = JNDIBean.getContextValue("MLeadsAPI", "CallFileLocation");
            String jdbc = JNDIBean.getContextValue("MLeadsAPI", "jdbc-url");
            String audioUrl = JNDIBean.getContextValue("MLeadsAPI", "audioURL");
            String audioPath = JNDIBean.getContextValue("MLeadsAPI", "audioPath");
            String audioExt = JNDIBean.getContextValue("MLeadsAPI", "audioExtension");

            DBConnection db = new DBConnection(jdbc);
            db.connect();

            List<String[]> result = db.exec(mleadsId);

            db.disconnect();

            GetInfoXmlResponse getInfo = new GetInfoXmlResponse();

            List<String> retVal = getInfo.infoJson(result, audioPath, audioUrl, audioExt);

            for (String s : retVal) {
                res.append(s);
            }

        } catch (java.lang.IndexOutOfBoundsException e) {
//            JSON out
            res.append("{"
                    + "\"response\": {"
                    + "\"id\":\"null\""
                    + "}"
                    + "}");
        }

        return res.toString();
    }
}
