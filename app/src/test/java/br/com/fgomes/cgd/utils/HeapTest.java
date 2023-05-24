package br.com.fgomes.cgd.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HeapTest {
    @Test
    public void heapBlank(){
        Heap heap = new Heap();
        assertEquals(0, heap.isBlank());
        assertEquals(0, heap.size());
    }
}
