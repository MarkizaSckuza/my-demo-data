Ext.define('DataGenerator.model.jobs.Step', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'id', type: 'int' },
        { name: 'locationBasedOutput', reference: 'Output', unique: true },
        { name: 'job', reference: 'Job' }
    ],

    schema: {
        namespace: 'DataGenerator.model.jobs'
    },
    
    hasOne: 'Output'
});
