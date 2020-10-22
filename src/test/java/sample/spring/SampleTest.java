package sample.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SampleTest {

    @Test
    public void whenCallGetParam__計算する() {
        try (MockedStatic<SpringDog> theMock = Mockito.mockStatic(SpringDog.class)) {
            Scheduler scheduler = new Scheduler();
            scheduler.doHoge(sut.targetMethod, 10, 10);

            theMock.when(SpringDog::getBar).thenReturn(scheduler);

            assertThat(SpringDog.getBar(), is("foo"));
        }
    }
}