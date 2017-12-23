package chapter4.beanLifecycle.beanInitBind;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SimpleBean {
    private static final String DEFAULT_NAМE = "Luke Skywalker";

    private String name;
    private int age = Integer.MIN_VALUE;

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void init(){
        System.out.println("Init bean");

        if(name == null){
            System.out.println("Using default name");
            name = DEFAULT_NAМE;
        }

        if(age == Integer.MIN_VALUE){
            throw new IllegalArgumentException(
                    "You must set the age property of any beans of type"
                            + SimpleBean.class); // Должно быть установлено свойство age любого бина типа
        }
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // TODO: before the app run, change value of @mainClassName in build.gradle to "chapter4.beanLifecycle.beanInitBind.SimpleBean"
    public static void main (String[] args){
        GenericXmlApplicationContext ctx =
                new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/chapter4/beanLifecycle/beanInitBind/sb_app_ctx.xml"); // todo before start set the right bean-config in app_ctx.xml
        ctx.refresh();

        SimpleBean sb1 = getBean("sb1",ctx);
        SimpleBean sb2 = getBean("sb2",ctx);
        SimpleBean sb3 = getBean("sb3",ctx);
    }

    private static SimpleBean getBean(String beanName, ApplicationContext context){
        try {
            SimpleBean bean = (SimpleBean) context.getBean(beanName);
            System.out.println(bean);
            return bean;
        } catch (BeanCreationException ex){
            System.out.println("An error occured in bean configuration: "
                    + ex.getMessage()); // В конфигурации бина произошла ошибка
            return null;
        }
    }
}
