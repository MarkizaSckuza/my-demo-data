Ext.define('DataGenerator.store.jobs.Output', {
    extend: 'Ext.data.Store',
    model: 'DataGenerator.model.jobs.Output',
    
    storeId: 'Output',
    
    proxy: {
        type: 'ajax',
        url : '/outputs.json',
        reader: {
            type: 'json'
        }
    }
});