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
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class ProviderMap {

    private static final ProviderMap INSTANCE = new ProviderMap();
    private HashMap<String, Integer> codeMap = new HashMap<>(300);

    private ProviderMap() {
        codeMap.put("AGTC", 22);
        codeMap.put("Carroll", 2);
        codeMap.put("Connor", 4);
        codeMap.put("Costakos", 5);
        codeMap.put("Dubra", 3);
        codeMap.put("Fishman", 20);
        codeMap.put("Goveas", 6);
        codeMap.put("Han", 7);
        codeMap.put("Harris", 8);
        codeMap.put("Heuer", 9);
        codeMap.put("Kay", 21);
        codeMap.put("Kim", 10);
        codeMap.put("Martinez", 11);
        codeMap.put("Robison", 12);
        codeMap.put("Salim", 13);
        codeMap.put("Stepien", 1);
        codeMap.put("Walsh", 14);
        codeMap.put("Warren", 15);
        codeMap.put("Wels", 16);
        codeMap.put("Weinberg", 17);
        codeMap.put("Wilkes", 18);
        codeMap.put("Wirostko", 19);
        codeMap.put("F Collison", 23);
    }

    public static final ProviderMap getInstance() {
        return INSTANCE;
    }

    public void addProvidersToDb(Connection db) {
        try {
            int nextId;
            try (Statement stat = db.createStatement()) {
                ResultSet rs = stat.executeQuery("select max(PROVIDER_ID) from PROVIDER");
                rs.first();
                nextId = rs.getInt(1) + 1;
            }

            try (PreparedStatement istat = db.prepareStatement("insert into PROVIDER (PROVIDER_ID, PROVIDER_TYPE_ID, LNAME, CREATED_BY, CREATED_DATE) values (?,?,?,?,?)")) {
                for (Entry<String, Integer> p : codeMap.entrySet()) {
                    if (p.getValue() < nextId) {
                        continue;
                    }
                    istat.setInt(1, p.getValue());
                    istat.setInt(2, 1);
                    istat.setString(3, p.getKey().replaceFirst("[A-Za-z]+ ", ""));
                    istat.setString(4, "BWILK");
                    istat.setDate(5, new Date(new java.util.Date().getTime()));
                    if (istat.executeUpdate() != 1) {
                        throw new SQLException("Code addition failed for provider: " + p.getKey().replaceFirst("[A-Za-z]+ ", ""));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosisMap.class.getName()).log(Level.SEVERE, "Failed to add new provider to database", ex);
            System.exit(2);
        }
    }

    public Integer getProviderCode(String dx) {
        if (dx == null) {
            return null;
        }
        dx = dx.trim();
        if (dx.isEmpty()) {
            return null;
        } else if (!codeMap.containsKey(dx)) {
            throw new IllegalArgumentException("Unknown provider: " + dx);
        } else {
            return codeMap.get(dx);
        }
    }
}
