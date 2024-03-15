package com.sky.quick_login

import android.content.Context
import com.netease.nis.quicklogin.QuickLogin
import com.netease.nis.quicklogin.helper.UnifyUiConfig
import com.netease.nis.quicklogin.listener.QuickLoginPreMobileListener
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener

/**
 * 网易一键登录模块
 */
object QuickLoginManager {
    lateinit var sdk: QuickLogin


    /**
     * 初始化
     *
     * 使用拉取授权页功能前必须先进行初始化操作，建议放在 Application 的 onCreate() 方法中
     */
    fun init(businessId: String, context: Context){
        sdk = QuickLogin.getInstance();
        sdk.init(context, businessId);

    }


    /**
     * 预取号
     *
     * 用户处于未授权状态时，调用该方法
     * 已授权的用户退出当前账号时，调用该方法
     * 在执行拉取授权页的方法之前，提前调用此方法，以提升用户前端体验
     * 此方法需要 1~2s 的时间取得临时凭证，不要和拉取授权页方法一起串行调用。建议放在启动页的 onCreate() 方法中或者 Application 的 onCreate() 方法中去调用
     * 不要频繁的多次调用
     */
    fun prefetchNum(listener: QuickLoginPreMobileListener){
        sdk.prefetchMobileNumber(listener)
    }


    /**
     * 拉起授权页
     *
     * 调用拉取授权页方法后将会调起运营商授权页面，已登录状态下请勿调用。
     * 每次调用拉取授权页方法前需先调用授权页配置方法(setUnifyUiConfig)，否则授权页可能展示异常。
     * 1秒之内只能调用一次，必须保证上一次拉起的授权页已经销毁再调用，否则SDK会返回请求频繁。
     */
    fun login(listener: QuickLoginTokenListener){
        sdk.onePass(listener)
    }


    /**
     * 关闭授权页
     *
     * 不再主动关闭授权页，接入者可根据自己业务需求在合适时机（如一键登录成功）调以下接口来主动关闭授权页
     */
    fun close(){
        sdk.quitActivity()
    }


    /**
     * 配置自定义授权页
     *
     * https://support.dun.163.com/documents/287305921855672320?docId=424017619994976256#5.%20%E8%AE%BE%E7%BD%AE%E6%8E%88%E6%9D%83%E9%A1%B5%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AE
     */
    fun config(context: Context){
        var uiConfig: UnifyUiConfig = UnifyUiConfig.Builder().build(context)
        sdk.setUnifyUiConfig(uiConfig)

    }


//        @SuppressLint("InflateParams")
//        fun getAConfig(context: Context): UnifyUiConfig {
//            val inflater = LayoutInflater.from(context)
////            val loadingRel =
////                inflater.inflate(androidx.appcompat.R.layout.abc_action_bar_title_item, null) as RelativeLayout
////            val layoutParamsLoading = RelativeLayout.LayoutParams(
////                RelativeLayout.LayoutParams.MATCH_PARENT,
////                RelativeLayout.LayoutParams.MATCH_PARENT
////            )
////            loadingRel.layoutParams = layoutParamsLoading //loading view自定义
//
//            return UnifyUiConfig.Builder()
//                .setStatusBarColor(Color.parseColor("#ffffff")) // 状态栏颜色
//                .setStatusBarDarkColor(true) // 状态栏字体图标颜色是否为暗色
//                .setLogoIconName("ico_logo") // 设置应用 logo 图标
//                .setLogoWidth(200) // 设置应用logo宽度
//                .setLogoHeight(70) // 设置应用 logo 高度
//                .setLogoTopYOffset(90) // 设置 logo 顶部 Y 轴偏移
//                .setMaskNumberColor(Color.BLACK) // 设置手机掩码颜色
//                .setMaskNumberSize(25) // 设置手机掩码字体大小
//                .setMaskNumberTypeface(Typeface.SERIF) // 设置手机掩码字体
//                .setMaskNumberTopYOffset(190) // 设置手机掩码顶部Y轴偏移
////            .setMaskNumberBackgroundRes("xxx") // 设置手机掩码背景
//                .setSloganSize(13) // 设置认证品牌字体大小
//                .setSloganColor(Color.parseColor("#9A9A9A")) // 设置认证品牌颜色
//                .setSloganTopYOffset(240) // 设置认证品牌顶部 Y 轴偏移
//                .setLoginBtnText("易盾一键登录") // 设置登录按钮文本
//                .setLoginBtnTextColor(Color.WHITE) // 设置登录按钮文本颜色
//                .setLoginBtnBackgroundRes("login_demo_auth_bt") // 设置登录按钮背景资源
//                .setLoginBtnWidth(240) // 设置登录按钮宽度
//                .setLoginBtnHeight(45) // 设置登录按钮高度
//                .setLoginBtnTextSize(15) // 设置登录按钮文本字体大小
//                .setLoginBtnTopYOffset(280) // 设置登录按钮顶部Y轴偏移
//                .setPrivacyTextStart("我已阅读并同意") // 设置隐私栏声明部分起始文案
//                .setProtocolText("用户协议") // 设置隐私栏协议文本
//                .setProtocolLink("https://www.baidu.com") // 设置隐私栏协议链接
//                .setPrivacyTextEnd("") // 设置隐私栏声明部分尾部文案
//                .setPrivacyTextColor(Color.parseColor("#292929")) // 设置隐私栏文本颜色，不包括协议
//                .setPrivacyProtocolColor(Color.parseColor("#3F51B5")) // 设置隐私栏协议颜色
//                .setPrivacySize(13) // 设置隐私栏区域字体大小
//                .setPrivacyBottomYOffset(24) // 设置隐私栏距离屏幕底部偏移
//                .setPrivacyMarginLeft(40) // 设置隐私栏水平方向的偏移
//                .setPrivacyMarginRight(40) // 设置隐私栏右侧边距
//                .setPrivacyTextMarginLeft(8) // 设置隐私栏复选框和文字内边距
//                .setCheckBoxGravity(TOP) // 设置隐私栏勾选框与文本协议对齐方式
//                .setPrivacyTextGravityCenter(true) // 设置隐私栏文案换行后是否居中对齐
//                .setPrivacyTextLayoutGravity(CENTER) // 设置隐私栏文案与勾选框对齐方式
//                .setPrivacyCheckBoxWidth(20) // 设置隐私栏复选框宽度
//                .setPrivacyCheckBoxHeight(20) // 设置隐私栏复选框高度
//                .setHidePrivacySmh(true) // 是否隐藏书名号
//                .setCheckedImageName("login_demo_check_cus") // 设置隐私栏复选框选中时的图片资源
//                .setUnCheckedImageName("login_demo_uncheck_cus") // 设置隐私栏复选框未选中时的图片资源
//                .setProtocolPageNavTitle("移动服务及隐私协议", "联通服务及隐私协议", "电信服务及隐私协议")// 设置协议详细页标题
//                .setProtocolPageNavColor(Color.parseColor("#FFFFFF"))// 设置协议详细页导航栏标题颜色
////                .setBackgroundVideo("android.resource://" + context.packageName + "/" + R.raw.monkey, "bg_1080") // 设置视频背景
//                .setLoadingVisible(true) // 点击授权页登录按钮loading
////                .setLoadingView(loadingRel) // loading view自定义
//                .setLoginListener(object : LoginListener() {
//                    override fun onDisagreePrivacy(privacyTv: TextView?, btnLogin: Button?): Boolean {
//                        privacyTv?.let {
//                            val animator =
//                                ObjectAnimator.ofFloat(privacyTv, "translationX", 0f, 40f, -40f, 0f)
//                            animator.duration = 300
//                            animator.start()
//                        }
//                        // 返回true自定义处理协议未勾选点击登录（默认false/弹窗）
//                        return true
//                    }
//                })
//                .build(context)
//        }
}