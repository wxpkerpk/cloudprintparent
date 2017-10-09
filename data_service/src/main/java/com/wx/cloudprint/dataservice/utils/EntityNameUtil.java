package com.wx.cloudprint.dataservice.utils;
import javax.persistence.Entity;

public class EntityNameUtil {
   public static  <T> String getEntityName(Class<T> entityClass){
        String entityName = entityClass.getSimpleName();
        Entity entity = entityClass.getAnnotation(Entity.class);
        if(!"".equals(entity.name())){
            entityName = entity.name();
        }
        return entityName;
    }
}
