/*Copyright (c) 2018-2019 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.services.emailmanager.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.services.emailmanager.EmailManager;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;

/**
 * Controller object for domain model class {@link EmailManager}.
 * @see EmailManager
 */
@RestController
@Api(value = "EmailManagerController", description = "controller class for java service execution")
@RequestMapping("/emailManager")
public class EmailManagerController {

    @Autowired
    private EmailManager emailManager;

    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public void init() throws Exception {
        emailManager.init();
    }

    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    public void sendEmail(@RequestParam(value = "toEmailAddress", required = false) String toEmailAddress, @RequestParam(value = "emailSubject", required = false) String emailSubject, @RequestParam(value = "emailMessage", required = false) String emailMessage) {
        emailManager.sendEmail(toEmailAddress, emailSubject, emailMessage);
    }
}