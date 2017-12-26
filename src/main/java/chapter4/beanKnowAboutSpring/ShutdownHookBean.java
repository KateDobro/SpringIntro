package chapter4.beanKnowAboutSpring;

import chapter4.beanLifecycle.beanDestroyBind.DestructiveBeanWithInterface;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericXmlApplicationContext;

// listing 4.16-4.18
public class ShutdownHookBean implements ApplicationContextAware{

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if(ctx instanceof GenericXmlApplicationContext)
            ((GenericXmlApplicationContext) ctx).registerShutdownHook();
    }

    public static void main (String[] args){
        GenericXmlApplicationContext ctx =
                new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/chapter4/beanKnowAboutSpring/bkns_app_ctx.xml"); // todo before start set the right bean-config in app_ctx.xml
        ctx.registerShutdownHook();
        ctx.refresh();

        DestructiveBeanWithInterface bean = (DestructiveBeanWithInterface)ctx.getBean("destructiveBean");
    }
}
