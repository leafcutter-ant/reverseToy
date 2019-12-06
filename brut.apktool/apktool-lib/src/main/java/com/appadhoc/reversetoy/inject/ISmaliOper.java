package com.appadhoc.reversetoy.inject;

import brut.util.OS;
import com.appadhoc.reversetoy.utils.Utils;

import java.io.File;
import java.util.regex.Matcher;

public abstract class ISmaliOper {
    String stubDir = "com.reverse.stub";
    String sdktype;

    public abstract void addOrModifyApplicationSmali(File hostDir, File aarSmaliFolder) throws Exception;
    public abstract File getAppStubSmaliFile();
    public abstract File getCodeMethodInit();

    ISmaliOper(String sdktype) {
        this.sdktype = sdktype;
    }

    public void modifyExistAppSmali(File hostdir, String hostAppName) throws Exception {
        if (!hostdir.exists()) {
            throw new Exception("host dir not exist");
        }
        if (hostAppName == null || hostAppName.equals("")) {
            throw new Exception("hostAppName must be not  null");
        }
        String hostAppNameFileName = hostAppName.replaceAll("\\.", File.separator) + ".smali";
//            invoke-direct {p0}, Lcom/reverse/stub/App;->initSDK()V
        String callMethodCode = "invoke-direct {p0}, L" + hostAppName.replaceAll("\\.", "/") + ";->initSDK()V";
        File codePieceFile = getCodeMethodInit();
        String methodCode = Utils.FileUtils.readStringFromFile(codePieceFile).toString();
        String methodCodeReplaceMent = Matcher.quoteReplacement(methodCode);
        File needModiFile = null;
        for (File subSmaiFolder : hostdir.listFiles()) {
            if (subSmaiFolder.isDirectory() && subSmaiFolder.getName().startsWith("smali")) {
                File file = new File(subSmaiFolder, hostAppNameFileName);
                if (file.exists()) {
                    needModiFile = file;
                    break;
                }
            }
        }
        if (needModiFile == null) {
            throw new Exception("can not find src Application smali file ,file name path " + hostAppName);
        }
        System.out.println(needModiFile.getAbsolutePath());
        String srcStr = Utils.FileUtils.readStringFromFile(needModiFile).toString();
        srcStr = srcStr.replaceFirst(".method\\s+public\\s+constructor\\s+<init>\\(\\)V(.*\\n)+?.end\\s+method", "$0\n\n" + methodCodeReplaceMent);
        srcStr = srcStr.replaceFirst(".method\\s+public\\s+(final\\s+)?onCreate\\(\\)V(.*\\n)+?\\s*.locals\\s+\\d+", "$0\n\n" + callMethodCode);
        Utils.FileUtils.writeString2File(needModiFile, srcStr);
        boolean replaceSuccess = srcStr.contains("method private initSDK");
        boolean replaceCallSuccess = srcStr.contains("->initSDK()V");
        if (replaceCallSuccess && replaceSuccess) {

        } else {
            throw new Exception("modify " + hostAppName + " smali modify failed");
        }
    }

    public void copyStubSmali2HostDir(String appName, File aarSmaliFolder) throws Exception {

        // copy App.smali file 2 host smali folder
        File stubDir = new File(aarSmaliFolder, appName.replaceAll("\\.", File.separator));
        if (!stubDir.exists()) {
            stubDir.mkdirs();
        }
        File stubAppSmaliFile = getAppStubSmaliFile();
        OS.cpfile2src(stubAppSmaliFile, stubDir);
    }
}
