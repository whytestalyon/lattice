/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.res;

import com.bwc.lat.io.exc.Eye;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class RayleighExamResult extends ExamResult {

    private final Integer Range_Start, Range_End, Mid_PoInteger, Yellow_1, Yellow_2;
    private final Eye eye;

    public RayleighExamResult(Integer Range_Start, Integer Range_End, Integer Mid_PoInteger, Integer Yellow_1, Integer Yellow_2, Eye eye) {
        this.Range_Start = Range_Start;
        this.Range_End = Range_End;
        this.Mid_PoInteger = Mid_PoInteger;
        this.Yellow_1 = Yellow_1;
        this.Yellow_2 = Yellow_2;
        this.eye = eye;
    }

    public Eye getEye() {
        return eye;
    }

    public Integer getRange_Start() {
        return Range_Start;
    }

    public Integer getRange_End() {
        return Range_End;
    }

    public Integer getMid_PoInteger() {
        return Mid_PoInteger;
    }

    public Integer getYellow_1() {
        return Yellow_1;
    }

    public Integer getYellow_2() {
        return Yellow_2;
    }

}
