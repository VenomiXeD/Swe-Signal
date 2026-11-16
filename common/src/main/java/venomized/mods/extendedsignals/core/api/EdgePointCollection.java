package venomized.mods.extendedsignals.core.api;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EdgePointCollection<P> implements Iterable<EdgePointSnapshot<P>> {
    private final List<EdgePointSnapshot<P>> backingList;

    public EdgePointCollection(Collection<EdgePointSnapshot<P>> edgePoints) {
        backingList = new ReferenceArrayList<>(edgePoints);
    }

    public EdgePointCollection(List<EdgePointSnapshot<P>> edgePoints) {
        backingList = new ReferenceArrayList<>(edgePoints);
    }

    /**
     * @return
     */
    @Override
    public @NotNull Iterator<EdgePointSnapshot<P>> iterator() {
        return backingList.iterator();
    }

    /**
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(Consumer<? super EdgePointSnapshot<P>> action) {
        backingList.forEach(action);
    }

    public <C extends IExtendedEdgePoint> EdgePointCollection<C> ofType(Class<C> clazz) {
        return new EdgePointCollection<>(
                backingList
                        .stream().filter(p -> clazz.isInstance(p.point()))
                        .map(p -> new EdgePointSnapshot<>(clazz.cast(p.point()), p.distance(), p.primary()))
                        .toList()
        );
    }

    public Stream<EdgePointSnapshot<P>> stream() {
        return backingList.stream();
    }
}
