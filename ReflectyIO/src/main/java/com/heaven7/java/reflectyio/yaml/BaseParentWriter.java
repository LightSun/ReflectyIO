package com.heaven7.java.reflectyio.yaml;

/*public*/ class BaseParentWriter  {

    private HostWriter hostWriter;

    public BaseParentWriter(HostWriter hostWriter) {
        this.hostWriter = hostWriter;
    }

    public HostWriter getHostWriter() {
        return hostWriter;
    }

    public BaseParentWriter append(String str){
        hostWriter.append(str);
        return this;
    }
}
