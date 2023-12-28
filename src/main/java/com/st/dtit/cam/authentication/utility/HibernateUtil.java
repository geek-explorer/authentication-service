package com.st.dtit.cam.authentication.utility;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class HibernateUtil {

       private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);

       private static  final String AUTHENTICATION_DATABASE = "hibernate-auth.cfg.xml";

       private static SessionFactory authSessionFactory = getSessionFactoryAuth();

       private static Metadata buildSessionFactory(String dataSource){
            try {
                final String configFile = getConfigurationFile(dataSource);
                final File hibernateConfigFile = new File(configFile);

                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure(hibernateConfigFile).build();

                Metadata metaData = new MetadataSources(standardRegistry)
                        .getMetadataBuilder()
                        .build();

                return metaData;
            }catch (Throwable ex){
                LOGGER.error("Initialization of Session Factory failed.",ex);
                throw new ExceptionInInitializerError(ex);
            }
       }

      public static SessionFactory getSessionFactoryAuth(){
              if (null == authSessionFactory || authSessionFactory.isClosed()){
                    authSessionFactory = buildSessionFactory(AUTHENTICATION_DATABASE).getSessionFactoryBuilder().build();;
              }
              return authSessionFactory;
       }

       private static  String getConfigurationFile(final String dataSource) throws Exception{
              final String dataSourceFile =  System.getProperty("database.connection.source-directory") + dataSource;
              LOGGER.info("DB Connection Source File : {}", dataSourceFile);
              return dataSourceFile;
       }


       private HibernateUtil(){}

}
