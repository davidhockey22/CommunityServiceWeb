package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;

import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@Join(path="/error", to="/Web/Error.xhtml")
public class ErrorBean {

}
