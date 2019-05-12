//////////////////////////   global variables defined     /////////////////////////////////////////

var LOGIN_USER_KEY = "AMbVbgk4jB7xIa0U";
var CUR_EQUIPMENT_ID_KEY = "sP2qMCrXa5WC%V38";

var MILLISECONDS_ONE_DAY = 86400000;
var PROJECT_BASE_MAPPING_V1 = "/v1/monitoring-system";
var USER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/users";
var AIR_CONDITIONER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/air-conditioners";
var FAULT_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/faults";
var ALLOCATION_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/allocations";
var REPAIR_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/repairs";
var REVISIT_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/revisits";
var EVALUATION_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/evaluations";

var SLEEP_TIME = 1000;


////////////////////////  global fragments defined  ////////////////////////////////////


var HEADER_NAVIGATION_DIV_HTML_TMP =
    '<div class="layui-header">' +
    '    <div class="layui-logo" style="width: 300px; font-size: 26px;">电梯风扇监控系统</div>' +
    '    <!-- 头部区域（可配合layui已有的水平导航） -->' +
    '    <ul class="layui-nav layui-layout-left" style="left: 300px;">' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/page/index">首页</a></li>' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/online-state">设备管理</a></li>' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/online-state">故障管理</a></li>' +
    '    </ul>' +
    '    <ul class="layui-nav layui-layout-right">' +
    '        <li class="layui-nav-item">' +
    '            <a href="javascript:;">\n' +
    '                <img src="/image/head-portrait.jpg" class="layui-nav-img">' +
    '                    <span id="login-username-span-id">贤心</span>' +
    '            </a>' +
    '            <dl class="layui-nav-child">' +
    '                <dd><a href="">基本资料</a></dd>' +
    '                <dd><a href="">安全设置</a></dd>' +
    '            </dl>' +
    '        </li>' +
    '        <li class="layui-nav-item" id="logout-btn-id"><a href="">退出</a></li>' +
    '    </ul>' +
    '</div>';



var HEADER_NAVIGATION_DIV_HTML =
    '<div class="layui-header">' +
    '    <div class="layui-logo" style="width: 300px; font-size: 26px;">电梯风扇监控系统</div>' +
    '    <!-- 头部区域（可配合layui已有的水平导航） -->' +
    '    <ul class="layui-nav layui-layout-left" style="left: 300px;">' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/page/index">首页</a></li>' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/online-state">设备管理</a></li>' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/online-state">故障管理</a></li>' +
    '    </ul>' +
    '    <ul class="layui-nav layui-layout-right">' +
    '        <li class="layui-nav-item">' +
    '            <a href="javascript:;">\n' +
    '                <img src="/image/head-portrait.jpg" class="layui-nav-img">' +
    '                    <span id="login-username-span-id">贤心</span>' +
    '            </a>' +
    '        </li>' +
    '        <li class="layui-nav-item" id="logout-btn-id"><a href="">退出</a></li>' +
    '    </ul>' +
    '</div>';


var HEADER_NAVIGATION_DIV_HTML_ADMIN =
    '<div class="layui-header">' +
    '    <div class="layui-logo" style="width: 300px; font-size: 26px;">电梯风扇监控系统</div>' +
    '    <!-- 头部区域（可配合layui已有的水平导航） -->' +
    '    <ul class="layui-nav layui-layout-left" style="left: 300px;">' +
    '        <li class="layui-nav-item"><a href="#">用户管理</a></li>' +
    '    </ul>' +
    '    <ul class="layui-nav layui-layout-right">' +
    '        <li class="layui-nav-item">' +
    '            <a href="javascript:;">\n' +
    '                <img src="/image/head-portrait.jpg" class="layui-nav-img">' +
    '                    <span id="login-username-span-id">贤心</span>' +
    '            </a>' +
    '        </li>' +
    '        <li class="layui-nav-item" id="logout-btn-id"><a href="">退出</a></li>' +
    '    </ul>' +
    '</div>';

///////////////////////////  设备管理侧边导航栏  /////////////////////////////////

var SIDE_NAVIGATION_DIV_HTML_ORDINARY_EQUIPMENT =
    '<div class="layui-side layui-bg-black" style="width: 300px;">' +
    '    <div class="layui-side-scroll" style="width: 100%">' +
    '        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->' +
    '        <ul class="layui-nav layui-nav-tree" style="width: 100%; text-align: center">' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/online-state" style="font-size: 20px"><i class="layui-icon layui-icon-console" style="font-size: 20px; color: #01AAED; margin: 0 10px;"></i>状态查看</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/add-info" style="font-size: 20px"><i class="layui-icon layui-icon-add-circle" style="font-size: 20px;color: #5cb85c; margin: 0 10px;"></i>添加设备</a></li>' +
    '        </ul>' +
    '    </div>' +
    '</div>';

var SIDE_NAVIGATION_DIV_HTML_CUSTOM_OR_REPAIR_EQUIPMENT =
    '<div class="layui-side layui-bg-black" style="width: 300px;">' +
    '    <div class="layui-side-scroll" style="width: 100%">' +
    '        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->' +
    '        <ul class="layui-nav layui-nav-tree" style="width: 100%; text-align: center">' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/online-state" style="font-size: 20px"><i class="layui-icon layui-icon-console" style="font-size: 20px; color: #01AAED; margin: 0 10px;"></i>状态查看</a></li>' +
    '        </ul>' +
    '    </div>' +
    '</div>';


///////////////////////  故障管理侧边导航栏  /////////////////////////

var SIDE_NAVIGATION_DIV_HTML_ORDINARY_FAULT =
    '<div class="layui-side layui-bg-black" style="width: 300px;">' +
    '    <div class="layui-side-scroll" style="width: 100%">' +
    '        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->' +
    '        <ul class="layui-nav layui-nav-tree" style="width: 100%; text-align: center">' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/online-state" style="font-size: 20px"><i class="layui-icon layui-icon-face-surprised" style="font-size: 20px; color: #FF5722; margin: 0 10px;"></i>故障查看</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/add" style="font-size: 20px"><i class="layui-icon layui-icon-add-circle" style="font-size: 20px;color: #5cb85c; margin: 0 10px;"></i>故障新增</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/evaluation" style="font-size: 20px"><i class="layui-icon layui-icon-edit" style="font-size: 20px; margin: 0 10px; color: #FFB800"></i>故障评价</a></li>' +
    '        </ul>' +
    '    </div>' +
    '</div>';

var SIDE_NAVIGATION_DIV_HTML_CUSTOM_FAULT =
    '<div class="layui-side layui-bg-black" style="width: 300px;">' +
    '    <div class="layui-side-scroll" style="width: 100%">' +
    '        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->' +
    '        <ul class="layui-nav layui-nav-tree" style="width: 100%; text-align: center">' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/online-state" style="font-size: 20px"><i class="layui-icon layui-icon-face-surprised" style="font-size: 20px; color: #FF5722; margin: 0 10px;"></i>故障查看</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/allocation" style="font-size: 20px"><i class="layui-icon layui-icon-auz" style="font-size: 20px;color: #5cb85c; margin: 0 10px;"></i>故障分配</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/revisit" style="font-size: 20px"><i class="layui-icon layui-icon-survey" style="font-size: 20px; margin: 0 10px; color: #FFB800"></i>故障回访</a></li>' +
    '        </ul>' +
    '    </div>' +
    '</div>';

var SIDE_NAVIGATION_DIV_HTML_REPAIR_FAULT =
    '<div class="layui-side layui-bg-black" style="width: 300px;">' +
    '    <div class="layui-side-scroll" style="width: 100%">' +
    '        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->' +
    '        <ul class="layui-nav layui-nav-tree" style="width: 100%; text-align: center">' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/online-state" style="font-size: 20px"><i class="layui-icon layui-icon-face-surprised" style="font-size: 20px; color: #FF5722; margin: 0 10px;"></i>故障查看</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/accept" style="font-size: 20px"><i class="layui-icon layui-icon-notice" style="font-size: 20px;color: #5cb85c; margin: 0 10px;"></i>故障确认</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/faults/page/handle" style="font-size: 20px"><i class="layui-icon layui-icon-set" style="font-size: 20px; margin: 0 10px; color: #FFB800"></i>故障处理</a></li>' +
    '        </ul>' +
    '    </div>' +
    '</div>';


var FOOTER_DIV_HTML =
    '<div class="layui-footer" style="left: 300px">' +
    '    <!-- 底部固定区域 -->' +
    '    河海大学常州校区 2015级物联网工程学院 毕业设计' +
    '</div>';


////////////////////////  global function defined  ////////////////////////////////////

// 初始化全局模块的 headerNavigationDiv
function initHeaderNavigationDiv() {
    $("#header-navigation-div-id").replaceWith(HEADER_NAVIGATION_DIV_HTML);

    var loginUser = getValidValueFromStore(LOGIN_USER_KEY);
    var username = loginUser.username;
    if (username.length <= 0) {
        username = loginUser.email;
    }
    $("#login-username-span-id").text(username);

}

// 初始化  Admin的  全局模块的 headerNavigationDiv
function initHeaderNavigationDivAdmin() {
    $("#header-navigation-div-id").replaceWith(HEADER_NAVIGATION_DIV_HTML_ADMIN);

    var loginUser = getValidValueFromStore(LOGIN_USER_KEY);
    var username = loginUser.username;
    if (username.length <= 0) {
        username = loginUser.email;
    }
    $("#login-username-span-id").text(username);
}

// 初始化 ORDINARY 的 Equipment 的 sideNavigationDiv
function initSideNavigationDivOrdinaryEquipment() {
    $("#side-navigation-div-id").replaceWith(SIDE_NAVIGATION_DIV_HTML_ORDINARY_EQUIPMENT);
}

// 初始化 CUSTOM OR REPAIR 的 Equipment 的 sideNavigationDiv
function initSideNavigationDivCustomOrRepairEquipment() {
    $("#side-navigation-div-id").replaceWith(SIDE_NAVIGATION_DIV_HTML_CUSTOM_OR_REPAIR_EQUIPMENT);
}

// 初始化 ORDINARY 的 FAULT 的 sideNavigationDiv
function initSideNavigationDivOrdinaryFault() {
    $("#side-navigation-div-id").replaceWith(SIDE_NAVIGATION_DIV_HTML_ORDINARY_FAULT);
}

// 初始化 CUSTOM 的 FAULT 的 sideNavigationDiv
function initSideNavigationDivCustomFault() {
    $("#side-navigation-div-id").replaceWith(SIDE_NAVIGATION_DIV_HTML_CUSTOM_FAULT);
}

// 初始化 REPAIR 的 FAULT 的 sideNavigationDiv
function initSideNavigationDivRepairFault() {
    $("#side-navigation-div-id").replaceWith(SIDE_NAVIGATION_DIV_HTML_REPAIR_FAULT);
}

// 初始化全局模块的FooterDiv
function initFooterDiv() {
    $("#footer-div-id").replaceWith(FOOTER_DIV_HTML);
}

// layui 初始化
function layuiInit() {
    layui.use(['element', 'layer', 'rate'], function () {
        var element = layui.element;
        var layer = layui.layer;
        var rate = layui.rate;

    });
}

// 获取风速描述
function getWindSpeed(windSpeed) {
    if (windSpeed === 'FAST') {
        return  '快';
    } else if (windSpeed === 'SLOW') {
        return '慢';
    } else {
        return '停止';
    }
}

// 获取故障状态
function getFaultState(state) {
    if (state === 'NEW') {
        return '新建';
    } else if (state === 'ALLOCATED') {
        return '已分配';
    } else if (state === 'REPAIRING') {
        return '正在维修';
    } else if (state === 'REPAIRED') {
        return '已维修';
    } else if (state === 'REVISITED') {
        return '已回访';
    } else {
        return '维修结束';
    }
}

function getDataFromServer(url) {
    var outResult = {};
    $.ajax({
        url: url,
        type: "get",
        async: false,
        success: function (result, status, xhr) {
            var obj = JSON.parse(result);
            var code = obj.code;
            var message = obj.message;
            var data = obj.data;
            if (code !== 200) {
                kingLayerMsg(message, airConditionerDefaultLayerMsgOptions);
            } else {
                outResult = data;
            }
        }
    });
    return outResult;
}

// 退出
function logoutEvent(ev) {

    $.ajax({
        url: USER_BASE_MAPPING_V1 + "/logout",  // 查询地图需要的所有设备信息
        type: "post",
        async: false,
        success: function (result, status, xhr) {
            var obj = JSON.parse(result);
            var code = obj.code;
            var message = obj.message;
            var data = obj.data;
            if (code !== 200) {
                kingLayerMsg(message, airConditionerDefaultLayerMsgOptions);
            } else {
                // 清空 store.js
                store.remove(LOGIN_USER_KEY);
                window.location.href = USER_BASE_MAPPING_V1 + "/page/login";
            }
        }
    });
    return false;
}


// 按钮定时器
function timer(btnId, time) {
    var btn = document.getElementById(btnId);
    btn.className = "login-btn-disabled";
    btn.innerText = time <= 0 ? "发送邮件" : "" + time + " 秒";
    var handler = setInterval(function () {
        if (time <= 0) {
            clearInterval(handler);
            btn.className = "login-btn";
            btn.innerText = "发送邮件";
            return false;
        } else {
            btn.innerText = "" + (time--) + " 秒";
        }
    }, 1000);
}

// 将数据保存到 store 中
function setValueToStore(key, data) {

    var value = {};
    var now = new Date();
    value.beginTime = now.getTime();
    value.validTime = MILLISECONDS_ONE_DAY;
    value.data = data;

    // 保存用户信息
    store.set(key, value);

}

// 获取 store 中的数据，无论数据是否过期
function getValueFromStoreAnyway(key) {
    var value = store.get(key);
    if (typeof value == "undefined" || value == null) {
        return {};
    }
    return value.data;
}

// 获取 store 中的数据，并判断是否已过期
function getValidValueFromStore(key) {
    var value = store.get(key);
    var now = new Date();
    if (typeof value == "undefined" || value == null || (now.getTime() - value.beginTime > value.validTime)) {
        // 已过期，移除 store 中的 key 对应的信息
        store.remove(key);
        return {};
    }
    return value.data;
}

// 更新 store 中的数据，
function updateValueFieldInStore(key, field, value) {
    var storeValue = store.get(key);
    if ($.isEmptyObject(storeValue)) {
        return false;
    }
    var data = storeValue.data;
    data[field] = value;
    setValueToStore(key, data);
    return true;
}

// 整合后的 layer.msg
function kingLayerMsg(message, options) {
    layer.msg(message, options);
    // 对弹窗的样式做一些调整
    $('.layui-layer-msg').css('min-width', '0');
}


// 解析路径中的参数，接收一个 location.search，不支持同名参数
function parseUrlParameter(paramString) {

    paramString = paramString.substring(1);
    var entries = paramString.split('&');
    var result = {};
    for (var i = 0; i < entries.length; i++) {
        var entry = entries[i].split('=');
        result[entry[0]] = entry[1];
    }
    return result;
}

//////////////////////////   air-conditioner common variables defined     /////////////////////////////////////////

var airConditionerDefaultLayerMsgOptions = {time: 3000, skin: 'layui-bg-red', offset: '128px'};


////////////////////////  air-conditioner common fragments defined  ////////////////////////////////////




////////////////////////  air-conditioner common function defined  ////////////////////////////////////

// 地址校验
function checkAddressInfoAndTip() {
    var province = $("#address-province").val().trim();
    if (typeof province == "undefined" || province == null || province.length <= 0) {
        kingLayerMsg('请选择省份!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    var city = $("#address-city").val().trim();
    if (typeof city == "undefined" || city == null || city.length <= 0) {
        kingLayerMsg('请选择城市!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    var district = $("#address-district").val().trim();
    if (typeof district == "undefined" || district == null || district.length <= 0) {
        kingLayerMsg('请选择地区!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    var detail = $("#address-detail").val().trim();
    if (typeof detail != "undefined" && detail != null && detail.indexOf(' ') !== -1) {
        kingLayerMsg('详细地址中不能包含空格!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    return true;
}
