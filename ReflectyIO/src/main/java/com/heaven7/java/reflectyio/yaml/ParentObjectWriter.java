package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.base.util.Platforms;

/*public*/ class ParentObjectWriter extends BaseParentWriter implements ParentTypeWriter {

    public ParentObjectWriter(HostWriter hostWriter) {
        super(hostWriter);
    }

    @Override
    public void name(String s) {
        append(getHostWriter().currentSpace())
                .append(s).append(": ");
    }

    @Override
    public void nullValue() {
        append(" ").append(Platforms.getNewLine());
    }
    @Override
    public void value(Number obj) {
        append(obj.toString()).append(Platforms.getNewLine());
    }
    @Override
    public void value(Boolean obj) {
        append(obj.toString()).append(Platforms.getNewLine());
    }
    @Override
    public void value(Character chz) {
        append(chz.toString()).append(Platforms.getNewLine());
    }
    @Override
    public void value(String str) {
        append(str).append(Platforms.getNewLine());
    }
}
