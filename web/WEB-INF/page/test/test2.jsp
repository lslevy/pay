<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.love.pay.controller.plat.PlatPayController"%>
<%
    String ret = PlatPayController.insert(request);
    if(ret==null){out.print("200 OK");}else{out.print("fail");}
%>