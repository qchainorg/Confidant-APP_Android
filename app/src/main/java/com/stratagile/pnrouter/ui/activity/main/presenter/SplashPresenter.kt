package com.stratagile.pnrouter.ui.activity.main.presenter

import android.Manifest
import android.app.Activity
import android.util.Log
import chat.tox.antox.toxme.ToxData
import chat.tox.antox.utils.CreateUserUtils
import chat.tox.antox.wrapper.ToxAddress
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pawegio.kandroid.toast
import com.socks.library.KLog
import com.stratagile.pnrouter.R
import com.stratagile.pnrouter.application.AppConfig
import com.stratagile.pnrouter.constant.ConstantValue
import com.stratagile.pnrouter.data.api.HttpAPIWrapper
import com.stratagile.pnrouter.entity.MyRouter
import com.stratagile.pnrouter.entity.RSAData
import com.stratagile.pnrouter.ui.activity.main.contract.SplashContract
import com.stratagile.pnrouter.utils.*
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author hzp
 * @Package com.stratagile.pnrouter.ui.activity.main
 * @Description: presenter of SplashActivity
 * @date 2018/09/10 22:25:34
 */
class SplashPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: SplashContract.View) : SplashContract.SplashContractPresenter {
    private val mCompositeDisposable: CompositeDisposable
    private val JUMPTOLOGIN = 1
    private val HASPUDATE = 3
    private val getLastVersionBack = false
    private var permissionState = -1    //-1表示原始状态,0表示允许,1表示拒绝.
    private var hasUpdate = false
    private var timeOver = false
    private val jump = JUMPTOLOGIN
    private var jumpToGuest = false

    override fun doAutoLogin() {
        Log.i("splash", "2")
    }

    override fun getLastVersion() {
        Log.i("splash", "1")
       /* if (SpUtil.getInt(AppConfig.instance, ConstantValue.LOCALVERSIONCODE, 0) !== VersionUtil.getAppVersionCode(AppConfig.instance)) {
            KLog.i("需要跳转到guest.........................")
            KLog.i(SpUtil.getInt(AppConfig.instance, ConstantValue.LOCALVERSIONCODE, 0))
            KLog.i(VersionUtil.getAppVersionCode(AppConfig.instance))
            jumpToGuest = true
        }*/
        var routerList = AppConfig.instance.mDaoMaster!!.newSession().routerEntityDao.loadAll()
        if(routerList.size == 0)
        {
            jumpToGuest = true
        }
    }

    override fun getPermission() {
        AndPermission.with(mView as Activity)
                .requestCode(101)
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .callback(permission)
                .start()
    }

    override fun observeJump() {
        Observable.interval(0, 1, TimeUnit.SECONDS).take(4)
                .map { aLong -> 2 - aLong }
                .observeOn(AndroidSchedulers.mainThread())//发射用的是observeOn
                .doOnSubscribe {
                    KLog.i("1")
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        KLog.i("2")
                    }

                    override fun onNext(remainTime: Long) {
                        KLog.i("剩余时间$remainTime")
                    }

                    override fun onError(e: Throwable) {
                        KLog.i("4")
                    }

                    override fun onComplete() {
                        //                        jump = JUMPTOGUEST;
                        timeOver = true
                        KLog.i("时间到，开始跳转")
                        if (permissionState != 0) {
                            return
                        }
                        if (jumpToGuest) {
                            mView.jumpToGuest()
                            return
                        }
                        if (jump == JUMPTOLOGIN) {
                            mView.jumpToLogin()
                        }
                    }
                })
    }

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    private val permission = object : PermissionListener {
        override fun onSucceed(requestCode: Int, grantedPermissions: List<String>) {
            FileUtil.init()
            LocalRouterUtils.inspectionLocalData();
            LocalRouterUtils.updateGreanDaoFromLocal()
            var routerList = AppConfig.instance.mDaoMaster!!.newSession().routerEntityDao.loadAll()
            var abvc = ""
            routerList.forEach {
                if (it.routerId == null || it.routerId.equals("") || it.userSn == null || it.userSn.equals("") || it.userId == null || it.userId.equals("") || it.username == null || it.username.equals("") || it.dataFileVersion == null || it.dataFileVersion.equals("")) {
                    AppConfig.instance.mDaoMaster!!.newSession().routerEntityDao.delete(it)
                }
            }
            routerList = AppConfig.instance.mDaoMaster!!.newSession().routerEntityDao.loadAll()
            getLastVersion()
            var toxId:String =  FileUtil.getLocalUserData("toxId")
            if(toxId == null && toxId.equals(""))
            {
                var toxData: ToxData = CreateUserUtils.createToxData("test",AppConfig.instance)
                if(toxData != null && toxData.address()!= null)
                {
                    var toxAddress: ToxAddress = toxData.address()
                    var toxId = toxAddress.toString()
                    FileUtil.saveUserData2Local(toxId,"toxId")
                }
            }
            ConstantValue.privateRAS = SpUtil.getString(AppConfig.instance, ConstantValue.privateRASSp, "")
            ConstantValue.publicRAS = SpUtil.getString(AppConfig.instance, ConstantValue.publicRASSp, "")
            if(ConstantValue.privateRAS.equals("") && ConstantValue.publicRAS.equals(""))
            {
                val gson = Gson()
                var rsaData = FileUtil.readRSAData();
                val localRSAArrayList: ArrayList<RSAData>
                if(rsaData.equals(""))
                {
                    val KeyPair = RxEncryptTool.generateRSAKeyPair(1024)
                    val aahh = KeyPair!!.private.format
                    val strBase64Private:String = RxEncodeTool.base64Encode2String(KeyPair.private.encoded)
                    val strBase64Public = RxEncodeTool.base64Encode2String(KeyPair.public.encoded)
                    ConstantValue.privateRAS = strBase64Private
                    ConstantValue.publicRAS = strBase64Public
                    SpUtil.putString(AppConfig.instance, ConstantValue.privateRASSp, ConstantValue.privateRAS!!)
                    SpUtil.putString(AppConfig.instance, ConstantValue.publicRASSp, ConstantValue.publicRAS!!)
                    localRSAArrayList = ArrayList()
                    var RSAData: RSAData = RSAData()
                    RSAData.privateKey = strBase64Private
                    RSAData.publicKey = strBase64Public
                    localRSAArrayList.add(RSAData)
                    FileUtil.saveRSAData(gson.toJson(localRSAArrayList))
                }else{
                    var rsaStr = rsaData
                    if (rsaStr != "") {
                        localRSAArrayList = gson.fromJson<ArrayList<RSAData>>(rsaStr, object : TypeToken<ArrayList<RSAData>>() {

                        }.type)
                        if(localRSAArrayList.size > 0)
                        {
                            ConstantValue.privateRAS = localRSAArrayList.get(0).privateKey
                            ConstantValue.publicRAS =  localRSAArrayList.get(0).publicKey
                            SpUtil.putString(AppConfig.instance, ConstantValue.privateRASSp, ConstantValue.privateRAS!!)
                            SpUtil.putString(AppConfig.instance, ConstantValue.publicRASSp, ConstantValue.publicRAS!!)
                        }
                    }
                }
            }
            // 权限申请成功回调。
            if (requestCode == 101) {
                permissionState = 0
                if (timeOver) {
                    if (jumpToGuest) {
                        mView.jumpToGuest()
                        return
                    }
                    else if (jump == JUMPTOLOGIN) {
                        mView.jumpToLogin()
                    }
                }
            }
        }

        override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {
            // 权限申请失败回调。
            if (requestCode == 101) {
                KLog.i("权限申请失败")
                permissionState = 0
                AppConfig.instance.toast(R.string.permission_denied)
                mView.exitApp()
            }
        }
    }
}