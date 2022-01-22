package cms.domain;

import java.util.Comparator;

public class SorterByPriority implements Comparator<Article> {
    @Override
    public int compare(Article o1, Article o2) {
        if(o1.getPriority()> o2.getPriority())
            return -1;
        else if(o1.getPriority()< o2.getPriority())
            return 1;
        else
            return 0;
    }
}

