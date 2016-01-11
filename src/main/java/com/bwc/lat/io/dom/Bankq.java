/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class Bankq {

    private static final AtomicInteger idCntr = new AtomicInteger(200);
    private final int bankq_id;
    private String ethnicity = null;
    private String race_indian = null;
    private String race_asian = null;
    private String race_hawaiian = null;
    private String race_black = null;
    private String race_white = null;
    private String race_unreported = null;

    public Bankq() {
        bankq_id = idCntr.getAndIncrement();
    }

    public int getBankq_id() {
        return bankq_id;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getRace_indian() {
        return race_indian;
    }

    public void setRace_indian(String race_indian) {
        this.race_indian = race_indian;
    }

    public String getRace_asian() {
        return race_asian;
    }

    public void setRace_asian(String race_asian) {
        this.race_asian = race_asian;
    }

    public String getRace_hawaiian() {
        return race_hawaiian;
    }

    public void setRace_hawaiian(String race_hawaiian) {
        this.race_hawaiian = race_hawaiian;
    }

    public String getRace_black() {
        return race_black;
    }

    public void setRace_black(String race_black) {
        this.race_black = race_black;
    }

    public String getRace_white() {
        return race_white;
    }

    public void setRace_white(String race_white) {
        this.race_white = race_white;
    }

    public String getRace_unreported() {
        return race_unreported;
    }

    public void setRace_unreported(String race_unreported) {
        this.race_unreported = race_unreported;
    }

    public void merge(Bankq mergeIn) {
        if (ethnicity == null) {
            ethnicity = mergeIn.ethnicity;
        }
        if (race_asian == null) {
            race_asian = mergeIn.race_asian;
        }
        if (race_hawaiian == null) {
            race_hawaiian = mergeIn.race_hawaiian;
        }
        if (race_black == null) {
            race_black = mergeIn.race_black;
        }
        if (race_indian == null) {
            race_indian = mergeIn.race_indian;
        }
        if (race_unreported == null) {
            race_unreported = mergeIn.race_indian;
        }
        if (race_white == null) {
            race_white = mergeIn.race_white;
        }
    }

    @Override
    public String toString() {
        return "Bankq{" + "bankq_id=" + bankq_id + ", ethnicity=" + ethnicity + ", race_indian=" + race_indian + ", race_asian=" + race_asian + ", race_hawaiian=" + race_hawaiian + ", race_black=" + race_black + ", race_white=" + race_white + ", race_unreported=" + race_unreported + '}';
    }

}
