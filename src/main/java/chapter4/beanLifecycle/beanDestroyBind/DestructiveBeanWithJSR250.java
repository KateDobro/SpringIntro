package chapter4.beanLifecycle.beanDestroyBind;

import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

public class DestructiveBeanWithJSR250 {

    private File file;
    private String filePath;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        System.out.println("Init bean");

        if (filePath == null) {
            throw new IllegalArgumentException(
                    "You must specify the filePath property of " // Свойство filePath должно быть установлено в классе
                            + DestructiveBeanWithJSR250.class);
        }

        this.file = new File(filePath);
        this.file.createNewFile();

        System.out.println("File exists: " + file.exists()); // файл существует
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying Bean"); // уничтожение бина
        if (!file.delete()) {
            System.err.println("ERROR: failed to delete file.");// ОШБК: не удается удалить файл
        }
        System.out.println("File exists: " + file.exists());// файл существует
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // TODO: before the app run, change value of @mainClassName in build.gradle
    // to "chapter4.beanLifecycle.beanDestroyBind.DestructiveBeanWithJSR250"
    public static void main (String[] args){
        GenericXmlApplicationContext ctx =
                new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/chapter4/beanLifecycle/beanDestroyBind/db_app_ctx.xml"); // todo before start set the right bean-config in app_ctx.xml
        ctx.registerShutdownHook(); // регистрация перехватчика завершения
        ctx.refresh();

        DestructiveBeanWithJSR250 destructiveBean = (DestructiveBeanWithJSR250)ctx.getBean("destructiveBean");

        // без перехватчика завершения - раскомментировать
//        System.out. println("Calling destroy()"); // вызов метода destroy()
//        ctx.destroy();
//        System.out.println("Called destroy()"); // метод destroy() вызван
    }

}