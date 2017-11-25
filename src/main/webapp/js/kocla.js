/**
 * Created by abc on 2016/3/22.
 */
(function($){
    $.fn.menus = function(options, param){
        if (typeof options == 'string') {
            return $.fn.menus.methods[options](this, param);
        }
        options = options || {};
        return this.each(function(){
            create(this,param);
        });
    };
    $.fn.menus.methods = {
        loadJSON:function(jq,param){
            alert(jq);
        },
        options: function(jq){
            return $.data(jq[0], 'menus').options;
        },
        panels: function(jq){
            return $.data(jq[0], 'menus').panels;
        },
        resize: function(jq, param){
            return jq.each(function(){
                setSize(this, param);
            });
        },
        getSelections: function(jq){
            return getSelections(jq[0]);
        },
        getSelected: function(jq){
            return getSelected(jq[0]);
        },
        getPanel: function(jq, which){
            return getPanel(jq[0], which);
        },
        getPanelIndex: function(jq, panel){
            return getPanelIndex(jq[0], panel);
        },
        select: function(jq, which){
            return jq.each(function(){
                select(this, which);
            });
        },
        unselect: function(jq, which){
            return jq.each(function(){
                unselect(this, which);
            });
        },
        add: function(jq, options){
            return jq.each(function(){
                add(this, options);
            });
        },
        remove: function(jq, which){
            return jq.each(function(){
                remove(this, which);
            });
        }
    };

    function create(obj){
        alert(obj);
    }
})(jQuery);
