$table = $("#dataGrid");
$(function () {
    initDataGrid();
});
var datePick = function (elem) {
    jQuery(elem).datepicker({dateFormat: 'yyyy-mm-dd'});
}

function getLocalTime(nS, row, index) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/, ' ');
}

var cache = {};

function listFiles(value) {

    var files = JSON.parse(value['files'])

    var content = '<table class="table table-striped text-center table-bordered table-hover">\n'
    content += '<link rel="stylesheet" href="/static/css/style.css"/>\n'
    content += '  <thead>\n' +
        '                <tr>\n' +
        '                  <th>文件名称</th>\n' +
        '                  <th>页数</th>\n' +
        '                  <th>打印份数</th>\n' +
        '                  <th>纸张大小</th>\n' +

        '                  <th>纸张厚度</th>\n' +
        '                  <th>单双面</th>\n' +
        '                  <th>黑白</th>\n' +
        '                  <th>合一</th>\n' +
        '                  <th>加载进度</th>\n' +
        '                  <th></th>\n' +

        '                </tr>\n' +
        '              </thead>' +
        '<tbody>'

    var text = {
        "1": "单面",
        "2": "双面",
        "mono": "黑白",
        "colorful": "彩色"

    }
    for (var file in files) {
        var f = files[file]
        var data = {
            "md5": f["fileID"],
            "size": f['size'],
            "row": f['row'],
            "col": f['col'],
            "start": f['startPage'],
            "end": f['endPage']
        }
        cache[data.md5] = data
        content += ('    <tr>\n' +
            '        <td>' + f['fileName'] + '</td>\n' +
            '        <td>' + (f['endPage'] - f['startPage'] + 1) + '</td>\n' +
            '        <td>' + f['copies'] + '</td>\n' +
            '        <td>' + f['size'] + '</td>\n' +

            '        <td>' + text[f['color']] + '</td>\n' +
            '        <td>' + (f['row'] * f['col']) + '</td>\n' +
            '        <td>' + f['caliper'] + '</td>\n' +
            '        <td>' + text[f['side']] + '</td>\n' +
            '        <td>' + '\t<div class="loading-bar" style="width: 110">\n' +
            '<div class="amount blue" style="width: 20%;">\n' +
            '<div class="loaded">\n' +
            '0' +

            '</div>\n' +
            '<div class="lines"></div>\n' +
            '\</div>\n' +
            '</div>' + '</td>\n' +

            '        <td>' + '<button type="button" class="btn" md5="' + data.md5 + '" onclick="print(this)">打印</button>' + '</td>\n' +

            '</tr>')


    }
    content += '</tbody>'
    return content

}

function makePage(data) {

    var content = []
    for (var index in data) {
        var url = data[index]
        content.push('<img src="' + url + '" height="100%" width="100%">')
    }
    return content.join("")

}

function convertImgToBase64(url, callback, index) {
    if (cache[url[index]] == null) {
        var canvas = document.createElement('CANVAS'),
            ctx = canvas.getContext('2d'),
            img = new Image;
        img.crossOrigin = 'Anonymous';

        img.onload = function () {
            canvas.height = img.height;
            canvas.width = img.width;
            ctx.drawImage(img, 0, 0);
            var dataURL = canvas.toDataURL('image/png');
            cache[url[index]] = dataURL
            callback.call(this, dataURL, index + 1);
            canvas = null;
        };
        img.src = url[index];
    } else {
        callback.call(this, cache[url[index]], index + 1);
    }

}

var imageCache = {}

function print(dom) {
    var data = $(dom).attr("md5");
    data = cache[data]

    $.ajax({
        url: "/console/order/files",
        data: data,
        success: function (array) {
            var base64Array = []
            var len = array.length
            var callbackfunction = function (dataUrl, i) {
                console.log("图片加载回调" + i)
                base64Array.push(dataUrl)
                var step = parseFloat("1") / len * 0.8
                var loader = $(dom).parent().parent().find('.amount')
                loader.find('.loaded').text(Math.ceil((step / 0.8) * base64Array.length * 100))
                $(loader).css('width', ((100 * step * base64Array.length + 20) + "%"))
                // $(loader).text((step/0.8)*base64Array.length*100)
                if (base64Array.length === len) {

                    var content = makePage(base64Array)
                    // $('#images').empty()
                    //
                    // $('#images').append(content)
                    $(content).jqprint()
                } else {


                    convertImgToBase64(array, callbackfunction, i)
                }

            }
            convertImgToBase64(array, callbackfunction, 0)


        }


    })
    console.log(data)
}

function initDataGrid() {
    $table.bootstrapTable({
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                orderNum : $("#orderNum").val()
            };
            return param;
        },
        search: false,//是否显示右上角的搜索框

        height: tableModel.getHeight(),
        idField: "id",

        showRefresh: false,
        showToggle: false,
        columns: [[
            {
                title: "订单日期",
                field: "orderDate",
                formatter: getLocalTime,
                sorttype: "date",
                searchable: true,
                name: "orderDate",
                index: "orderDate",
                searchoptions: {dataInit: datePick, attr: {title: '选择日期'}}
            },
            {
                title: "订单电话", field: "user.tel", search: true,
                searchoptions: {sopt: ['eq']}
            },
            {
                title: "价格(元)", field: "money", formatter: function (value, row, index) {

                    return (parseFloat(value) / 100).toFixed(2)
                }
            },
            {
                name: 'payState',
                title: '订单状态',
                index: 'payState',
                width: 90,
                editable: false,
                search: true,
                field: "payState",
                searchoptions: {
                    sopt: ["eq"],
                    value: "'':全部;PAYING:正在支付;PAID:已经支付;PRINTED:已经打印;FINISH:已完成"
                },
                formatter: stateFormatter
            },
            {title: "详情", field: "operate3", align: 'center', events: operateEvents, formatter: detailFormatter},

            {title: "打印", field: "operate1", align: 'center', events: operateEvents, formatter: printFormatter},

            {title: "操作", field: "operate2", align: 'center', events: operateEvents, formatter: operateFormatter}
        ]],
        url: '/console/order/search',
        queryParams: function (params) {
            console.log(params)
            return params;
        },
        // responseHandler: function (res) {
        //     return {
        //         rows: res.result.pageInfo.list,
        //         total: res.result.pageInfo.total
        //     }
        // },
        sortName: 'id',
        sortOrder: 'desc',
        pagination: true,
        sidePagination: 'server',
        pageSize: 5,
        pageList: [5, 40, 50, 100],
        toolbar: "#toolbar",

    });
}


function detailFormatter(value, row, index) {
    console.log(row)
    return [
        '<a class="detail" href="javascript:void(0);" >',
        '<i class="glyphicon glyphicon-zoom-out"></i>详情',
        '</a>'
    ].join('');
}

function showDetail(value) {

    var name = value['dispatching'] == null ? value['user']['nickName'] : value["dispatching"]['nickname']
    var phone = value['dispatching'] == null ? value['user']['tel'] : value["dispatching"]['phone']
    var content = '<table class="table table-striped">\n' +
        '    <tr>\n' +
        '        <td>用户名字</td>\n' +
        '        <td>' + name + '</td>\n' +
        '    </tr>\n' +
        '    <tr>\n' +
        '        <td>订单日期</td>\n' +
        '        <td>' + getLocalTime(value['orderDate']) + '</td>\n' +
        '    </tr>\n' +
        '    <tr>\n' +
        '        <td>配送号码</td>\n' +
        '        <td>' + phone + '</td>\n' +
        '    </tr>\n' +
        '    <tr>\n' +
        '        <td>价格</td>\n' +
        '        <td>' + (parseFloat(value['money']) / 100).toFixed(2) + '</td>\n' +
        '    </tr>\n'

    if (value['dispatching'] == null) {
        content += ('    <tr>\n' +
            '        <td >配送方式</td>' +
            '        <td>上门自取</td>' +
            '    </tr>'
        )

    } else {
        content += ('    <tr>\n' +
            '        <td >配送方式</td>' +
            '        <td>送货上门</td>' +
            '    </tr>'
        )
        content += ('    <tr>\n' +
            '        <td>配送地址</td>\n' +
            '        <td>' + value["dispatching"]['address'] + '</td>\n' +
            '    </tr>\n' +
            '    <tr>\n' +
            '        <td>配送留言</td>\n' +
            '        <td>' + value["dispatching"]['message'] + '</td>\n' +
            '    </tr>\n')
    }
    content += '</table>'

    return content

}

function stateFormatter(value, row, index) {
    var map = {
        "PAYING": "正在支付",
        "PAID": "已经支付",
        "PRINTED": "已经打印",
        "FINISH": "已结束"
    }
    return map[value]
}

function operateFormatter(value, row, index) {
    return [
        '<a class="printed" href="javascript:void(0);">',
        '<i class="glyphicon glyphicon-check"></i>已打印',
        '</a>  ',
        '<a href="/console/role/from?roleId=' + row.roleId + '" >',
        '<i class="glyphicon glyphicon-remove"></i>取消订单',
        '</a>  ',
        '<a class="remove" href="javascript:void(0);">',
        '<i class="glyphicon glyphicon-ok"></i>已经完成',
        '</a>'
    ].join('');
}

function printFormatter(value, row, index) {
    return [
        '<a class="print" href="javascript:void(0);" >',
        '<i class="glyphicon glyphicon-print"></i>打印',
        '</a>'
    ].join('');
}

window.operateEvents = {
    'click .remove': function (e, value, row, index) {
        operaModel.delRow(row.roleId, '/console/role/delete', 'roleId');
    },
    'click .print': function (e, value, row, index) {
        layer.open({
            type: 1,
            title: '打印',
            shadeClose: true,
            shade: false,
            maxmin: true, //开启最大化最小化按钮
            area: ['1200px', '600px'],
            content: listFiles(row)
        });
    },
    'click .detail': function (e, value, row, index) {
        layer.open({
            type: 1,

            title: '订单详情',
            shadeClose: true,
            shade: false,
            maxmin: true, //开启最大化最小化按钮
            area: ['500px', '500px'],
            content: showDetail(row)
        });
    }
}

// -----------------------------------------------------------------------
// Eros Fratini - eros@recoding.it
// jqprint 0.3
//
// - 19/06/2009 - some new implementations, added Opera support
// - 11/05/2009 - first sketch
//
// Printing plug-in for jQuery, evolution of jPrintArea: http://plugins.jquery.com/project/jPrintArea
// requires jQuery 1.3.x
//
// Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
//------------------------------------------------------------------------

