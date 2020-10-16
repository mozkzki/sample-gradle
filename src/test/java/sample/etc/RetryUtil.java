package sample.etc;

import java.util.function.Consumer;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RetryUtil<T> {

    private static final long BACKOFF_INITIAL_INTERVAL = 500;
    private static final double BACKOFF_MULTIPLIER = 1.5;
    private static final long BACKOFF_MAX_ELAPSED_TIME = 7000;

    private ExponentialBackOff backOff;
    private BackOffExecution backOffExec;

    public RetryUtil() {
        backOff = new ExponentialBackOff(BACKOFF_INITIAL_INTERVAL, BACKOFF_MULTIPLIER);
        backOff.setMaxElapsedTime(BACKOFF_MAX_ELAPSED_TIME);
        backOffExec = backOff.start();
    }

    public void call(Consumer<T> targetMethod, T arg) throws Exception {
        call(targetMethod, arg, 0);
    }

    private void call(Consumer<T> targetMethod, T arg, int count) throws Exception {
        long waitInterval = backOffExec.nextBackOff();
        if (waitInterval == BackOffExecution.STOP) {
            // リトライしても失敗した場合
            System.out.println("Retry out. Count: " + count);
            throw new RuntimeException();
        } else {
            try {
                // ターゲットメソッドを実行
                targetMethod.accept(arg);
            } catch (Exception e) {
                // 対象のExceptionの場合のみリトライする (catch句でジェネリクスが使用不可)
                if (!isRetryTargetException(e)) {
                    // dummy
                }
                System.out.println("Func failed. Retry start.... Count: " + (count + 1));
                System.out.println(e.getMessage());
                Thread.sleep(waitInterval);
                // リトライ
                call(targetMethod, arg, count + 1);
            }
        }
    }

    private boolean isRetryTargetException(Exception e) {
        return true;
    }

}
