package lt.vu.mif.lino2234.bo;

import java.util.concurrent.ExecutionException;

public interface InfoCollectorBo {

    String getInfo() throws ExecutionException, InterruptedException;
    void resetInfo();
    boolean isDone();
}
