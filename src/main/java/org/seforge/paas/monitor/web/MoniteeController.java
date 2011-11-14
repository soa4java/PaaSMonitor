package org.seforge.paas.monitor.web;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.seforge.paas.monitor.domain.AppInstance;
import org.seforge.paas.monitor.domain.AppServer;
import org.seforge.paas.monitor.domain.MBean;
import org.seforge.paas.monitor.domain.MBeanAttribute;
import org.seforge.paas.monitor.domain.Phym;
import org.seforge.paas.monitor.domain.Vim;
import org.seforge.paas.monitor.extjs.TreeNode;
import org.seforge.paas.monitor.reference.MoniteeState;
import org.seforge.paas.monitor.service.AppServerService;
import org.seforge.paas.monitor.service.PhymService;
import org.seforge.paas.monitor.service.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@RequestMapping("/monitees/**")
@Controller
public class MoniteeController {
	private AppServerService appServerService;
	private PhymService phymService;
	private VelocityEngine velocityEngine;
	private Reporter reporter;
	
	@Autowired
	public void setAppServerService(AppServerService appServerService){
		this.appServerService = appServerService;
	}
	
	@Autowired
	public void setPhymService(PhymService phymService){
		this.phymService = phymService;
	}
	
	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@Autowired
	public void setReporter(Reporter reporter){
		this.reporter = reporter;
	}

    @RequestMapping(method = RequestMethod.GET)
    public void get(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
//    	reporter.report();    
    	String ip = "localhost";
		String port = "8999";
		Map map = new HashMap<String, List>();
		JMXServiceURL url;
		try {
			url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
					+ ip + ":" + port + "/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			Set<ObjectName> newSet = null;
			newSet = mbsc.queryNames(null, null);
			for(ObjectName name : newSet){
				MBean mb = new MBean();
				mb.setName(name.getCanonicalName());
				mb.persist();
				MBeanInfo info = mbsc.getMBeanInfo(name);
				MBeanAttributeInfo[] attributes = info.getAttributes();
				System.out.println(attributes.length); 
				for (MBeanAttributeInfo attr : attributes) {
					MBeanAttribute attribute = new MBeanAttribute();
					attribute.setName(attr.getName());
					attribute.setType(attr.getType());
					attribute.setDescription(attr.getDescription());
					attribute.setMBean(mb);
					attribute.persist();
				}			
			}		
			jmxc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index() {
        return "monitees/index";
    }
    
    @RequestMapping(params = "node", method = RequestMethod.GET)
	public ResponseEntity<String> listJsonTree(
			@RequestParam("node") String nodeId) {
		HttpStatus returnStatus = HttpStatus.BAD_REQUEST;
		List<TreeNode> response = null;
		try {
			List<Phym> records = Phym.findAllPhyms();
			// If these is no record now
			if (records.size() <= 0) {
				response = new ArrayList<TreeNode>();
				TreeNode root = new TreeNode("There are no monitee now");
				root.setLeaf(true);
				response.add(root);
				returnStatus = HttpStatus.OK;
			}
			// There are phyms in the db
			else {
				if (nodeId.equals("root")) {
					response = new ArrayList<TreeNode>();
					for (Phym phym : records) {
						if(phym.getIsMonitee()){
							TreeNode phymNode = new TreeNode();
							phymNode.setText(phym.getName());
							phymNode.setId("phym" + phym.getId().toString());
							phymNode.setLeaf(false);
							phymNode.setExpanded(false);
							response.add(phymNode);							
						}						
					}
				} else if (nodeId.indexOf("phym") != -1) {
					Long id = Long.valueOf(nodeId.substring(nodeId
							.indexOf("phym") + 4));
					Phym phym = Phym.findPhym(id);
					Set<Vim> vims = phym.getVims();
					if (vims.size() > 0) {
						response = new ArrayList<TreeNode>();
						for (Vim vim : vims) {
							if(vim.getIsMonitee()){
								TreeNode vimNode = new TreeNode();
								vimNode.setText(vim.getName());
								vimNode.setId("vim" + vim.getId().toString());
								vimNode.setLeaf(false);
								response.add(vimNode);								
							}							
						}
						if(response.size()<=0){
							TreeNode vimNode = new TreeNode();
							vimNode.setText("There is no Virtual Machine");
							vimNode.setLeaf(true);
							response.add(vimNode);
						}
					} else {
						response = new ArrayList<TreeNode>();
						TreeNode vimNode = new TreeNode();
						vimNode.setText("There is no Virtual Machine");
						vimNode.setLeaf(true);
						response.add(vimNode);
					}

				} else if (nodeId.indexOf("vim") != -1) {
					Long id = Long.valueOf(nodeId.substring(nodeId
							.indexOf("vim") + 3));
					Vim vim = Vim.findVim(id);
					Set<AppServer> appServers = vim.getAppServers();

					appServerService.checkState(appServers);
					if (appServers.size() > 0) {
						response = new ArrayList<TreeNode>();
						for (AppServer appServer : appServers) {
							if(appServer.getIsMonitee()){
								TreeNode appServerNode = new TreeNode();
								appServerNode.setText(appServer.getName() + ":" + appServer.getStatus());
								appServerNode.setId("appServer" + appServer.getId().toString());
								appServerNode.setLeaf(false);
								response.add(appServerNode);								
							}							
						}
						if(response.size()<=0){
							TreeNode appServerNode = new TreeNode();
							appServerNode.setText("There is no App Server");
							appServerNode.setLeaf(true);
							response.add(appServerNode);
						}
					} else {
						response = new ArrayList<TreeNode>();
						TreeNode appServerNode = new TreeNode();
						appServerNode.setText("There is no App Server");
						appServerNode.setLeaf(true);
						response.add(appServerNode);
					}

				} else if (nodeId.indexOf("appServer") != -1) {
					Long id = Long.valueOf(nodeId.substring(nodeId
							.indexOf("appServer") + 9));
					AppServer appServer = AppServer.findAppServer(id);
					appServerService.checkState(appServer);
					Set<AppInstance> appInstances = appServer.getAppInstances();
					if (appServer.getStatus().equals(MoniteeState.STARTED) && appInstances.size() > 0) {
						response = new ArrayList<TreeNode>();
						appServerService.checkInstancesState(appServer);
						for (AppInstance appInstance : appInstances) {
							if(appInstance.getIsMonitee()){
								TreeNode appInstanceNode = new TreeNode();
								appInstanceNode.setText(appInstance.getContextName()+":"+appInstance.getStatus());
								appInstanceNode.setId("appInstance" + appInstance.getId().toString());
								appInstanceNode.setLeaf(true);							
								response.add(appInstanceNode);
							}							
						}
						if(response.size()<=0){
							TreeNode appInstanceNode = new TreeNode();
							appInstanceNode.setText("There is no App Instance");
							appInstanceNode.setLeaf(true);
							response.add(appInstanceNode);
						}
					} else {
						response = new ArrayList<TreeNode>();
						TreeNode appInstanceNode = new TreeNode();
						appInstanceNode.setText("There is no App Instance");
						appInstanceNode.setLeaf(true);
						response.add(appInstanceNode);
					}

				}
				returnStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Return list of retrieved performance areas
		return new ResponseEntity<String>(new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("MM/dd/yy"), Date.class)
				.serialize(response), returnStatus);
	}
    
    
}
