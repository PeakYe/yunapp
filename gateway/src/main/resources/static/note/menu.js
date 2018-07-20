var menu = new Vue({
    el: '#menu',
    data: {
        pos: {
            left: 0,
            top: 0
        },
        showMenu: false,
        focus: false,
        object: null,
        rightMenu: [
            {
                id: 'del', name: '删除', method: function () {
                    floads.deleteFolder(menu.object);
                }
            }
        ]
    },
    methods: {
        showNoteMenu(e, item) {
            this.object = item;
            console.log(e)
            this.pos.left = e.clientX + 'px';
            this.pos.top = e.clientY + 'px';
            this.showMenu = true;
            this.focus = true;
        },
        hideRightMenu() {
            this.showMenu = false;
        }
    }
});


var floads = new Vue({
    el: '#floads',
    data: {
        data: {},
        folders: [],
        open: false,
        maxMenuNum:3,
        minw: 300,
        width: '300px'
    },
    mounted: function () {
        var that = this;
        // axios.get('/note/note/list').then(function (d) {
        //     var rootMenu={
        //         id:'root',
        //         title:'root',
        //         subs:[]
        //     };
        //
        //     var data={};
        //     for(var index=0;i<d.length;i++){
        //         data[d[index].id]=d[index];
        //     }
        //
        //
        //     for(var key in data){
        //         var obj=data[key];
        //         if(obj.parentId){
        //             var parent=data[obj.parentId];
        //             console.log(parent)
        //             if(parent!=null){
        //                 if(!parent.subs){
        //                     parent.subs=[];
        //                 }
        //                 parent.subs.push(obj);
        //             }
        //         }else{
        //             obj.parentId=rootMenu.id;
        //             rootMenu.subs.push(obj);
        //         }
        //     }
        //     data[rootMenu.id]=rootMenu;
        //     that.folders.push(rootMenu);
        //
        // });


        var data = {
            file1: {
                id: 'flie1',
                parentId: '',
                title: 'root1'
            },
            file2: {
                id: 'file2',
                parentId: '',
                title: 'root2'
            },
            file11: {
                id: 'file11',
                parentId: 'file1',
                title: 'wqwqwe'
            },
            file12: {
                id: 'file12',
                parentId: 'file1',
                title: 'wqwqwe'
            },
            file111: {
                id: 'file111',
                parentId: 'file11',
                title: 'wqwqwe'
            },
            file1111: {
                id: 'file1111',
                parentId: 'file111',
                title: 'wqwqwe'
            }
        }

        var rootMenu = {
            id: 'root',
            title: 'root',
            subs: []
        };


        for (key in data) {
            var obj = data[key];
            if (obj.parentId) {
                var parent = data[obj.parentId];
                console.log(parent)
                if (parent != null) {
                    if (!parent.subs) {
                        parent.subs = [];
                    }
                    parent.subs.push(obj);
                }
            } else {
                obj.parentId = rootMenu.id;
                rootMenu.subs.push(obj);
            }
        }


        data[rootMenu.id] = rootMenu;

        this.data = data;
        this.folders.push(rootMenu);

    },
    methods: {
        select: function (index, folder) {
            if(!this.open){
                this.folders=[folder]
            }else if ( index > this.maxMenuNum - 2) {
                this.folders.shift();
                this.folders.push(folder)
            } else {
                this.folders.splice(index + 1, this.folders.length - index - 1);
                this.folders.push(folder)
            }
            this.width = this.minw * this.folders.length + 'px';
        },
        back: function () {
            var currentFload = this.folders[0];
            if (currentFload != null) {
                var parent = this.data[currentFload.parentId];
                if (parent != null) {
                    this.folders.unshift(parent);
                    if (this.folders.length > this.maxMenuNum) {
                        this.folders.pop();
                    }
                }
            }
        },
        noteMenu: function (e, folder) {
            menu.showNoteMenu(e, folder);
        },
        deleteFolder: function (folder) {
            this.data[folder.id] = null;
            // console.log('del '+folder.title)
            // folder=null;
            folder.title = "deleted";
            folder.deleted = true;
        },
        setWidth:function(){
            this.width = this.minw * this.folders.length + 'px';
        },
        push: function () {
            if (this.open) {
                while (this.folders.length > 1) {
                    this.folders.shift();
                    this.setWidth();
                }
            } else {
                while (this.folders.length < this.maxMenuNum) {
                    var first = this.folders[0];
                    var parent = this.data[first.parentId];
                    if (parent != null && !parent.deleted) {
                        this.folders.unshift(parent);
                        this.setWidth();
                    }else{
                        break;
                    }
                }
            }
            this.open=!this.open;
        }
    }
});

