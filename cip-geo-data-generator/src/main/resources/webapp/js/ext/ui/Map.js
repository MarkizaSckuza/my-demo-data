Ext.define("DataGenerator.ui.Map",{
    extend: 'Ext.container.Container',
    collapsible: false,
    region: 'center',
    id: 'map-control',
    minWidth: 100,
    layout: 'fit',
    html: '<div id="map-toolbar"></div><div id="map" style="height: 95%;"></div>',
    listeners: {
        resize: {
            fn: function (sndr, width) {
                if (Ext.getCmp('map-toolbar')) {
                    Ext.getCmp('map-toolbar').setWidth(width);
                }
            }
        }
    }
});
