// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.paas.monitor.domain;

import java.lang.Boolean;
import java.lang.String;
import java.util.Set;
import org.seforge.paas.monitor.domain.Vim;

privileged aspect Phym_Roo_JavaBean {
    
    public String Phym.getName() {
        return this.name;
    }
    
    public void Phym.setName(String name) {
        this.name = name;
    }
    
    public String Phym.getIp() {
        return this.ip;
    }
    
    public void Phym.setIp(String ip) {
        this.ip = ip;
    }
    
    public String Phym.getUsername() {
        return this.username;
    }
    
    public void Phym.setUsername(String username) {
        this.username = username;
    }
    
    public String Phym.getPassword() {
        return this.password;
    }
    
    public void Phym.setPassword(String password) {
        this.password = password;
    }
    
    public Set<Vim> Phym.getVims() {
        return this.vims;
    }
    
    public void Phym.setVims(Set<Vim> vims) {
        this.vims = vims;
    }
    
    public Boolean Phym.getIsMonitee() {
        return this.isMonitee;
    }
    
    public void Phym.setIsMonitee(Boolean isMonitee) {
        this.isMonitee = isMonitee;
    }
    
}
