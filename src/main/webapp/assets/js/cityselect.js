/*
 Ajax 三级省市联动

 settings 参数说明
 -----
 url:省市数据josn文件路径
 prov:默认省份
 city:默认城市
 dist:默认地区（县）
 nodata:无数据状态
 required:必选项
 ------------------------------ */
(function ($) {
    $.fn.citySelect = function (settings) {
        if (this.length < 1) {
            return;
        }
        ;

        // 默认值
        settings = $.extend({
            url: "../assets/js/city.min.js",
            prov: null,
            city: null,
            dist: null,
            nodata: null,
            required: true
        }, settings);

        var box_obj = this;
        var area_obj = box_obj.find(".namelist");
        var prov_obj = box_obj.find(".arealist .province");
        var city_obj = box_obj.find(".arealist .city");
        var prov_val = settings.prov;
        var city_val = settings.city;
        var dist_val = settings.dist;
        var select_prehtml = "";
        var city_json;

        // 赋值市级函数
        var cityStart = function () {
            var prov_id = prov_obj.get(0).selectedIndex;
            if (!settings.required) {
                prov_id--;
            }
            ;
            city_obj.empty().attr("disabled", true);
            dist_obj.empty().attr("disabled", true);

            if (prov_id < 0 || typeof(city_json.citylist[prov_id].c) == "undefined") {
                if (settings.nodata == "none") {
                    city_obj.css("display", "none");
                    dist_obj.css("display", "none");
                } else if (settings.nodata == "hidden") {
                    city_obj.css("visibility", "hidden");
                    dist_obj.css("visibility", "hidden");
                }
                ;
                return;
            }
            ;

            // 遍历赋值市级下拉列表
            temp_html = select_prehtml;
            $.each(city_json.citylist[prov_id].c, function (i, city) {
                temp_html += "<option value='" + city.i + "'>" + city.n + "</option>";
            });
            city_obj.html(temp_html).attr("disabled", false).css({"display": "", "visibility": ""});
            distStart();
        };

        // 赋值地区（县）函数
        var distStart = function () {
            var prov_id = prov_obj.get(0).selectedIndex;
            var city_id = city_obj.get(0).selectedIndex;
            if (!settings.required) {
                prov_id--;
                city_id--;
            }
            ;
            dist_obj.empty().attr("disabled", true);

            if (prov_id < 0 || city_id < 0 || typeof(city_json.citylist[prov_id].c[city_id].a) == "undefined") {
                if (settings.nodata == "none") {
                    dist_obj.css("display", "none");
                } else if (settings.nodata == "hidden") {
                    dist_obj.css("visibility", "hidden");
                }
                ;
                return;
            }
            ;

            // 遍历赋值市级下拉列表
            temp_html = "";
            $.each(city_json.citylist[prov_id].c[city_id].a, function (i, dist) {
                temp_html += "<option value='" + dist.i + "'>" + dist.s + "</option>";
            });
            dist_obj.html(temp_html).attr("disabled", false).css({"display": "", "visibility": ""});
        };

        var init = function () {
            // 遍历赋值省份下拉列表
            temp_html = select_prehtml;
            $.each(city_json.citylist, function (i, prov) {
                temp_html += "<a href='###'>" + prov.p + "</a>";
            });
            area_obj.html(temp_html);

            // 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
            setTimeout(function () {
                if (settings.prov != null) {
                    prov_obj.html(prov_val);
                }
                if (settings.city != null) {
                    city_obj.html(city_val);
                }
                ;
            }, 1);

            // 选中省份时发生事件
            prov_obj.bind("click", function () {
                cityStart();
            });

            // 选中市级时发生事件
            city_obj.bind("click", function () {
                distStart();
            });
        };

        // 设置省市json数据
        if (typeof(settings.url) == "string") {
            $.getJSON(settings.url, function (json) {
                city_json = json;
                init();
            });
        } else {
            city_json = settings.url;
            init();
        }
        ;
    };
})(jQuery);