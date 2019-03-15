import io.gridgo.utils.UnsafeUtils;
import sun.misc.Unsafe;

public class TestUnsafeDirectMemory {

    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        long size = 1024l;
        long addr = unsafe.allocateMemory(size);
        System.out.println(addr);
    }
}
