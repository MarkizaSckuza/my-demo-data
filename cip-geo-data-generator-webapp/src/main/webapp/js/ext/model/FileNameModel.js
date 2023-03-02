Ext.define('DataGenerator.model.FileNameModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name:'selected',type:"boolean",defaultValue: false},
        {name:'fileName',type:"string"},
        {name:'fileNameToShow',type:"string"},
        {name:'id',type:"string"}
    ]
});
