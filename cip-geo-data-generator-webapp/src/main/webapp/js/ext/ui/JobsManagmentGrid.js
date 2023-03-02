Ext.define("DataGenerator.ui.JobsManagmentGrid", {
    extend: 'Ext.grid.Panel',
    title: 'Jobs',
    id: 'jobs-grid',
    reference:'ref-jobs-grid',
    name: 'name-jobs-grid',
    height: 300,
    viewConfig: {forceFit: true},
    floating:false,
    floatable: false,
    //nonLocationOutputForm:null,
    //locationOutputForm: null,
    //stepsGrid:null,
    //nonLocationOutputGrid:null,
    //scenariosGrid:null,
    viewModel:{type:'jobs'},
    //controller:'jobs',
    bind:{
        store:'{jobs}',
        selection:'{currentJob}'
    },
    //store: Ext.data.StoreManager.lookup('jobsStore'),
    tbar: [
        {
            xtype: 'button',
            text: 'Create',
            handler: function() {
                //if(!win) {
                    var form = Ext.widget('form', {
                        layout: {
                            type: 'vbox',
                            align: 'stretch'
                        },
                        border: false,
                        bodyPadding: 10,
                        fieldDefaults: {
                            labelAlign: 'top',
                            labelWidth: 100
                        },
                        items: [
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Job\'s file name',
                                id: 'jobs_name_txt',
                                name: 'jobs_name_txt_name',
                                allowBlank: false
                            }],
                        buttons: [{
                            text: 'Cancel',
                            handler: function () {
                                this.up('form').getForm().reset();
                                this.up('window').hide();
                            }
                        }, {
                            text: 'Submit',
                            handler: function () {
                                if (this.up('form').getForm().isValid()) {
                                    // In a real application, this would submit the form to the configured url
                                    var jobName = Ext.getCmp('jobs_name_txt').getValue();
                                    this.up('form').getForm().reset();
                                    this.up('window').close();
                                    Ext.MessageBox.alert('Job was added.', 'New job ' + 'was added.');
                                }
                            }
                        }]
                    });
                    win = Ext.widget('window', {
                        title: 'Create new job',
                        closeAction: 'hide',
                        width: 300,
                        height: 350,
                        layout: 'fit',
                        resizable: true,
                        modal: true,
                        items: form
                    });
                //}
                win.show();
            }
        }
    ],
    columns: [
        { text: 'Jobs',  dataIndex: 'fileNameToShow', width: 120 ,bind:'{currentJob.fileNameToShow}'}
        ]//,
    //listeners: {
    //    selectionchange:function(sm, selection) {
    //        console.log(records);
    //    //    var app = DataGenerator.getApplication();
    //    //
    //    //    var item = selection[0];
    //    //    this.stepsGrid.jobSelected = item.id;
    //    //    Ext.data.StoreManager.lookup('stepsStore').loadData(item.data.steps);
    //    //    var stepsGrid = Ext.getCmp('steps-grid');
    //    //    stepsGrid.setTitle('Steps for '+item.data.fileNameToShow);
    //    //    stepsGrid.fileName = item.data.fileNameToShow;
    //    //    Ext.data.StoreManager.lookup('nonLocationOutputStore').removeAll();
    //    //    Ext.data.StoreManager.lookup('scenariosForStep').removeAll();
    //    //
    //    },
    //    afterRender:function(grid){
    //        this.getViewModel().getStore('jobs').load();
    //    }
    //}//,


    //showContactForm:
});
