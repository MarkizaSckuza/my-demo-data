Ext.define('DataGenerator.model.jobs.Output', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'selected', type: 'boolean' },
        { name: 'path', type: 'string' },
        { name: 'partitionType', type: 'string' },
        { name: 'separator', type: 'string' },
        { name: 'factoryName', type: 'string' },
        { name: 'description', type: 'string' },
        { name: 'location', type: 'string' },
        { name: 'step', reference: 'Step' }
    ],
    
    idProperty: 'factoryName',

    schema: {
        namespace: 'DataGenerator.model.jobs'
    }
});
