Ext.define("DataGenerator.ui.OutputPanel",{
    extend: 'Ext.panel.Panel',
    id: 'output-panel',
    title: 'Outputs manager',
    //region: 'east',
    floatable: false,
    margin: '0 0 0 0',
    autoScroll: true,
    layout: 'column',
    items:[
        {
            xtype: 'container',
            height:400,
            columnWidth: .30,
            html:'<div id="outputManagerGrid"></div>'
        },
        {
            xtype: 'container',
            columnWidth: .70,
            html:'<div id="outputManagerForm"></div>'
        }
    ]
});
