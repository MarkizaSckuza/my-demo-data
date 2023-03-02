Ext.define('DataGenerator.ui.jobs.JobForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.jobform',
    
    controller: 'job',
    
    title: 'New Job',
    width: 300,
    
    autoShow: true,
    
    viewModel: 'jobviewmodel',
    
    initComponent: function () {
        var me = this;
        
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    layout: 'fit',
                    bindForm: true,
                    items: [
                        {
                            xtype: 'fieldset',
                            padding: 5,
                            height: '100%',
                            defaults: {
                                width: '100%',
                                labelWidth: 110
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'fileName',
                                    fieldLabel: 'File name',
                                    allowBlank: false,
                                    bind: '{record.fileName}'
                                },
                                {
                                    xtype: 'textfield',
                                    name: 'fileNameToShow',
                                    fieldLabel: 'File name to show',
                                    allowBlank: false,
                                    bind: '{record.fileNameToShow}'
                                }
                            ]
                        }
                    ],
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