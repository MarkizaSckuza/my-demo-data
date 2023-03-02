Ext.define('DataGenerator.model.StepModel', {
    extend: 'Ext.data.Model',
    alias:'model.StepModel',
    uses:[
        'DataGenerator.model.FileNameModel',
        'DataGenerator.model.OutputModel'
    ],
    fields: [
        {name:'id',type:"int"}
    ],
    autoLoad:true,
    autoSync:true,
    hasMany:{
        model:'DataGenerator.model.FileNameModel',
        store:'DataGenerator.model.FileNameModel',
        name: 'scenarios'
    },
    hasMany:{
        model:'DataGenerator.model.OutputModel',
        store:'DataGenerator.model.OutputModel',
        name: 'outputs'
    },
    hasOne:{
        model:'DataGenerator.model.OutputModel',
        store:'DataGenerator.model.OutputModel',
        name: 'locationBasedOutput'
    }
});