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

    private final int Range_Start, Range_End, Mid_Point, Yellow_1, Yellow_2;
    private final Eye eye;

    public RayleighExamResult(int Range_Start, int Range_End, int Mid_Point, int Yellow_1, int Yellow_2, Eye eye) {
        this.Range_Start = Range_Start;
        this.Range_End = Range_End;
        this.Mid_Point = Mid_Point;
        this.Yellow_1 = Yellow_1;
        this.Yellow_2 = Yellow_2;
        this.eye = eye;
    }

    public Eye getEye() {
        return eye;
    }

    public int getRange_Start() {
        return Range_Start;
    }

    public int getRange_End() {
        return Range_End;
    }

    public int getMid_Point() {
        return Mid_Point;
    }

    public int getYellow_1() {
        return Yellow_1;
    }

    public int getYellow_2() {
        return Yellow_2;
    }

}
