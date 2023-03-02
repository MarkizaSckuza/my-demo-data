Ext.define("DataGenerator.ui.LocationOutputManagmentForm", {
    extend: 'Ext.form.Panel',
    id: 'location-output-form',
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
                    title: 'Location Output parameters',
                    titeleCopy : 'Location Output parameters',
                    id: 'location-output-field-set',
                    margin: '10 10 10 10',
                    layout: 'anchor',
                    autoWidth: true,
                    stepSelected:-1,
                    jobSelected:-1,
                    flex: 1,
                    items:[
                        {
                            xtype       : 'combobox',
                            name        : 'Partition type:',
                            id:'location-based-outputs-combobox',
                            displayField: 'fileName',
                            fieldLabel  : 'Location output :',
                            valueField  : 'fileName',
                            editable    : false,
                            value       : '0',
                            store: Ext.data.StoreManager.lookup('locationOutputStore')
                        },

                        {xtype     : 'textfield',
                            name      : 'path',
                            fieldLabel: 'Path :',
                            msgTarget: 'side',
                            allowBlank: false},

                            {
                                xtype       : 'combobox',
                                name        : 'partition_type',
                                id:'location-partition-type-combobox',
                                fieldLabel  : 'Partition type:',
                                valueField  : 'typeId',
                                editable    : false,
                                value       : '0',
                                store       : [['0','First type'],['1','Second type']]
                            },
                        {
                            xtype     : 'textfield',
                            id      : 'LocationSeparatorTxt',
                            fieldLabel: 'Separator :',
                            allowBlank: false

                        },
                        {
                            xtype: 'checkbox',
                            boxLabel: 'Overwrite default separator',
                            id: 'LocationSeparatorCheckbox',
                            checked: true,
                            inputValue: 'overwrite',
                            flex: 1,
                            listeners: {
                                change: function (cb, checked) {
                                    if (checked) Ext.getCmp('LocationSeparatorTxt').disable();
                                    else Ext.getCmp('LocationSeparatorTxt').enable();
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
