package com.android.server.hidata.wavemapping.chr;

import android.util.IMonitor;
import android.util.IMonitor.EventStream;
import com.android.server.hidata.wavemapping.chr.entity.BuildModelChrInfo;
import com.android.server.hidata.wavemapping.chr.entity.StgUsageChrInfo;
import com.android.server.hidata.wavemapping.cons.Constant;
import com.android.server.hidata.wavemapping.dao.EnterpriseApDAO;
import com.android.server.hidata.wavemapping.dao.MobileApDAO;
import com.android.server.hidata.wavemapping.entity.ParameterInfo;
import com.android.server.hidata.wavemapping.entity.RegularPlaceInfo;
import com.android.server.hidata.wavemapping.modelservice.ModelBaseService;
import com.android.server.hidata.wavemapping.util.FileUtils;
import com.android.server.hidata.wavemapping.util.LogUtil;
import com.android.server.hidata.wavemapping.util.TimeUtil;
import java.io.File;

public class BuildModelChrService {
    public StgUsageChrInfo getStgUsageChrInfo() {
        try {
            return new StgUsageChrInfo(((float) FileUtils.getDirSize(new File(Constant.getDbPath()))) / 1024.0f, ((float) FileUtils.getDirSize(new File(Constant.getRawDataPath()))) / 1024.0f, ((float) FileUtils.getDirSize(new File(Constant.getModelPath()))) / 1024.0f, ((float) FileUtils.getDirSize(new File(Constant.getLogPath()))) / 1024.0f, ((float) FileUtils.getDirSize(new File(Constant.getDataPath()))) / 1024.0f, ((float) FileUtils.getDirSize(new File(Constant.getROOTPath()))) / 1024.0f);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getStgUsageChrInfo,e:");
            stringBuilder.append(e.getMessage());
            LogUtil.e(stringBuilder.toString());
            return null;
        }
    }

    public void setApTableChrStatInfo(BuildModelChrInfo buildModelChrInfo) {
        if (buildModelChrInfo != null) {
            try {
                EnterpriseApDAO enterpriseApDAO = new EnterpriseApDAO();
                MobileApDAO mobileApDAO = new MobileApDAO();
                buildModelChrInfo.setTrainDataMain(enterpriseApDAO.findAllCount());
                buildModelChrInfo.setTestDataMain(mobileApDAO.findAllCountBySrctype(1));
                buildModelChrInfo.setUpdateMain(mobileApDAO.findAllCountBySrctype(2));
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setApTableChrStatInfo,e:");
                stringBuilder.append(e.getMessage());
                LogUtil.e(stringBuilder.toString());
            }
        }
    }

    public void buildModelChrInfo(ParameterInfo param, int trainRet, RegularPlaceInfo placeInfo, BuildModelChrInfo buildModelChrInfo, int disCriminateRet) {
        if (buildModelChrInfo != null && param != null && placeInfo != null) {
            try {
                ModelBaseService modelBaseService = new ModelBaseService();
                buildModelChrInfo.setRetAll(trainRet);
                buildModelChrInfo.setRetMain(disCriminateRet);
                buildModelChrInfo.setMaxDistAll(param.getMaxDist());
                buildModelChrInfo.setModelAll(placeInfo.getModelName());
                buildModelChrInfo.setMaxDistCell(((float) FileUtils.getDirSize(new File(modelBaseService.getModelFilePath(placeInfo, param)))) / 1024.0f);
                StringBuilder stringBuilder;
                if (commitChr(buildModelChrInfo)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("buildModelChrInfo success,");
                    stringBuilder.append(buildModelChrInfo.toString());
                    LogUtil.d(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(TimeUtil.getTime());
                    stringBuilder.append(",buildModelChrInfo success,");
                    stringBuilder.append(buildModelChrInfo.toString());
                    stringBuilder.append(Constant.lineSeperate);
                    LogUtil.wtLogFile(stringBuilder.toString());
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("buildModelChrInfo failure,");
                    stringBuilder.append(buildModelChrInfo.toString());
                    LogUtil.d(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(TimeUtil.getTime());
                    stringBuilder.append(",buildModelChrInfo failure,");
                    stringBuilder.append(buildModelChrInfo.toString());
                    stringBuilder.append(Constant.lineSeperate);
                    LogUtil.wtLogFile(stringBuilder.toString());
                }
            } catch (Exception e) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("commitBuildModelChr,e:");
                stringBuilder2.append(e.getMessage());
                LogUtil.e(stringBuilder2.toString());
            }
        }
    }

    public void setBuildModelChrInfo(String place, ParameterInfo param, RegularPlaceInfo placeInfo, BuildModelChrInfo buildModelChrInfo, BuildModelChrService buildModelChrService) {
        if (buildModelChrInfo != null && param != null && placeInfo != null) {
            buildModelChrInfo.setLoc(place);
            buildModelChrInfo.setBatchAll(placeInfo.getBatch());
            buildModelChrInfo.setFingerAll(placeInfo.getFingerNum());
            buildModelChrInfo.setLabel(param.isMainAp());
            buildModelChrInfo.setConfigVerAll(param.getConfig_ver());
            buildModelChrInfo.setStorage(getStgUsageChrInfo());
            setApTableChrStatInfo(buildModelChrInfo);
        }
    }

    /* JADX WARNING: Missing block: B:8:0x0212, code skipped:
            if (r1 != null) goto L_0x0214;
     */
    /* JADX WARNING: Missing block: B:9:0x0214, code skipped:
            android.util.IMonitor.closeEventStream(r1);
     */
    /* JADX WARNING: Missing block: B:19:0x023e, code skipped:
            if (r1 != null) goto L_0x0214;
     */
    /* JADX WARNING: Missing block: B:20:0x0241, code skipped:
            return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean commitChr(BuildModelChrInfo buildModelChrInfo) {
        EventStream buildModelEstream = null;
        EventStream apTypeEstream = null;
        EventStream stgUsageEstream = null;
        boolean ret;
        try {
            buildModelEstream = IMonitor.openEventStream(CHRConst.MSG_WAVEMAPPING_BUILDMODEL_EVENTID);
            apTypeEstream = IMonitor.openEventStream(CHRConst.MSG_WAVEMAPPING_APTYPE_CLASSID);
            stgUsageEstream = IMonitor.openEventStream(CHRConst.MSG_WAVEMAPPING_STGUSAGE_CLASSID);
            buildModelEstream.setParam(BuildBenefitStatisticsChrInfo.E909002049_LOCATION_TINYINT, buildModelChrInfo.getLoc());
            buildModelEstream.setParam("batchA", buildModelChrInfo.getBatchAll());
            buildModelEstream.setParam("fingA", buildModelChrInfo.getFingerAll());
            buildModelEstream.setParam("retA", buildModelChrInfo.getRetAll());
            buildModelEstream.setParam("trainA", buildModelChrInfo.getTrainDataAll());
            buildModelEstream.setParam("testA", buildModelChrInfo.getTestDataAll());
            buildModelEstream.setParam("updA", buildModelChrInfo.getUpdateAll());
            buildModelEstream.setParam("firstA", buildModelChrInfo.getFirstTimeAll());
            buildModelEstream.setParam("modA", buildModelChrInfo.getModelAll());
            buildModelEstream.setParam("mDistA", buildModelChrInfo.getMaxDistAll());
            buildModelEstream.setParam("cfgA", buildModelChrInfo.getConfigVerAll());
            buildModelEstream.setParam("batchM", buildModelChrInfo.getBatchMain());
            buildModelEstream.setParam("fingM", buildModelChrInfo.getFingerMain());
            buildModelEstream.setParam("retM", buildModelChrInfo.getRetMain());
            buildModelEstream.setParam("trainM", buildModelChrInfo.getTrainDataMain());
            buildModelEstream.setParam("testM", buildModelChrInfo.getTestDataMain());
            buildModelEstream.setParam("updM", buildModelChrInfo.getUpdateMain());
            buildModelEstream.setParam("firstM", buildModelChrInfo.getFirstTimeMain());
            buildModelEstream.setParam("modM", buildModelChrInfo.getModelMain());
            buildModelEstream.setParam("mDistM", buildModelChrInfo.getMaxDistMain());
            buildModelEstream.setParam("cfgM", buildModelChrInfo.getConfigVerMain());
            buildModelEstream.setParam("batchC", buildModelChrInfo.getBatchCell());
            buildModelEstream.setParam("fingC", buildModelChrInfo.getFingerCell());
            buildModelEstream.setParam("retC", buildModelChrInfo.getRetCell());
            buildModelEstream.setParam("trainC", buildModelChrInfo.getTrainDataCell());
            buildModelEstream.setParam("testC", buildModelChrInfo.getTestDataCell());
            buildModelEstream.setParam("updC", buildModelChrInfo.getUpdateCell());
            buildModelEstream.setParam("firstC", buildModelChrInfo.getFirstTimeCell());
            buildModelEstream.setParam("modC", buildModelChrInfo.getModelCell());
            buildModelEstream.setParam("mDistC", buildModelChrInfo.getMaxDistCell());
            buildModelEstream.setParam("cfgC", buildModelChrInfo.getConfigVerCell());
            buildModelEstream.setParam("lab", buildModelChrInfo.getLabel());
            buildModelEstream.setParam("ref", buildModelChrInfo.getRef());
            apTypeEstream.setParam("enterpriseAp", buildModelChrInfo.getAPType().getEnterpriseAp());
            apTypeEstream.setParam("mobileApSrc1", buildModelChrInfo.getAPType().getMobileApSrc1());
            apTypeEstream.setParam("mobileApSrc2", buildModelChrInfo.getAPType().getMobileApSrc2());
            apTypeEstream.setParam("ApSrc3", buildModelChrInfo.getAPType().getMobileApSrc3());
            apTypeEstream.setParam("update", buildModelChrInfo.getAPType().getUpdate());
            apTypeEstream.setParam("totalFound", buildModelChrInfo.getAPType().getTotalFound());
            apTypeEstream.setParam("finalUsed", buildModelChrInfo.getAPType().getFinalUsed());
            stgUsageEstream.setParam("db", buildModelChrInfo.getStorage().getDbSize());
            stgUsageEstream.setParam("raw", buildModelChrInfo.getStorage().getRawdataSize());
            stgUsageEstream.setParam("mod", buildModelChrInfo.getStorage().getModelSize());
            stgUsageEstream.setParam("log", buildModelChrInfo.getStorage().getLogSize());
            stgUsageEstream.setParam("data", buildModelChrInfo.getStorage().getDataSize());
            stgUsageEstream.setParam("back", buildModelChrInfo.getStorage().getBackupsize());
            buildModelEstream.setParam("apRec", apTypeEstream);
            buildModelEstream.setParam("stor", stgUsageEstream);
            IMonitor.sendEvent(buildModelEstream);
            ret = true;
            if (buildModelEstream != null) {
                IMonitor.closeEventStream(buildModelEstream);
            }
            if (stgUsageEstream != null) {
                IMonitor.closeEventStream(stgUsageEstream);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("commitBuildModelChr,e:");
            stringBuilder.append(e2.getMessage());
            LogUtil.e(stringBuilder.toString());
            ret = false;
            if (buildModelEstream != null) {
                IMonitor.closeEventStream(buildModelEstream);
            }
            if (stgUsageEstream != null) {
                IMonitor.closeEventStream(stgUsageEstream);
            }
        } catch (Throwable th) {
            if (buildModelEstream != null) {
                IMonitor.closeEventStream(buildModelEstream);
            }
            if (stgUsageEstream != null) {
                IMonitor.closeEventStream(stgUsageEstream);
            }
            if (apTypeEstream != null) {
                IMonitor.closeEventStream(apTypeEstream);
            }
        }
    }
}
