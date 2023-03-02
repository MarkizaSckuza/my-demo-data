Ext.define("DataGenerator.ui.OptionsPanel",{
    extend: 'Ext.panel.Panel',
    id: 'options-panel',
    title: 'Generator Options',
    region: 'east',
    floatable: false,
    margin: '0 0 0 0',
    autoScroll: true,
    layout: {
        type: 'vbox',
        align : 'stretch',
        pack  : 'start'
    },
    items:[
        {
            xtype: 'container',
            height:500,
            html:'<div id="options"></div>'
        },
        {
            xtype: 'container',
            height:50,
            html:'<div id="commands"></div>'
        },
        {
            xtype: 'container',
            flex:2,
            html:'<div id="files-list"></div>'
        },
        {
            xtype: 'container',
            height:150,
            html:'<input type="file" id="options-file" name="optionsFile" style="display: none" >'
        }
    ]
});