import java.util.Comparator;

class SortBySuccess implements Comparator<Generation> {
    public int compare(Generation a, Generation b)
    {
        return a.getNumSuccesses() - b.getNumSuccesses();
    }
}
  