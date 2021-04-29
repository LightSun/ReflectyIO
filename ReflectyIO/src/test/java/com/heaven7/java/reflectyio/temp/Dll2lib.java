package com.heaven7.java.reflectyio.temp;

import com.heaven7.java.base.util.FileUtils;

import java.io.File;
import java.util.List;

public final class Dll2lib {

    public static final String PEXPORTS = "E:/study/tools/pexports/bin/pexports.exe";
    public static final String LIB = "E:/visualstudio/ide/VC/Tools/MSVC/14.16.27023/bin/Hostx64/x64/lib.exe";

    // E:/study/tools/pexports/bin/pexports.exe E:\360Downloads\opencv\sources\.build_mingw\lib\libopencv_calib3d451.dll.a > E:\360Downloads\opencv\sources\.build_mingw\bin/.lib/libopencv_calib3d451.def
    public static void main(String[] args) {
        //String dir = "E:\\360Downloads\\opencv\\sources\\.build_mingw\\bin";
        String dir = "E:\\360Downloads\\opencv\\sources\\.build\\bin\\Debug";
        String outDir = dir + "/.lib";
        List<String> files = FileUtils.getFiles(new File(dir), "dll");
        for (String str: files){
            String file = FileUtils.getFileName(str);
            //generate .def
            CmdHelper cmdHelper = new CmdHelper(new CmdBuilder().str(PEXPORTS)
                    .str(str)
                    .str(">")
                    .str(outDir + "/" + file + ".def")
                    .toCmd());
            cmdHelper.setWorkDir(dir);
            cmdHelper.execute();
            //generate .lib
            cmdHelper = new CmdHelper(new CmdBuilder().str(LIB)
                    .str(str)
                    .str("/DEF:"+ outDir + "/" + file + ".def")
                    .str("/MACHINE:X86")
                    .str("/OUT:" + outDir + "/" + file + ".lib")
                    .toCmd());
            cmdHelper.setWorkDir(dir);
            cmdHelper.execute();
            break;
        }
    }
}
