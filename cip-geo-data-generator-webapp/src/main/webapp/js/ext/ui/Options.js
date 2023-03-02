Ext.define("DataGenerator.ui.Options", {
    extend: 'Ext.form.Panel',
    id: 'options',
    padding: '0',
    //layout:'fit',
    height:500,
    items:[
        {
            xtype: 'fieldcontainer',
            fieldLabel: 'Current Options File',
            margin: '10 10 10 10',
            labelWidth: 140,
            anchor: '100%',

            layout: 'hbox',
            items: [
                {
                    xtype: 'label',
                    id: 'current-file',
                    text: '',
                    margin: '0 0 0 0',
                    cls: 'emphasis'
                }
            ]
        },
        {
            xtype: 'fieldset',
            title: 'Pedestrians',
            margin: '10 10 10 10',
            items:[
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Count',
                    id: 'pedestrians-count',
                    name: 'pedestriansCount',
                    margin: '10 10 10 10',
                    value: 0,
                    minValue: 0,
                    flex: 1
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Speed (mph)',
                    id: 'pedestrians-speed',
                    name: 'pedestriansSpeed',
                    margin: '10 10 10 10',
                    value: 0,
                    minValue: 0,
                    flex: 1
                }
            ]
        },
        {
            xtype: 'fieldset',
            title: 'Bicyclists',
            margin: '10 10 10 10',
            items:[
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Count',
                    id: 'bicyclists-count',
                    name: 'bicyclistsCount',
                    margin: '10 10 10 10',
                    value: 0,
                    minValue: 0,
                    flex: 1
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Speed (mph)',
                    id: 'bicyclists-speed',
                    name: 'bicyclistsSpeed',
                    margin: '10 10 10 10',
                    value: 0,
                    minValue: 0,
                    flex: 1
                }
            ]
        },
        {
            xtype: 'fieldset',
            title: 'Cars',
            margin: '10 10 10 10',
            items:[
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Count',
                    id: 'cars-count',
                    name: 'carsCount',
                    margin: '10 10 10 10',
                    value: 0,
                    minValue: 0,
                    flex: 1
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Speed (mph)',
                    id: 'cars-speed',
                    name: 'carsSpeed',
                    margin: '10 10 10 10',
                    value: 0,
                    minValue: 0,
                    flex: 1
                }
            ]
        },
        {
            xtype: 'fieldcontainer',
            fieldLabel: 'Starting Timestamp',
            margin: '10 10 10 10',
            labelWidth: 140,
            anchor: '100%',

            layout: 'hbox',
            items: [
                {
                    xtype: 'datefield',
                    name: 'startDate',
                    id: 'start-date',
                    format: 'Y-m-d',
                    width: 110,
                    allowBlank: false
                },
                {
                    xtype: 'timefield',
                    name: 'startTime',
                    id: 'start-time',
                    format: 'H:i:s',
                    width: 110,
                    allowBlank: false
                }
            ]
        },
        {
            xtype: 'numberfield',
            labelWidth: 140,
            fieldLabel: 'Tracking Interval (sec)',
            id: 'track-interval',
            name: 'trackInterval',
            margin: '10 10 10 10',
            value: 0,
            minValue: 0,
            flex: 1
        },
        {
            xtype: 'textfield',
            fieldLabel: 'Fluctuation:',
            name:'fluctuation',
            id:'fluctuation',
            labelWidth: 140,
            margin: '10 10 10 10',
            allowBlank: false
        },
        {
            xtype: 'textfield',
            fieldLabel: 'File name to save:',
            name:'file-name',
            id:'file-name',
            labelWidth: 140,
            margin: '10 10 10 10',
            allowBlank: false
        }
    ]
});
