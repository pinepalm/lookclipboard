/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 19:28:51
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-04-26 19:53:40
 * @Description: file content
 */
function addRecord(info){
    var test = JSON.parse(info);
    document.getElementById("parsed").innerHTML=test.info;
    document.getElementById("raw").innerHTML=info;
}