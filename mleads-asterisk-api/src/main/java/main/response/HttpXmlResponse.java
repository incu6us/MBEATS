/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.response;

/**
 *
 * @author vpryimak
 */
public class HttpXmlResponse {
    
    private static String respCode = "911";
    private static String msisdn = "n/a";
    private static String callerID = "n/a";
    private static String localSip = "n/a";
    
    public static String getCallResponse(String respCode, String msisdn, String callerID, String localSip){
        String response = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                + "<response>"
                + "<status>" + respCode + "</status>"
                + "<msisdn>" + msisdn + "</msisdn>"
                + "<callerid>" + callerID + "</callerid>"
                + "<local>" + localSip + "</local>"
                + "</response>";
        
        return response;
    }

}
