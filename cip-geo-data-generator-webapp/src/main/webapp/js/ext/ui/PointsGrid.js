Ext.define("DataGenerator.ui.PointsGrid", {
    extend: 'Ext.grid.Panel',
    title: 'Route Points',
    id: 'points-grid',
    plugins: [Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1,
        delay: 10
    })],
    columns: [
        { text: '#',  dataIndex: 'id', width: 55 },
        { text: 'Latitude', dataIndex: 'lat', flex: 2 },
        { text: 'Longitude', dataIndex: 'lng', flex: 2 },
        {
            text: 'POI',
            dataIndex: 'poiType',
            width: 100,
            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                var ret;
                if(value == 0)ret = 'No type';
                else if(value == 1)ret = 'Begin';
                else if(value == 2)ret = 'Stay for 1 min';
                else if(value == 3)ret = 'Stay if WiFi';
                return ret;
            },
            editor: {
                xtype: 'combobox',
                queryMode: 'local',
                valueField: 'id',
                displayField: 'name',
                store: 'poiStore',
                listeners: {
                    change: function (thisCmb, newValue, oldValue) {
                        var app = DataGenerator.getApplication();
                        app.polyLines[app.selectedPolyLineIndex].getPath().getAt(app.selectedPolyLinePointIndex).poiType = newValue;
                    }
                }
            }
        },
        {
            xtype:'actioncolumn',
            width:24,
            items: [{
                icon: 'img/delete.png',
                tooltip: 'Delete',
                handler: function (grid, rowIndex, colIndex) {
                    var app = DataGenerator.getApplication();
                    var rec = grid.getStore().getAt(rowIndex);
                    var id = rec.get('id');
                    app.removePoint(id);
                }
            }]
        }
    ],
    height: 250,
    viewConfig: {forceFit: true}
});
