// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.paas.monitor.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seforge.paas.monitor.domain.JmxAppInstance;

privileged aspect JmxAppInstance_Roo_Json {
    
    public String JmxAppInstance.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static JmxAppInstance JmxAppInstance.fromJsonToJmxAppInstance(String json) {
        return new JSONDeserializer<JmxAppInstance>().use(null, JmxAppInstance.class).deserialize(json);
    }
    
    public static String JmxAppInstance.toJsonArray(Collection<JmxAppInstance> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<JmxAppInstance> JmxAppInstance.fromJsonArrayToJmxAppInstances(String json) {
        return new JSONDeserializer<List<JmxAppInstance>>().use(null, ArrayList.class).use("values", JmxAppInstance.class).deserialize(json);
    }
    
}