package com.appadhoc.reversetoy;


import brut.androlib.Androlib;
import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import brut.androlib.ApkOptions;
import brut.common.BrutException;
import brut.directory.DirectoryException;
import com.appadhoc.reversetoy.aar.AarManager;
import com.appadhoc.reversetoy.sign.SignTool;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws BrutException, IOException {

        // decode apk

        try {
            decodeApk();
            File file = buildApk();
            System.out.println(file.getAbsoluteFile());
            File sign = SignTool.sign(file);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void decodeApk() throws Exception {

        //-----------decoder setting--------------
        ApkDecoder decoder = new ApkDecoder();
        decoder.setForceDelete(true);
        ApkOptions options = new ApkOptions();
        options.verbose = true;
        File file = new File("/Users/jiaozhengxiang/Desktop/sample-debug.apk");
        File apkOutFile = new File("/Users/jiaozhengxiang/Desktop/work/toy_workspace/apk_workspace");
        decoder.setApkFile(file);
        decoder.setOutDir(apkOutFile);

        //-----------------------------------------
        //-------AarManager setting----------------
        AarManager manager = AarManager.getInstance().init("/Users/jiaozhengxiang/Desktop/work/toy_workspace", "/Users/jiaozhengxiang/Desktop/work/aar-workspace/abtest-lite-v5.1.3-sp.aar");
        decoder.decode(manager);
        logger.info("##########解压apk文件[完成]##########");
        manager.smaliClassFilesAndModifyids(apkOutFile);
        logger.info("##########重新编排ID并拷贝文件到宿主文件夹[完成]##########");
//        manager.smaliClassFilesAndModifyids();

//        manager.asdfasdfasdf();
//        File unsignfile = buildApk();

//        signApk(unsignfile);
    }

    public static File buildApk() {
        ApkOptions options = new ApkOptions();
        options.verbose = true;
        options.debugMode = true;

        File workdir = new File("/Users/jiaozhengxiang/Desktop/apktool_workspace");
        File unsignFile = new File("/Users/jiaozhengxiang/Desktop/apktool_workspace", "helloBuildByapi.apk");
        try {
            new Androlib(options).build(workdir, unsignFile);
        } catch (BrutException e) {
            e.printStackTrace();
        }
        return unsignFile;
    }
}
