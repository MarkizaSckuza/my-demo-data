Ext.define("DataGenerator.ui.Map",{
    extend: 'Ext.container.Container',
    collapsible: false,
    region: 'center',
    id: 'map-control',
    margin: '0 0 0 0',
    layout:'fit',
    height:750,
    html: '<div id="map-toolbar"></div><div id="map"></div>'
});
