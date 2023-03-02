Ext.define('DataGenerator.ui.jobs.LocationForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.locationform',
    
    padding: '0',

    layout: {
        type: 'vbox',
        anchor: '100%'
    },
    defaults: {
        anchor: '100%',
        labelWidth: 100
    },
    viewModel : {
        type: 'outputviewmodel'
    },
    
    initComponent: function () {
        var me = this,
            outputStore = Ext.create('DataGenerator.store.jobs.Output');
    
        outputStore.load();
        
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'fieldset',
                    title: 'Location Output parameters',
                    bind: {
                        title: 'Location Output parameters for {record.factoryName}'
                    },
                    margin: '10 10 10 10',
                    layout: 'anchor',
                    autoWidth: true,
                    flex: 1,
                    items: [
                        {
                            xtype: 'combobox',
                            displayField: 'factoryName',
                            fieldLabel: 'Location output:',
                            valueField: 'factoryName',
                            editable: false,
                            bind: {
                                value: '{record.locationBasedOutput.factoryName}'
                            },
                            store: outputStore
                        },
                        {
                            xtype: 'textfield',
                            bind: '{record.path}',
                            fieldLabel: 'Path:',
                            msgTarget: 'side',
                            allowBlank: false
                        },
                        {
                            xtype: 'combobox',
                            name: 'partition_type',
                            fieldLabel: 'Partition type:',
                            valueField: 'typeId',
                            editable: false,
                            store: [['0','First type'],['1','Second type']],
                            bind: {
                                value: '{record.partitionType}'
                            }
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Separator:',
                            allowBlank: false,
                            bind: '{record.separator}',
                            itemId: 'separator'
                        },
                        {
                            xtype: 'checkbox',
                            boxLabel: 'Overwrite default separator',
                            checked: false,
                            inputValue: 'overwrite',
                            flex: 1,
                            listeners: {
                                change: function (cb, checked) {
                                    var separatorField = cb.up('fieldset').down('[itemId=separator]');
                                    
                                    if (checked) {
                                        separatorField.disable();
                                    } else {
                                        separatorField.enable();
                                    }
                                }
                            }
                        }
                    ]
        }
    ]
        });
        
        me.callParent(arguments);
    }
});
