/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.exc;

import java.util.HashMap;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public enum ExamColumn {

    STAFF_CONSENTING(29),
    OCCULAR_HEALTH_QUESTIONAIRE(35),
    NEITZ(64),
    AOHRR_TYPE(65),
    AOHRR_DVALUE(66),
    D15_SATURATED_OD_TRIAL_1_MOI_ANGLE(67),
    D15_SATURATED_OD_TRIAL_1_MOI_MAJOR_RADIUS(68),
    D15_SATURATED_OD_TRIAL_1_MOI_MINOR_RADIUS(69),
    D15_SATURATED_OD_TRIAL_1_MOI_TOTAL_ERROR_SCORE(70),
    D15_SATURATED_OD_TRIAL_1_MOI_SELECTIVITY_INDEX(71),
    D15_SATURATED_OD_TRIAL_1_MOI_CONFUSION_INDEX(72),
    D15_SATURATED_OD_TRIAL_1_MOI_COLOR_DISCRIMINATON(73),
    D15_SATURATED_OD_TRIAL_1_MOI_NOTES(74),
    D15_SATURATED_OD_TRIAL_2_MOI_ANGLE(75),
    D15_SATURATED_OD_TRIAL_2_MOI_MAJOR_RADIUS(76),
    D15_SATURATED_OD_TRIAL_2_MOI_MINOR_RADIUS(77),
    D15_SATURATED_OD_TRIAL_2_MOI_TOTAL_ERROR_SCORE(78),
    D15_SATURATED_OD_TRIAL_2_MOI_SELECTIVITY_INDEX(79),
    D15_SATURATED_OD_TRIAL_2_MOI_CONFUSION_INDEX(80),
    D15_SATURATED_OD_TRIAL_2_MOI_COLOR_DISCRIMINATON(81),
    D15_SATURATED_OD_TRIAL_2_MOI_NOTES(82),
    D15_SATURATED_OS_TRIAL_1_MOI_ANGLE(83),
    D15_SATURATED_OS_TRIAL_1_MOI_MAJOR_RADIUS(84),
    D15_SATURATED_OS_TRIAL_1_MOI_MINOR_RADIUS(85),
    D15_SATURATED_OS_TRIAL_1_MOI_TOTAL_ERROR_SCORE(86),
    D15_SATURATED_OS_TRIAL_1_MOI_SELECTIVITY_INDEX(87),
    D15_SATURATED_OS_TRIAL_1_MOI_CONFUSION_INDEX(88),
    D15_SATURATED_OS_TRIAL_1_MOI_COLOR_DISCRIMINATON(89),
    D15_SATURATED_OS_TRIAL_1_MOI_NOTES(90),
    D15_SATURATED_OS_TRIAL_2_MOI_ANGLE(91),
    D15_SATURATED_OS_TRIAL_2_MOI_MAJOR_RADIUS(92),
    D15_SATURATED_OS_TRIAL_2_MOI_MINOR_RADIUS(93),
    D15_SATURATED_OS_TRIAL_2_MOI_TOTAL_ERROR_SCORE(94),
    D15_SATURATED_OS_TRIAL_2_MOI_SELECTIVITY_INDEX(95),
    D15_SATURATED_OS_TRIAL_2_MOI_CONFUSION_INDEX(96),
    D15_SATURATED_OS_TRIAL_2_MOI_COLOR_DISCRIMINATON(97),
    D15_SATURATED_OS_TRIAL_2_MOI_NOTES(98),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_ANGLE(99),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_MAJOR_RADIUS(100),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_MINOR_RADIUS(101),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_TOTAL_ERROR_SCORE(102),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_SELECTIVITY_INDEX(103),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_CONFUSION_INDEX(104),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_COLOR_DISCRIMINATON(105),
    D15_SATURATED_BINOCULAR_TRIAL_1_MOI_NOTES(106),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_ANGLE(107),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_MAJOR_RADIUS(108),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_MINOR_RADIUS(109),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_TOTAL_ERROR_SCORE(110),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_SELECTIVITY_INDEX(111),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_CONFUSION_INDEX(112),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_COLOR_DISCRIMINATON(113),
    D15_SATURATED_BINOCULAR_TRIAL_2_MOI_NOTES(114),
    D15_DESATURATED_OD_TRIAL_1_MOI_ANGLE(115),
    D15_DESATURATED_OD_TRIAL_1_MOI_MAJOR_RADIUS(116),
    D15_DESATURATED_OD_TRIAL_1_MOI_MINOR_RADIUS(117),
    D15_DESATURATED_OD_TRIAL_1_MOI_TOTAL_ERROR_SCORE(118),
    D15_DESATURATED_OD_TRIAL_1_MOI_SELECTIVITY_INDEX(119),
    D15_DESATURATED_OD_TRIAL_1_MOI_CONFUSION_INDEX(120),
    D15_DESATURATED_OD_TRIAL_1_MOI_COLOR_DISCRIMINATON(121),
    D15_DESATURATED_OD_TRIAL_1_MOI_NOTES(122),
    D15_DESATURATED_OD_TRIAL_2_MOI_ANGLE(123),
    D15_DESATURATED_OD_TRIAL_2_MOI_MAJOR_RADIUS(124),
    D15_DESATURATED_OD_TRIAL_2_MOI_MINOR_RADIUS(125),
    D15_DESATURATED_OD_TRIAL_2_MOI_TOTAL_ERROR_SCORE(126),
    D15_DESATURATED_OD_TRIAL_2_MOI_SELECTIVITY_INDEX(127),
    D15_DESATURATED_OD_TRIAL_2_MOI_CONFUSION_INDEX(128),
    D15_DESATURATED_OD_TRIAL_2_MOI_COLOR_DISCRIMINATON(129),
    D15_DESATURATED_OD_TRIAL_2_MOI_NOTES(130),
    D15_DESATURATED_OS_TRIAL_1_MOI_ANGLE(131),
    D15_DESATURATED_OS_TRIAL_1_MOI_MAJOR_RADIUS(132),
    D15_DESATURATED_OS_TRIAL_1_MOI_MINOR_RADIUS(133),
    D15_DESATURATED_OS_TRIAL_1_MOI_TOTAL_ERROR_SCORE(134),
    D15_DESATURATED_OS_TRIAL_1_MOI_SELECTIVITY_INDEX(135),
    D15_DESATURATED_OS_TRIAL_1_MOI_CONFUSION_INDEX(136),
    D15_DESATURATED_OS_TRIAL_1_MOI_COLOR_DISCRIMINATON(137),
    D15_DESATURATED_OS_TRIAL_1_MOI_NOTES(138),
    D15_DESATURATED_OS_TRIAL_2_MOI_ANGLE(139),
    D15_DESATURATED_OS_TRIAL_2_MOI_MAJOR_RADIUS(140),
    D15_DESATURATED_OS_TRIAL_2_MOI_MINOR_RADIUS(141),
    D15_DESATURATED_OS_TRIAL_2_MOI_TOTAL_ERROR_SCORE(142),
    D15_DESATURATED_OS_TRIAL_2_MOI_SELECTIVITY_INDEX(143),
    D15_DESATURATED_OS_TRIAL_2_MOI_CONFUSION_INDEX(144),
    D15_DESATURATED_OS_TRIAL_2_MOI_COLOR_DISCRIMINATON(145),
    D15_DESATURATED_OS_TRIAL_2_MOI_NOTES(146),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_ANGLE(147),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_MAJOR_RADIUS(148),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_MINOR_RADIUS(149),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_TOTAL_ERROR_SCORE(150),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_SELECTIVITY_INDEX(151),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_CONFUSION_INDEX(152),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_COLOR_DISCRIMINATON(153),
    D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_NOTES(154),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_ANGLE(155),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_MAJOR_RADIUS(156),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_MINOR_RADIUS(157),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_TOTAL_ERROR_SCORE(158),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_SELECTIVITY_INDEX(159),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_CONFUSION_INDEX(160),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_COLOR_DISCRIMINATON(161),
    D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_NOTES(162),
    RAYLEIGH_OD_RANGE_START(163),
    RAYLEIGH_OD_RANGE_END(164),
    RAYLEIGH_OD_MID_POINT(165),
    RAYLEIGH_OD_YELLOW_1(166),
    RAYLEIGH_OD_YELLOW_2(167),
    RAYLEIGH_OS_RANGE_START(168),
    RAYLEIGH_OS_RANGE_END(169),
    RAYLEIGH_OS_MID_POINT(170),
    RAYLEIGH_OS_YELLOW_1(171),
    RAYLEIGH_OS_YELLOW_2(172),
    ONE_HUNDRED_HUE_OD_CLASSICAL_TOTAL_ERROR_SCORE(173),
    ONE_HUNDRED_HUE_OD_CLASSICAL_NOTES(174),
    ONE_HUNDRED_HUE_OD_MOI_ANGLE(175),
    ONE_HUNDRED_HUE_OD_MOI_MAJOR_RADIUS(176),
    ONE_HUNDRED_HUE_OD_MOI_MINOR_RADIUS(177),
    ONE_HUNDRED_HUE_OD_MOI_TOTOL_ERROR_SCORE(178),
    ONE_HUNDRED_HUE_OD_MOI_SELECTIVITY_INDEX(179),
    ONE_HUNDRED_HUE_OD_MOI_CONFUSION_INDEX(180),
    ONE_HUNDRED_HUE_OD_MOI_NOTES(181),
    ONE_HUNDRED_HUE_OS_CLASSICAL_TOTAL_ERROR_SCORE(182),
    ONE_HUNDRED_HUE_OS_CLASSICAL_NOTES(183),
    ONE_HUNDRED_HUE_OS_MOI_ANGLE(184),
    ONE_HUNDRED_HUE_OS_MOI_MAJOR_RADIUS(185),
    ONE_HUNDRED_HUE_OS_MOI_MINOR_RADIUS(186),
    ONE_HUNDRED_HUE_OS_MOI_TOTOL_ERROR_SCORE(187),
    ONE_HUNDRED_HUE_OS_MOI_SELECTIVITY_INDEX(188),
    ONE_HUNDRED_HUE_OS_MOI_CONFUSION_INDEX(189),
    ONE_HUNDRED_HUE_OS_MOI_NOTES(190),
    ONE_HUNDRED_HUE_BINOCULAR_CLASSICAL_TOTAL_ERROR_SCORE(191),
    ONE_HUNDRED_HUE_BINOCULAR_CLASSICAL_NOTES(192),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_ANGLE(193),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_MAJOR_RADIUS(194),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_MINOR_RADIUS(195),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_TOTOL_ERROR_SCORE(196),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_SELECTIVITY_INDEX(197),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_CONFUSION_INDEX(198),
    ONE_HUNDRED_HUE_BINOCULAR_MOI_NOTES(199),
    ISHIHARA_24_CORRECT_ANSWERS(200),
    ISHIHARA_24_TYPE(201),
    ISHIHARA_38_CORRECT_ANSWERS(202),
    ISHIHARA_38_TYPE(203),
    CAD_TEST_BINOCULAR_DIAGNOSIS(204),
    CAD_TEST_BINOCULAR_RG_THRESHOLD(205),
    CAD_TEST_BINOCULAR_YB_THRESHOLD(206),
    CAD_TEST_OD_DIAGNOSIS(207),
    CAD_TEST_OD_RG_THRESHOLD(208),
    CAD_TEST_OD_YB_THRESHOLD(209),
    CAD_TEST_OS_DIAGNOSIS(210),
    CAD_TEST_OS_RG_THRESHOLD(211),
    CAD_TEST_OS_YB_THRESHOLD(212),
    DILATION_PHENYLPHRINE_OD(215),
    DILATION_PHENYLPHRINE_OS(216),
    DILATION_TROPICAMIDE_OD(217),
    DILATION_TROPICAMIDE_OS(218),
    PERIMETRY_MP_CROSSHAIR_OD(219),
    PERIMETRY_MP_CROSSHAIR_OS(220),
    PERIMETRY_MP_DISEASE_OD(221),
    PERIMETRY_MP_DISEASE_OS(222),
    PERIMETRY_MP_FIXATION_OD(223),
    PERIMETRY_MP_FIXATION_OS(224),
    PERIMETRY_HVF_10_2_OD(225),
    PERIMETRY_HVF_10_2_OD_FOVEAL_SENSITIVITY(226),
    PERIMETRY_HVF_10_2_OS(227),
    PERIMETRY_HVF_10_2_OS_FOVEAL_SENSITIVITY(228),
    PERIMETRY_HVF_OTHER_OD(229),
    PERIMETRY_HVF_OTHER_OD_FOVEAL_SENSITIVITY(230),
    PERIMETRY_HVF_OTHER_OS(231),
    PERIMETRY_HVF_OTHER_OS_FOVEAL_SENSITIVITY(232),
    PERIMETRY_OCTOPUS(233),
    BCVA_ETDRS_OD_VA(234),
    BCVA_ETDRS_OD_SPHERE(235),
    BCVA_ETDRS_OD_CYLINDER(236),
    BCVA_ETDRS_OD_AXIS(237),
    BCVA_ETDRS_OS_VA(238),
    BCVA_ETDRS_OS_SPHERE(239),
    BCVA_ETDRS_OS_CYLINDER(240),
    BCVA_ETDRS_OS_AXIS(241),
    BCVA_ETDRS_BINOCULAR_VA(242),
    IOL_MASTER_OD_AL(243),
    IOL_MASTER_OD_K1(244),
    IOL_MASTER_OD_K2(245),
    IOL_MASTER_OD_ACD(246),
    IOL_MASTER_OS_AL(247),
    IOL_MASTER_OS_K1(248),
    IOL_MASTER_OS_K2(249),
    IOL_MASTER_OS_ACD(250),
    SKIN_PIGMENT(251),
    AMSLER_OD_RESULT(252),
    AMSLER_OS_RESULT(253),
    STEREO(254),
    CONTRAST_SENSITIVITY(255),
    AUTO_REFRACTOR_OD_SPHERE(256),
    AUTO_REFRACTOR_OD_CYLINDER(257),
    AUTO_REFRACTOR_OD_AXIS(258),
    AUTO_REFRACTOR_OS_SPHERE(259),
    AUTO_REFRACTOR_OS_CYLINDER(260),
    AUTO_REFRACTOR_OS_AXIS(261),
    MD_VISIT_UNDILATED(262),
    MD_VISIT_DILATED(263),
    GENETICS_PEDIGREE(264),
    GENETICS_RELATIONSHIP_HOW_RELATED(265),
    GENETICS_RELATIONSHIP_TO_WHOM(266),
    GENETICS_SALIVA_COLLECT(267),
    GENETICS_SALIVA_COLLECTED_BY(268),
    GENETICS_SALIVA_SENT_TO_1(269),
    GENETICS_SALIVA_SENT_TO_2(270),
    GENETICS_SALIVA_RESULTS(271),
    GENETICS_BLOOD_COLLECT(272),
    GENETICS_BLOOD_COLLECTED_BY(273),
    GENETICS_BLOOD_SENT_TO_1(274),
    GENETICS_BLOOD_SENT_TO_2(275),
    GENETICS_BLOOD_RESULTS(276),
    LUNCH(277),
    OPTOS_COLOR_OPTOS_COLOR_OD(278),
    OPTOS_COLOR_OPTOS_COLOR_OS(279),
    OPTOS_AF_OPTOS_AF_OD(280),
    OPTOS_AF_OPTOS_AF_OS(281),
    SPECTRALIS_AF_SPECTRALIS_OD(282),
    SPECTRALIS_AF_SPECTRALIS_OS(283),
    FUNDUS_VISUCAM_VISUCAM_30_OD(284),
    FUNDUS_VISUCAM_VISUCAM_30_OS(285),
    FUNDUS_VISUCAM_VISUCAM_45_OD(286),
    FUNDUS_VISUCAM_VISUCAM_45_OS(287),
    OCT_OPTOVUE_OD(288),
    OCT_OPTOVUE_OS(289),
    OCT_CIRRUS_CIRRUS_UNDILATED_OD(290),
    OCT_CIRRUS_CIRRUS_UNDILATED_OS(291),
    OCT_CIRRUS_CIRRUS_DILATED_OD(292),
    OCT_CIRRUS_CIRRUS_DILATED_OS(293),
    OCT_BIOPTIGEN_BIOPTIGEN_CLINICAL_OD(294),
    OCT_BIOPTIGEN_BIOPTIGEN_CLINICAL_OS(295),
    OCT_BIOPTIGEN_BIOPTIGEN_GLAUCOMA_OD(296),
    OCT_BIOPTIGEN_BIOPTIGEN_GLAUCOMA_OS(297),
    AOSLO_2_0_OD(298),
    AOSLO_2_0_OD_FIXATION(299),
    AOSLO_2_0_OS(300),
    AOSLO_2_0_OS_FIXATION(301),
    AOSLO_2_1_OD(302),
    AOSLO_2_1_OD_FIXATION(303),
    AOSLO_2_1_OS(304),
    AOSLO_2_1_OS_FIXATION(305),
    AOSLO_2_2_OD(306),
    AOSLO_2_2_OD_FIXATION(307),
    AOSLO_2_2_OS(308),
    AOSLO_2_2_OS_FIXATION(309),
    AOSLO_3_0_OD(310),
    AOSLO_3_0_OD_FIXATION(311),
    AOSLO_3_0_OS(312),
    AOSLO_3_0_OS_FIXATION(313),
    AOSLO_4_0_OD(314),
    AOSLO_4_0_OD_FIXATION(315),
    AOSLO_4_0_OS(316),
    AOSLO_4_0_OS_FIXATION(317),
    AO_FLOOD_OD(318),
    AO_FLOOD_OD_FIXATION(319),
    AO_FLOOD_OS(320),
    AO_FLOOD_OS_FIXATION(321),
    AO_FLOOD_1_1_OD(322),
    AO_FLOOD_1_1_OD_FIXATION(323),
    AO_FLOOD_1_1_OS(324),
    AO_FLOOD_1_1_OS_FIXATION(325),
    AO_GENERAL_NOTES(326),
    AOSLO_MEH_OD(327),
    AOSLO_MEH_OD_FIXATION(328),
    AOSLO_MEH_OS(329),
    AOSLO_MEH_OS_FIXATION(330),
    AOSLO_NYEEI_OD(331),
    AOSLO_NYEEI_OD_FIXATION(332),
    AOSLO_NYEEI_OS(333),
    AOSLO_NYEEI_OS_FIXATION(334);

    private static final HashMap<Integer, ExamColumn> colMap = new HashMap<>(50);

    static {
        for (ExamColumn sc : ExamColumn.values()) {
            colMap.put(sc.getColIndex(), sc);
        }
    }
    private final int colIndex;

    private ExamColumn(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public static ExamColumn getColByIndex(int idx) {
        ExamColumn col = null;
        if (colMap.containsKey(idx)) {
            col = colMap.get(idx);
        }
        return col;
    }
}
