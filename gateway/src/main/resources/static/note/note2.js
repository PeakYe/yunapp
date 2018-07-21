var app = new Vue({
    el: '#hello',
    native: true,
    data: {
        showCardList: false,
        showCardDetail: false,
        currentCard: {
            name: '笔记本'
        },
        currentManagedCard: {
            id: 0,
            name: '笔记本'
        },
        closeIframe: true,
        dialogTableVisible: false,
        notes: {},
        currentNoteList:[],
        nodeTab: null,
        tabs: [],
        noteCards: [],

        textHeight: 400,
        leftHeight: 400
    },
    mounted() {
        window.addEventListener('cardScroll', this.cardScroll);
        this.textHeight = document.body.offsetHeight - 140;
        this.leftHeight = document.body.offsetHeight - 61;
        var that=this;


        // axios.get('/note/group/list').then(function (data) {
        //     var noCard=localStorage.cardId==null;
        //     for (var i=0;i<data.length; i++) {
        //         app.noteCards.push(data[i]);
        //         if(!noCard && data[i].id==localStorage.cardId){
        //             that.openCard(data[i]);
        //         }
        //     }
        //
        //
        // });

    },
    methods: {
        showCardMange(index) {
            this.currentManagedCard = this.noteCards[index];
            this.currentManagedCard.index = index;
            this.showCardDetail = true;
        },
        openCard(card) {
            var that = this;
            if (this.notes[card.id] == null) {
                axios.post('/note/note/groupNotes',"id="+card.id).then(function (data) {
                    that.currentNoteList=data;
                    that.notes[card.id] = data;
                }).catch(function (e) {
                    console.error(e);
                })
            }
            that.currentCard = card;
            that.currentNoteList=this.notes[card.id];
            localStorage.cardId=card.id;
        },
        closeNoteCard() {
            this.showCardDetail = false;
        },
        removeTab(targetName) {
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
        deleteCard() {

            var that = this;
            this.$confirm('确认删除卡片“' + this.currentManagedCard.name + '”?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios.post('/note/group/delete', 'groupId=' + this.currentManagedCard.id).then(function (data) {
                    if (data) {
                        that.$message({
                            type: 'info',
                            message: '删除成功'
                        });
                        that.showCardDetail = false;

                        var index = that.currentManagedCard.index;
                        that.currentManagedCard = {};
                        that.noteCards.splice(index, 1);
                    }
                }).catch((e) => {
                    console.log(e);
                })

            }).catch(() => {

            });

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
        deleteNote(item,index){

            var that=this;

            this.$confirm('删除“' + item.title + '”?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({value}) => {
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
        rightClick(e,item) {
            // console.log(item)
            // e.stopPropagation();
            item.popoverShow=true;
        },
        handleSelect() {

        },
        cardScroll(event) {
            this.$refs.cardScroll.scrollLeft += event.deltaY / 1.6;
        },
        newNoteCard() {
            var that = this;
            this.$prompt('输入卡片名称', '提示', {
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
                }).catch(function (e) {
                    that.$message({
                        type: 'error',
                        message: '创建失败'
                    })
                })
            }).catch((e) => {
//                    this.$message({
//                        type: 'info',
//                        message: '取消输入'
//                    });
            });
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

