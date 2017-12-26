package chapter4.beanKnowAboutSpring;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericXmlApplicationContext;

// listing 4.13-15
public class BeanNamePrinter implements BeanNameAware{

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void someOperation(){
        System.out.println("Bean ["+ beanName +"] - someOperation()");
    }

    public static void main(String[] args){
        GenericXmlApplicationContext ctx =
                new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/chapter4/beanKnowAboutSpring/bkns_app_ctx.xml"); // todo before start set the right bean-config in app_ctx.xml
        ctx.refresh();

        BeanNamePrinter bean = (BeanNamePrinter)ctx.getBean("beanNamePrinter");
        bean.someOperation();
    }
}
