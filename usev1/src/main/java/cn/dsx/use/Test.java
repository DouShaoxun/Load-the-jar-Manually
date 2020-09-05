package cn.dsx.use;

import cn.dsx.common.BaseInterface;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author dousx
 * @version 1.0
 * @date 2020-09-05 9:17
 */
public class Test {
    public static void main(String[] args) {
        URLClassLoader loader = null;
        Class<?> aClass = null;
        try {
            File directory = new File("src/main/resources");
            String baseDirectory = directory.getCanonicalPath() + "/jar/";

            // 加载jar包
            loader = new URLClassLoader(
                    new URL[]{new File(baseDirectory + "v1.jar").toURL()},
                    Thread.currentThread().getContextClassLoader());
            // 获取类
            aClass = loader.loadClass("cn.dsx.common.impl.SomeImpl");

            //创建对象 强制转成父类
            BaseInterface base = (BaseInterface) aClass.newInstance();
            System.out.println(base.sayString());


            // 创建对象
            Object object = aClass.newInstance();
            //获取实例当中的方法名
            Method method = aClass.getMethod("sayString");
            // 执行方法
            Object result = method.invoke(object);
            System.out.println(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //卸载关闭外部jar
            try {
                loader.close();
            } catch (IOException e) {
                System.out.println("关闭外部jar失败：" + e.getMessage());
            }
        }
    }
}
