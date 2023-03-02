
Ext.define("DataGenerator.ui.WiFiGrid", {
    extend: 'Ext.grid.Panel',
    id: 'wifi-grid',
    title: 'WiFi zones',
    plugins: [Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1,
        delay: 10
    })],
    columns: [
        { text: 'WiFi #',  dataIndex: 'id', width: 65 },
        {
            text: 'Center',
            dataIndex: 'center',
            flex: 1,
            renderer: function(value, p, record){
                return "(" + record.data.lat + "," + record.data.lng + ")";
            }

        },
        {
            text: 'When Crossing',
            dataIndex: 'whenCrossing',
            width: 100,
            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                var ret;
                if(value == 0)ret = "Stay ALL";
                else if(value == 1)ret = "Stay selected";
                return ret;
            },
            editor: {
                xtype: 'combobox',
                queryMode: 'local',
                valueField: 'id',
                displayField: 'name',
                store: {
                    fields: ['id', 'name'],
                    data: [
                        {id: 0, name: "Stay ALL"},
                        {id: 1, name: "Stay selected"}
                    ]
                    },
                listeners: {
                    change: function (thisCmb, newValue, oldValue) {
                        var app = DataGenerator.getApplication();
                    }
                }
            }
        },
        { text: 'Time spent', dataIndex: 'timeSpent', flex: 2,
            editor:{xtype: 'numberfield',
                id: 'timeSpent',
                name: 'timeSpent',
                margin: '10 10 10 10',
                value: 0,
                minValue: 0,
                flex: 1}
        }
    ],
    height: 250,
    viewConfig: {forceFit: true},
    listeners: {
        selectionchange: function (view, selections, options) {
            var app = DataGenerator.getApplication();
            if(app.currentWiFi!=undefined &&
                app.currentWiFi!=null){
                app.currentWiFi.setOptions(app.optionsWiFi);
            }
            app.currentWiFi = app.WiFiMarkers[selections[0].id-1]
            app.currentWiFi.setOptions(app.optionsWiFiSelected);
        }
    }
});