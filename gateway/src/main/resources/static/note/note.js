var note=new Vue({
    el:'#note',
    data:{
        tabs:[],
        nodeTab: null,
    },
    method:{
        removeTab:function(targetName) {
            let tabs = this.tabs;
            let activeName = this.nodeTab;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.id+'' === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.id+'';
                        }
                    }
                });
            }

            this.nodeTab = activeName;
            this.tabs = tabs.filter(tab => tab.id != targetName);

        },
        openNode(note) {
            var hasTab = false;
            for (var i in this.tabs) {
                if (this.tabs[i].id == note.id) {
                    hasTab = true;
                    break;
                }
            }
            note.changed=false;
            if (!hasTab) {
                this.tabs.push(note)
            }
            this.nodeTab = note.id+'';
            // var win = this.$refs['iframe_'+note.id][0].contentWindow;
            // win.noteChanged=this.noteChanged;
        },
        deleteNote(item){

            var that=this;

            this.$confirm('删除“' + item.title + '”?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                axios.post('/note/note/delete','id='+item.id).then(function (data) {
                    if(data){
                        for(var i in that.currentNoteList){
                            if(that.currentNoteList[i].id==item.id){
                                that.currentNoteList.splice(i,1);
                                for(var j in that.tabs) {
                                    if (that.tabs[j].id == item.id) {
                                        that.tabs.splice(j,1);
                                        if(that.tabs[j]!=null){
                                            that.nodeTab=that.tabs[j].id+'';
                                        }
                                        break;
                                    }
                                }

                                break;
                            }
                        }
                    }
                }).catch(function () {

                })
            })


        },
        renameNote(item){
            this.$prompt('重命名“' + item.title + '”?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({value}) => {
                axios.post('/note/note/rename',{title:value,id:item.id}).then(function (data) {
                    if(data){
                        item.title=value;
                    }
                })
            }).catch(function (e) {
                console.log(e)
            })
        },
        saveNote(item){
            var win = this.$refs['iframe_'+item.id][0].contentWindow;
            var note=win.getNote();

            axios.post('/note/note/save',note).then(function (data) {
                if (data == note.id) {
                    win.saved();
                }
            }).catch(function (e) {

            });
        },
        noteChanged(id){
            for(var i in this.nodeTab){
                if(nodeTab[i].id==id){
                    nodeTab[i].changed=true;
                }
            }
        },
        createNote() {
            var that = this;
            this.$prompt('输入笔记本名称', '标题', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
//                    inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
//                    inputErrorMessage: '邮箱格式不正确'
            }).then(({value}) => {
                axios.post('/note/note/save', {title: value,groupId:that.currentCard.id}).then(function (data) {
                    var note={
                        id: data,
                        groupId: that.currentCard.id,
                        title: value
                    };
                    that.currentNoteList.push(note);
                    that.openNode(note);
                }).catch(function (e) {
                    that.$message({
                        type: 'error',
                        message: '创建失败'
                    })
                })
            }).catch(function () {

            })
        },
        openUrl(url){
            window.open(url);
        }
    }
});

openNote=note.openNote;