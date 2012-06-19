import java.util.Comparator;
import java.util.Iterator;

/**
 * The SSet<T> interface is a simple interface that allows a class to implement
 * all the functionality of the (more complicated) SortedSet<T> interface.
 * 
 * This class is a modified version of the class created by Professor Morin.
 * 
 * @param <T>
 */
public interface SSet<T> extends Iterable<T> {
	/**
	 * @return the comparator used by this SSet
	 */
	public Comparator<? super T> comparator();

	/**
	 * @return the number of elements in this SSet
	 */
	public int size();

	/**
	 * Find the smallest element in the SSet that is greater than or equal to x.
	 * 
	 * @param x
	 * @return the smallest element in the SSet that is greater than or equal to
	 *         x or null if no such element exists
	 */
	public T find(T x);

	/**
	 * Find the smallest element in the SSet that is greater than or equal to x.
	 * If x is null, return the smallest element in the SSet
	 * 
	 * @param x
	 * @return the smallest element in the SSet that is greater than or equal to
	 *         x or null if no such element exists. If x is null then the
	 *         smallest element in the SSet
	 */
	public T findGE(T x);

	/**
	 * Find the largest element in the SSet that is less than to x. If x is
	 * null, return the largest element in the SSet
	 * 
	 * @param x
	 * @return the largest element in the SSet that is less than x. If x is null
	 *         then the smallest element in the SSet
	 */
	public T findLT(T x);

	/**
	 * Add the element x to the SSet
	 * 
	 * @param x
	 * @return true if the element was added or false if x was already in the
	 *         set
	 */
	public boolean add(T x);

	/**
	 * Remove the element x from the SSet
	 * 
	 * @param x
	 * @return true if x was removed and false if x was not removed (because x
	 *         was not present)
	 */
	public boolean remove(T x);

	/**
	 * Clear the SSet, removing all elements from the set
	 */
	public void clear();

	/**
	 * Return an iterator that iterates over the elements in sorted order,
	 * starting at the first element that is greater than or equal to x.
	 */
	public Iterator<T> iterator(T x);

	/**
	 * The following operations follow the mathematical definitions of a SET. For more information,
	 * look at the interface, defined in Java's API jva.util.SET
	 */

	/** Modifies the current SSet with the resulting union with sset.
	 *  Again, union here follows the definition of a set union. 
	 * @param sset
	 * @return boolean upon success
	 */
	public boolean unionWith(SSet<T> sset);
	
	/** Modifies the current SSet with the resulting intersection with sset.
	 * @param sset
	 * @return boolean upon success
	 */
	public boolean intersectWith(SSet<T> sset);
	
	/** Modifies the current SSet with the resulting difference with sset.
	 * Difference here means that all elements that belong to the current set, but
	 * do not belong to the input set, i.e. sset, are retained.
	 * @param sset
	 * @return boolean upon success
	 */
	public boolean differenceWith(SSet<T> sset);

	/** Checks if all elements in the current Set exist in the sset.
	 * @param sset
	 * @return boolean - true if it's a subset, false otherwise
	 */
	public boolean subsetOf(SSet<T> sset);

	/** Checks if x is a member of the current set.
	 * @param x
	 * @return boolean - true if x is a member, false otherwise
	 */
	public boolean belongsTo(T x);

	/**
	 * Convenience method. Create a string representing all elements, with useful information for testing/debugging.
	 * @return String
	 */
	public String toString(); 
}
