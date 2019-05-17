package com.heaven7.java.xml;

/**
 * the xml member proxy helper
 * @author heaven7
 */
/*public*/ class XmlMemberProxyHelper {

    private final ThreadLocal<Integer> mLocalIndex = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return -1;
        }
    };
    private String elementPath;
    private String[] elementNames;

    private final Callback mCallback;

    public XmlMemberProxyHelper(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public String elementName(boolean next){
        if(!init()){
            return null;
        }
        Integer index = mLocalIndex.get();
        if(next){
            if(index >= elementNames.length - 1){
                return null;
            }
            mLocalIndex.set(index + 1);
            return elementNames[++index];
        }else {
            return elementNames[index];
        }
    }

    public boolean hasNextName(){
        if(!init()){
            return false;
        }
        return mLocalIndex.get() < elementNames.length - 1;
    }

    public void reset(){
        mLocalIndex.set(-1);
    }

    private boolean init() {
        if(elementPath == null){
            XmlElement element = mCallback.getXmlElement();
            if(element == null){
               // throw new XmlException("for complex xml element. you must define the element names. like 'root/person/...'");
                return false ;
            }
            elementPath = element.value();
            elementNames = elementPath.split("/");
        }
        return true;
    }

    public interface Callback{
        /**
         * call this to lazy init xml element.
         * @return the annotation object of xml element.
         */
        XmlElement getXmlElement();
    }
}
