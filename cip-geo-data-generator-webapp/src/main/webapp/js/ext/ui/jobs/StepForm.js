Ext.define('DataGenerator.ui.jobs.StepForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.stepform',
    
    controller: 'job',
    
    title: 'New Step',
    width: 300,
    
    autoShow: true,
    
    initComponent: function () {
        var me = this;
        
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 120,
                    buttons: [
                        { text: 'Save', action: 'save' },
                        { text: 'Close', handler: function () { me.close(); } }
                    ]
                }
            ]
        });
        
        me.callParent(arguments);
    }
});