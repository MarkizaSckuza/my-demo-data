Ext.define('DataGenerator.model.jobs.Scenario', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'selected', type: 'boolean' },
        { name: 'fileName', type: 'string' },
        { name: 'step', reference: 'Step' }
    ],
    
    idProperty: 'fileName',

    schema: {
        namespace: 'DataGenerator.model.jobs'
    }
});
