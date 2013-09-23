/**
 * Created on  13-09-09 17:20
 */
package com.taobao.geek.jetcache.impl;

import com.alibaba.fastjson.util.IdentityHashMap;
import com.taobao.geek.jetcache.CacheConfig;
import com.taobao.geek.jetcache.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * @author yeli.hl
 */
class ClassUtil {

    private static IdentityHashMap<Method, String> methodMap = new IdentityHashMap<Method, String>();

    public static String getSubArea(CacheConfig cacheConfig, Method method){
        // TODO 对参数的类型发生变化做出感知

        String prefix = methodMap.get(method);

        if (prefix == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(cacheConfig.getVersion()).append('_');
            sb.append(Type.getType(method.getDeclaringClass()).getDescriptor());
            sb.append('.');
            Type t = Type.getType(method);
            sb.append(method.getName());
            sb.append(t.getDescriptor());
            methodMap.put(method, sb.toString());
            return sb.toString();
        } else {
            return prefix;
        }
    }

    public static Class<?>[] getAllInterfaces(Object obj) {
        Class<?> c = obj.getClass();
        HashSet<Class<?>> s = new HashSet<Class<?>>();
        do {
            Class<?>[] its = c.getInterfaces();
            for (Class<?> it : its) {
                s.add(it);
            }
            c = c.getSuperclass();
        } while (c != null);
        return s.toArray(new Class<?>[s.size()]);
    }

    public static String getMethodSig(Method m) {
        return m.getName() + com.taobao.geek.jetcache.objectweb.asm.Type.getType(m).getDescriptor();
    }
}
