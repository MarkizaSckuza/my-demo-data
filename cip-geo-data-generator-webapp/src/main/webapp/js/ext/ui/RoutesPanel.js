Ext.define("DataGenerator.ui.RoutesPanel",{
    extend: 'Ext.panel.Panel',
    xtype: 'panel',
    id: 'routes-panel',
    title: 'Routes',
    region: 'west',
    floatable: false,
    margin: '0 0 0 0',
    //width: 400,
    //minWidth: 100,
    autoScroll: true,
    html: '<div id="routes"></div><div id="points"></div><div id="WiFi"></div>'//,
    //listeners: {
    //    resize: {
    //        fn: function(sndr, width) {
    //            if (Ext.getCmp('routes-grid')) {
    //                Ext.getCmp('routes-grid').setWidth(width);
    //                Ext.getCmp('routes-grid').getView().setWidth(width);
    //                Ext.getCmp('routes-grid').getView().refresh();
    //            }
    //            if (Ext.getCmp('points-grid')) {
    //                Ext.getCmp('points-grid').setWidth(width);
    //                Ext.getCmp('points-grid').getView().setWidth(width);
    //                Ext.getCmp('points-grid').getView().refresh();
    //            }
    //        }
    //    }
    //}
});