Ext.define("DataGenerator.ui.jobs.NonLocationForm", {
    extend: 'Ext.form.Panel',
    alias: 'widget.nonlocationform',
    id: 'non-location-output-form',
    xtype: 'panel',
    autoScroll: true,
    autoLoad: true,
    padding: '0',

//    height: 300,
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
   
    items: [
        {
            xtype: 'fieldset',
            title: 'Non Location Output parameters',
            bind: {
                title: 'Non Location Output parameters for {record.factoryName}'
            },
            margin: '10 10 10 10',
            layout: 'anchor',
            autoWidth: true,
            flex: 1,
            items:[
                {
                    xtype: 'textfield',
                    bind: '{record.path}',
                    fieldLabel: 'Path :',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'combobox',
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
                    fieldLabel: 'Separator :',
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
            ],
            clearTitle:function(){
                this.setTitle(this.titeleCopy);
            }
        }
    ]
});
