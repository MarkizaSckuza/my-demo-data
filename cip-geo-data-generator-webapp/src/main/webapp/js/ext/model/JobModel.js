Ext.define('DataGenerator.model.JobModel', {
    extend: 'Ext.data.Model',
    alias:'model.JobModel',
    uses:[
        'DataGenerator.model.StepModel'
    ],
    fields: [
        {name:'selected',type:"boolean",defaultValue: false},
        {name:'fileName',type:"string"},
        {name:'fileNameToShow',type:"string"},
        {name:'id',type:"int"},
    ],
    autoLoad:true,
    autoSync:true,
    hasMany:{
        model:'DataGenerator.model.StepModel',
        store:'DataGenerator.model.StepModel',
        name: 'steps'
    },
    idProperty: 'fileName'
});