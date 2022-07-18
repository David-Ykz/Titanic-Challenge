import java.util.Comparator;

class SortBySuccess implements Comparator<Generation> {
    public int compare(Generation a, Generation b)
    {
        return (int)(a.getDeviation() + 0.5) - (int)(b.getDeviation() + 0.5);
    }
}
  