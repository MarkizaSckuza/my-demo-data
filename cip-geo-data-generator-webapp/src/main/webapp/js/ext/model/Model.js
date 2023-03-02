Ext.define('DataGenerator.model.Model', {
    extend: 'Ext.data.Model',

    fields: [{
        name: 'id',
        type: 'int'
    }],

    schema: {
        namespace: 'DataGenerator.model',  // generate auto entityName

        proxy: {     // Ext.util.ObjectTemplate
            type: ajax,
            url: '{entityName}.json',
            reader: {
                type: 'json',
                rootProperty: '{entityName:lowercase}'
            }
        }
    }
});