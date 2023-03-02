Ext.define('DataGenerator.controller.JobsManagmentController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.jobs',

    id: 'jobsController',

    init: function() {
        this.control({
            'grid[name=name-jobs-grid]': {
 //              selectionchange: 'onJobsGridSelectionChange'
            },
            'grid[name=name-steps-grid]': {
                reconfigure: 'onStepsGridReconfigure'
            }
        });
    },

    onStepsGridReconfigure: function(gird, store, columns, oldStore, oldColumns, eOpts) {
        if(store) {
            // Override the default association store settings
            store.autoSync = true;
            store.proxy = this.getViewModel().getStore('steps').getProxy();
            store.onCreateRecords = this.getViewModel().getStore('steps').onDestroyRecords
            store.onUpdateRecords = this.getViewModel().getStore('steps').onUpdateRecords;
            store.onDestroyRecords = this.getViewModel().getStore('steps').onDestroyRecords
        }
    }//,
    //onJobsGridSelectionChange: function(sm, selection) {
    //    selection[0];
    //}

});