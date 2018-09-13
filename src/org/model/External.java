/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;


import java.util.List;

/**
 *
 * @author erwin
 */
public class External {
    public String nama;
    public List<String> from;
    public List<String> to;

    public External(String nama, List<String> from, List<String> to) {
        this.nama = nama;
        this.from = from;
        this.to = to;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<String> getFrom() {
        return from;
    }

    public void setFrom(List<String> from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }
    
    
}
