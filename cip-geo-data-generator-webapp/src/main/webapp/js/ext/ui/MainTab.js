Ext.define("DataGenerator.ui.MainTab", {
    extend: 'Ext.tab.Panel',
        activeTab: 0,
        autoScroll: true,
        viewConfig: { forceFit: true },
        config:{fullscreen: true},
        defaults:{hideMode: 'offsets',
            autoHeight: true},
        items: [{
            xtype: 'container',
            title: 'Scenario management',
            autoScroll: true,
            fullscreen: true,
            id: 'scenario-management',
            //items: [routesPanel, optionsPanel, mapEditor]
            layout:'column',
            items: [
                {
                    xtype: 'container',
                    autoScroll: true,
                    columnWidth: .30,
                    html: '<div id="routesPanel"></div>'
                },
                {
                    xtype: 'container',
                    autoScroll: true,
                    columnWidth: .412,
                    html: '<div id="mapEditor"></div>'
                },
                {
                    xtype: 'container',
                    autoScroll: true,
                    columnWidth: .288,
                    html: '<div id="optionsPanel"></div>'
                }
            ]
        }]
        });