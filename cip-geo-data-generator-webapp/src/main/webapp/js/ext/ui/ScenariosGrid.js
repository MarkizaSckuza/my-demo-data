Ext.define("DataGenerator.ui.ScenariosGrid", {
    extend: 'Ext.grid.Panel',
    title: 'Scenarios ',
    titleCopy: 'Scenarios ',
    id: 'scenarios-grid',
    height: 900,
    //multiSelect: true,
    //simpleSelect: true,
    titeleCopy: 'Scenarios',
    viewConfig: {forceFit: true},
    floating:false,
    floatable: false,
    stepSelected:-1,
    jobSelected:-1,

    initComponent: function() {
        this.columns = this.getColumns();
        this.callParent();

    },
    getColumns: function(){
        return [
            {
                xtype: 'checkcolumn',
                text: 'Selected',
                dataIndex:'selected'
            },
            { text: 'Scenario',  dataIndex: 'fileNameToShow', width: 400 }
        ]
    },
    clearTitle:function(){
        this.setTitle(this.titeleCopy);
    }

});
