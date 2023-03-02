Ext.define('DataGenerator.controller.Job', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.job',

    init: function () {
        var me = this;
        
        me.control({
            'jobgrid': {
                selectionchange: me.onJobSelectionChange
            },
            'jobgrid button[action=create]': {
                click: me.onJobCreateClick
            },
            'jobgrid button[action=save-all-jobs]': {
                click: me.onJobSaveAllJobsClick
            },
            'jobform button[action=save]': {
                click: me.onJobSaveClick
            },
            'stepgrid': {
                selectionchange: me.onStepSelectionChange
            },
            'stepgrid button[action=create]': {
                click: me.onStepCreateClick
            },
            'stepgrid button[action=remove]': {
                click: me.onStepRemoveClick
            },
            'nonlocationgrid': {
                selectionchange: me.onNonLocationSelectionChange
            }
        });
        
        me.stores = {
            scenarios: Ext.create('DataGenerator.store.jobs.Scenario', { autoLoad: true }),
            steps: Ext.create('DataGenerator.store.jobs.Step', { autoLoad: true }),
            outputs: Ext.create('DataGenerator.store.jobs.Output', { autoLoad: true })
        };
    },
    
    onJobSelectionChange: function (grid, records) {
        var me = this,
            stepGrid = me.lookupReference('stepGrid'),
            record = records[0];
    
        if (record) {
            stepGrid.down('button[action=create]').enable();
            
            stepGrid.reconfigure(record.steps());
            stepGrid.getViewModel().setData({ job: record });

            me.clearGrids();
        } else {
            stepGrid.down('button[action=create]').disable();
        }
    },
    
    onJobSaveClick: function (btn) {
        var me = this,
            jobGrid = me.lookupReference('jobGrid') || Ext.ComponentQuery.query('[reference=jobGrid]')[0],
            form = btn.up('form').getForm(),
            win = btn.up('window'),
            record = form.getRecord();
        
        if (form.isValid()) {
            if (record) {
                jobGrid.getStore().add(record);
            } else {
                Ext.msg.alert('Failure', 'Something wrong! Can\'t get record');
            }
            
            win.close();
        }
    },
    
    onStepSelectionChange: function (grid, records) {
        var me = this,
            stepGrid = me.lookupReference('stepGrid'),
            scenarioGrid = me.lookupReference('scenarioGrid'),
            nonLocationGrid = me.lookupReference('nonLocationGrid'),
            locationForm = me.lookupReference('locationForm'),
            record = records[0],
            locationBasedOutput;
    
        if (record) {
            scenarioGrid.reconfigure(record.scenarios());
            scenarioGrid.getViewModel().setData({ step: record });

            nonLocationGrid.reconfigure(record.outputs());
            nonLocationGrid.getViewModel().setData({ step: record });
            
            
            locationBasedOutput = record.getLocationBasedOutput();

            if (locationBasedOutput) {
                locationForm.getViewModel().setData({ record: locationBasedOutput });
            }
            
            stepGrid.down('button[action=remove]').enable();
        } else {
            stepGrid.down('button[action=remove]').disable();
        }
    },
    
    onNonLocationSelectionChange: function (grid, records) {
        var me = this,
            nonLocationForm = me.lookupReference('nonLocationForm'),
            record = records[0];
        
        if (record) {
            nonLocationForm.getViewModel().setData({ record: record });
        }
    },
    
    onJobCreateClick: function () {
        var job = Ext.create('DataGenerator.model.jobs.Job', { fileName: 'file.json' }),
            w = Ext.widget('jobform');
        
        w.down('form').getForm().loadRecord(job);
        w.getViewModel().setData({ record: job });
    },
    
    onStepCreateClick: function (grid, records) {        
        var me = this,
            stepGrid = me.lookupReference('stepGrid'),
            stepStore = stepGrid.getStore(),
            record = Ext.create('DataGenerator.model.jobs.Step');
    
        var scenarioStore = me.stores.scenarios,
            outputStore = me.stores.outputs;
    
        scenarioStore.load(function (scenarios) {
            record.scenarios().add(scenarios);
        });
        
        outputStore.load(function (outputs) {
            record.outputs().add(outputs);
        });
        
        stepStore.add(record);
    },
    
    onStepRemoveClick: function () {
        var me = this,
            stepGrid = me.lookupReference('stepGrid'),
            record;
    
        if (stepGrid.getSelectionModel().hasSelection()) {
            record = stepGrid.getSelectionModel().getSelection()[0];
            stepGrid.getStore().remove(record);
            me.clearGrids();
        }
        
    },
    
    onJobSaveAllJobsClick: function (btn) {
        var me = this,
            jobGrid = me.lookupReference('jobGrid');

        var jobs = [];

        Ext.each(jobGrid.getStore().data.items, function (job) {
            jobs.push(Ext.merge({}, job.getData(), job.getAssociatedData()));
        });
        
        console.log(jobs, jobGrid.getStore().getProxy());
        
        Ext.Ajax.request({
            url: jobGrid.getStore().getProxy().url,
            success: function () {
                Ext.Msg.alert('Success', 'Jobs saved successfully');
            },
            failure: function () {
                Ext.Msg.alert('Failure', 'Jobs wasn\'t saved successfully');
            },
            jsonData : jobs
        });
    },
    
    clearGrids: function () {
        var me = this,
            scenarioGrid = me.lookupReference('scenarioGrid'),
            nonLocationGrid = me.lookupReference('nonLocationGrid');
    
        // @TODO: find out correct way to bind empty store
        scenarioGrid.reconfigure(Ext.create('Ext.data.Store'));
        nonLocationGrid.reconfigure(Ext.create('Ext.data.Store'));
    }
});