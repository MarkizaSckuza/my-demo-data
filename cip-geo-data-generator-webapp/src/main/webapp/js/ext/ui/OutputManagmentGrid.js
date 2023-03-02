Ext.define("DataGenerator.ui.OutputManagmentGrid", {
    extend: 'Ext.grid.Panel',
    title: 'Outputs',
    id: 'outputs-grid',
    height: 900,
    viewConfig: {forceFit: true},
    floating:false,
    floatable: false,
    columns: [
        { text: '#',  dataIndex: 'id', width: 55 }
        ],
    clearTitle:function(){
        this.setTitle(this.titeleCopy);
    }
});
