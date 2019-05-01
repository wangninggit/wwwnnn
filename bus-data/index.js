const moment = require('moment');
const fs = require('fs');
const _ = require('lodash');
const pinyin = require('pinyin');


let startId = 1;
let invoceStartCode = 3200171320;
let invoiceStartNumber = 43641694;
const startDayStr = '2018-01-01';


// http://www.resgain.net/xmdq.html
// 患者名字
const names = ["赵婧文", "赵中锴", "赵山川", "赵吾光", "赵璇海",
    "钱卫国", "钱虹君", "钱品阎", "钱品妍", "孙旭诚", "孙菀茹", "孙荣廷", "孙芳", "李丽颖", "李启菁",
    "李婷", "李娉", "李冠英", "吴诗琪", "吴美兴", "吴春芗", "吴易奚", "郑哲隽", "郑春晖", "郑文英"
];

const cancerArr = ['肺癌', '鼻咽癌', '喉癌', '胃癌', '肝癌', '食道癌', '胰腺癌', '胆囊癌', '白血病', '淋巴癌', '骨癌', '宫颈癌'];

// http://www.chinadmd.com/file/sa6oewrprevevix6ipt6ep6w_5.html
// 地区字符串
const addressStrArr = ["山东省肥城市", "北京市朝阳区", "北京市西城区", "北京市石景山区", "北京市顺义区", "北京市昌平区",
    "北京市平谷区", "天津市和平区", "河北省石家庄市桥东区", "河北省井陉县", "河北省深泽县", "河北省迁西县", "河北省邯郸市",
    "山西省大同县", "山西省左云县", "山西省长治市"
];

const doctorArr = [
    {
        "hospitalName": "北医三院",
        "hospitalAddress": "北京市朝阳区",
        "doctorId": "d001",
        "doctorName": "金启昭",
    },
    {
        "hospitalName": "北大人民医",
        "hospitalAddress": "北京市西城区",
        "doctorId": "d002",
        "doctorName": "曹杰卿",
    },
    {
        "hospitalName": "北京阜外医院",
        "hospitalAddress": "北京市顺义区",
        "doctorId": "d003",
        "doctorName": "姜博",
    },
    {
        "hospitalName": "山东省立医院",
        "hospitalAddress": "山东济南",
        "doctorId": "d004",
        "doctorName": "杨勤",
    },
    {
        "hospitalName": "聊城市人民医院",
        "hospitalAddress": "山东聊城",
        "doctorId": "d005",
        "doctorName": "姜骏烁",
    }
];

// 生成某个范围的随机数 >m <n
const randomNum = (m, n) => {
    const c = n - m + 1;
    return Math.floor(Math.random() * c + m);
};

// 身份证号
const generatePatientIDCard = () => {
    const pre = 370983000000000000;
    return pre + randomNum(100000000000, 999999999999);
};

const randomGender = () => {
    const i = randomNum(0, 1);
    if (i > 0) {
        return "male";
    }
    return "female";
};
const dayOrderIndexMap = {};

// 订单号生成按日期
const orderNumberInit = (dayStr) => {
    // GTON-20180612-0001
    if (!dayOrderIndexMap[dayStr]) {
        dayOrderIndexMap[dayStr] = 1;
    }
    const orderId = 'GTON-' + dayStr + '-' + dayOrderIndexMap[dayStr];
    dayOrderIndexMap[dayStr] = dayOrderIndexMap[dayStr] + 1;
    return orderId;
};

// 检测报告编号生成按订单号
const testingReportNumberInit = (orderId) => {
    return orderId + '-' + randomNum(1, 999);
};

// 检测套餐数组
const testingArr = [
    {
        "testingItemId": 1,
        "testingAgency": "杰傲湃思",
        "testingItem": "HER2（ERBB2）扩增",
        "testingItemPrice": 1500000,
        "testingItemCost": 1000000,
        "testingAgencyId": 1001,
        "testingAgencyAddress": "北京市海淀区",
        "testingReportNumber": "TODO",
        "samplingTime": 1525252456000,
        "sampleConfirmTime": 1525252456000,
        "testingReportUploadTime": 1525252456000,
        "testingResult": "正常",
        "testingReportUrl": "http://todaysoft.com.cn"
    },
    {
        "testingItemId": 2,
        "testingAgency": "杰傲湃思",
        "testingItem": "PIK3CA基因突变",
        "testingItemPrice": 1200000,
        "testingItemCost": 1000000,
        "testingAgencyId": 1001,
        "testingAgencyAddress": "北京市海淀区",
        "testingReportNumber": "1002",
        "samplingTime": 1525252456000,
        "sampleConfirmTime": 1525252456000,
        "testingReportUploadTime": 1525252456000,
        "testingResult": "正常",
        "testingReportUrl": "http://todaysoft.com.cn"
    },
    {
        "testingItemId": 3,
        "testingAgency": "北京迈基诺",
        "testingItem": "EGFR基因突变",
        "testingItemPrice": 800000,
        "testingItemCost": 700000,
        "testingAgencyId": 1002,
        "testingAgencyAddress": "北京市海淀区",
        "testingReportNumber": "TODO",
        "samplingTime": 1525252456000,
        "sampleConfirmTime": 1525252456000,
        "testingReportUploadTime": 1525252456000,
        "testingResult": "正常",
        "testingReportUrl": "http://todaysoft.com.cn"
    },
    {
        "testingItemId": 4,
        "testingAgency": "北京迈基诺",
        "testingItem": "EGFR基因突变（血浆）",
        "testingItemPrice": 500000,
        "testingItemCost": 400000,
        "testingAgencyId": 1002,
        "testingAgencyAddress": "北京市海淀区",
        "testingReportNumber": "TODO",
        "samplingTime": 1525252456000,
        "sampleConfirmTime": 1525252456000,
        "testingReportUploadTime": 1525252456000,
        "testingResult": "正常",
        "testingReportUrl": "http://todaysoft.com.cn"
    },
    {
        "testingItemId": 5,
        "testingAgency": "所罗门",
        "testingItem": "KRAS基因突变",
        "testingItemPrice": 2000000,
        "testingItemCost": 1800000,
        "testingAgencyId": 1003,
        "testingAgencyAddress": "北京市海淀区",
        "testingReportNumber": "TODO",
        "samplingTime": 1525252456000,
        "sampleConfirmTime": 1525252456000,
        "testingReportUploadTime": 1525252456000,
        "testingResult": "正常",
        "testingReportUrl": "http://todaysoft.com.cn"
    },
    {
        "testingItemId": 6,
        "testingAgency": "所罗门",
        "testingItem": "KRAS基因突变（血浆）",
        "testingItemPrice": 2100000,
        "testingItemCost": 1900000,
        "testingAgencyId": 1003,
        "testingAgencyAddress": "北京市海淀区",
        "testingReportNumber": "TODO",
        "samplingTime": 1525252456000,
        "sampleConfirmTime": 1525252456000,
        "testingReportUploadTime": 1525252456000,
        "testingResult": "正常",
        "testingReportUrl": "http://todaysoft.com.cn"
    }
];


const randomTestingArr = (orderId) => {
    const rNum = randomNum(1, 3);
    let indexArr = [];
    for (let i = 0; i <= rNum; i += 1) {
        indexArr.push(randomNum(0, testingArr.length - 1));
    }
    indexArr = _.uniq(indexArr);
    const tArr = indexArr.map((index) => {
        const tObj = testingArr[index];
        tObj.testingReportNumber = testingReportNumberInit(orderId);
        return tObj;
    });
    return tArr;
};

// 地服信息数组，也是创建人
const localStaffArr = [
    {
        "localStaffId": "l001",
        "localStaffJobNumber":"l001",
        "localStaffName": "秦艺菲",
        "localStaffLocation": {
            "regionId": "3",
            "regionName": "华中",
            "provinceId": "3",
            "provinceName": "河南",
            "cityId": "3",
            "cityName": "洛阳",
            "countyId": "3",
            "countyName": "老城区"
        }
    },
    {
        "localStaffId": "l002",
        "localStaffJobNumber":"l002",
        "localStaffName": "秦健容",
        "localStaffLocation": {
            "regionId": "1",
            "regionName": "华东",
            "provinceId": "1",
            "provinceName": "山东",
            "cityId": "1",
            "cityName": "泰安",
            "countyId": "1",
            "countyName": "肥城"
        }
    },
    {
        "localStaffId": "l003",
        "localStaffJobNumber":"l003",
        "localStaffName": "陈品如",
        "localStaffLocation": {
            "regionId": "2",
            "regionName": "华南",
            "provinceId": "2",
            "provinceName": "广东",
            "cityId": "2",
            "cityName": "惠州",
            "countyId": "2",
            "countyName": "龙门"
        }
    },
    {
        "localStaffId": "l004",
        "localStaffJobNumber":"l004",
        "localStaffName": "陈禹西",
        "localStaffLocation": {
            "regionId": "3",
            "regionName": "华中",
            "provinceId": "3",
            "provinceName": "河南",
            "cityId": "3",
            "cityName": "洛阳",
            "countyId": "3",
            "countyName": "老城区"
        }
    },
    {
        "localStaffId": "l005",
        "localStaffJobNumber":"l005",
        "localStaffName": "孔祥栋",
        "localStaffLocation": {
            "regionId": "4",
            "regionName": "华北",
            "provinceId": "4",
            "provinceName": "北京",
            "cityId": "4",
            "cityName": "昌平",
            "countyId": "4",
            "countyName": "回龙观"
        }
    },
    {
        "localStaffId": "l006",
        "localStaffJobNumber":"l006",
        "localStaffName": "华圣杰",
        "localStaffLocation": {
            "regionId": "5",
            "regionName": "华西",
            "provinceId": "5",
            "provinceName": "甘肃",
            "cityId": "5",
            "cityName": "兰州",
            "countyId": "5",
            "countyName": "城关区"
        }
    }
];

const expressArr = [
    {
        "expressNumber": "821148818417",
        "expressCompanyName": "顺丰",
        "expressCompanyId": "sf",
    },
    {
        "expressNumber": "1116076709214",
        "expressCompanyName": "EMS",
        "expressCompanyId": "ems",
    },
    {
        "expressNumber": "7042503016",
        "expressCompanyName": "德邦快递",
        "expressCompanyId": "debangwuliu",
    }
];

const chineseToPinyin = (word) => {
    const arr = pinyin(word, {style: pinyin.STYLE_NORMAL});
    return arr.reduce((a, b) => a + b[0], '');
};

const eventMap = {
    "id": 1,
    "tag": "order",
    "event": "orderCreated",
    "eventTime": 1528792076000,
    "data": {
        "orderId": "GTON-20180612-0001",
        "orderNumber": "GTON-20180612-0001",
        "orderCreatorId": "u001",
        "orderCreatorName": "张伟",
        "orderPrice": 2700000,
        "patientName": "王伟",
        "patientAddress": "山东省肥城市",
        "patientGender": "male",
        "patientIDCard": "370983176606150432",
        "patientAge": 60,
        "patientClinicalDiagnosis": "胰腺癌",
        "testings": [
            {
                "testingItemId": 4001,
                "testingAgency": "杰傲湃思",
                "testingItem": "HER2（ERBB2）扩增",
                "testingItemPrice": 1500000,
                "testingItemCost": 1000000,
                "testingAgencyId": 1001,
                "testingAgencyAddress": "北京市海淀区",
                "samplingTime": 1528792086000
            },
            {
                "testingItemId": 4002,
                "testingAgency": "杰傲湃思",
                "testingItem": "PIK3CA基因突变",
                "testingItemPrice": 1200000,
                "testingItemCost": 1000000,
                "testingAgencyId": 1001,
                "testingAgencyAddress": "北京市海淀区",
                "samplingTime": 1528792086000
            }
        ],
        "localStaffId": "l001",
        "localStaffName": "王芳",
        "localStaffJobNumber":"l001",
        "localStaffLocation": {
            "regionId": "1",
            "regionName": "华北",
            "provinceId": "1",
            "provinceName": "山东",
            "cityId": "2",
            "cityName": "泰安",
            "countyId": "3",
            "countyName": "肥城"
        },
        "hospitalName": "北医三院",
        "hospitalAddress": "北京市",
        "doctorId": "d001",
        "doctorName": "李伟",
        "integral": 1000
    }
};

const invoiceEventMap = {
    "id": 7,
    "tag": "order",
    "event": "orderInvoiced",
    "eventTime": 1528798313000,
    "data": {
        "orderIds": [
            "GTON-20180612-0001"
        ],
        "invoiceDate": "2018年6月12日",
        "invoiceGoodsCodeVersion": "1",
        "invoiceTaxClassificationCode": "1.1",
        "invoiceCode": "3200171320",
        "invoiceNumber": "43641694",
        "invoiceBuyerName": "王伟",
        "invoiceBuyerTaxPayerNumber": null,
        "invoiceBuyerAddress": null,
        "invoiceBuyerBank": null,
        "invoiceItemName": "*信息技术服务*基因检测服务费",
        "invoiceItemCount": 1,
        "invoiceItemUnitPrice": 1350000,
        "invoicePostTaxAmount": 1269000,
        "invoiceTaxRate": 6,
        "invoiceTaxAmount": 81000,
        "invoiceAmount": 1350000,
        "invoiceSellerName": "江苏华生基因数据科技股份有限公司",
        "invoiceSellerTaxpayerNumber": "913209005592111",
        "invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
        "invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111"
    }
};

const logisticsEventMap = {
    "id": 8,
    "tag": "order",
    "event": "orderLogistics",
    "eventTime": 1525252456007,
    "data": {
        "orderId": "1001",
        "logisticsType": "invoice",
        "detail": {
            "expressNumber": "276371773511",
            "expressCompanyName": "顺丰",
            "expressCompanyId": "sf",
        }
    }
};

const randomPaymentType = () => {
    const r = randomNum(0, 2);
    switch (r) {
        case 0:
            return 'alipay';
        case 1:
            return 'wechatpay';
        case 2:
            return 'bankTransfer';
    }
};

const randomLocalStaffPayroll = () => {
    const r = randomNum(0, 2);
    if (r === 0) {
        return true;
    }
    return false;
};

const randomCreateStr = (dayStr) => {
    // 从早10点开始
    const r = randomNum(8, 15);
    let resStr;
    if (r < 10) {
        resStr = dayStr + ' ' + '0' + r + ':00:00';
    } else {
        resStr = dayStr + ' ' + r + ':00:00';
    }
    const m = moment(resStr, 'YYYY-MM-DD HH:mm:ss');
    return {
        dateStr: resStr,
        timeStamp: m.format('x'),
        moment: m
    };
};

const getNextTimeStr = (t) => {
    const m = t.add(1, 'h');
    // + 1小时
    return {
        dateStr: m.format('YYYY-MM-DD HH:mm:ss'),
        timeStamp: m.format('x'),
        moment: m
    };
};

/**
 *
 * @param m 当前的moment对象
 * @param n 一天生成多少条数据
 */
const dayHandle = (m, n) => {
    let eventArr = [
        'orderCreated',
        'orderPayed',
        'orderRefunded',
        'orderApproved',
        'sampleConfirmed',
        'testingReportUploaded',
        'integralGranted',
        'orderInvoiced',
        'orderLogistics'
    ];
    let allArr = [];
    const currentDayStr = m.format('YYYY-MM-DD');
    const orderPreStr = m.format('YYYYMMDD');
    for (let i = 0; i < n; i += 1) {
        const eventRandomIndex = randomNum(0, 6);
        let randomEventArr = [];
        const eventArrBak = JSON.parse(JSON.stringify(eventArr));
        switch (true) {
            case eventRandomIndex <= 2:
                randomEventArr = eventArrBak.splice(0, 3);
                break;
            case eventRandomIndex === 3:
                randomEventArr = eventArrBak.splice(0, 6);
                break;
            case eventRandomIndex > 3 && eventRandomIndex <= 8:
                randomEventArr = eventArrBak.splice(0);
                break;
        }
        // 初始化共享数据
        let eventObj = Object.assign({}, eventMap);
        const orderId = orderNumberInit(orderPreStr);
        eventObj.data.orderId = orderId;
        eventObj.data.orderNumber = orderId;
        // 地服
        const randomLocalStaffIndex = randomNum(0, localStaffArr.length - 1);
        const localStaffObj = localStaffArr[randomLocalStaffIndex];
        eventObj.data.orderCreatorId = localStaffObj.localStaffId;
        eventObj.data.orderCreatorName = localStaffObj.localStaffName;
        eventObj.data.localStaffId = localStaffObj.localStaffId;
        eventObj.data.localStaffJobNumber = localStaffObj.localStaffJobNumber;
        eventObj.data.localStaffName = localStaffObj.localStaffName;
        eventObj.data.localStaffLocation = localStaffObj.localStaffLocation;
        const randomPatientNameIndex = randomNum(0, names.length - 1);
        // 患者
        eventObj.data.patientName = names[randomPatientNameIndex];
        eventObj.data.patientAge = randomNum(50, 90);
        const cancerRandomIndex = randomNum(0, cancerArr.length - 1);
        eventObj.data.patientClinicalDiagnosis = cancerArr[cancerRandomIndex];
        eventObj.data.patientIDCard = generatePatientIDCard();
        const addressRandomIndex = randomNum(0, addressStrArr.length - 1);
        eventObj.data.patientAddress = addressStrArr[addressRandomIndex];
        eventObj.data.patientGender = randomGender();
        const tArr = randomTestingArr(orderId);
        eventObj.data.testings = tArr;
        eventObj.data.orderPrice = tArr.reduce((c, n) => c + n.testingItemPrice, 0);
        const doctorObj = doctorArr[randomNum(0, doctorArr.length - 1)];
        eventObj.data = Object.assign(eventObj.data, doctorObj);
        eventObj.data.integral = randomNum(500, 1000);
        let preEventTimeStr;
        let preEventTimeMoment;
        let preEventTimeStamp;
        let timeObj;
        // 处理各个事件
        randomEventArr.forEach((event) => {
            eventObj = JSON.parse(JSON.stringify(eventObj));
            eventObj.id = startId;
            eventObj.event = event;
            switch (event) {
                case 'orderCreated':
                    eventObj = JSON.parse(JSON.stringify(eventObj));
                    timeObj = randomCreateStr(currentDayStr);
                    eventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    allArr.push(eventObj);
                    break;
                case 'orderPayed':
                    eventObj = JSON.parse(JSON.stringify(eventObj));
                    timeObj = getNextTimeStr(preEventTimeMoment);
                    eventObj.data.orderCreateTime = preEventTimeStamp;
                    eventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    eventObj.data.tradeNumber = randomNum(1, 1000000000).toString();
                    eventObj.data.paymentType = randomPaymentType();
                    eventObj.data.localStaffPayroll = randomLocalStaffPayroll();
                    eventObj.data.orderPayerName = localStaffObj.localStaffName;
                    eventObj.data.orderPayerAccount = chineseToPinyin(eventObj.data.orderPayerName);
                    eventObj.data.orderPayAttorneyUrl = "http://todaysoft.com.cn";
                    eventObj.data.payTotal = eventObj.data.orderPrice;
                    allArr.push(eventObj);
                    break;
                case 'orderRefunded':
                    eventObj = JSON.parse(JSON.stringify(eventObj));
                    delete eventObj.data.paymentType;
                    delete eventObj.data.localStaffPayroll;
                    delete eventObj.data.orderPayerName;
                    delete eventObj.data.orderPayerAccount;
                    delete eventObj.data.orderPayAttorneyUrl;
                    delete eventObj.data.payTotal;
                    if (eventObj.data.testings.length === 1) {
                        break;
                    }
                    timeObj = getNextTimeStr(preEventTimeMoment);
                    eventObj.data.orderCreateTime = preEventTimeStamp;
                    eventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    eventObj.data.refundReceiverName = localStaffObj.localStaffName;
                    eventObj.data.refundReceiverAccount = chineseToPinyin(localStaffObj.localStaffName);
                    eventObj.data.refundType = 'bankTransfer';
                    eventObj.data.testings = eventObj.data.testings.slice(0, eventObj.data.testings.length - 1);
                    eventObj.data.orderPrice = eventObj.data.testings.reduce((c, n) => c + n.testingItemPrice, 0);
                    eventObj.data.refundTotal = eventObj.data.orderPrice;
                    allArr.push(eventObj);
                    break;
                case 'sampleConfirmed':
                    eventObj = JSON.parse(JSON.stringify(eventObj));
                    delete eventObj.data.paymentType;
                    delete eventObj.data.localStaffPayroll;
                    delete eventObj.data.orderPayerName;
                    delete eventObj.data.orderPayerAccount;
                    delete eventObj.data.orderPayAttorneyUrl;
                    delete eventObj.data.payTotal;
                    delete eventObj.data.refundReceiverName;
                    delete eventObj.data.refundReceiverAccount;
                    delete eventObj.data.refundType;
                    delete eventObj.data.refundTotal;
                    timeObj = getNextTimeStr(preEventTimeMoment);
                    eventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    eventObj.data.testingItemId = tArr[randomNum(0, tArr.length - 1)].testingItemId;
                    eventObj.data.testings = eventObj.data.testings.map((t) => {
                        t.samplingTime = timeObj.timeStamp;
                        t.sampleConfirmTime = timeObj.timeStamp;
                        return t;
                    });
                    allArr.push(eventObj);
                    break;
                case 'testingReportUploaded':
                    eventObj = JSON.parse(JSON.stringify(eventObj));
                    delete eventObj.data.paymentType;
                    delete eventObj.data.localStaffPayroll;
                    delete eventObj.data.orderPayerName;
                    delete eventObj.data.orderPayerAccount;
                    delete eventObj.data.orderPayAttorneyUrl;
                    delete eventObj.data.payTotal;
                    delete eventObj.data.refundReceiverName;
                    delete eventObj.data.refundReceiverAccount;
                    delete eventObj.data.refundType;
                    delete eventObj.data.refundTotal;
                    timeObj = getNextTimeStr(preEventTimeMoment);
                    eventObj.data.orderApproveTime = preEventTimeStamp;
                    eventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    eventObj.data.testingItemId = tArr[randomNum(0, tArr.length - 1)].testingItemId;
                    eventObj.data.testings = eventObj.data.testings.map((t) => {
                        t.testingReportUploadTime = timeObj.timeStamp;
                        t.testingResult = '正常';
                        return t;
                    });
                    allArr.push(eventObj);
                    break;
                case 'integralGranted':
                    eventObj = JSON.parse(JSON.stringify(eventObj));
                    delete eventObj.data.paymentType;
                    delete eventObj.data.localStaffPayroll;
                    delete eventObj.data.orderPayerName;
                    delete eventObj.data.orderPayerAccount;
                    delete eventObj.data.orderPayAttorneyUrl;
                    delete eventObj.data.payTotal;
                    delete eventObj.data.refundReceiverName;
                    delete eventObj.data.refundReceiverAccount;
                    delete eventObj.data.refundType;
                    delete eventObj.data.refundTotal;
                    timeObj = getNextTimeStr(preEventTimeMoment);
                    eventObj.data.orderApproveTime = preEventTimeStamp;
                    eventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    eventObj.data.dcwId = 'dcw' + randomNum(100000, 999999);
                    eventObj.data.dcwIntegral = eventObj.data.integral;
                    eventObj.data.dcwIntegralRaw = eventObj.data.integral;
                    allArr.push(eventObj);
                    break;
                case 'orderInvoiced':
                    timeObj = getNextTimeStr(preEventTimeMoment);
                    let invoiceEventObj = Object.assign({}, invoiceEventMap);
                    invoiceEventObj = JSON.parse(JSON.stringify(invoiceEventObj));
                    invoiceEventObj.id = startId;
                    invoiceEventObj.eventTime = timeObj.timeStamp;
                    preEventTimeStr = timeObj.dateStr;
                    preEventTimeMoment = timeObj.moment;
                    preEventTimeStamp = timeObj.timeStamp;
                    invoiceEventObj.data.orderIds = [orderId];
                    invoiceEventObj.data.invoiceDate = currentDayStr;
                    invoiceEventObj.data.invoiceCode = invoceStartCode.toString();
                    invoiceEventObj.data.invoiceStartNumber = invoiceStartNumber.toString();
                    invoiceEventObj.data.invoiceBuyerName = eventObj.data.patientName;
                    invoiceEventObj.data.invoiceItemUnitPrice = eventObj.data.orderPrice;
                    invoiceEventObj.data.invoiceTaxAmount = parseInt(invoiceEventObj.data.invoiceItemUnitPrice * 0.06, 10);
                    invoiceEventObj.data.invoicePostTaxAmount = parseInt(invoiceEventObj.data.invoiceItemUnitPrice - invoiceEventObj.data.invoiceTaxAmount);
                    invoiceEventObj.data.invoiceAmount = invoiceEventObj.data.invoiceItemUnitPrice;
                    allArr.push(invoiceEventObj);
                    break;
                case 'orderLogistics':
                    // 添加一个发票物流
                    let r = randomNum(0, 2);
                    const invoiceObj = JSON.parse(JSON.stringify(logisticsEventMap));
                    invoiceObj.id = startId;
                    invoiceObj.data.orderId = orderId;
                    invoiceObj.data.logisticsType = 'invoice';
                    invoiceObj.data.detail = Object.assign({}, invoiceObj.detail, expressArr[r]);
                    allArr.push(invoiceObj);
                    startId = startId + 1;
                    // 遍历添加检测报告和检测样本物流信息
                    eventObj.data.testings = eventObj.data.testings.forEach((t, index) => {
                        r = randomNum(0, 2);
                        const testingObj1 = JSON.parse(JSON.stringify(logisticsEventMap));
                        testingObj1.id = startId;
                        testingObj1.data.orderId = orderId;
                        testingObj1.data.logisticsType = 'testingReport';
                        testingObj1.data.detail = Object.assign({}, testingObj1.detail, expressArr[r], {testingItemId: t.testingItemId});
                        allArr.push(testingObj1);
                        startId = startId + 1;
                        const testingObj2 = JSON.parse(JSON.stringify(logisticsEventMap));
                        testingObj2.id = startId;
                        testingObj2.data.orderId = orderId;
                        testingObj2.data.logisticsType = 'sample';
                        testingObj2.data.detail = Object.assign({}, testingObj2.detail, expressArr[r], {testingItemId: t.testingItemId});
                        allArr.push(testingObj2);
                        if (index !== eventObj.data.testings.length - 1) {
                            startId = startId + 1;
                        }
                    });
                    break;
            }
            startId += 1;
        });
        invoceStartCode += 1;
        invoiceStartNumber += 1;
    }
    return allArr;
};

const main = () => {
    // 时间自增
    let execDay = moment(startDayStr, 'YYYY-MM-DD');
    let resultArr = [];
    let sum = 0;
    //let stopDay = moment().format('YYYY-MM-DD');  // 当天
    let stopDay = '2018-09-04'; // 指定时间
    // 自增加一天
    while (true) {
        if (execDay.format('YYYY-MM-DD') > stopDay) {
            // fs.writeFileSync('./result.json', JSON.stringify(resultArr));
            const resObj = {
                "status": 200,
                "message": "成功",
                "data": resultArr
            };
            fs.writeFileSync('./result.json', JSON.stringify(resObj));
            console.log(sum);
            break;
        }
        sum += 1;
        console.log(execDay.format('YYYY-MM-DD'));
        const daySum = randomNum(3, 9);
        // 处理当前日期的数据
        resultArr = resultArr.concat(dayHandle(execDay, daySum));
        execDay = execDay.add(1, 'days');
    }
};
main();

