Ext.define('DataGenerator.ui.jobs.StepGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.stepgrid',
    title: 'Steps',
    
    tbar: [
        {
            xtype: 'button',
            text: 'Create',
            action: 'create',
            disabled: true
        },
        {
            xtype: 'button',
            text: 'Remove',
            action: 'remove',
            disabled: true
        }
    ],
    
//    viewModel: 'jobviewmodel',
//    bind: {
//        store: '{currentJob.steps}',
//        selection: '{job}',
//        title: 'Steps for {job.fileNameToShow}'
//    },
    
    viewModel : {
        type: 'stepviewmodel'
    },
    
    bind: {
        title: 'Steps for {job.fileNameToShow}'
    },
    
    initComponent: function () {
        var me = this,
            store = Ext.create('DataGenerator.store.jobs.Step');

        Ext.applyIf(me, {
//            store: store,
            
            columns: [
                {
                    text: '#',
                    dataIndex: 'id',
                    flex: 1
                }
            ]
        });
        
        me.callParent(arguments);
    }
});
