package bootstrap

import com.google.inject.AbstractModule

class MyDBModule extends AbstractModule {

  protected def configure: Unit = bind(classOf[InitialData]).asEagerSingleton()

}