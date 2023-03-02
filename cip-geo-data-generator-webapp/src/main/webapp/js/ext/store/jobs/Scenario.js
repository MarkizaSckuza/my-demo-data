Ext.define('DataGenerator.store.jobs.Scenario', {
    extend: 'Ext.data.Store',
    model: 'DataGenerator.model.jobs.Scenario',
    
    storeId: 'Scenario',
    
    proxy: {
        type: 'ajax',
        url : '/scenarios.json',
        reader: {
            type: 'json'
        }
    }
});