# 华生基因数据分析服务HTTP方案表设计

## 用户管理

### 用户表 user

* id 用户id，Integer类型，自增，主键
* phoneNumber 手机号，String类型，唯一，不能为null
* email 邮箱，String类型，唯一，不能为null
* nickname 昵称，String类型，不能为null
* password 密码，String类型，加密，不能为null
* salt 加密密码用的盐值，String类型，不能为null
* deleted 是否删除，boolean类型，默认false
* activate 是否启用，boolean类型，默认false
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 功能模块表 page

系统的功能模块名，如`用户模块`、`权限模块`。

* id 模块id，Integer类型，自增，主键
* name 模块名，String类型，不能为null
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 角色表 role

* id 角色id，Integer类型，自增，主键
* name 英文角色名称，String类型，唯一，不能为null
* label 中文名标签，String类型，不能为null
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）


### 权限表 permission

* id 权限id，Integer类型，自增，主键
* pageId 关联的功能模块Id，外键，Integer类型，不能为null
* name 英文权限名称，String类型，唯一，不能为null
* label 中文名标签，String类型，不能为null
* type 权限类型，路由，String类型，不能为null
* url 权限路径，如果没有则为null
* parent 父级权限id，Integer类型，如果是第一级别为null
* sort 排序，Integer类型，默认为0
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 用户角色中间表 user_role

* id 记录id，Integer类型，自增，主键
* userId 用户id，外键，Integer类型，不能为null
* roleId 角色id，外键，Integer类型，不能为null
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 角色权限中间表 role_permission

* id 记录id，Integer类型，自增，主键
* roleId 角色id，外键，Integer类型，不能为null
* permissionId 权限id，外键，Integer类型，不能为null
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 字典类别 dictionary_category

* id 类别，Integer类型，自增，主键
* label 中文描述，唯一，String类型，唯一，不能为null
* updateTime 最后一次更新时间
* updateTime 创建时间

### 字典管理 dictionary

* id 字典id，Integer类型，自增，主键
* label 中文描述，唯一，String类型，唯一，不能为null
* value 英文标识，String类型，不能为null
* categoryId 字典类型id，Integer类型，外键
* updateTime 最后一次更新时间
* updateTime 创建时间

## 业务表

### 基因检测业务事件日志表(原始数据) gene_log

* id 记录 id，Integer类型，自增，主键
* tag 此消息所属的tag名，String类型
* event 事件类型，字符串类型（String）
* eventTime 事件发生时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* messageId 阿里云的消息Id，String类型
* recordId 华生数据接口产生的Id，Integer类型
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* quarter 季度，Integer类型，可选1、2、3、4
* half_year 半年，Integer类型，可选1、2
* orderId 订单Id，tag为order时才有，String类型
* record 消息的原数据，longtext类型
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 发票表 invoice

* invoiceCode 发票代码，字符串类型（String），主键
* invoiceNumber 发票号码，字符串类型（String）
* invoiceGoodsCodeVersion 商品编码版本号，字符串类型（String）
* invoiceTaxClassificationCode 税收分类编码，字符串类型（String）
* invoiceTime 开票事件时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* invoiceDate 发票上的开票时间，字符串类型（String）
* invoiceBuyerName 购买方名称，字符串类型（String）
* invoiceBuyerTaxPayerNumber 购买方纳税人识别号，字符串类型（String）
* invoiceBuyerAddress 购买方地址电话，字符串类型（String）
* invoiceBuyerBank 购买方开户行及账号，字符串类型（String）
* invoiceItemName 发票货物或应税劳务、服务名称，字符串类型（String）
* invoiceItemCount 发票货物数量，整数类型(Integer)
* invoiceItemUnitPrice 发票货物单价，长整数类型（Long），单位：厘，实际金额 * 1000
* invoicePostTaxAmount 发票税后金额，长整数类型（Long），单位：厘，实际金额 * 1000
* invoiceTaxRate 发票税率，整数类型(Integer)，税率*100，如 6%等于6
* invoiceTaxAmount 税额，长整数类型（Long），单位：厘，实际金额 * 1000
* invoiceAmount 税价合计，长整数类型（Long），单位：厘，实际金额 * 1000
* invoiceSellerName 销售方名称，字符串类型（String）
* invoiceSellerTaxPayerNumber 销售方纳税人识别号，字符串类型（String）
* invoiceSellerAddress 销售方地址电话，字符串类型（String）
* invoiceSellerBank 销售方开户银行及账号，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 财务基因检测明细表 gene_detail

* id 明细id，Integer类型，自增，主键
* tag 最后一次修改此明细的事件消息所属的tag名，String类型
* messageId 阿里云的消息Id，String类型
* recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* quarter_str 季度，事件触发主要事件的时间精确到天的字符串，如`2018-q1`，1到4不一定是消息发送时间，String类型
* half_year_str 半年，事件触发主要事件的时间精确到天的字符串，如`2018-h1`、`2018-h2`，不一定是消息发送时间，String类型
* event 最后一次修改此明细的事件类型，String类型
* eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderIncome 订单收入，和订单金额一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
* cashIncome 现金收入（到账金额），订单支付后填写，实际为订单金额，长整数类型（Long），单位：厘，实际金额 1000
* financeConfirmIncome 财务确认收入和发票金额一致，理论上也应该和订单金额一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
* testingItemCost 检测成本，所有检测项目或套餐成本的和，长整数类型（Long），单位：厘，实际金额1000
* unboundIntegral 解除绑定的积分，这个实际上是成本中的积分，长整数类型（Long）
* integral 要发放的积分，长整数类型（Long）
* orderId 订单ID，字符串类型（String）
* orderNumber 订单编号，字符串类型（String）
* orderPrice 订单金额（也是到账金额，订单收入），长整数类型（Long），单位：厘，实际金额 1000
* orderCreatorId 订单创建人ID，字符串类型（String）
* orderCreatorName 订单创建人姓名，字符串类型（String）
* orderCreateTime 订单创建时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayerId 订单支付人ID，字符串类型（String）
* orderPayerName 订单支付人名字，字符串类型（String）
* paymentType 对应系统内的支付方式，字符串类型（String）
* orderApproveTime 订单预审通过时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayTime 订单支付时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* localStaffPayroll 是否是地服代付，布尔类型（Boolean），`是`为`true`，`否`为`false`
* patientName 患者名字，字符串类型（String）
* patientIDCard 患者身份证号，字符串类型（String）
* patientAge 患者年龄，整数类型（Integer）
* patientGender 患者性别，字符串类型（String）
* patientAddress 患者地址，字符串类型（String），可选`male`、`female`
* patientClinicalDiagnosis 患者临床诊断，字符串类型（String）
* localStaffId 地服ID，字符串类型（String），关联地服表
* localStaffName 地服名字，字符串类型（String）
* localStaffRegionId 大区ID，字符串类型（String），关联大区表
* localStaffRegionName 大区名，字符串类型（String）
* localStaffProvinceId 省ID，字符串类型（String），关联省表
* localStaffProvinceName 省名，字符串类型（String）
* localStaffCityId 市ID，字符串类型（String），关联市表
* localStaffCityName 市名，字符串类型（String）
* localStaffCountyId 区ID，字符串类型（String），关联县区表
* localStaffCountyName 区名，字符串类型（String）
* doctorId 医生ID，字符串类型（String），关联医生表
* doctorName 医生名字，字符串类型（String）
* hospitalName 医院名字，字符串类型（String）
* hospitalAddress 医院地址，字符串类型（String）
* dcwApproveTime dcw特殊案例上传并审核通过的时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* dcwId 特殊案例ID，字符串类型（String）
* refundTotal 退款金额，长整数类型（Long），单位：厘，实际金额 * 1000
* refundTime 退款时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 基因检测业务日明细表 gene_daily_detail

如果一个订单创建在今天，支付在明天，开票在后天则会有三条记录。

* id 明细id，Integer类型，自增，主键
* tag 最后一次修改此明细的事件消息所属的tag名，String类型
* messageId 阿里云的消息Id，String类型
* recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* quarter 季度，Integer类型，可选1、2、3、4
* half_year 半年，Integer类型，可选1、2
* event 最后一次修改此明细的事件类型，String类型
* eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* changeCount 此条记录一共修改了多少次，只计算同一天或同一月等，跨天从0开始，Integer类型
* orderIncome 订单收入，和订单金额一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
* cashIncome 现金收入（到账金额），订单支付后填写，实际为订单金额，长整数类型（Long），单位：厘，实际金额 1000
* financeConfirmIncome 财务确认收入和发票金额一致，理论上也应该和订单金额一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
* testingItemCost 检测成本，所有检测项目或套餐成本的和，长整数类型（Long），单位：厘，实际金额1000
* integralCost 积分支出，长整数类型（Long），单位：厘，实际金额1000
* unboundIntegral 解除绑定的积分，这个实际上是成本中的积分，长整数类型（Long）
* integral 要发放的积分，长整数类型（Long）
* orderId 订单ID，字符串类型（String）
* orderNumber 订单编号，字符串类型（String）
* orderPrice 订单金额（也是到账金额，订单收入），长整数类型（Long），单位：厘，实际金额 1000
* orderCreatorId 订单创建人ID，字符串类型（String）
* orderCreatorName 订单创建人姓名，字符串类型（String）
* orderCreateTime 订单创建时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayerId 订单支付人ID，字符串类型（String）
* orderPayerName 订单支付人名字，字符串类型（String）
* paymentType 对应系统内的支付方式，字符串类型（String）
* orderApproveTime 订单预审通过时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayTime 订单支付时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* localStaffPayroll 是否是地服代付，布尔类型（Boolean），`是`为`true`，`否`为`false`
* patientName 患者名字，字符串类型（String）
* patientGender 患者性别
* patientIDCard 患者身份证号，字符串类型（String）
* patientAge 患者年龄，整数类型（Integer）
* patientGender 患者性别，字符串类型（String）
* patientAddress 患者地址，字符串类型（String），可选`male`、`female`
* patientClinicalDiagnosis 患者临床诊断，字符串类型（String）
* localStaffId 地服ID，字符串类型（String），关联地服表
* localStaffName 地服名字，字符串类型（String）
* localStaffRegionId 大区ID，字符串类型（String），关联大区表
* localStaffRegionName 大区名，字符串类型（String）
* localStaffProvinceId 省ID，字符串类型（String），关联省表
* localStaffProvinceName 省名，字符串类型（String）
* localStaffCityId 市ID，字符串类型（String），关联市表
* localStaffCityName 市名，字符串类型（String）
* localStaffCountyId 区ID，字符串类型（String），关联县区表
* localStaffCountyName 区名，字符串类型（String）
* doctorId 医生ID，字符串类型（String），关联医生表
* doctorName 医生名字，字符串类型（String）
* hospitalName 医院名字，字符串类型（String）
* hospitalAddress 医院地址，字符串类型（String）
* dcwApproveTime dcw特殊案例上传并审核通过的时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* dcwId 特殊案例ID，字符串类型（String）
* refundTotal 退款金额，长整数类型（Long），单位：厘，实际金额 * 1000
* refundTime 退款时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）


### 基因检测业务月明细表 gene_monthly_detail

如果一个订单创建在5月，支付在6月，开票在7月则会有三条记录。

* id 明细id，Integer类型，自增，主键
* tag 最后一次修改此明细的事件消息所属的tag名，String类型
* messageId 阿里云的消息Id，String类型
* recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* quarter 季度，Integer类型，可选1、2、3、4
* half_year 半年，Integer类型，可选1、2
* event 最后一次修改此明细的事件类型，String类型
* eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderIncome 订单收入，和订单金额一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
* cashIncome 现金收入（到账金额），订单支付后填写，实际为订单金额，长整数类型（Long），单位：厘，实际金额 1000
* financeConfirmIncome 财务确认收入和发票金额一致，理论上也应该和订单金额一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
* testingItemCost 检测成本，所有检测项目或套餐成本的和，长整数类型（Long），单位：厘，实际金额1000
* unboundIntegral 解除绑定的积分，这个实际上是成本中的积分，长整数类型（Long）
* integral 要发放的积分，长整数类型（Long）
* orderId 订单ID，字符串类型（String）
* orderNumber 订单编号，字符串类型（String）
* orderPrice 订单金额（也是到账金额，订单收入），长整数类型（Long），单位：厘，实际金额 1000
* orderCreatorId 订单创建人ID，字符串类型（String）
* orderCreatorName 订单创建人姓名，字符串类型（String）
* orderCreateTime 订单创建时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayerId 订单支付人ID，字符串类型（String）
* orderPayerName 订单支付人名字，字符串类型（String）
* paymentType 对应系统内的支付方式，字符串类型（String）
* orderApproveTime 订单预审通过时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayTime 订单支付时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* localStaffPayroll 是否是地服代付，布尔类型（Boolean），`是`为`true`，`否`为`false`
* patientName 患者名字，字符串类型（String）
* patientGender 患者性别
* patientIDCard 患者身份证号，字符串类型（String）
* patientAge 患者年龄，整数类型（Integer）
* patientGender 患者性别，字符串类型（String）
* patientAddress 患者地址，字符串类型（String），可选`male`、`female`
* patientClinicalDiagnosis 患者临床诊断，字符串类型（String）
* localStaffId 地服ID，字符串类型（String），关联地服表
* localStaffName 地服名字，字符串类型（String）
* localStaffRegionId 大区ID，字符串类型（String），关联大区表
* localStaffRegionName 大区名，字符串类型（String）
* localStaffProvinceId 省ID，字符串类型（String），关联省表
* localStaffProvinceName 省名，字符串类型（String）
* localStaffCityId 市ID，字符串类型（String），关联市表
* localStaffCityName 市名，字符串类型（String）
* localStaffCountyId 区ID，字符串类型（String），关联县区表
* localStaffCountyName 区名，字符串类型（String）
* doctorId 医生ID，字符串类型（String），关联医生表
* doctorName 医生名字，字符串类型（String）
* hospitalName 医院名字，字符串类型（String）
* hospitalAddress 医院地址，字符串类型（String）
* dcwApproveTime dcw特殊案例上传并审核通过的时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* dcwId 特殊案例ID，字符串类型（String）
* refundTotal 退款金额，长整数类型（Long），单位：厘，实际金额 * 1000
* refundTime 退款时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 基因检测业务季明细表 gene_quarterly_detail

参考月明细

### 基因检测业务半年明细表 gene_half_yearly_detail

参考月明细

### 基因检测业务年明细表 gene_yearly_detail

如果一个订单创建在今年，支付在明年有两条记录。

* id 明细id，Integer类型，自增，主键
* tag 最后一次修改此明细的事件消息所属的tag名，String类型
* messageId 阿里云的消息Id，String类型
* recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* quarter 季度，Integer类型，可选1、2、3、4
* half_year 半年，Integer类型，可选1、2
* event 最后一次修改此明细的事件类型，String类型
* eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderIncome 订单收入，和订单金额一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
* cashIncome 现金收入（到账金额），订单支付后填写，实际为订单金额，长整数类型（Long），单位：厘，实际金额 1000
* financeConfirmIncome 财务确认收入和发票金额一致，理论上也应该和订单金额一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
* testingItemCost 检测成本，所有检测项目或套餐成本的和，长整数类型（Long），单位：厘，实际金额1000
* unboundIntegral 解除绑定的积分，这个实际上是成本中的积分，长整数类型（Long）
* integral 要发放的积分，长整数类型（Long）
* orderId 订单ID，字符串类型（String）
* orderNumber 订单编号，字符串类型（String）
* orderPrice 订单金额（也是到账金额，订单收入），长整数类型（Long），单位：厘，实际金额 1000
* orderCreatorId 订单创建人ID，字符串类型（String）
* orderCreatorName 订单创建人姓名，字符串类型（String）
* orderCreateTime 订单创建时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayerId 订单支付人ID，字符串类型（String）
* orderPayerName 订单支付人名字，字符串类型（String）
* paymentType 对应系统内的支付方式，字符串类型（String）
* orderApproveTime 订单预审通过时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayTime 订单支付时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* localStaffPayroll 是否是地服代付，布尔类型（Boolean），`是`为`true`，`否`为`false`
* patientName 患者名字，字符串类型（String）
* patientGender 患者性别
* patientIDCard 患者身份证号，字符串类型（String）
* patientAge 患者年龄，整数类型（Integer）
* patientGender 患者性别，字符串类型（String）
* patientAddress 患者地址，字符串类型（String），可选`male`、`female`
* patientClinicalDiagnosis 患者临床诊断，字符串类型（String）
* localStaffId 地服ID，字符串类型（String），关联地服表
* localStaffName 地服名字，字符串类型（String）
* localStaffRegionId 大区ID，字符串类型（String），关联大区表
* localStaffRegionName 大区名，字符串类型（String）
* localStaffProvinceId 省ID，字符串类型（String），关联省表
* localStaffProvinceName 省名，字符串类型（String）
* localStaffCityId 市ID，字符串类型（String），关联市表
* localStaffCityName 市名，字符串类型（String）
* localStaffCountyId 区ID，字符串类型（String），关联县区表
* localStaffCountyName 区名，字符串类型（String）
* doctorId 医生ID，字符串类型（String），关联医生表
* doctorName 医生名字，字符串类型（String）
* hospitalName 医院名字，字符串类型（String）
* hospitalAddress 医院地址，字符串类型（String）
* dcwApproveTime dcw特殊案例上传并审核通过的时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* dcwId 特殊案例ID，字符串类型（String）
* refundTotal 退款金额，长整数类型（Long），单位：厘，实际金额 * 1000
* refundTime 退款时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

###  订单发票关联表 order_invoice

* id ID，Integer类型，自增，主键
* orderId 订单ID，字符串类型（String）
* invoiceCode 发票代码，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 检测套餐表 testing_item

* id ID，Integer类型，自增，主键
* orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
* testingResult 检测结果，字符串类型（String）
* testingAgency 检测机构名称，字符串类型（String）
* testingItem 检测项目或套餐，字符串类型（String）
* testingItemId 检测项目或套餐ID，整数类型(Integer)
* testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
* testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
* testingAgencyId 检测机构ID，整数类型（Integer）
* testingAgencyAddress 检测机构地址，字符串类型（String）
* testingReportNumber 检测报告编号，字符串类型（String）
* testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 物流支出表 logistics_payout

* id ID，Integer类型，自增，主键
* tag 最后一次修改此明细的事件消息所属的tag名，String类型
* messageId 阿里云的消息Id，String类型
* recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* event 最后一次修改此明细的事件类型，String类型
* eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* total OA中报销金额金额，长整数类型（Long），单位：厘，实际金额 * 1000
* localStaffId 地服ID，字符串类型（String），关联地服表
* localStaffName 地服名字，字符串类型（String）
* localStaffRegionId 大区ID，字符串类型（String），关联大区表
* localStaffRegionName 大区名，字符串类型（String）
* localStaffProvinceId 省ID，字符串类型（String），关联省表
* localStaffProvinceName 省名，字符串类型（String）
* localStaffCityId 市ID，字符串类型（String），关联市表
* localStaffCityName 市名，字符串类型（String）
* localStaffCountyId 区ID，字符串类型（String），关联县区表
* localStaffCountyName 区名，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 地服表 localStaff

* localStaffId 地服ID，字符串类型（String），主键
* localStaffName 地服名字，字符串类型（String）
* localStaffRegionId 大区ID，字符串类型（String），关联大区表
* localStaffRegionName 大区名，字符串类型（String）
* localStaffProvinceId 省ID，字符串类型（String），关联省表
* localStaffProvinceName 省名，字符串类型（String）
* localStaffCityId 市ID，字符串类型（String），关联市表
* localStaffCityName 市名，字符串类型（String）
* localStaffCountyId 区ID，字符串类型（String），关联县区表
* localStaffCountyName 区名，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 医生表 doctor

* doctorId 医生ID，字符串类型（String），主键
* doctorName 医生名字，字符串类型（String）
* hospitalName 医院名字，字符串类型（String）
* hospitalAddress 医院地址，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 省分表 province

* provinceId 省ID，字符串类型（String），主键
* provinceName 省名，字符串类型（String）
* regionId 大区ID，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 市表 city

* cityId 市ID，字符串类型（String），主键
* cityName 市名，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 县区表 county

* countyId 区ID，字符串类型（String），主键
* countyName 区名，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 大区表 region

* regionId 大区ID，字符串类型（String）
* regionName 大区名，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 省大区对应表 province_region(删掉) 

这个表应该可以不要，一个省只能属于一个大区

* id ID，Integer类型，自增，主键
* provinceId 省ID，字符串类型（String）
* regionId 大区ID，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 快照表 snapshoot

* id ID，Integer类型，自增，主键
* type 快照种类，字符串类型（String），已知的有detail、region、profit、logistics
* timeRange 时间区间标识，字符串类型（String），已知的有day、month、year、quarter、halfYear
* day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
* month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
* year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
* quarter 季度，Integer类型，可选1、2、3、4
* half_year 半年，Integer类型，可选1、2
* record 消息的原数据，longtext类型 
* recreate 是否需要重新生成，默认`false`，当对应的表中的数据有修改的时候，将此字段设置为`true`，由定时任务去执行重新生成逻辑
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 审计收入报表 finance_income(刪掉)

当一个订单完成的时候添加一条记录，如果完成后退款，则更新或删除此条记录
TODO 多个发票如何展示

* id ID，Integer类型，自增，主键
* invoiceGoodsCodeVersion 商品编码版本号，字符串类型（String）
* invoiceTaxClassificationCode 税收分类编码，字符串类型（String）
* invoiceTime 开票日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* invoiceCode 发票代码，字符串类型（String）
* invoiceNumber 发票号码，字符串类型（String）
* invoiceBuyerName 购买方名称，字符串类型（String）
* invoiceBuyerTaxPayerNumber 购买方纳税人识别号，字符串类型（String）
* invoiceBuyerAddress 购买方地址电话，字符串类型（String）
* invoiceBuyerBank 购买方开户行及账号，字符串类型（String）
* invoiceItemName 发票货物或应税劳务、服务名称，字符串类型（String）
* invoiceItemCount 发票货物数量，整数类型(Integer)
* invoiceItemUnitPrice 发票货物单价，长整数类型（Long），单位：厘，实际金额 * 1000
* invoicePostTaxAmount 发票税后金额，长整数类型（Long），单位：厘，实际金额 * 1000
* invoiceTaxRate 发票税率，整数类型(Integer)，税率*100，如 6%等于6
* invoiceTaxAmount 税额，长整数类型（Long），单位：厘，实际金额 * 1000
* invoiceAmount 税价合计，长整数类型（Long），单位：厘，实际金额 * 1000
* invoiceSellerName 销售方名称，字符串类型（String）
* invoiceSellerTaxpayerNumber 销售方纳税人识别号，字符串类型（String）
* invoiceSellerAddress 销售方地址电话，字符串类型（String）
* invoiceSellerBank 销售方开户银行及账号，字符串类型（String）
* orderId 订单ID，字符串类型（String）
* orderNumber 订单编号（单据号），字符串类型（String）
* orderCreatorId 订单创建人ID，字符串类型（String）
* orderCreatorName 订单创建人姓名，字符串类型（String）
* orderPrice 订单金额，长整数类型（Long），单位：厘，实际金额 * 1000
* orderCreateTime 订单创建时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayerId 订单支付人ID，字符串类型（String）
* orderPayerName 订单支付人名字，字符串类型（String）
* orderApproveTime 订单预审通过时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* orderPayTime 订单支付时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* paymentType 对应系统内的支付方式，字符串类型（String）
* localStaffPayroll 是否是地服代付，布尔类型（Boolean），`是`为`true`，`否`为`false`
* patientName 患者名字，字符串类型（String）
* patientGender 患者性别
* patientIDCard 患者身份证号，字符串类型（String）
* patientAge 患者年龄，整数类型（Integer）
* patientGender 患者性别，字符串类型（String），可选`male`、`female`
* patientAddress 患者地址，字符串类型（String）
* patientClinicalDiagnosis 患者临床诊断，字符串类型（String）
* testingResult 检测结果，字符串类型（String）
* testingAgency 检测机构名称，字符串类型（String）
* testingItem 检测项目或套餐，字符串类型（String）
* testingItemId 检测项目或套餐ID，整数类型(Integer)
* testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
* testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
* testingAgencyId 检测机构ID，整数类型（Integer）
* testingAgencyAddress 检测机构地址，字符串类型（String）
* testingReportNumber 检测报告编号，字符串类型（String）
* testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* doctorId 医生ID，字符串类型（String）
* doctorName 医生名字，字符串类型（String）
* hospitalName 医院名字，字符串类型（String）
* hospitalAddress 医院地址，字符串类型（String）
* localStaffId 地服ID，字符串类型（String）
* localStaffName 地服名字，字符串类型（String）
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 错误日志表 error_log

* id ID，Integer类型，自增，主键
* type 类型，String类型
* tag 错误的事件消息所属的tag名，String类型
* messageId 错误消息的阿里云的消息Id，String类型
* recordId 错误事件消息的华生数据接口产生的Id，Integer类型
* event 错误事件类型，String类型
* eventTime 错误事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* error 错误信息，String类型
* status  修复状态，Boolean类型
* updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
* createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 日报表汇总表 TODO

每天定时计算的时候，直接把今天的各个项目的和计算出来，一天一条记录存在此表

### 检测套餐明细表 testing_item_detail

- id 明细id，Integer类型，自增，主键
- tag 最后一次修改此明细的事件消息所属的tag名，String类型
- messageId 阿里云的消息Id，String类型
- recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
- day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
- month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
- year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
- quarter 季度，Integer类型，可选1、2、3、4
- half_year 半年，Integer类型，可选1、2
- event 最后一次修改此明细的事件类型，String类型
- eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- changeCount 记录修改次数，Integer类型，默认值为1
- testingItemOrderIncome 套餐订单收入，和套餐价格一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCashIncome 套餐现金收入（到账金额），订单初审通过后填写，实际为套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemFinanceConfirmIncome 财务确认套餐收入，理论上应该和套餐价格一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemRecordCost 当前套餐的检测成本，长整数类型（Long），单位：厘，实际金额1000
- orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
- testingResult 检测结果，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingItem 检测项目或套餐，字符串类型（String）
- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- testingReportNumber 检测报告编号，字符串类型（String）
- testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 检测套餐日明细表 testing_item_daily_detail

- id 明细id，Integer类型，自增，主键
- tag 最后一次修改此明细的事件消息所属的tag名，String类型
- messageId 阿里云的消息Id，String类型
- recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
- day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
- month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
- year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
- quarter 季度，Integer类型，可选1、2、3、4
- half_year 半年，Integer类型，可选1、2
- event 最后一次修改此明细的事件类型，String类型
- eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- changeCount 记录修改次数，Integer类型，默认值为1
- testingItemOrderIncome 套餐订单收入，和套餐价格一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCashIncome 套餐现金收入（到账金额），订单初审通过后填写，实际为套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemFinanceConfirmIncome 财务确认套餐收入，理论上应该和套餐价格一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemRecordCost 当前套餐的检测成本，长整数类型（Long），单位：厘，实际金额1000
- orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
- testingResult 检测结果，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingItem 检测项目或套餐，字符串类型（String）
- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- testingReportNumber 检测报告编号，字符串类型（String）
- testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 检测套餐月明细表 testing_item_monthly_detail

- id 明细id，Integer类型，自增，主键
- tag 最后一次修改此明细的事件消息所属的tag名，String类型
- messageId 阿里云的消息Id，String类型
- recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
- day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
- month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
- year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
- quarter 季度，Integer类型，可选1、2、3、4
- half_year 半年，Integer类型，可选1、2
- event 最后一次修改此明细的事件类型，String类型
- eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- changeCount 记录修改次数，Integer类型，默认值为1
- testingItemOrderIncome 套餐订单收入，和套餐价格一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCashIncome 套餐现金收入（到账金额），订单初审通过后填写，实际为套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemFinanceConfirmIncome 财务确认套餐收入，理论上应该和套餐价格一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemRecordCost 当前套餐的检测成本，长整数类型（Long），单位：厘，实际金额1000
- orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
- testingResult 检测结果，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingItem 检测项目或套餐，字符串类型（String）
- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- testingReportNumber 检测报告编号，字符串类型（String）
- testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 检测套餐季度明细表 testing_item_quarterly_detail

- id 明细id，Integer类型，自增，主键
- tag 最后一次修改此明细的事件消息所属的tag名，String类型
- messageId 阿里云的消息Id，String类型
- recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
- day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
- month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
- year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
- quarter 季度，Integer类型，可选1、2、3、4
- half_year 半年，Integer类型，可选1、2
- event 最后一次修改此明细的事件类型，String类型
- eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- changeCount 记录修改次数，Integer类型，默认值为1
- testingItemOrderIncome 套餐订单收入，和套餐价格一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCashIncome 套餐现金收入（到账金额），订单初审通过后填写，实际为套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemFinanceConfirmIncome 财务确认套餐收入，理论上应该和套餐价格一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemRecordCost 当前套餐的检测成本，长整数类型（Long），单位：厘，实际金额1000
- orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
- testingResult 检测结果，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingItem 检测项目或套餐，字符串类型（String）
- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- testingReportNumber 检测报告编号，字符串类型（String）
- testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 检测套餐半年明细表 testing_item_half_yearly_detail

- id 明细id，Integer类型，自增，主键
- tag 最后一次修改此明细的事件消息所属的tag名，String类型
- messageId 阿里云的消息Id，String类型
- recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
- day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
- month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
- year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
- quarter 季度，Integer类型，可选1、2、3、4
- half_year 半年，Integer类型，可选1、2
- event 最后一次修改此明细的事件类型，String类型
- eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- changeCount 记录修改次数，Integer类型，默认值为1
- testingItemOrderIncome 套餐订单收入，和套餐价格一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCashIncome 套餐现金收入（到账金额），订单初审通过后填写，实际为套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemFinanceConfirmIncome 财务确认套餐收入，理论上应该和套餐价格一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemRecordCost 当前套餐的检测成本，长整数类型（Long），单位：厘，实际金额1000
- orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
- testingResult 检测结果，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingItem 检测项目或套餐，字符串类型（String）
- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- testingReportNumber 检测报告编号，字符串类型（String）
- testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 检测套餐年明细表 testing_item_yearly_detail

- id 明细id，Integer类型，自增，主键
- tag 最后一次修改此明细的事件消息所属的tag名，String类型
- messageId 阿里云的消息Id，String类型
- recordId 最后一次修改此明细的事件消息的华生数据接口产生的Id，Integer类型
- day_str 事件触发主要事件的时间精确到天的字符串，如`2018-05-09`，不一定是消息发送时间，String类型
- month_str 事件触发主要事件的时间精确到月的字符串，如`2018-05`，不一定是消息发送时间，String类型
- year_str 事件触发主要事件的时间精确到天的字符串，如`2018`，不一定是消息发送时间，String类型
- quarter 季度，Integer类型，可选1、2、3、4
- half_year 半年，Integer类型，可选1、2
- event 最后一次修改此明细的事件类型，String类型
- eventTime 最后一次修改此明细的事件消息的产生日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- changeCount 记录修改次数，Integer类型，默认值为1
- testingItemOrderIncome 套餐订单收入，和套餐价格一致，订单创建就有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCashIncome 套餐现金收入（到账金额），订单初审通过后填写，实际为套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemFinanceConfirmIncome 财务确认套餐收入，理论上应该和套餐价格一致，开票事件后有，长整数类型（Long），单位：厘，实际金额 1000
- testingItemRecordCost 当前套餐的检测成本，长整数类型（Long），单位：厘，实际金额1000
- orderId 订单ID，非外键关联订单，因为多个表都会关联它，字符串类型（String）
- testingResult 检测结果，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingItem 检测项目或套餐，字符串类型（String）
- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItemPrice  检测项目或套餐价格，长整数类型（Long），单位：厘，实际金额 1000
- testingItemCost  检测项目或套餐成本，长整数类型（Long），单位：厘，实际金额 1000
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- testingReportNumber 检测报告编号，字符串类型（String）
- testingReportUploadTime 检测报告上传时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- samplingTime 采样日期，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- sampleConfirmTime 样本确认时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

### 套餐信息表 testing_item_tab

- testingItemId 检测项目或套餐ID，整数类型(Integer)
- testingItem 检测项目或套餐，字符串类型（String）
- testingAgency 检测机构名称，字符串类型（String）
- testingAgencyId 检测机构ID，整数类型（Integer）
- testingAgencyAddress 检测机构地址，字符串类型（String）
- updateTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）
- createTime 最后一次更新时间，1970年1月1日00:00:00距今的毫秒数，长整数（Long）

