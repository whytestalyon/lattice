/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import java.util.HashMap;

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
    }

    public static final ProviderMap getInstance() {
        return INSTANCE;
    }

    public Integer getProviderCode(String dx) {
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
