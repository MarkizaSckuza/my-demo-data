Ext.define("DataGenerator.ui.RoutesGrid", {
    extend: 'Ext.grid.Panel',
    id: 'routes-grid',
    columns: [
        { text: '#',  dataIndex: 'id', width: 55 },
        {
            text: 'Points',
            dataIndex: 'points',
            flex: 1,

            renderer: function(value) {
                return value.map(function(elem){
                    return "(" + elem.lat + "," + elem.lng + ")";
                }).join(",");
            }
        }
    ],
    height: 250
});