// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.paas.monitor.web;

import java.lang.String;
import org.seforge.paas.monitor.domain.Phym;
import org.seforge.paas.monitor.domain.Vim;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect VimController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=ByIp", "form" }, method = RequestMethod.GET)
    public String VimController.findVimsByIpForm(Model uiModel) {
        return "vims/findVimsByIp";
    }
    
    @RequestMapping(params = "find=ByIp", method = RequestMethod.GET)
    public String VimController.findVimsByIp(@RequestParam("ip") String ip, Model uiModel) {
        uiModel.addAttribute("vims", Vim.findVimsByIp(ip).getResultList());
        return "vims/list";
    }
    
    @RequestMapping(params = { "find=ByPhym", "form" }, method = RequestMethod.GET)
    public String VimController.findVimsByPhymForm(Model uiModel) {
        uiModel.addAttribute("phyms", Phym.findAllPhyms());
        return "vims/findVimsByPhym";
    }
    
    @RequestMapping(params = "find=ByPhym", method = RequestMethod.GET)
    public String VimController.findVimsByPhym(@RequestParam("phym") Phym phym, Model uiModel) {
        uiModel.addAttribute("vims", Vim.findVimsByPhym(phym).getResultList());
        return "vims/list";
    }
    
}