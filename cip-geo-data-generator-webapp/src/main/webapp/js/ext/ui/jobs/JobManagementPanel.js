Ext.define('DataGenerator.ui.jobs.JobManagementPanel',{
    extend: 'Ext.panel.Panel',
    
    // @TODO: do not forget to move this definitions to right place
    requires: [
        'DataGenerator.controller.Job',
        
        'DataGenerator.ui.jobs.JobGrid',
        'DataGenerator.ui.jobs.ScenarioGrid',
        'DataGenerator.ui.jobs.StepGrid',
        'DataGenerator.ui.jobs.NonLocationGrid',
        
        'DataGenerator.ui.jobs.JobForm',
        'DataGenerator.ui.jobs.StepForm',
        'DataGenerator.ui.jobs.LocationForm',
        'DataGenerator.ui.jobs.NonLocationForm',
        
        'DataGenerator.ui.jobs.JobViewModel',
        'DataGenerator.ui.jobs.StepViewModel',
        'DataGenerator.ui.jobs.ScenarioViewModel',
        'DataGenerator.ui.jobs.OutputViewModel',
        
//        'DataGenerator.store.jobs.Job',
        'DataGenerator.store.jobs.Step',
        'DataGenerator.store.jobs.Scenario',
        'DataGenerator.store.jobs.Output'
    ],
    title: 'Jobs manager',
    
    controller: 'job',
    
    margin: '0 0 0 0',
    layout: 'border',
    items:[
        {
            xtype: 'container',
            region: 'center',
            layout: 'vbox',
            defaults: {
                xtype: 'container',
                flex: 1,
                layout: 'hbox',
                width: '100%',
                defaults: {
                    flex: 1,
                    height: '100%',
                    style: 'border: 2px solid #cecece'
                }
            },
            items: [
                {
                    items: [
                        { xtype: 'jobgrid', reference: 'jobGrid' },
                        { xtype: 'scenariogrid', reference: 'scenarioGrid' }
                    ]
                },
                {
                    items: [
                        { xtype: 'stepgrid', reference: 'stepGrid' },
                        { xtype: 'nonlocationgrid', reference: 'nonLocationGrid' }
                    ]
                }
            ]
        },
        {
            xtype: 'container',
            region: 'east',
            layout: 'vbox',
            defaults: {
                xtype: 'container',
                flex: 1,
                height: '100%',
                layout: 'vbox',
                style: {
                    background: 'white',
                    border: '2px solid #cecece'
                }
            },
            items: [
                {
                    items: [
                        { xtype: 'locationform', reference: 'locationForm' }
                    ]
                },
                {
                    items: [
                        { xtype: 'nonlocationform', reference: 'nonLocationForm' }
                    ]
                }
            ]
        }
    ]
});
