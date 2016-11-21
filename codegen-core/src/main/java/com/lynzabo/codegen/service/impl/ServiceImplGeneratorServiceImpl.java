/**
 * Copyright (c) 2016 乐视云计算有限公司（lecloud.com）. All rights reserved
 */
package com.lynzabo.codegen.service.impl;

import com.lynzabo.codegen.except.CodegenException;
import com.lynzabo.codegen.model.GenDTO;
import com.lynzabo.codegen.model.ServiceImplDTO;
import com.lynzabo.codegen.service.Generator;
import com.lynzabo.codegen.supports.CodegenConfig;
import com.lynzabo.codegen.supports.FreemarkerUtil;
import com.lynzabo.codegen.util.StringUtil;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Service类文件生成
 * @author linzhanbo .
 * @since 2016年11月17日, 16:59 .
 * @version 1.0 .
 */
@Service("serviceImplGeneratorService")
public class ServiceImplGeneratorServiceImpl extends AbstractGeneratorServiceImpl implements Generator {
    public void render() {
        GenDTO genDTO = CodegenConfig.getInstance().getGenDTO();
        ServiceImplDTO serviceImplDTO = genDTO.getServiceImplDTO();
        Map<String,Object> dataItems = new HashMap<String,Object>();
        //ftl需要
        dataItems.put("serviceImplPackage", getServiceImplPackage());
        dataItems.put("daoPackage",getDaoPackage());
        dataItems.put("daoName",getDaoName());
        dataItems.put("daoEnitityName", StringUtil.sub1Upper(getDaoName()));
        dataItems.put("modelPackage",getModelPackage());
        dataItems.put("entityName",getEntityName());
        dataItems.put("servicePackage", getServicePackage());
        dataItems.put("serviceName", getServiceName());
        dataItems.put("serviceImplDescription", getServiceImplDescription());
        dataItems.put("serviceIOCName", StringUtil.firstLower(getServiceIOCName()));
        dataItems.put("serviceImplName", getServiceImplName());

        //serviceImpl properties
        Map serviceImplPropsMap = serviceImplDTO.getProperties();
        dataItems.putAll(serviceImplPropsMap);
        //global properties
        Map globalPropsMaps = CodegenConfig.getInstance().getProperties();
        dataItems.putAll(globalPropsMaps);
        try {
            FreemarkerUtil.renderToFile(dataItems, serviceImplDTO.getFtl(), MessageFormat.format("{0}/{1}/{2}.java", getServiceImplLocation(), getServiceImplPackage().replace(".", "/"), getServiceImplName()));
        } catch (Exception e) {
            throw new CodegenException(e);
        }
    }
}
