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
public class DiagnosisMap {

    private static final DiagnosisMap INSTANCE = new DiagnosisMap();
    private HashMap<String, Integer> codeMap = new HashMap<>(300);

    private DiagnosisMap() {
        codeMap.put("Albinism", 2);
        codeMap.put("\"Metamorphopsia\"", 71);
        codeMap.put("ACHM Likely", 1);
        codeMap.put("ACHM, carrier", 1);
        codeMap.put("ACHM, suspect", 1);
        codeMap.put("Achromatopsia", 1);
        codeMap.put("Acquired Optic Disc Pit", 86);
        codeMap.put("AD", 117);
        codeMap.put("aging control", 117);
        codeMap.put("Aging MCI", 117);
        codeMap.put("AIR", 12);
        codeMap.put("Albinism", 2);
        codeMap.put("Albinism Carrier", 2);
        codeMap.put("Alzheimer's", 15);
        codeMap.put("Alzheimer's Disease", 15);
        codeMap.put("AMD", 17);
        codeMap.put("AMD, PCIOL", 17);
        codeMap.put("AMN", 18);
        codeMap.put("Aniridia", 19);
        codeMap.put("Anisometropia", 20);
        codeMap.put("Asteroid Hyalosis", 117);
        codeMap.put("Astrocytic Hamartoma", 117);
        codeMap.put("Atypical Optic Neuritis", 88);
        codeMap.put("AZOOR", 21);
        codeMap.put("Bardet-Biedl Syndrome", 23);
        codeMap.put("BCM", 24);
        codeMap.put("BCM Carrier", 24);
        codeMap.put("BDR", 117);
        codeMap.put("BED", 117);
        codeMap.put("BED Carrier", 117);
        codeMap.put("Best Disease", 6);
        codeMap.put("Bilateral Maculopathy", 117);
        codeMap.put("Birdshot", 27);
        codeMap.put("Birdshot Retinopathy HLA-A29+", 27);
        codeMap.put("Bradyopsia", 28);
        codeMap.put("BRAO", 29);
        codeMap.put("BRAO Inf", 29);
        codeMap.put("BRVO", 30);
        codeMap.put("Bullseye Maculopathy", 31);
        codeMap.put("CAR", 32);
        codeMap.put("Carrier", 117);
        codeMap.put("CB", 117);
        codeMap.put("CB Carrier", 117);
        codeMap.put("CB carrier?", 117);
        codeMap.put("Central Scotoma", 117);
        codeMap.put("Chemotherapy", 117);
        codeMap.put("Choroideremia", 35);
        codeMap.put("Choroideremia - Suspected", 35);
        codeMap.put("Choroideremia Carrier", 35);
        codeMap.put("Cilioretinal Artery Occlusion", 117);
        codeMap.put("CME", 36);
        codeMap.put("CNV S/P Lucentis", 38);
        codeMap.put("Coat's?", 117);
        codeMap.put("Color Blind", 117);
        codeMap.put("Color Vision Deficiency", 39);
        codeMap.put("Commotio", 39);
        codeMap.put("Commotio Retinae", 40);
        codeMap.put("Cone Dystrophy", 42);
        codeMap.put("Cone Rod Dystrophy", 45);
        codeMap.put("CRAO", 46);
        codeMap.put("CRVO", 117);
        codeMap.put("CSC (resolving)", 47);
        codeMap.put("CSNB", 79);
        codeMap.put("CSNB - Oguchi", 17);
        codeMap.put("CSNB carrier (normal)", 49);
        codeMap.put("CSR", 49);
        codeMap.put("CSR (Active)", 49);
        codeMap.put("CSR (Chronic)", 49);
        codeMap.put("CSR (Recovered)", 49);
        codeMap.put("CSR Followup", 49);
        codeMap.put("Deutan Normal", 117);
        codeMap.put("Diabetic Macular Edema", 54);
        codeMap.put("Diabetic Retinopathy", 7);
        codeMap.put("DM w/o DR", 117);
        codeMap.put("DME", 54);
        codeMap.put("Doyne's Honeycomb Dystrophy", 55);
        codeMap.put("Drusen", 56);
        codeMap.put("Drusen Pre Stem-Cell therapy", 117);
        codeMap.put("Epiretinal membrane", 57);
        codeMap.put("Focal laser (DME)", 117);
        codeMap.put("Foveal Hypoplasia", 58);
        codeMap.put("Functional Imaging", 117);
        codeMap.put("Geographic Atrophy", 60);
        codeMap.put("Glaucoma", 9);
        codeMap.put("Glaucoma Suspect", 61);
        codeMap.put("Gyrate Atrophy", 59);
        codeMap.put("High Myopia", 74);
        codeMap.put("Histoplasmosis Scarring", 10);
        codeMap.put("iCSNB", 47);
        codeMap.put("Idiopathic Cone Dystrophy", 117);
        codeMap.put("IRVAN", 62);
        codeMap.put("late onset PR degeneration", 117);
        codeMap.put("LCA", 64);
        codeMap.put("LCHAD", 117);
        codeMap.put("LHON", 65);
        codeMap.put("Lightning burn", 66);
        codeMap.put("Low Vision?", 117);
        codeMap.put("LVAVA", 117);
        codeMap.put("Mac Hole", 67);
        codeMap.put("Mac Hole (spontaneously resolved)", 67);
        codeMap.put("Mac Hole Repair OD", 67);
        codeMap.put("Mac Tel", 68);
        codeMap.put("Macular Hole", 67);
        codeMap.put("Macular Hole (pre-op)", 67);
        codeMap.put("Maculopathy", 117);
        codeMap.put("Marshfield AMD", 17);
        codeMap.put("MCI", 117);
        codeMap.put("MCI/AD", 117);
        codeMap.put("MEWDS", 72);
        codeMap.put("Microscotoma", 70);
        codeMap.put("Multiple Sclerosis", 73);
        codeMap.put("Myopia", 74);
        codeMap.put("Myopia +++", 74);
        codeMap.put("Normal", 75);
        codeMap.put("Normal?", 75);
        codeMap.put("OA", 78);
        codeMap.put("OA1 Carrier", 78);
        codeMap.put("OCA", 76);
        codeMap.put("OCA2", 76);
        codeMap.put("Occult Macular Dystrophy", 77);
        codeMap.put("Occult Maculopathy", 117);
        codeMap.put("Ocular Albinism", 78);
        codeMap.put("Ocular Albinism Carrier", 78);
        codeMap.put("Ocular Trauma", 39);
        codeMap.put("OD Scotoma", 117);
        codeMap.put("Oguchi", 79);
        codeMap.put("Old Normal", 75);
        codeMap.put("Oligocone Trichromacy", 80);
        codeMap.put("ONH Drusen", 117);
        codeMap.put("ONH Dysplasia", 82);
        codeMap.put("Optic Atrophy", 83);
        codeMap.put("Optic Neuritis", 88);
        codeMap.put("OS scotoma, OD ganglion cell loss", 117);
        codeMap.put("Parkinson's Disease", 89);
        codeMap.put("Partial Colorblindness", 117);
        codeMap.put("Pascal Laser", 117);
        codeMap.put("Pattern Dystrophy", 90);
        codeMap.put("PED", 91);
        codeMap.put("PED and AMD", 91);
        codeMap.put("PIC", 92);
        codeMap.put("Pigmentary Retinopathy", 93);
        codeMap.put("Plaquenil", 94);
        codeMap.put("Plaquenil & Sjogren's", 94);
        codeMap.put("Plaquenil Drugose", 94);
        codeMap.put("Plaquenil Toxicity", 94);
        codeMap.put("plaquenil-normal", 94);
        codeMap.put("PMB", 95);
        codeMap.put("POHS", 117);
        codeMap.put("Poosible Cone Dystrophy", 117);
        codeMap.put("Possible CHM carrier", 117);
        codeMap.put("Post Stem-Cell Therapy", 117);
        codeMap.put("post-laser surgery imaging", 117);
        codeMap.put("Protan Color Vision Defect", 117);
        codeMap.put("Protanope", 117);
        codeMap.put("R/G Color Vision Defect", 96);
        codeMap.put("Repair Macular RD", 117);
        codeMap.put("Reticular Dystrophy of Retinitis PigmentosaE", 117);
        codeMap.put("Retinal detachment", 99);
        codeMap.put("Retinitis Pigmentosa", 4);
        codeMap.put("Retinitis Pigmentosa, AD, affected", 4);
        codeMap.put("Retinitis Pigmentosa, atypical?", 4);
        codeMap.put("Retinitis Pigmentosa, Simplex, affected", 4);
        codeMap.put("Retinitis Pigmentosa, suspected", 4);
        codeMap.put("Retinitis Pigmentosa, XL, affected", 4);
        codeMap.put("Retinitis Pigmentosa, XL, carrier", 4);
        codeMap.put("Retinitis PigmentosaE atrophy", 4);
        codeMap.put("Retinopathy of Prematurity", 117);
        codeMap.put("Rod Cone Dystrophy", 100);
        codeMap.put("Rubella Retinopathy", 108);
        codeMap.put("s/p mac hole repair ", 67);
        codeMap.put("scleral buckle", 109);
        codeMap.put("Scotoma (OS)", 117);
        codeMap.put("Serpiginous Choroiditis", 110);
        codeMap.put("Sickle trait complication", 111);
        codeMap.put("Stargardt's", 113);
        codeMap.put("Stargardt's Carrier", 113);
        codeMap.put("TBI", 114);
        codeMap.put("Traumatic Retinal Detachment", 39);
        codeMap.put("Unexplained Vision Loss", 115);
        codeMap.put("Unexplained Visual Loss", 115);
        codeMap.put("unilateral cone dysfunction", 116);
        codeMap.put("Unknown", 117);
        codeMap.put("Ushers", 8);
        codeMap.put("Usher's", 8);
        codeMap.put("Ushers - Suspected", 8);
        codeMap.put("Ushers 2 (possible)", 8);
        codeMap.put("Vision loss", 117);
        codeMap.put("Visual Disturbance", 119);
        codeMap.put("Visual Dysfunction", 117);
        codeMap.put("Vitamin A deficiency", 120);
        codeMap.put("Vitreomacular Traction OS", 121);
        codeMap.put("Vitreomacular Traction OU", 121);
        codeMap.put("VMT w/ macular hole OD", 121);
        codeMap.put("XL Retinoschisis", 122);
        codeMap.put("X-Linked Cone Dystrophy", 40);
        codeMap.put("X-Linked Myopia", 74);
        codeMap.put("XLRetinitis Pigmentosa", 4);
        codeMap.put("Cataracts", 33);
        codeMap.put("Cataracts OU", 33);
    }

    public static final DiagnosisMap getInstance() {
        return INSTANCE;
    }

    public Integer getDiagnosisCode(String dx) {
        dx = dx.trim();
        if (dx.isEmpty()) {
            return null;
        } else if (!codeMap.containsKey(dx)) {
            throw new IllegalArgumentException("Unknown diagnosis: " + dx);
        } else {
            return codeMap.get(dx);
        }
    }
}
