/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.call;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author vpryimak
 */
@javax.ws.rs.ApplicationPath("call")
public class CallApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        // following code can be used to customize Jersey 2.0 JSON provider:
        try {
            Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
            // Class jsonProvider = Class.forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature");
            // Class jsonProvider = Class.forName("org.glassfish.jersey.jettison.JettisonFeature");
            resources.add(jsonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically re-generated by NetBeans REST support to populate
     * given list with all resources defined in the project.
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(main.call.FraudCheck.class);
        resources.add(main.call.GetStatForSip.class);
        resources.add(main.call.MLeadsIDResource.class);
        resources.add(main.call.SipToMSISDNResource.class); 
        resources.add(main.call.AutoDial.class); 
    }
    
}




