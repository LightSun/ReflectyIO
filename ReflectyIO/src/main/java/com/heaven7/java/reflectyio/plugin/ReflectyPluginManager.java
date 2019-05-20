package com.heaven7.java.reflectyio.plugin;

import com.heaven7.java.base.util.SparseArrayDelegate;
import com.heaven7.java.base.util.SparseFactory;

/**
 * the reflecty plugin manager
 * @author heaven7
 * @since 1.0.6
 */
public final class ReflectyPluginManager {

    private final SparseArrayDelegate<ReflectyPlugin> mPluginMap = SparseFactory.newSparseArray(5);

    private static class Creator{
        static final ReflectyPluginManager INSTANCE = new ReflectyPluginManager();
    }
    private ReflectyPluginManager(){}

    /**
     * get the default plugin manager
     * @return the plugin manager
     */
    public static ReflectyPluginManager getDefault(){
        return Creator.INSTANCE;
    }

    /**
     * register the reflecty plugin
     * @param type the type
     * @param plugin the reflecty plugin
     */
    public void registerReflectyPlugin(int type, ReflectyPlugin plugin){
        mPluginMap.put(type, plugin);
    }
    /**
     * unregister the reflecty plugin
     * @param type the type
     */
    public void unregisterReflectyPlugin(int type){
        mPluginMap.getAndRemove(type);
    }
    /**
     * get the reflecty plugin
     * @param type the type
     * @return the plugin
     */
    public ReflectyPlugin getReflectyPlugin(int type){
        return mPluginMap.get(type);
    }

}
