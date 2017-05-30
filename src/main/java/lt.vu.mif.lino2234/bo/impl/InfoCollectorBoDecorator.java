package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.bo.InfoCollectorBo;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

@Decorator
public abstract class InfoCollectorBoDecorator implements InfoCollectorBo {

    @Inject
    @Delegate
    @Any
    InfoCollectorBo collector;

    private String count;

    public void callAsyncMethod() throws ExecutionException, InterruptedException {
        count = collector.getInfo();
        if(collector.isDone()){
            count += " Calculation time : " + 3 + " seconds";
        }
    }

    @Override
    public String getInfo() throws ExecutionException, InterruptedException{
        this.callAsyncMethod();
        return count;
    }


}
