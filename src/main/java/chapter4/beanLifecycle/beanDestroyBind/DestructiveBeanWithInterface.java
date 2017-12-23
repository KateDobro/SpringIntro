package chapter4.beanLifecycle.beanDestroyBind;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.File;

public class DestructiveBean implements InitializingBean {

    private File file;
    private String filePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Init bean");

        if (filePath == null) {
            throw new IllegalArgumentException(
                    "You must specify the filePath property of " // Свойство filePath должно быть установлено в классе
                            + DestructiveBean.class);
        }

        this.file = new File(filePath);
        this.file.createNewFile();

        System.out.println("File exists: " + file.exists()); // файл существует
    }

    public void destroy() {
        System.out.println("Destroying Bean"); // уничтожение бина
        if (!file.delete()) {
            System.err.println("ERROR: failed to delete file.");// ОШБК: не удается удалить файл
            System.out.println("File exists: " + file.exists());// файл существует
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // TODO: before the app run, change value of @mainClassName in build.gradle
    // to "chapter4.beanLifecycle.beanDestroyBind.DestructiveBean"
    public static void main (String[] args){
        GenericXmlApplicationContext ctx =
                new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/chapter4/beanLifecycle/beanDestroyBind/db_app_ctx.xml"); // todo before start set the right bean-config in app_ctx.xml
        ctx.refresh();

        DestructiveBean destructiveBean = (DestructiveBean)ctx.getBean("destructiveBean");

        System.out. println("Calling destroy()"); // вызов метода destroy()
        ctx.destroy();
        System.out.println("Called destroy()"); // метод destroy() вызван
    }

}