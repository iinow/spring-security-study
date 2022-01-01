package io.security.basicsecurity.security.configs;

import io.security.basicsecurity.security.factory.MethodResourcesFactoryBean;
import io.security.basicsecurity.security.processor.ProtectPointcutPostProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity
@Configuration
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

  @Override
  protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
    return mapBasedMethodSecurityMetadataSource();
  }

  @Bean
  public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
    return new MapBasedMethodSecurityMetadataSource(methodResourcesMapFactoryBean().getObject());
  }

  @Bean
  public MethodResourcesFactoryBean methodResourcesMapFactoryBean() {
    return new MethodResourcesFactoryBean("method");
  }

  @Bean
  public MethodResourcesFactoryBean pointcutResourcesMapFactoryBean() {
    return new MethodResourcesFactoryBean("pointcut");
  }

//  @Bean
//  public BeanPostProcessor protectPointcutPostProcessor() throws Exception {
//    Class<?> clazz = Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
//    Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
//    declaredConstructor.setAccessible(true);
//    Object instance = declaredConstructor.newInstance(mapBasedMethodSecurityMetadataSource());
//    Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
//    setPointcutMap.setAccessible(true);
//    setPointcutMap.invoke(instance, pointcutResourcesMapFactoryBean().getObject());
//
//    return (BeanPostProcessor) instance;
//  }

  @Bean
  public ProtectPointcutPostProcessor pointcutPostProcessor() throws Exception {
    var processor = new ProtectPointcutPostProcessor(mapBasedMethodSecurityMetadataSource());
    processor.setPointcutMap(pointcutResourcesMapFactoryBean().getObject());
    return processor;
  }
}
