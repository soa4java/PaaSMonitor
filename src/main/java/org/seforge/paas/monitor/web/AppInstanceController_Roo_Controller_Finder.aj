// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.paas.monitor.web;

import java.lang.String;
import org.seforge.paas.monitor.domain.AppInstance;
import org.seforge.paas.monitor.domain.AppServer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect AppInstanceController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=ByAppServer", "form" }, method = RequestMethod.GET)
    public String AppInstanceController.findAppInstancesByAppServerForm(Model uiModel) {
        uiModel.addAttribute("appservers", AppServer.findAllAppServers());
        return "appinstances/findAppInstancesByAppServer";
    }
    
    @RequestMapping(params = "find=ByAppServer", method = RequestMethod.GET)
    public String AppInstanceController.findAppInstancesByAppServer(@RequestParam("appServer") AppServer appServer, Model uiModel) {
        uiModel.addAttribute("appinstances", AppInstance.findAppInstancesByAppServer(appServer).getResultList());
        return "appinstances/list";
    }
    
}