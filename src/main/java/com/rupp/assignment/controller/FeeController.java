package com.rupp.assignment.controller;

import com.rupp.assignment.json.JFee;
import com.rupp.assignment.json.JMessage;
import com.rupp.assignment.json.JMessage.MessageType;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;


@Controller
@RequestMapping(value = {"fee", "fees" })
public class FeeController {

    private static final Logger LOG = LoggerFactory.getLogger(FeeController.class);

    @Autowired
    private com.rupp.assignment.service.FeeService service;
    @Autowired
    private JMessage message;  


    @RequestMapping(value = "v1/all", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="Get all fees", notes = "Get all fees", response = JFee.class, responseContainer = "List")

    public Collection<JFee> getAll(HttpServletRequest request, WebRequest webRequest,
                                   @RequestHeader(required = false, value = "If-Modified-Since") Date since) {
        return service.getAll();
    }

    @RequestMapping(value = "v1/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="Get fee by id", notes = "Get fee by id", response = JFee.class)
    public JFee getDetails(HttpServletRequest request, @PathVariable int id) {

        return service.getDetails(id);
    }

    @RequestMapping(value = "v1", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="Create fee", notes = "Create fee", response = JMessage.class)
    public JMessage create(HttpServletRequest request, @ModelAttribute JFee domain) {
    	if(	domain.getCourseId() <= 0 || domain.getFee() <= 0  || domain.getFeeTypeId() <= 0 ){		
    		this.message.setMessage("Please fill all require fields!");
        	this.message.setStatus(MessageType.ERROR);
        	return this.message;
        }
        return service.create(domain);
    }

    @RequestMapping(value = "v1/{id}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="Update fee", notes = "Update fee", response = JMessage.class)
    public JMessage update(HttpServletRequest request, @PathVariable int id, @ModelAttribute JFee domain) {
    	if(	domain.getCourseId() <= 0 || domain.getFee() <= 0  || domain.getFeeTypeId() <= 0 ){		
    		this.message.setMessage("Please fill all require fields!");
        	this.message.setStatus(MessageType.ERROR);
        	return this.message;
        }
        return service.update(id, domain);
    }

    @RequestMapping(value = "v1/remove", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="Remove fee", notes = "Remove fee", response = JMessage.class)
    public JMessage remove(HttpServletRequest request) {
        return service.remove(Integer.parseInt(request.getParameter("id")));
    }
    
    @RequestMapping(value = "v1/{feetypeid}/{courseid}", method = RequestMethod.GET)
    @ResponseBody
    public Integer getExistFee(HttpServletRequest request, @PathVariable int feetypeid, @PathVariable int courseid) {
        return service.getExistFee(feetypeid, courseid);
    }
    
}
