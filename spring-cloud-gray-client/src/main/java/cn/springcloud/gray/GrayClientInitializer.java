package cn.springcloud.gray;

import cn.springcloud.gray.request.RequestLocalStorage;
import cn.springcloud.gray.servernode.ServerExplainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Slf4j
public class GrayClientInitializer implements ApplicationContextAware, InitializingBean {
    private ApplicationContext cxt;

    @Override
    public void afterPropertiesSet() throws Exception {
        GrayClientHolder.setGrayManager(getBean("grayManager", GrayManager.class));
        GrayClientHolder.setRequestLocalStorage(getBean("requestLocalStorage", RequestLocalStorage.class));
        GrayClientHolder.setServerExplainer(getBean("serverExplainer", ServerExplainer.class));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cxt = applicationContext;
    }


    private <T> T getBean(String beanName, Class<T> cls) {
        T t = null;
        try {
            t = cxt.getBean(beanName, cls);
        } catch (BeansException e) {
            log.warn("没有从spring容器中找到name为'{}', class为'{}'的Bean", beanName, cls);
        }
        if (t == null) {
            t = cxt.getBean(cls);
        }
        return t;
    }
}
