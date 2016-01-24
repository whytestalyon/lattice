/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class PersonnelMap {

    private static final HashMap<String, Integer> pmap = new HashMap<>(20);

    static {
        pmap.put("NICU", 111);
        pmap.put("M Goldberg", 106);
        pmap.put("K Packard", 12);
        pmap.put("J Jones", 112);
        pmap.put("K McKenney", -1);
        pmap.put("C Skumatz", 105);
        pmap.put("P Summerfelt", 108);
    }

    public static void syncPersonnelMapWithDB(Connection db) {
        pmap.forEach((name, id) -> {
            try {
                boolean idExists;
                try (Statement stat = db.createStatement()) {
                    ResultSet rs = stat.executeQuery("select count(*) as CNT from PERSONNEL where personnel_id = " + id);
                    rs.first();
                    idExists = rs.getInt("CNT") > 0;
                }
                if (!idExists) {
                    int nextId;
                    try (Statement stat = db.createStatement()) {
                        ResultSet rs = stat.executeQuery("select max(personnel_id) from PERSONNEL");
                        rs.first();
                        nextId = rs.getInt(1) + 1;
                    }
                    try (PreparedStatement stat = db.prepareStatement("INSERT\n"
                            + "INTO\n"
                            + "  PERSONNEL\n"
                            + "  (\n"
                            + "    PERSONNEL_ID,\n"
                            + "    FNAME,\n"
                            + "    LNAME,\n"
                            + "    CREATED_BY,\n"
                            + "    CREATED_DATE\n"
                            + "  )\n"
                            + "  VALUES\n"
                            + "  (\n"
                            + "    " + nextId + ",\n"
                            + "    '" + name.split(" ")[0] + "',\n"
                            + "    '" + name.split(" ")[1] + "',\n"
                            + "    'BWILK',\n"
                            + "    ?\n"
                            + "  )")) {
                        stat.setDate(1, new Date(new java.util.Date().getTime()));
                        stat.executeUpdate();
                    }
                    pmap.put(name, nextId);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PersonnelMap.class.getName()).log(Level.SEVERE, "Syncing personnel data failed.", ex);
                System.exit(2);
            }
        });
        //add in last mapping value because didn't want it to be added to database
        pmap.put("Done at Neurology", 112);
    }

    public static Integer getPersonnelId(String personnel) {
        return pmap.get(personnel);
    }
}
