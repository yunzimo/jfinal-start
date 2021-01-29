package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/27 18:31
*
*/
public class SeparateList {
    // 使用流遍历操作
    /**
    * @Description
    * @Author  ChengShaoFan
    * @Date   2021/1/27 18:33
    * @Param list
* @Param size
    * @Return  java.util.List<java.util.List<T>>
    * @Exception
    *
    */
    public static <T> List<List<T>> partition(final List<T> list, final int size) {
        Integer limit = (list.size() + size - 1) / size;
        List<List<T>> mglist = new ArrayList<List<T>>();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            mglist.add(list.stream().skip(i * size).limit(size).collect(Collectors.toList()));
        });
        return mglist;
    }

}
