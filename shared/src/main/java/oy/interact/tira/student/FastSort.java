package oy.interact.tira.student;

import java.util.Comparator;

import oy.interact.tira.util.NotYetImplementedException;

public class FastSort {

	private FastSort() {
		// empty
	}

	////////////////////////////////////
   // FastSort sorting to natural order
   ////////////////////////////////////

   public static <E extends Comparable<E>> void sort(E[] array, int begin, int end) {
      throw new NotYetImplementedException("FastSort not yet implemented");
   }


   ///////////////////////////////////////////////////////
   // FastSort sorting to order determined by the Comparator
   ///////////////////////////////////////////////////////

   public static <E> void sort(E[] array, Comparator<E> comparator) {
      throw new NotYetImplementedException("FastSort not yet implemented");
   }

   public static <E> void sort(E[] array, int begin, int end, Comparator<E> comparator) {
      throw new NotYetImplementedException("FastSort not yet implemented");
   }

}
