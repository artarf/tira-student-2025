package oy.interact.tira.task_03;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;

import java.awt.Graphics;

import oy.interact.tira.student.Partition;
import oy.interact.tira.student.ArrayUtils;

public class Shapes {

    private Shape [] shapeArray = null;
    private int count = 0;
    private ShapesListener listener = null;

    private Timer removalAnimationTimer = null;
    private int removalIndex = 0;
    private int downShiftingIndex = 0;
    private enum WhichRemoval {
        SLOW,
        MEDIUM,
        FAST
    }
    private WhichRemoval whichRemoval = WhichRemoval.SLOW;
    private enum Operation {
        REMOVING,
        DOWNSHIFTING,
        PARTITIONING_FIND_FIRST_TO_SWAP,
        PARTITIONING_FIND_SECOND_TO_SWAP
    }
    private Operation operation = Operation.REMOVING;
    private Predicate<Shape> predicate;
    private int partitionClearedCount = 0;
    private int operationCount = 0;

    public Shapes() throws ShapeException {
        this(16);
    }

    public Shapes(int size) throws ShapeException {
        if (size < 2) {
            throw new ShapeException("Shapes capacity must be >= 2");
        }
        shapeArray = new Shape[size];
    }

    public Shapes(final Shapes another) {
        this.shapeArray = new Shape[another.shapeArray.length];
        this.count = another.count;
        this.shapeArray = ArrayUtils.copyOf(another.shapeArray);
    }

    public void setListener(ShapesListener listener) {
        this.listener = listener;
        cancelTimer();
    }

    public int count() {
        return count;
    }

    public int capacity() {
        return shapeArray.length;
    }

    public void add(final Shape shape) throws NullPointerException, ShapeException {
        if (null == shape) {
            throw new NullPointerException("Cannot add null shapes");
        }
        if (count >= shapeArray.length - 1) {
            try {
                reallocate();
            } catch (Exception e) {
                throw new ShapeException("Could not reallocate shape array");
            }
        }
        shapeArray[count++] = shape;
        if (null != listener) {
            listener.shapesChanged();
        }
    }

    public void removeAll() {
        this.count = 0;
        shapeArray = new Shape[16];
        cancelTimer();
    }

    public void remove(final Shape shape) throws NullPointerException, InterruptedException {
        if (null == shape)
            throw new NullPointerException("Cannot remove null shapes");
        boolean found = false;
        int index = 0;
        for (; index < shapeArray.length && shapeArray[index] != null; index++) {
            if (shape.id == shapeArray[index].id) {
                shapeArray[index] = null;
                found = true;
                count--;
                break;
            }
        }
        if (found) {
            if (null != listener) {
                listener.shapesChanged();
            }
            moveElementsStepDownFrom(index + 1);
        }
    }

    public void removeAndShiftDown(final int index) throws ArrayIndexOutOfBoundsException, InterruptedException {
        if (index < 0 || index > count - 1) {
            throw new ArrayIndexOutOfBoundsException("Attempting to access shapesArray out of bounds.");
        }
        shapeArray[index] = null;
        if (null != listener) {
            listener.shapesChanged();
        }
        moveElementsStepDownFrom(index + 1);
        count--;
    }

    public void setSelected(int index, boolean selectionState) {
        if (index < 0 || index > count - 1) {
            throw new ArrayIndexOutOfBoundsException("Attempting to access shapesArray out of bounds.");
        }
        shapeArray[index].setSelected(selectionState);
        if (null != listener) {
            listener.shapesChanged();
        }
    }

    public void setSelected(final int[] fromArray, boolean selectionState) {
        for (int index : fromArray) {
            setSelected(index, selectionState);
        }
        if (null != listener) {
            listener.shapesChanged();
        }
    }

    public void select(Predicate<Shape> toCompare) {
        int index = 0;
        for (Shape shape : shapeArray) {
            if (shape == null) {
                break;
            }
            if (toCompare.test(shape)) {
                shapeArray[index].setSelected(true);
            }
            index++;
        }
    }

    public String [] getRemovalStatus() {
        String [] status = new String[3];
        status[0] = String.format("%s", whichRemoval.name());
        status[1] = String.format("Index being removed: %d, downshifting at %d", removalIndex, downShiftingIndex);
        status[2] = String.format("Operations required: %d", operationCount);
        return status;
    }

    public void removeNotSelected1() throws ArrayIndexOutOfBoundsException, InterruptedException {
        // 1st bad way, crashes since index out of bounds.
        int endIndex = count();
        for (int index = 0; index < endIndex; index++) {
            if (!shapeArray[index].isSelected()) {
                // Remove moves elements after the removed down by one index, filling
                // the empty gap that otherwise would have a null in it.
                removeAndShiftDown(index);
            }
        }
    }

    public void removeNotSelected2() throws ArrayIndexOutOfBoundsException, InterruptedException {
        // 2nd bad (slow) way
        if (listener != null) {
            removalIndex = 0;
            downShiftingIndex = 0;
            whichRemoval = WhichRemoval.SLOW;
            operation = Operation.REMOVING;
            operationCount = 0;
            scheduleTimer();
        } else {
            for (int index = 0; index < count; index++) {
                // This is not buggy but slow, since it does the removal
                // and shifting elements down for _each_ element removed.
                if (!shapeArray[index].isSelected()) {
                    removeAndShiftDown(index);
                    index -= 1; // since for loop increases index by one
                }
            }
        }
    }

    public void removeNotSelected3() throws ArrayIndexOutOfBoundsException, InterruptedException {
        if (listener != null) {
            removalIndex = count - 1;
            downShiftingIndex = 0;
            whichRemoval = WhichRemoval.MEDIUM;
            operation = Operation.REMOVING;
            operationCount = 0;
            scheduleTimer();
        } else {
            // 3rd works, but...
            for (int index = count - 1; index >= 0; index--) {
                // This avoids moving elements after removed to be shifted down,
                // since we remove from the end towards the beginning, so we 
                // are not moving the same elements so many times.
                if (!shapeArray[index].isSelected()) {
                    removeAndShiftDown(index);
                }
            }
        }
    }

    public void removeNotSelected4() {
        // 4th fast way
        if (listener != null) {
            whichRemoval = WhichRemoval.FAST;
            // Use removalIndex as the index, downShiftingIndex as nextIndex.
            removalIndex = -1;
            downShiftingIndex = -1;
            partitionClearedCount = 0;
            predicate = shape -> shape.isSelected();
            operation = Operation.PARTITIONING_FIND_FIRST_TO_SWAP;
            operationCount = 0;
            scheduleTimer();
        } else {
            // STUDENT TODO: change this following the task instructions, step by step!
            // int firstNotSelectedIndex = Partition.byRule(shapeArray, count, shape -> shape.isSelected());
            // removeFrom(firstNotSelectedIndex);
            // shapeArray = Partition.filter(shapeArray, count, shape -> shape.isSelected());
        }
    }

    /**
     * Remove all elements from the shapeArray from the counter forward
     * by putting null to the array indices.
     * 
     * Implementation must update the count of elements left in Shapes.
     * 
     * @param index The index from which (inclusive) to start nulling elements.
     */
    public void removeFrom(int index) {
        // STUDENT TODO implement this following the instructions.
        // When ever you null an object in the loop, do also this:
        // if (null != listener) {
        //     listener.shapesChanged();
        // }
    }


    private void scheduleTimer() {
        removalAnimationTimer = new Timer();
        removalAnimationTimer.schedule(new TimerTask() {
			@Override
			public void run() {
                switch (whichRemoval) {
                    case FAST:
                        partitionVeryFastly();
                        break;
                    case MEDIUM:
                        removeOneMediumFastly();
                        break;
                    case SLOW:
                        removeOneSlowly();
                        break;
                    default:
                        break;
                    
                }
			}			
		}, 750);        
    }

    private void cancelTimer() {
        if (removalAnimationTimer != null) {
            removalAnimationTimer.cancel();
            removalAnimationTimer = null;
        }
    }

    private void removeOneSlowly() {
        System.out.println("removeOneSlowly");
        if (removalIndex < shapeArray.length) {
            switch (operation) {
                case REMOVING:
                    if (null == shapeArray[removalIndex]) {
                        System.out.println("removeOneSlowly: null met, finishing.");
                        cancelTimer();
                        return;
                    }
                    if (!shapeArray[removalIndex].isSelected()) {
                        shapeArray[removalIndex] = null;
                        operationCount++;
                        System.out.println("removeOneSlowly: removed one");
                        if (null != listener) {
                            listener.shapesChanged();
                        }
                        downShiftingIndex = removalIndex + 1;
                        operation = Operation.DOWNSHIFTING;
                    } else {
                        removalIndex++;
                        System.out.println("removeOneSlowly: selected, not removing");
                    }
                    break;
                case DOWNSHIFTING:
                    System.out.println("removeOneSlowly: downshifting");
                    shapeArray[downShiftingIndex - 1] = shapeArray[downShiftingIndex];
                    shapeArray[downShiftingIndex] = null;
                    operationCount += 2;
                    if (null != listener) {
                        listener.shapesChanged();
                    }
                    downShiftingIndex++;
                    if (downShiftingIndex >= count) {
                        System.out.println("downshifted all, back to removing");
                        operation = Operation.REMOVING;
                        count--;
                    }
                    break;
                default:
                    break;
                
            }
            scheduleTimer();
        } else {
            cancelTimer();
        }
    }        

    private void removeOneMediumFastly() {
        System.out.println("removeOneMediumFastly");
        if (removalIndex >= 0) {
            switch (operation) {
                case REMOVING:
                    // if (null == shapeArray[removalIndex]) {
                    //     System.out.println("removeOneMediumFastly: null met, finishing.");
                    //     cancelTimer();
                    //     return;
                    // }
                    if (!shapeArray[removalIndex].isSelected()) {
                        shapeArray[removalIndex] = null;
                        operationCount++;
                        System.out.println("removeOneMediumFastly: removed one");
                        if (null != listener) {
                            listener.shapesChanged();
                        }
                        downShiftingIndex = removalIndex + 1;
                        operation = Operation.DOWNSHIFTING;
                    } else {
                        while (removalIndex >= 0 && shapeArray[removalIndex].isSelected()) {
                            removalIndex--;
                        }
                        System.out.println("removeOneMediumFastly: selected, not removing");
                    }
                    break;
                case DOWNSHIFTING:
                    System.out.println("removeOneMediumFastly: downshifting");
                    shapeArray[downShiftingIndex - 1] = shapeArray[downShiftingIndex];
                    shapeArray[downShiftingIndex] = null;
                    operationCount += 2;
                    if (null != listener) {
                        listener.shapesChanged();
                    }
                    downShiftingIndex++;
                    if (downShiftingIndex > count) {
                        System.out.println("downshifted all, back to removing");
                        operation = Operation.REMOVING;
                        removalIndex--;
                        count--;
                    }
                    break;
                default:
                    break;                
            }
            scheduleTimer();
        } else {
            cancelTimer();
        }
    }

    private void partitionVeryFastly() {
        System.out.println("partitionVeryFastly");
        switch (operation) {
            case DOWNSHIFTING:
                break;
            case PARTITIONING_FIND_FIRST_TO_SWAP:
            System.out.println("partitionVeryFastly: finding first to swap");
                for (removalIndex = 0; removalIndex < count; removalIndex++) {
                    if (!predicate.test(shapeArray[removalIndex])) {
                        downShiftingIndex = removalIndex + 1;
                        break;
                    }
                }
                if (null != listener) {
                    listener.shapesChanged();
                }
                if (removalIndex < count) {
                    operation = Operation.PARTITIONING_FIND_SECOND_TO_SWAP;
                    System.out.println("partitionVeryFastly: finding the other to swap with");
                    scheduleTimer();
                } else {
                    System.out.println("partitionVeryFastly: nothing to swap, ending");
                    cancelTimer();
                }
                break;
            case PARTITIONING_FIND_SECOND_TO_SWAP:
                if (downShiftingIndex < count) {
                    // If the element at next index conforms to the rule, swap
                    // it with the not conforming element in position index.
                    if (predicate.test(shapeArray[downShiftingIndex])) {
                        System.out.println("partitionVeryFastly: swapping");
                        ArrayUtils.swap(shapeArray, removalIndex, downShiftingIndex);
                        operationCount += 3;
                        // If swapping was done, add one to index since now it has a conforming element.
                        removalIndex++;
                    }
                    downShiftingIndex++;
                } else {
                    System.out.println("partitionVeryFastly: starting to remove suffix of array");
                    operation = Operation.REMOVING;
                }
                if (null != listener) {
                    listener.shapesChanged();
                }
                scheduleTimer();
                break;
            case REMOVING:
            System.out.println("partitionVeryFastly: removing suffix elements");
                if (removalIndex < count) {
                    shapeArray[removalIndex++] = null;
                    operationCount++;
                    partitionClearedCount++;
                    scheduleTimer();
                } else {
                    count -= partitionClearedCount;
                    partitionClearedCount = 0;
                    System.out.println("partitionVeryFastly: suffix cleared");
                    cancelTimer();
                }
                if (null != listener) {
                    listener.shapesChanged();
                }
                break;
            default:
                break;            
        }

    }

    public Shape getShape(final int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > count - 1) {
            throw new ArrayIndexOutOfBoundsException("Attempting to access shapesArray out of bounds.");
        }
        return shapeArray[index];
    }

    private void moveElementsStepDownFrom(final int index) {
        if (index == 0 || index >= shapeArray.length) {
            return;
        }

        int counter = index;
        for (; counter < shapeArray.length; counter++) {
            if (shapeArray[counter] == null) {
                break;
            }
            shapeArray[counter - 1] = shapeArray[counter];
            shapeArray[counter] = null;
        }
    }

    public void draw(Graphics graphics) {
        for (Shape shape : shapeArray) {
            if (null == shape) {
                break;
            }
            shape.draw(graphics);
        }
    }

    private void reallocate() throws NegativeArraySizeException, NullPointerException {
        int newCapacity = shapeArray.length * 2;
        this.shapeArray = ArrayUtils.copyOf(shapeArray, newCapacity);
    }

    public void invariant() throws ShapeException {
        // Do not check invariants when animating (and we have a listener).
        if (listener != null) {
            return;
        }
        int index = 0;
        for (; index < count; index++) {
            if (shapeArray[index] == null) {
                throw new ShapeException("Shapes must not contain null shapes in 0..<count");
            }
            if (!shapeArray[index].isSelected) {
                throw new ShapeException("No shape should be selected after removing them from the Shapes array");
            }
        }
        for (; index < shapeArray.length; index++) {
            if (shapeArray[index] != null) {
                throw new ShapeException("Shapes must contain only null shapes in count..<length");
            }
        }
    }

}
