package org.mk96.common.util;

import java.util.List;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkMapUtils {

    public <T1, T2> List<T2> mapList(List<T1> source, Function<T1, T2> mapper) {
        return source.stream().map(mapper).toList();
    }

    public <T1, T2, T3> List<T3> mapList(List<T1> source, Function<T1, T2> mapper1, Function<T2, T3> mapper2) {
        return source.stream().map(mapper1).map(mapper2).toList();
    }

}
