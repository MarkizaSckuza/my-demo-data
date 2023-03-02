Ext.define('DataGenerator.ui.jobs.NonLocationGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.nonlocationgrid',
    title: 'Non Location outputs',
    
    viewModel : {
        type: 'outputviewmodel'
    },
    
    bind: {
        title: 'Non Location outputs for {step.id}'
    },

    initComponent: function() {
        var me = this;
        
        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'checkcolumn',
                    text: 'Selected',
                    dataIndex:'selected'
                },
                {
                    text: 'Factory',
                    dataIndex: 'factoryName',
                    flex: 1
                }
            ]
        });
        
        me.callParent(arguments);
    }
});
