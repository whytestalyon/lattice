/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.exam;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public enum ExamType {

    AUTOREFRACTION(1),
    OHQ(2),
    PEDIGREE(3),
    OCTOPUS(4),
    OPKO(5),
    OCT_CIRRUS_UNDILATED(14),
    OCT_OPTOVUE(133),
    COLOR_NEITZ(15),
    COLOR_AOHRR(16),
    IOL_MASTER(18),
    CONTRAST_SENSITIVITY(22),
    DILATION(24),
    COLOR_D15(28),
    COLOR_ISHIHARA(30),
    COLOR_100HUE(32),
    COLOR_RAYLEIGH(33),
    STEREO_VISION(35),
    SKIN_PIGMENT(36),
    LUNCH(44),
    HUMPHREY_VISUAL_FIELD(45),
    AMSLER_GRID(48),
    VISUAL_ACUITY(53),
    OCT_CIRRUS_DILATED(55),
    FUNDUS_OPTOS(73),
    AO_SLO_2_2_IMAGING(79),
    AO_SLO_2_2_NOTES(80),
    AO_SLO_3_0_IMAGING(83),
    AO_SLO_3_0_NOTES(84),
    GENETICS_COLLECT(90),
    GENETICS_SHIP(91),
    AO_SLO_2_0_IMAGING(106),
    AO_SLO_2_0_NOTES(108),
    AO_SLO_2_1_IMAGING(111),
    AO_SLO_2_1_NOTES(112),
    AO_FLOOD_1_0_IMAGING(114),
    AO_FLOOD_1_0_NOTES(117),
    MD_VISIT(118),
    OCT_BIOPTIGEN(124),
    FUNDUS_VISUCAM(125),
    AF_OPTOS(127),
    COLOR_CAD(128),
    CONSENT(129),
    SPECTRALIS(130),
    AO_FLOOD_1_1_IMAGING(131),
    AO_FLOOD_1_1_NOTES(132),
    AO_SLO_4_0_IMAGING(133),
    AO_SLO_4_0_NOTES(134),
    AO_MEH_IMAGING(-1),
    AO_MEH_NOTES(-1),
    AO_NYEEI_IMAGING(-1),
    AO_NYEEI_NOTES(-1);

    private final int id;

    private ExamType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
