package com.heaven7.java.reflectyio.yaml;


import com.heaven7.java.base.util.Platforms;

/*public*/ class ParentArrayWriter extends BaseParentWriter implements ParentTypeWriter {

    public ParentArrayWriter(HostWriter hostWriter) {
        super(hostWriter);
    }
    @Override
    public void name(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullValue() {
       append(getHostWriter().currentSpace()).append(Platforms.getNewLine());
    }

    @Override
    public void value(Number obj) {
        append(getHostWriter().currentSpace()).append(obj.toString()).append(Platforms.getNewLine());
    }

    @Override
    public void value(Boolean obj) {
        append(getHostWriter().currentSpace()).append(obj.toString()).append(Platforms.getNewLine());
    }

    @Override
    public void value(Character chz) {
        append(getHostWriter().currentSpace()).append(chz.toString()).append(Platforms.getNewLine());
    }
    @Override
    public void value(String str) {
        append(getHostWriter().currentSpace()).append(str).append(Platforms.getNewLine());
    }
}
