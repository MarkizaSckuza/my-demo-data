Ext.define("DataGenerator.ui.JobManagmentPanel",{
    extend: 'Ext.panel.Panel',
    id: 'jobs-panel',
    title: 'Jobs manager',
    //region: 'east',
    floatable: false,
    margin: '0 0 0 0',
    autoScroll: true,
    layout: 'column',
    controller:'jobs',
    referenceHolder: true,
    //viewModel:{type:'jobs'},
    width: 500,
    items:[
        {
            xtype: 'container',
            height:800,
            columnWidth: .30,
            referenceHolder: true,
            html:'<div id="stepsManagerGrid"><div id="jobsManagerGrid"></div>'
        },
        {
            xtype: 'container',
            height: 600,
            columnWidth: .30,
            layout: {
                type: 'vbox',
                anchor: '100%'
            },
            defaults: {
                anchor: '100%',
                labelWidth: 100
            },
            items: [
                {
                    xtype: 'container',
                    //height:400,
                    flex: 1,
                    referenceHolder: true,
                    title: 'Scenarios to process',
                    html:'<div id="scenariosGrid"></div>'
                },
                {
                    xtype: 'container',
                    flex: 1,
                    title: 'Non location outputs',
                    referenceHolder: true,
                    html:'<div id="nonLocationOutputGrid"></div>'
                }

            ]
        },
        {
            xtype: 'container',
            columnWidth: .40,
            height:600,
            items: [
                {
                    xtype: 'container',
                    referenceHolder: true,
                    flex: 1,
                    title: 'Location based output parameters',
                    html:'<div id="locationOutputParametrsForm"></div>'
                },
                {
                    xtype: 'container',
                    flex: 1,
                    referenceHolder: true,
                    title: 'Non location outputs parameters',
                    html:'<div id="NonlocationOutputParametrsForm"></div>'
                }

            ]
        }
    ]
});




