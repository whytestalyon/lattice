/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.res;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class SteroExamResult extends ExamResult{
    private String stereo;

    public SteroExamResult(String stereo) {
        this.stereo = stereo;
    }

    public String getStereo() {
        return stereo;
    }
    
    
}
