/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vpryimak
 */
public class DBConnection {

    private String url = null;

    private Connection c = null;
    private PreparedStatement stmt = null;

    public DBConnection(String url) {
        this.url = url;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        String url = this.url;

        Class.forName("com.mysql.jdbc.Driver");
        c = DriverManager.getConnection(url);
    }

    public List<String[]> exec(String userFieldID) throws SQLException {
        stmt = c.prepareStatement("SELECT calldate, src, dst, lastapp, duration, billsec, disposition, uniqueid, userfield FROM asterisk.cdr WHERE userfield = ? ORDER BY calldate");
        stmt.setString(1, userFieldID);
        ResultSet rs = stmt.executeQuery();

        List<String[]> result = new ArrayList<String[]>();
        String resultLine[] = null;
        
        while (rs.next()) {
            resultLine = new String[9];
            resultLine[0] = rs.getString("userfield");
            resultLine[1] = rs.getString("calldate");
            resultLine[2] = rs.getString("src");
            resultLine[3] = rs.getString("dst");
            resultLine[4] = rs.getString("lastapp");
            resultLine[5] = rs.getString("duration");
            resultLine[6] = rs.getString("billsec");
            resultLine[7] = rs.getString("disposition");
            resultLine[8] = rs.getString("uniqueid");
            result.add(resultLine);
        }

        return result;
    }
    
    public List<String> execSipInfo(String group) throws SQLException {
        stmt = c.prepareStatement("select usrs.username from sip_groups grp \n" +
"left join user_lnk_group lnk on grp.id=lnk.group_id\n" +
"left join sip_users usrs on lnk.user_id=usrs.id where grp.group_name=? and usrs.ipaddr!='' order by usrs.username");
        stmt.setString(1, group);
        ResultSet rs = stmt.executeQuery();

        List<String> result = new ArrayList<String>();
        String resultLine = null;
        
        while (rs.next()) {
            resultLine = rs.getString("username");
            result.add(resultLine);
        }

        return result;
    }

    public void fraudInsertMsisdn(Integer id, String msisdn){
        try {
            stmt = c.prepareStatement("INSERT INTO asterisk.autodial_msisdns (mbeats_id, msisdn) VALUES (?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, msisdn);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void autodialInsertMsisdn(Integer id, String msisdn){
        try {
            stmt = c.prepareStatement("INSERT INTO autodial (id, msisdn) VALUES (?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, msisdn);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconnect() throws SQLException {
        stmt.close();
        c.close();
    }
}
