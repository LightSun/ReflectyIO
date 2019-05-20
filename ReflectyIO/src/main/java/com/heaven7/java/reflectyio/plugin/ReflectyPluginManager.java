package com.heaven7.java.reflectyio.plugin;

import com.heaven7.java.base.util.SparseArrayDelegate;
import com.heaven7.java.base.util.SparseFactory;

import static com.heaven7.java.reflectyio.ReflectyIo.PLUGIN_TYPE_JSON;
import static com.heaven7.java.reflectyio.ReflectyIo.PLUGIN_TYPE_XML;
import static com.heaven7.java.reflectyio.ReflectyIo.PLUGIN_TYPE_YAML;

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

    static {
        int[] types = {
                PLUGIN_TYPE_XML,
                PLUGIN_TYPE_YAML,
                PLUGIN_TYPE_JSON,
        };
        String[] names = {
                "com.heaven7.java.reflectyio.plugin.XmlReflectyPlugin",
                "com.heaven7.java.reflectyio.plugin.YamlReflectyPlugin",
                "com.heaven7.java.reflectyio.plugin.JsonReflectyPlugin"
        };
        for (int i = 0 , size = types.length ; i < size ; i ++){
            try {
                ReflectyPlugin rp = (ReflectyPlugin) Class.forName(names[i]).newInstance();
                getDefault().registerReflectyPlugin(types[i], rp);
            }catch (Exception e){
                //ignore
            }
        }
    }

    /**
     * get the default plugin manager
     * @return the plugin manager
     */
    public static ReflectyPluginManager getDefault(){
        return Creator.INSTANCE;
    }

    /**
     * register the reflecty plugin. the built-in plugin types are {@linkplain com.heaven7.java.reflectyio.ReflectyIo#PLUGIN_TYPE_XML},
     * {@linkplain com.heaven7.java.reflectyio.ReflectyIo#PLUGIN_TYPE_YAML} and {@linkplain com.heaven7.java.reflectyio.ReflectyIo#PLUGIN_TYPE_JSON}.
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
