Ext.define('DataGenerator.model.jobs.Job', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'fileName', type: 'string' },
        { name: 'fileNameToShow', type: 'string' }
    ],
    
    idProperty: 'fileName',

    schema: {
        namespace: 'DataGenerator.model.jobs',
     
        proxy: {
            type: 'ajax',
            url : '/jobs.json',
            reader: {
                type: 'json'
            }
        }
    }
});
