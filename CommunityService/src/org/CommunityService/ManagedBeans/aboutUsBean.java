package org.CommunityService.ManagedBeans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ApplicationScoped
@Join(path = "/aboutus", to = "/Web/AboutUs.xhtml")
public class aboutUsBean {

}
