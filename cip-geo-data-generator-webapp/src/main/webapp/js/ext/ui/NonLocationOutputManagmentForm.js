Ext.define("DataGenerator.ui.NonLocationOutputManagmentForm", {
    extend: 'Ext.form.Panel',
    id: 'non-location-output-form',
    xtype: 'panel',
    autoScroll: true,
    autoLoad: true,
    padding: '0',

    height: 300,
    layout: {
        type: 'vbox',
        anchor: '100%'
    },
    defaults: {
        anchor: '100%',
        labelWidth: 100
    },
    items: [
        {
            xtype: 'fieldset',
            title: 'Non Location Output parameters',
            titeleCopy : 'Non Location Output parameters',
            margin: '10 10 10 10',
            layout: 'anchor',
            autoWidth: true,
            stepSelected:-1,
            jobSelected:-1,
            id: 'non-location-output-field-set',
            flex: 1,
            items:[
                {xtype     : 'textfield',
                    name      : 'path',
                    fieldLabel: 'Path :',
                    msgTarget: 'side',
                    allowBlank: false},

                {
                    xtype       : 'combobox',
                    name        : 'partition_type',
                    id:'non-location-partition-type-combobox',
                    fieldLabel  : 'Partition type:',
                    valueField  : 'typeId',
                    editable    : false,
                    value       : '0',
                    store       : [['0','First type'],['1','Second type']]
                },
                {
                    xtype     : 'textfield',
                    id      : 'NonLocationSeparatorTxt',
                    fieldLabel: 'Separator :',
                    allowBlank: false

                },
                {
                    xtype: 'checkbox',
                    boxLabel: 'Overwrite default separator',
                    id: 'NonLocationSeparatorCheckbox',
                    checked: true,
                    inputValue: 'overwrite',
                    flex: 1,
                    listeners: {
                        change: function (cb, checked) {
                            if (checked) Ext.getCmp('NonLocationSeparatorTxt').disable();
                            else Ext.getCmp('NonLocationSeparatorTxt').enable();
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
