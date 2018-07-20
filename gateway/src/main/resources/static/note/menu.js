var floads=new Vue({
    el:'#floads',
    data:{
        folders:{}
    },
    mounted:function () {
        var that=this;
        axios.get('/note/note/list').then(function (d) {
            var rootMenu={
                id:'root',
                title:'root',
                subs:[]
            };

            var data={};
            for(var index=0;i<d.length;i++){
                data[d[index].id]=d[index];
            }


            for(var key in data){
                var obj=data[key];
                if(obj.parentId){
                    var parent=data[obj.parentId];
                    console.log(parent)
                    if(parent!=null){
                        if(!parent.subs){
                            parent.subs=[];
                        }
                        parent.subs.push(obj);
                    }
                }else{
                    obj.parentId=rootMenu.id;
                    rootMenu.subs.push(obj);
                }
            }
            data[rootMenu.id]=rootMenu;
            that.folders=data;

        });

    },
    methods:{
        select:function (index,fload) {
            var currentFload=this.folders[index];
            if(index>this.maxMenuNum-2){
                this.folders.shift();
                this.folders.push(fload)
            }else{
                this.folders.splice(index+1,this.folders.length-index-1);
                this.folders.push(fload)
            }
        },
        back:function(){
            var currentFload=folders[0];
            if(currentFload!=null){
                var parent=this.data[currentFload.parentFileId];
                if(parent!=null){
                    folders.unshift(parent);
                    if(folders.length>this.maxMenuNum){
                        folders.pop();
                    }
                }
            }
        }
    }
});


var menu=new Vue({
    el:'#menu',
    data:{
        pos:{
            left:0,
            top:0
        },
        showMenu: false,
        foucs:false,
        rightMenu:[
            {id:'add',name:'增加笔记'},
            {id:'del',name:'删除笔记'}
        ]
    },
    methods:{
        showNoteMenu(e,item){
            this.left=e.offsetX+'px';
            this.top=e.offsetY+'px';
            this.showMenu=true;
            this.focus=true;
        },
        menuClick(menu){
            this.showMenu=false;
            alert(menu.id)
        },
        hideRightMenu(){
            this.showMenu=false;
        }
    }
});