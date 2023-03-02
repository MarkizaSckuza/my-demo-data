Ext.define("DataGenerator.ui.OptionsPanel",{
    extend: 'Ext.panel.Panel',
    xtype: 'panel',
    id: 'options-panel',
    title: 'Generator Options',
    region: 'east',
    floatable: false,
    width: 400,
    minWidth: 100,
    autoScroll: true,
    html: '<div id="options"></div><div id="commands"></div><div id="files-list"></div><input type="file" id="options-file" name="optionsFile" style="display: none" >',
    listeners: {
        resize: {
            fn: function (sndr, width) {
                if (Ext.getCmp('files-grid')) {
                    Ext.getCmp('files-grid').setWidth(width);
                }
            }
        }
    }
});