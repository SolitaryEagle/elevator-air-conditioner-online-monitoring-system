//////////////////////////   global variables defined     /////////////////////////////////////////

var PROTOCOL_FIELD = "ASF;DASGIUASVNVEWFOSF%*&";

var LOGIN_USER_KEY = PROTOCOL_FIELD + "loginUser";

var MILLISECONDS_ONE_DAY = 86400000;
var PROJECT_BASE_MAPPING_V1 = "/v1/monitoring-system";
var USER_BASE_MAPPING_V1 = "/v1/monitoring-system/users";


////////////////////////  global function defined  ////////////////////////////////////

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
    if (typeof value == "undefined" || value == null){
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
    if ($.isEmptyObject(storeValue)){
        return false;
    }
    var data = storeValue.data;
    data[field] = value;
    setValueToStore(key, data);
    return true;
}


