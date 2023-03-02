Ext.define('DataGenerator.view.JobsViewModel', {
    extend: 'Ext.app.ViewModel',

    requires: [
        'DataGenerator.model.JobModel'
    ],

    alias: 'viewmodel.jobs',

    //data: {
    //
    //},

    stores: {
        jobs: {
            model: 'DataGenerator.model.JobModel',
            pageSize: 20,
            proxy: {
                type: 'ajax',
                actionMethods: {
                    create: 'POST',
                    read: 'GET',
                    update: 'POST',
                    destroy: 'POST'
                },
                api: {
                    create: '/api/jobs',
                    read: '/api/jobs',
                    update: '/api/jobs',
                    destroy: '/api/jobs'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'jobs',
                    totalProperty: 'total'
                }
            },
            autoSync: true,
            autoLoad: true,//{start: 0, limit: 20},

            onCreateRecords: function(records, operation, success) {
                console.log(records);
            },
            onReadRecords: function(records, operation, success) {
                console.log(records);
            },
            onUpdateRecords: function(records, operation, success) {
                // If update failed, reject all changes
                if(!success) {
                    // Call rejectChanges method of the store
                    this.rejectChanges();

                    Ext.Msg.show({
                        title: 'Update Failed',
                        message: 'The changes you have made are rejected.',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            },

            onDestroyRecords: function(records, operation, success) {
                console.log(records);
            }
        }//,

            //autoLoad: false,
            //autoSync: false,
            //
            //onCreateRecords: function(records, operation, success) {
            //    console.log(records);
            //},
            //
            //onUpdateRecords: function(records, operation, success) {
            //    // If update failed, reject all changes
            //    if(!success) {
            //        // Call rejectChanges method of the store
            //        this.rejectChanges();
            //
            //        Ext.Msg.show({
            //            title: 'Update Failed',
            //            message: 'The changes you have made are rejected.',
            //            buttons: Ext.Msg.OK,
            //            icon: Ext.Msg.ERROR
            //        });
            //    }
            //},
            //
            //onDestroyRecords: function(records, operation, success) {
            //    console.log(records);
            //}
        },

    formulas:{
        currentJob:{
            bind:{
                bindTo:'{ref-jobs-grid.selection}',
                deep:true
            },
            get:function(job){
                return job;
            },
            set:function(job){
                this.set('currentJob',job);
            }
        },
        currentStep:{
            bind:{
                bindTo:'{ref-steps-grid.selection}',
                deep:true
            },
            get:function(step){
                return step;
            },
            set:function(step){
                this.set('currentStep',step);
            }
        },
        currentOutput:{
            bind:{
                bindTo:'{ref-non-location-outputs-grid.selection}',
                deep:true
            },
            get:function(output){
                return output;
            },
            set:function(output){
                this.set('currentOutput',output);
            }
        }
    }
});