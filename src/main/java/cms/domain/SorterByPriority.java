package cms.domain;

import java.util.Comparator;

public class SorterByPriority implements Comparator<Article> {
    @Override
    public int compare(Article o1, Article o2) {
        return Integer.compare(o2.getPriority(), o1.getPriority());
    }
}

