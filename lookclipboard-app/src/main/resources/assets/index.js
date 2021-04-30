/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 19:28:51
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-30 19:33:53
 * @Description: file content
 */
function addRecord(recordJson){
    var record = JSON.parse(recordJson);
    document.getElementById("parsed").innerHTML=record.content;
    document.getElementById("raw").innerHTML=recordJson;
}