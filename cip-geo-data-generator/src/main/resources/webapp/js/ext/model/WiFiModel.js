Ext.define('DataGenerator.model.WiFiModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name:'id',type:"int"},
        {name:'lat',type:"number"},
        {name:'lng',type:"number"},
        {name: 'radius', type: 'number'},
        {name:'whenCrossing',type:"int"},
        {name:'timeSpent',type:"int"}
       ]
});