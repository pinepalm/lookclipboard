/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 19:28:51
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-12 16:54:24
 * @Description: index.js
 */
function addRecord(recordJson) {
    var record = JSON.parse(recordJson);
    document.getElementById("parsed").innerHTML = record.content;
    document.getElementById("raw").innerHTML = recordJson;
}

function testSetOpacity() {
    settingsService.setOpacity(0.5);
}

function testSetAlwaysOnTop() {
    settingsService.setAlwaysOnTop(true);
}

function testGetAllRecords() {
    var condition = {
        dataFormats: [
            ["text/plain"]
        ]
    };
    var result = JSON.parse(clipboardService.getRecordsCountByCondition(JSON.stringify(condition)));
    document.getElementById("parsed").innerHTML = result.data;
    document.getElementById("raw").innerHTML = JSON.stringify(result);
}