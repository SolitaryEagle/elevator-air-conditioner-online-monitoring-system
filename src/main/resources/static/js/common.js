//////////////////////////   global variables defined     /////////////////////////////////////////

var LOGIN_USER_KEY = "AMbVbgk4jB7xIa0U";
var CUR_EQUIPMENT_ID_KEY = "sP2qMCrXa5WC%V38";

var MILLISECONDS_ONE_DAY = 86400000;
var PROJECT_BASE_MAPPING_V1 = "/v1/monitoring-system";
var USER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/users";
var AIR_CONDITIONER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/air-conditioners";

var SLEEP_TIME = 1000;


////////////////////////  global fragments defined  ////////////////////////////////////

var HEADER_NAVIGATION_DIV_HTML =
    '<div class="layui-header">' +
    '    <div class="layui-logo" style="width: 300px; font-size: 26px;">电梯风扇监控系统</div>' +
    '    <!-- 头部区域（可配合layui已有的水平导航） -->' +
    '    <ul class="layui-nav layui-layout-left" style="left: 300px;">' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/page/index">首页</a></li>' +
    '        <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/add-info">设备管理</a></li>' +
    '        <li class="layui-nav-item"><a href="#">维修管理</a></li>' +
    '    </ul>' +
    '    <ul class="layui-nav layui-layout-right">' +
    '        <li class="layui-nav-item">' +
    '            <a href="javascript:;">\n' +
    '                <img src="/image/head-portrait.jpg" class="layui-nav-img">' +
    '                    贤心' +
    '            </a>' +
    '            <dl class="layui-nav-child">' +
    '                <dd><a href="">基本资料</a></dd>' +
    '                <dd><a href="">安全设置</a></dd>' +
    '            </dl>' +
    '        </li>' +
    '        <li class="layui-nav-item"><a href="">退出</a></li>' +
    '    </ul>' +
    '</div>';

var SIDE_NAVIGATION_DIV_HTML =
    '<div class="layui-side layui-bg-black" style="width: 300px;">' +
    '    <div class="layui-side-scroll" style="width: 100%">' +
    '        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->' +
    '        <ul class="layui-nav layui-nav-tree" lay-filter="test" style="width: 100%; text-align: center">' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/add-info" style="font-size: 20px"><i class="layui-icon layui-icon-add-circle" style="font-size: 20px;color: #5cb85c;"></i> 添加设备</a></li>' +
    '            <li class="layui-nav-item"><a href="/v1/monitoring-system/air-conditioners/page/online-state" style="font-size: 20px"><i class="layui-icon layui-icon-console" style="font-size: 20px; color: #01AAED;"></i> 状态查看</a></li>' +
    '            <li class="layui-nav-item"><a href="" style="font-size: 20px"><i class="layui-icon layui-icon-face-surprised" style="font-size: 20px"></i> 故障查看</a></li>' +
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
}

// 初始化全局模块的 sideNavigationDiv
function initSideNavigationDiv() {
    $("#side-navigation-div-id").replaceWith(SIDE_NAVIGATION_DIV_HTML);
}

// 初始化全局模块的FooterDiv
function initFooterDiv() {
    $("#footer-div-id").replaceWith(FOOTER_DIV_HTML);
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

