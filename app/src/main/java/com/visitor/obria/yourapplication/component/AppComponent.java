package com.visitor.obria.yourapplication.component;

import com.visitor.obria.yourapplication.bean.StudenBean;
import com.visitor.obria.yourapplication.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    StudenBean test();
}
