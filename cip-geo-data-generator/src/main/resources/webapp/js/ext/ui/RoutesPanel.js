Ext.define("DataGenerator.ui.RoutesPanel",{
    extend: 'Ext.panel.Panel',
    xtype: 'panel',
    id: 'routes-panel',
    title: 'Routes',
    region: 'west',
    floatable: false,
    width: 400,
    minWidth: 100,
    autoScroll: true,
    html: '<div id="routes"></div><div id="points"></div><div id="WiFi"></div>',
    listeners: {
        resize: {
            fn: function(sndr, width) {
                if (Ext.getCmp('routes-grid')) {
                    Ext.getCmp('routes-grid').setWidth(width);
                }
                if (Ext.getCmp('points-grid')) {
                    Ext.getCmp('points-grid').setWidth(width);
                }
                if (Ext.getCmp('wifi-grid')) {
                    Ext.getCmp('wifi-grid').setWidth(width);
                }
            }
        }
    }
});