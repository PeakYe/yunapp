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
            // {
            //     id: 'newNote',name:'创建文件夹',method:function (arg,selecedMenu) {
            //         var note=createNote(menu.object.id);
            //         if(note!=null){
            //
            //         }
            //     }
            // },
            // {
            //     id: 'del', name: '删除', method: function () {
            //         floads.deleteFolder(menu.object);
            //     }
            // }
        ]
    },
    methods: {
        /**
         *
         * @param e
         * @param item 触发元素对象
         * @param list 显示菜单
         */
        showNoteMenu(e, item, list) {
            this.rightMenu = list;
            this.object = item;
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
        maxMenuNum: 3,
        minw: 300,
        width: '300px'
    },
    mounted: function () {
        var that = this;
        axios.post('/note/note/getFolders').then(function (d) {
            var rootMenu = {
                id: 'root',
                title: 'root',
                type: 'folder',
                subs: []
            };

            var data = {};
                    for (var index = 0; index < d.length; index++) {
                var obj = d[index];
                data[obj.fileId] = {
                    id: obj.fileId,
                    title: obj.fileName,
                    type: obj.fileType,
                    trueId: obj.trueId,
                    parentId: obj.parentId,
                    subs: []
                };
            }


            for (var key in data) {
                var obj = data[key];

                if (obj.parentId) {
                    var parent = data[obj.parentId];
                    if (parent != null) {
                        parent.subs.push(obj);
                    }
                } else {
                    obj.parentId = rootMenu.id;
                    rootMenu.subs.push(obj);
                }


            }
            data[rootMenu.id] = rootMenu;
            that.folders.push(rootMenu);

        });
    },
    methods: {
        select: function (index, folder) {

            if (folder.type == 'folder') {
                if (!this.open) {
                    this.folders = [folder]
                } else if (index > this.maxMenuNum - 2) {
                    this.folders.shift();
                    this.folders.push(folder)
                } else {
                    this.folders.splice(index + 1, this.folders.length - index - 1);
                    this.folders.push(folder)
                }
                this.width = this.minw * this.folders.length + 'px';
            } else {
                if (this.open) {
                    this.push();
                }
                openNote(folder);
            }
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
            var that=this;
            menu.showNoteMenu(e, folder, [
                {
                    id: 'newNote',
                    name: '创建笔记',
                    method: function (arg, selecedMenu) {
                        // arg===folder
                        that.createNote(folder.trueId);
                    }
                },
                {
                    id: 'newFolder',
                    name: '创建文件夹',
                    method: function (arg, selecedMenu) {
                        // arg===folder
                        that.createFolder(folder);
                    }
                },
                {
                    id: 'del', name: '删除',
                    method: function () {
                        that.delete(folder);
                    }
                }
            ]);
        },
        delete: function (folder) {
            this.data[folder.id] = null;
            // console.log('del '+folder.title)
            // folder=null;
            var that=this;
            if(folder.subs.length>0) {
                that.$message({
                    type: 'error',
                    message: '文件夹存在文件，不能删除'
                });
                return;
            }

            this.$confirm('删除“' + folder.title + '”?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                axios.post('/note/note/delete','id='+folder.id).then(function (data) {
                    if(data){
                        folder.title = "deleted";
                        folder.deleted = true;
                    }
                }).catch(function () {
                    that.$message({
                        type: 'error',
                        message: '删除失败'
                    })
                })
            })
        },
        createFolder: function (parentFolder) {
            var that=this;
            this.$prompt(null, '输入文件夹名称', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
//                    inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
//                    inputErrorMessage: '邮箱格式不正确'
            }).then(({value}) => {

                axios.post('/note/group/create', {name: value}).then(function (data) {
                    that.noteCards.push({
                        id: data,
                        name: value
                    });
                    var f={
                        id: 'folder'+data,
                        title: value,
                        type: 'folder',
                        trueId: data,
                        parentId: parentFolder.id,
                        subs: []
                    };
                    that.data[f.id]=f;
                    that.data[f.parentId].subs.push(f);
                }).catch(function (e) {
                    that.$message({
                        type: 'error',
                        message: '创建失败'
                    })
                })
            })
        },
        setWidth: function () {
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
                    } else {
                        break;
                    }
                }
            }
            this.open = !this.open;
        },
        createNote(folderId) {
            var that = this;
            this.$prompt('输入笔记本名称', '标题', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
//                    inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
//                    inputErrorMessage: '邮箱格式不正确'
            }).then(({value}) => {
                axios.post('/note/note/save', {title: value,groupId:folderId}).then(function (data) {
                    var note=data;
                    if (note != null) {
                        var f={
                            id: note.fileType+note.fileId,
                            title: note.fileName,
                            type: note.fileType,
                            trueId: note.fileId,
                            parentId: note.parentId,
                            subs: []
                        };
                        that.data[f.id]=f;
                        that.data[f.parentId].subs.push(f);

                    }
                }).catch(function (e) {
                    that.$message({
                        type: 'error',
                        message: '创建失败'
                    })
                })
            }).catch(function () {

            })
        },
    }
});

