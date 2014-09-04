/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.bean;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.Reference;

/**
 *
 * @author vpryimak
 */
public class JNDIBean {

    private static Map<String, String> jndi = new HashMap<>();

    public static String getContextValue(String jndiName, String value) {

        try {
            Context ctx = new InitialContext();
//            NamingEnumeration<NameClassPair> callFileLocation = ctx.list("");
//            while (callFileLocation.hasMore()) {
//                System.out.println("JNDI Resources: " + callFileLocation.next().getName());
//            }

            NamingEnumeration neb = ctx.listBindings("");
//            System.out.println(neb);
            while (neb.hasMore()) {
                Binding b = (Binding) neb.next();
                if (b.getName().equals(jndiName)) {
                    try {
                        Reference obj = (Reference) b.getObject();
                        java.util.Enumeration en = obj.getAll();
                        while (en.hasMoreElements()) {
                            javax.naming.RefAddr ra = (javax.naming.RefAddr) en.nextElement();
//                        System.out.println(ra.getType() + "=" + ra.getContent());
                            jndi.put(ra.getType(), (String) ra.getContent());
                        }
                    } catch (ClassCastException e) {
                        Logger.getLogger(JNDIBean.class.getName()).log(Level.SEVERE, "JNDI Resource reload needed!", e);
                        new InitialContext().lookup(jndiName);
                    }
                }
            }
        } catch (NamingException e) {
            Logger.getLogger(JNDIBean.class.getName()).log(Level.SEVERE, null, e);
        }

        if (value.equals("")) {
            return null;
        } else {
            return jndi.get(value);
        }
    }
}
