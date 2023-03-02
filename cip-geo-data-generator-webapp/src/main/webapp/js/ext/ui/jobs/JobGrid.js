Ext.define('DataGenerator.ui.jobs.JobGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.jobgrid',
    title: 'Jobs',

    viewModel: 'jobviewmodel',

    bind: {
        store: '{jobs}'
    },
    
    tbar: [
        {
            xtype: 'button',
            text: 'Create',
            action: 'create'
        },
        {
            xtype: 'button',
            text: 'Save all jobs',
            action: 'save-all-jobs',
            reference: 'saveAllBtn'
        }
    ],
    
    initComponent: function () {
        var me = this;
        
        Ext.applyIf(me, {
            columns: [
                {
                    text: 'Job name',
                    dataIndex: 'fileNameToShow',
                    flex: 1
                }
            ]
        });
        
        me.callParent(arguments);
    }
});
