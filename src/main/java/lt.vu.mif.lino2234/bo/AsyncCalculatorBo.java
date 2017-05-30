package lt.vu.mif.lino2234.bo;

import java.util.concurrent.Future;

public interface AsyncCalculatorBo {

    Future<Long> asyncMethod();
}
