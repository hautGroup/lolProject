package com.teljjb.extend;

/**
 * Created by dezhonger on 2017/4/12.
 */

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class VelocityToolbox20View extends VelocityToolboxView {
    ThreadLocal<ToolManager> th = new ThreadLocal<ToolManager>();
    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        ViewToolContext ctx = new ViewToolContext(getVelocityEngine(), request, response,
                getServletContext());

        ctx.putAll(model);

        if (this.getToolboxConfigLocation() != null) {
            if(th.get()!=null){
                ctx.addToolbox(th.get().getToolboxFactory().createToolbox(Scope.APPLICATION));
            }else{
                ToolManager tm = new ToolManager();
                tm.setVelocityEngine(getVelocityEngine());
                tm.configure(getServletContext().getRealPath(getToolboxConfigLocation()));
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.APPLICATION));
                th.set(tm);
            }

        }

        return ctx;
    }
}
