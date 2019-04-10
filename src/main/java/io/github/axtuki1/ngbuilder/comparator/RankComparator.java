package io.github.axtuki1.ngbuilder.comparator;

import java.util.Comparator;

public class RankComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer p1, Integer p2) {
        return p1 > p2 ? -1 : 1;
    }
}