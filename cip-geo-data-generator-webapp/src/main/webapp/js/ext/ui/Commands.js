Ext.define("DataGenerator.ui.Commands", {
    extend: 'Ext.panel.Panel',
    id: 'command-buttons',
    margin: '5 0 0 10',
    items:[

        {
            xtype: 'button',
            text: 'Save...',
            margin: '0 0 0 10',
            handler: function() {
                var app = DataGenerator.getApplication();
                app.saveAsOptions();
            }
        }
    ]
});

