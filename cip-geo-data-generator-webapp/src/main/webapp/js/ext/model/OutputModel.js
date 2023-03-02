Ext.define('DataGenerator.model.Output', {
    extend: 'Ext.data.Model',
    fields: [
        {name:'selected',type:"boolean",defaultValue: false},
        {name:'factoryName',type:"string"},
        {name:'path',type:"string"},
        {name:'partitionType',type:"int"},
        {name:'separator',type:"string"},
        {name:'description',type:"string"},
        {name:'location',type:"boolean"}
    ]
});