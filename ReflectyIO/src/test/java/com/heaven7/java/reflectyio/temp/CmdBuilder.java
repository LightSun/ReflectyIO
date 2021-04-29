package com.heaven7.java.reflectyio.temp;

import java.util.ArrayList;
import java.util.List;

public final class CmdBuilder {

    private List<String> mList;

    public CmdBuilder() {
        this.mList = new ArrayList<String>();
    }
    public CmdBuilder str(String str){
        if(!str.isEmpty()){
            mList.add(str);
        }
        return this;
    }
    public CmdBuilder strs(String[] cmd){
        for (String c : cmd){
            str(c);
        }
        return this;
    }
    public CmdBuilder cmd(String str){
        return strs(str.split(" "));
    }
    public CmdBuilder and(){
        mList.add("&");
        return this;
    }
    public CmdBuilder or(){
        mList.add("||");
        return this;
    }
    public CmdBuilder success(){
        mList.add("&&");
        return this;
    }
    public String[] toCmd(){
        return mList.toArray(new String[mList.size()]);
    }
}
