package com.wuhk.enums;

/**
 * @Author liushaobin
 * @Description: 定义枚举状态码 信息集
 * @Date: 2021/9/1 18:39
 */
public enum GeneralStatusCode {
    SUCCESS(1000, "处理成功"),
    ERROR(5999, "服务器异常"),
    FAIL(5998, "校验异常"),


    T_IS_EXIST(4001, "通过税务人拉取接口拉取数据或等待总局清分"),

    T_NOT_NULL_FULL_NAME(4002, "税务人姓名不能为空"),
    T_NOT_NULL_NATION(4003, "民族不能为空"),
    T_EID_NOT_NULL(4004, "网络身份id不能为空"),
    T_GENDER_NOT_NULL(4005, "性别不能为空"),
    T_POLITICS_STATE_NOT_NULL(4006, "政治面貌不能为空"),
    T_ID_CARD_TYPE_NOT_NULL(4007, "证件类型不能为空"),
    T_ID_CARD_NOT_NULL(4008, "证件号码不能为空"),
    T_ID_CARD_TIME_START_NOT_NULL(4009, "证件有效期起不能为空"),
    T_ID_CARD_TIME_END_NOT_NULL(4010, "证件有效期止不能为空"),
    T_JOB_CODE_NOT_NULL(4011, "职务代码不能为空"),
    T_ZJ_INTERFACE_NOT_NULL(4012, "调用总局接口返回空"),
    T_TAX_AUTHORITY_CODE_NOT_NULL(4013, "税务机关代码不能为空"),

    T_DATASOURCE_N_R(4020, "数据来源[dataSource]不合法"),

    T_UPDATE_EID_NOT_E(4030, "可信身份不存在"),
    T_TAX_AUTHORITY_NOT_E(4031, "未查询到税务机关信息"),
    T_POSITION_INFO_NULL(4040, "未查询到岗位信息"),
    T_ER_N(4050, "该税务人不可用，请做税务人修改进行变更状态"),


    N_NAME_NOTNULL(4201, "姓名不能为空"),
    N_GENDER_NOTNULL(4202, "性别不能为空"),
    N_CARDTYPE_NOTNULL(4203, "身份证件类型不能为空"),
    N_IDCARD_NOTNULL(4204, "身份证件号码不能为空"),
    N_STADATE_NOTNULL(4205, "证件有效起始时间不能为空"),
    N_ENDDATE_NOTNULL(4206, "证件有效结束时间不能为空"),
    N_MOB_NOTNULL(4207, "电话不能为空"),
    N_TAXCODE_NOTNULL(4208, "税务机关代码不能为空"),
    N_TAXPLACE_NOTNULL(4209, "纳税所在地不能为空"),
    N_ADDRESS_NOTNULL(4210, "地址不能为空"),
    N_CARDNUM_NOTNULL(4211, "银行卡号不能为空"),
    N_UID_NOTNULL(4212, "网络身份id不能为空"),
    N_DATA_EXIST(4213, "该自然人数据已存在，请调用数据拉取接口"),
    N_NOCHAGE_POWD(4214, "当前没有可信账号，不能修改密码"),
    N_DATA_REST(4215, "该用户为注销用户，请调用数据恢复接口"),
    N_DATASTART_INCT(4216, "证件有效期起始期格式错误"),
    N_INFOR_EXIST(4217, "自然人数据已存在，不可再次新增"),
    N_TAXAUTCODE_NOTNULL(4218, "税务机关代码不能为空"),
    N_PAGENUM_NOTNULL(4219, "当前页码不能为空"),
    N_PAGESIZE_NOTNULL(4220, "每页条数不能为空"),
    N_ZJ_INTERFACE_NOTNULL(4221, "调用总局接口返回空"),
    N_ACCOUNT_NOTEXIST(4222, "该可信账号不存在，不可注销"),
    N_DATA_SOURCE(4223, "数据来源不能为空"),
    N_CARDFORMAT_ERROR(4224, "身份证件号码格式有误"),
    N_MOBILEFORMAT_ERROR(4225, "手机号格式有误"),
    N_CARDNUM_FORMATERR(4226, "银行卡号格式有误"),
    N_TAXNUM_FORMATERR(4227, "税务机关代码格式有误"),
    N_DATAEND_INCT(4228, "证件有效期起始期格式错误"),
    USERTYPE_NOTNULL(4229, "用户类型不能为空"),
    ACCOUNT_NOTEXIST(4230, "该账号不存在，请检查您输入的账号"),
    ROLEID_NOTNULL(4231, "角色id不能为空"),
    LEG_NOTBLANK_TJLX(4232, "状态类型不能为空"),

    N_INFO_ERROR(4101, "所查询的自然人信息不存在，请核验输入信息是否有误"),
    N_INFO_CHCEK(4102, "实名办税监控系统两要素三级实名核验接口，核验失败"),
    N_PCV_NOTNULL(4103, "自然人人像控件版本号不能为空"),
    N_PICFILE_NOTNULL(4104, "自然人人像数据不能为空"),
    N_INFO_CHCEK1(4105, "实名办税监控系统四要素三级实名核验接口，核验失败"),
    N_INFO_CHCEK2(4106, "实名办税监控系统二要素四级实名核验接口，核验失败"),
    N_INFO_CHCEK3(4107, "实名办税监控系统四要素四级实名核验接口，核验失败"),
    N_INFO_CHCEK4(4108, "实名办税监控系统四要素五级实名核验接口，核验失败"),
    N_CERT_DISACCORD(4109, "自然人证件有效期有误，核验失败"),
    N_BUSINESS_NOTNULL(4110, "自然人业务流水号不能为空"),
    N_DN_NOTNULL(4111, "自然人ID验证数据不能为空"),
    N_CARDVERSION_NOTNULL(4112, "自然人读卡控件版本号不能为空"),
    N_CREDIBLEACCOUNT_NOTNULL(4113, "可信系统账号不能为空"),
    N_CREDIBLEPASS_NOTNULL(4114, "可信系统密码不能为空"),
    N_CLIENTID_NOTNULL(4115, "应用系统id不能为空"),
    N_SUBACCOUNT_EXIST(4116, "可信身份系统用户账号已存在"),
    N_LEGALERINFO_ERROR(4117, "所查询的法人信息不存在，请核验输入信息是否有误"),
    N_TAXERINFO_ERROR(4118, "所查询的税务人信息不存在，请核验输入信息是否有误"),
    N_USERTYPE_NOTNULL(4119, "用户类型不能为空(0-税务人 1-自然人 2-法人)"),
    N_IDCARDSTAXPAYER_NOTNULL(4120, "身份证件号码或者纳税人识别号不能为空"),
    N_SUBPASSWORD_NOTNULL(4121, "应用密码不能为空"),
    N_TAXERTYPE_NOTNULL(4122, "税务人操作类型不能为空，(1-停用，2-恢复，3-特殊恢复)"),
    N_TAXERUID_NOTNULL(4123, "税务人网络身份id不能为空"),
    N_NATURALEREIDINFO_NOTNULL(4124, "该自然人可信账号已注册，请勿重复注册"),
    N_TYPEFORMAT_ERROR(4125, "输入类型错误，请查看输入类型"),
    N_IDCARDLENGTH_ERROR(4126, "身份证件号码输入不正确，请输入自己正确的号码信息"),
    L_LEGALERACCOUNT_EXIST(4127, "该法人可信账号已注册，请勿重复注册"),
    T_TAXERACCOUNT_EXIST(4128, "该税务人可信账号已注册，请勿重复注册"),
    TLN_TELLPHONE_EXIST(4129, "输入的手机号与实体表不一致，请确认后再重新输入"),
    N_NUMBER_ERROR(4130, "只能是数字(0,1,2)"),
    NTL_NUMBER1_ERROR(4141, "只能是数字(1,2,3)"),
    NTL_ACCOUNTLENGTH_ERROR(4131, "长度不能超过50"),
    NTL_ACCOUNTLENGTH1_ERROR(4142, "必须是数字和字母组合，并且长度为80"),
    NTL_PHONEEXIST_ERROR(4132, "请输入合法的手机号"),
    N_TAXPAYERINFO_ERROR1(4133, "税号包含小写字母,添加失败！"),
    N_TAXPAYERINFO_ERROR2(4134, "税号要为数字和大写字母的任意组合!"),
    N_TAXPAYERINFO_ERROR3(4135, "税号不符合规范,请输入正确税号！"),
    N_TAXPAYERINFO_ERROR4(4136, "税号不规范,请输入正确税号！"),
    N_TAXPAYERINFO_ERROR5(4137, "税号超长,请输入正确税号！"),
    N_TAXPAYERINFO_ERROR6(4138, "税号过短,请输入正确税号！"),
    N_TAXPAYERINFO_ERROR7(4139, "税号格式有误："),
    L_LEGALERNSRSBHINFO_ERROR8(4140, "法人可信账号与纳税人识别号不一致，请与纳税人识别号保持一致！"),
    T_TAXERINFOUID_ERROR(4143, "停用失败，没有对应网络身份"),
    T_TAXERINFOUID_ERROR1(4144, "恢复失败，没有对应网络身份"),
    SPECIAL_RECOVERY_PERIOD(4145, "特殊恢复有效期不能为空，请填写有效期时间"),
    NTL_ACCROUNTLEVEL_NULL(4146, "可信身份实名认证等级为空，没有认证！"),
    N_NUMBER_ERROR1(4147, "只能是数字(0,1)"),
    C_CERTIFICATE_NOTNULL(4148, "证书状态不能为空！"),
    C_DATE_CERTIFICATE_NOTNULL(4149, "证书有效起始日期不能为空"),
    C_DATE_CERTIFICATE_NOTNULL1(4150, "证书有效终止日期不能为空"),
    C_CERTIFICATEID_NOTNULL(4151, "证书id不能为空！"),
    LOG_EXPORT_NOTNULL(4152, "导出文档为空，传入的日志id不存在"),


    LEG_NOTBLANK_EID(4300, "网络身份标识不能为空"),
    LEG_NOTBLANK_CREDITCODE(4301, "统一社会信用代码不能为空"),
    LEG_NOTBLANK_TAXAUTHORITYCODE(4302, "税务机关代码不能为空"),
    LEG_NOTBLANK_COMPANYNAME(4303, "企业名称不能为空"),
    LEG_NOTBLANK_COMPANYREGDATE(4304, "企业注册日期不能为空"),
    LEG_NOTBLANK_REGISTRATIONNUMBER(4305, "注册号不能为空"),
    LEG_NOTBLANK_COMPANYTYPE(4306, "企业类型不能为空"),
    LEG_NOTBLANK_COMPANYADDRESS(4307, "企业地址不能为空"),
    LEG_NOTBLANK_BANK(4308, "开户银行不能为空"),
    LEG_NOTBLANK_ACCOUNT(4309, "开户帐号不能为空"),
    LEG_NOTBLANK_STATE(4310, "状态不能为空"),
    LEG_NOTBLANK_RISKLEVEL(4311, "风险等级不能为空"),
    LEG_NOTBLANK_LEGALREPIDCARDTYPE(4312, "法定代表人证件类型不能为空"),
    LEG_NOTBLANK_LEGALREPIDCARD(4313, "法定代表人证件号码不能为空"),
    LEG_NOTBLANK_LEGALREPFULLNAME(4314, "法定代表人证件姓名不能为空"),
    LEG_NOTBLANK_LEGALREPMOBILE(4315, "法定代表人手机号不能为空"),
    LEG_NOTBLANK_FINANCIALIDCARDTYPE(4316, "财务负责人证件类型不能为空"),
    LEG_NOTBLANK_FINANCIALIDCARD(4317, "财务负责人证件号码不能为空"),
    LEG_NOTBLANK_FINANCIALFULLNAME(4318, "财务负责人证件姓名不能为空"),
    LEG_NOTBLANK_FINANCIALMOBILE(4319, "财务负责人手机号不能为空"),
    LEG_NOTBLANK_LINKUSERIDCARDTYPE(4320, "关联自然人证件类型不能为空"),
    LEG_NOTBLANK_LINKUSERIDCARD(4321, "关联自然人证件号码不能为空"),
    LEG_NOTBLANK_LINKUSERFULLNAME(4322, "关联自然人证件姓名不能为空"),
    LEG_NOTBLANK_OPTYPE(4331, "操作类型不能为空"),
    LEG_NOTBLANK_USERTYPE(4332, "自然人类型不能为空"),
    LEG_NOTBLANK_USERLIST(4333, "自然人列表不能为空"),
    LEG_NOTNULL_PAGENUM(4338, "当前页码不能为空"),
    LEG_NOTNULL_PAGESIZE(4339, "每页条数不能为空"),

    LEG_LENGTH_EID(4340, "网络身份标识长度错误"),
    LEG_LENGTH_CREDITCODE(4341, "统一社会信用代码长度错误"),
    LEG_LENGTH_TAXAUTHORITYCODE(4342, "税务机关代码长度错误"),
    LEG_LENGTH_COMPANYNAME(4343, "企业名称长度错误"),
    LEG_LENGTH_COMPANYREGDATE(4344, "企业注册日期长度错误或无效"),
    LEG_LENGTH_REGISTRATIONNUMBER(4345, "注册号长度错误"),
    LEG_LENGTH_COMPANYTYPE(4346, "企业类型长度错误"),
    LEG_LENGTH_COMPANYADDRESS(4347, "企业地址长度错误"),
    LEG_LENGTH_BANK(4348, "开户银行长度错误"),
    LEG_LENGTH_ACCOUNT(4349, "开户帐号长度错误"),
    LEG_LENGTH_STATE(4350, "状态长度错误"),
    LEG_LENGTH_RISKLEVEL(4351, "风险等级长度错误"),
    LEG_LENGTH_LEGALREPIDCARDTYPE(4352, "法定代表人证件类型长度错误"),
    LEG_LENGTH_LEGALREPIDCARD(4353, "法定代表人证件号码长度错误"),
    LEG_LENGTH_LEGALREPFULLNAME(4354, "法定代表人证件姓名长度错误"),
    LEG_LENGTH_LEGALREPMOBILE(4355, "法定代表人手机号长度错误"),
    LEG_LENGTH_FINANCIALIDCARDTYPE(4356, "财务负责人证件类型长度错误"),
    LEG_LENGTH_FINANCIALIDCARD(4357, "财务负责人证件号码长度错误"),
    LEG_LENGTH_FINANCIALFULLNAME(4358, "财务负责人证件姓名长度错误"),
    LEG_LENGTH_FINANCIALMOBILE(4359, "财务负责人手机号长度错误"),
    LEG_LENGTH_LINKUSERIDCARDTYPE(4360, "关联自然人证件类型长度错误"),
    LEG_LENGTH_LINKUSERIDCARD(4361, "关联自然人证件号码长度错误"),
    LEG_LENGTH_LINKUSERFULLNAME(4362, "关联自然人证件姓名长度错误"),
    LEG_LENGTH_ENTERPRISENAME(4379, "纳税人姓名长度错误"),
    LEG_ERR_OPTYPE(4371, "操作类型错误"),
    LEG_ERR_USERTYPE(4372, "自然人类型错误"),
    LEG_MISMATCH_OPUSERTYPE(4373, "操作类型与自然人类型不匹配"),
    LEG_ERR_DATE(4377, "日期格式错误"),
    LONG_ERR_DATE(4378, "查询起止时间相差不得大于半年"),

    LEG_NOTEXIST_LEGALER(4380, "该企业实体信息不存在"),
    LEG_NOTEXIST_LEGALREP(4381, "查询法定代表人不存在，关联失败"),
    LEG_NOTEXIST_FINANCIAL(4382, "查询财务负责人不存在，关联失败"),
    LEG_NOTEXIST_LINKUSER(4383, "查询关联自然人不存在，详见DATA"),
    LEG_MISMATCH_EID(4390, "网络身份ID不匹配"),
    LEG_MISMATCH_APPID(4391, "应用ID不匹配"),
    LEG_ERR_UNKNOWN(4399, "法人未知错误"),

    NET_NOTNULL_USERTYPE(4400, "用户类型不能为空"),
    NET_LENGTH_USERTYPE(4410, "用户类型长度错误"),
    NET_NOTNULL_IDCARD(4401, "身份唯一标识不能为空"),
    NET_LENGTH_IDCARD(4411, "身份唯一标识长度错误"),
    NET_NOTNULL_APPID(4402, "应用ID不能为空"),
    NET_LENGTH_APPID(4412, "应用ID长度错误"),
    NET_NOTNULL_APPACCOUNT(4403, "应用帐号不能为空"),
    NET_LENGTH_APPACCOUNT(4413, "应用帐号长度错误"),
    NET_NOTNULL_APPPASSWORD(4404, "应用密码不能为空"),
    NET_LENGTH_APPPASSWORD(4414, "应用密码长度错误"),
    NET_ERR_USERTYPE(4420, "用户类型错误(0-税务人 1-自然人 2-法人)"),
    NET_EXIST_USERAPP(4421, "该用户与应用的关联已存在"),

    SID_NOT_NULL(4422, "sid不能为空"),
    STARTTIME_NOT_NULL(4423, "操作时间起不能为空"),
    ENDTIME_NOT_NULL(4424, "操作时间止不能为空"),
    ORGCODE_NOT_NULL(4425, "组织机构代码不能为空"),
    PAGING_ERROR(4426, "分页异常"),
    USER_INFO_ERROR(4427, "用户信息不匹配"),
    PARAMETER_PARSE_EXCEPTION(4496, "HTTP解析请求参数异常"),
    HTTP_MEDIA_TYPE_EXCEPTION(4497, "HTTP解析请求类型或不支持方法异常"),
    SQL_ERROR_EXCEPTION(4498, "SQL语法异常"),
    SYSTEM_EXCEPTION(4499,"日志服务异常");

    private Integer code;
    private String message;

    GeneralStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static GeneralStatusCode getGeneralStatusCode(int code) {
        for (GeneralStatusCode ele : values()) {
            if (ele.getCode() == code) {
                return ele;
            }
        }
        return null;
    }
}
