Ext.define('PaaSMonitor.store.AppInstances', {
    extend: 'Ext.data.Store',
    model: 'PaaSMonitor.model.AppInstance',
    // autoLoad: true,
    proxy: {
        type: 'rest',
        url: 'appinstances',
        headers: {
        	'Accept': 'application/json'
        },        
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});