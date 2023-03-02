Ext.define('DataGenerator.ui.Viewport', {

    extend: 'Ext.container.Viewport',
    
    controllers: [
        'DataGenerator.controller.Job'
    ],

    defaults: {
        collapsible: false,
        layout: 'fit'
    }
});