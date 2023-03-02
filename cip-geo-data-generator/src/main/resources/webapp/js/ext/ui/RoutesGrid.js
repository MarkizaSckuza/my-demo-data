Ext.define("DataGenerator.ui.RoutesGrid", {
    extend: 'Ext.grid.Panel',
    id: 'routes-grid',
    columns: [
        { text: 'Route #',  dataIndex: 'id', width: '20%' },
        {
            text: 'Points',
            dataIndex: 'points',
            flex: 1,
            width: '80%',

            renderer: function(value) {
                return value.map(function(elem){
                    return "(" + elem.lat + "," + elem.lng + ")";
                }).join(",");
            }
        }
    ],
    height: 400
});