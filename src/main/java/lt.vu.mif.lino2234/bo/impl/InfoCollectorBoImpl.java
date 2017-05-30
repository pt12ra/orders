package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.bo.InfoCollectorBo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Named(value = "infoCollectorBo")
@ApplicationScoped
public class InfoCollectorBoImpl implements InfoCollectorBo, Serializable{

    private static final long serialVersionUID = -2136192697217500615L;

    @Inject private AsyncCalculatorBoImpl asyncCalculatorBoImpl;

    private String count;
    private Future<Long> resultInFuture = null;

    public void callAsyncMethod() throws ExecutionException, InterruptedException {
        if (resultInFuture == null) {
            resultInFuture = asyncCalculatorBoImpl.asyncMethod();
            count =  "Please wait. Counting orders";
        } else {
            String result = resultInFuture.get().toString();
//            resultInFuture = null;
            count =  "Processing done: Counted " + result.toString() + " orders ";
        }
    }

    @Override
    public String getInfo() throws ExecutionException, InterruptedException{
        callAsyncMethod();
        return count;
    }

    @Override
    public void resetInfo() {
        resultInFuture = null;
    }

    @Override
    public boolean isDone() {
        return resultInFuture != null && resultInFuture.isDone();
    }
}
