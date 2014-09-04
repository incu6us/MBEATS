/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.call;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import main.bean.JNDIBean;
import main.fwrite.FWriter;
import main.response.HttpXmlResponse;

/**
 * REST Web Service
 *
 * @author vpryimak
 */
@Path("{sip}/{mleadsid}/{msisdn:.*}")
@RequestScoped
public class SipToMSISDNResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SipToMSISDNResource
     */
    public SipToMSISDNResource() {
    }

    /**
     * Retrieves representation of an instance of main.SipToMSISDNResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/xml")
    public String getXml(@PathParam("sip") String sip, @PathParam("mleadsid") String mleadsID, @PathParam("msisdn") String msisdn) {
        //TODO return proper representation object
        try {
            Date date = new Date();
            Integer fileNameHash = date.hashCode();
            String fileName = fileNameHash.toString() + ".call";

            String location = JNDIBean.getContextValue("MLeadsAPI", "CallFileLocation");
            String jdbc = JNDIBean.getContextValue("MLeadsAPI", "jdbc-url");

            String local_sip = new Integer(sip).toString();

            if (!msisdn.equals("")) {
                if (location != null) {
                    if (FWriter.getWriter(location + "/" + fileName,
                            "Channel: SIP/" + local_sip + "\r\n"
                            + "CallerID: " + (msisdn.contains("/") ? msisdn.split("/")[1] : msisdn) + "\r\n"
                            + "MaxRetries: 0\r\n"
                            + "RetryTime: 1\r\n"
                            + "WaitTime: 20\r\n"
                            + "Context: gsm-out\r\n"
                            + "SetVar: mleadsID="+mleadsID+"\r\n"
                            + "Extension: " + (msisdn.contains("/") ? msisdn.split("/")[0].replaceAll("\\s|-|(|)", "") : msisdn.replaceAll("\\s|-|(|)", "")) + "\r\n"
                            + "Priority: 1")) {

                        // Ok
                        Logger.getLogger(SipToMSISDNResource.class.getName()).log(Level.SEVERE, "Calling... (code: 0): "+local_sip+" - "+msisdn, "");
                        return HttpXmlResponse.getCallResponse("0", (msisdn.contains("/") ? msisdn.split("/")[0].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), (msisdn.contains("/") ? msisdn.split("/")[1].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), local_sip.replaceAll("<|>", "_"));
                    } else {
                        // can't read location JNDI
                        Logger.getLogger(SipToMSISDNResource.class.getName()).log(Level.SEVERE, "Can't read JNDI (error_code: 1)", "");
                        return HttpXmlResponse.getCallResponse("1", (msisdn.contains("/") ? msisdn.split("/")[0].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), (msisdn.contains("/") ? msisdn.split("/")[1].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), local_sip.replaceAll("<|>", "_"));
                    }
                } else {
                    // can't write file for calling
                    Logger.getLogger(SipToMSISDNResource.class.getName()).log(Level.SEVERE, "Can't write call-file (error_code: 2)", "");
                    return HttpXmlResponse.getCallResponse("2", (msisdn.contains("/") ? msisdn.split("/")[0].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), (msisdn.contains("/") ? msisdn.split("/")[1].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), local_sip.replaceAll("<|>", "_"));
                }
            } else {
                // MSISDN is null
                Logger.getLogger(SipToMSISDNResource.class.getName()).log(Level.SEVERE, "MSISDN is null (error_code: 3)", "");
                return HttpXmlResponse.getCallResponse("3", "n/a", "n/a", local_sip.replaceAll("<|>", "_"));
            }
        } catch (NumberFormatException e) {
            // SIP number contains string
            Logger.getLogger(SipToMSISDNResource.class.getName()).log(Level.SEVERE, "SIP number contains a string (error_code: 4):", e);
            return HttpXmlResponse.getCallResponse("4", (msisdn.contains("/") ? msisdn.split("/")[0].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), (msisdn.contains("/") ? msisdn.split("/")[1].replaceAll("<|>", "_") : msisdn.replaceAll("<|>", "_")), "n/a");
        } catch (Exception e) {
            // other exceptions
            Logger.getLogger(SipToMSISDNResource.class.getName()).log(Level.SEVERE, "Exception (error_code: 911):", e);
            return HttpXmlResponse.getCallResponse("911", "n/a", "n/a", "n/a");
        }
    }
}
