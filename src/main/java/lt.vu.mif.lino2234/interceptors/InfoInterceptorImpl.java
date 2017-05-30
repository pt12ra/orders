package lt.vu.mif.lino2234.interceptors;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.time.LocalDateTime;

@CustomInfoInterceptor
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class InfoInterceptorImpl implements Serializable {

    @AroundInvoke
    public Object printInfo(InvocationContext ctx) throws Exception {

        LocalDateTime time = LocalDateTime.now();
        String className = ctx.getTarget().getClass().getName();
        String methodName = ctx.getMethod().getName();

        Object result = ctx.proceed();

        System.out.println("Invoked " + time + " " + className + " " + methodName);
        return result;
    }
}
