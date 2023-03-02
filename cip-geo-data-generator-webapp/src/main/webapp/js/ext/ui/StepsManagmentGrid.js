Ext.define("DataGenerator.ui.StepsManagmentGrid", {
    extend: 'Ext.grid.Panel',
    title: 'Steps',
    titleCopy: 'Steps',
    id: 'steps-grid',
    reference:'ref-steps-grid',
    name: 'name-steps-grid',
    height: 400,
    viewConfig: {forceFit: true},
    fileName: '',
    floating:false,
    floatable: false,
    controller:'jobs',
    jobSelected:-1,
    nonLocationOutputForm:null,
    locationOutputForm: null,
    nonLocationOutputGrid:null,
    scenariosGrid:null,
    viewModel:{type:'jobs'},
    bind:{
        store: '{currentJob.steps}',
        selection:'{currentStep}'
    },
    //store: Ext.data.StoreManager.lookup('stepsStore'),
    tbar: [
        {
            xtype: 'button',
            text: 'Create',
            handler: function(){
                //TODO: update by POST function
            }
        }
    ],
    columns: [
        { text: '#',  dataIndex: 'id', width: 100 ,bind:'{currentStep.id}'},
        {
            xtype:'actioncolumn',
            width:24,
            items: [{
                icon: 'img/delete.png',
                tooltip: 'Delete'//,
           //     handler: function (grid, rowIndex, colIndex) {
           ////
           //         }


            }]
        }
    ]//,
    //listeners: {
    //    selectionchange: function (sm, selection) {
    //        var app = DataGenerator.getApplication();
    //
    //        var item = selection[0];
    //        Ext.state.Manager.clear(this.nonLocationOutputGrid.stateId);
    //        Ext.state.Manager.clear(this.nonLocationOutputGrid.getStore().stateId);
    //
    //        var nonLocationStore = Ext.create('Ext.data.ArrayStore', {
    //            storeId  : 'non_location_array_store',
    //            autoLoad : true,
    //            fields   : [
    //                'selected',
    //                'factoryName'
    //            ]
    //        });
    //
    //        nonLocationStore.loadData(item.data.outputs);
    //
    //
    //        this.nonLocationOutputGrid.reconfigure(nonLocationStore
    //            , this.nonLocationOutputGrid.getColumns());
    //
    //        this.nonLocationOutputGrid.setTitle(nonLocationOutputGrid.titeleCopy+' for '+this.fileName + ' step: '+item.id);
    //
    //        Ext.state.Manager.clear(this.scenariosGrid.stateId);
    //        Ext.state.Manager.clear(this.scenariosGrid.getStore().stateId);
    //
    //        var scenariosStore = Ext.create('Ext.data.ArrayStore', {
    //            storeId  : 'non_scenario_array_store',
    //            autoLoad : true,
    //            fields   : [
    //                'fileName',
    //                'fileNameToShow',
    //                'selected'
    //            ]
    //        });
    //        scenariosStore.loadData(item.data.scenarios);
    //
    //        this.scenariosGrid.reconfigure(
    //            scenariosStore
    //            , this.scenariosGrid.getColumns());
    //        this.scenariosGrid.setTitle(this.scenariosGrid.titeleCopy+' for '+this.fileName + ' step: '+item.id);
    //    }
    //},
    //updateJob:function(jobId){
    //    if(this.jobSelected>=0){
    //        //upload steps for prev. job back to memory
    //        app.jobs[this.jobSelected].steps =[];
    //        var index = 0;
    //        Ext.data.StoreManager.lookup('stepsStore').each(function(rec)
    //            {
    //                app.jobs[this.jobSelected].steps.push({id: index,
    //                    scenarios:rec.data.scenarios,
    //                    outputs: rec.data.outputs
    //                });
    //                index=index+1;
    //            }
    //        );
    //
    //    }
    //    this.jobSelected = jobId;
    //    Ext.data.StoreManager.lookup('stepsStore').removeAll();
    //}
});
