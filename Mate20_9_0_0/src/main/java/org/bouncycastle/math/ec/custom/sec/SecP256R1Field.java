package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;

public class SecP256R1Field {
    private static final long M = 4294967295L;
    static final int[] P = new int[]{-1, -1, -1, 0, 0, 0, 1, -1};
    private static final int P7 = -1;
    static final int[] PExt = new int[]{1, 0, 0, -2, -1, -1, -2, 1, -2, 1, -2, 1, 1, -2, 2, -2};
    private static final int PExt15s1 = Integer.MAX_VALUE;

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.add(iArr, iArr2, iArr3) != 0 || (iArr3[7] == -1 && Nat256.gte(iArr3, P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.add(16, iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= PExt15s1 && Nat.gte(16, iArr3, PExt))) {
            Nat.subFrom(16, PExt, iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (Nat.inc(8, iArr, iArr2) != 0 || (iArr2[7] == -1 && Nat256.gte(iArr2, P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (((long) iArr[0]) & 4294967295L) + 1;
        iArr[0] = (int) j;
        j >>= 32;
        if (j != 0) {
            j += ((long) iArr[1]) & 4294967295L;
            iArr[1] = (int) j;
            j = (j >> 32) + (((long) iArr[2]) & 4294967295L);
            iArr[2] = (int) j;
            j >>= 32;
        }
        j += (((long) iArr[3]) & 4294967295L) - 1;
        iArr[3] = (int) j;
        j >>= 32;
        if (j != 0) {
            j += ((long) iArr[4]) & 4294967295L;
            iArr[4] = (int) j;
            j = (j >> 32) + (((long) iArr[5]) & 4294967295L);
            iArr[5] = (int) j;
            j >>= 32;
        }
        j += (((long) iArr[6]) & 4294967295L) - 1;
        iArr[6] = (int) j;
        iArr[7] = (int) ((j >> 32) + ((4294967295L & ((long) iArr[7])) + 1));
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] fromBigInteger = Nat256.fromBigInteger(bigInteger);
        if (fromBigInteger[7] == -1 && Nat256.gte(fromBigInteger, P)) {
            Nat256.subFrom(P, fromBigInteger);
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            Nat.shiftDownBit(8, iArr, 0, iArr2);
        } else {
            Nat.shiftDownBit(8, iArr2, Nat256.add(iArr, P, iArr2));
        }
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = Nat256.createExt();
        Nat256.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.mulAddTo(iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= PExt15s1 && Nat.gte(16, iArr3, PExt))) {
            Nat.subFrom(16, PExt, iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (Nat256.isZero(iArr)) {
            Nat256.zero(iArr2);
        } else {
            Nat256.sub(P, iArr, iArr2);
        }
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        int[] iArr3 = iArr2;
        long j = ((long) iArr[9]) & 4294967295L;
        long j2 = ((long) iArr[10]) & 4294967295L;
        long j3 = ((long) iArr[11]) & 4294967295L;
        long j4 = ((long) iArr[12]) & 4294967295L;
        long j5 = ((long) iArr[13]) & 4294967295L;
        long j6 = ((long) iArr[14]) & 4294967295L;
        long j7 = ((long) iArr[15]) & 4294967295L;
        long j8 = (((long) iArr[8]) & 4294967295L) - 6;
        long j9 = j8 + j;
        j += j2;
        j2 = (j2 + j3) - j7;
        j3 += j4;
        j4 += j5;
        j5 += j6;
        long j10 = j6 + j7;
        j9 = j5 - j9;
        long j11 = j8;
        j8 = 0 + (((((long) iArr[0]) & 4294967295L) - j3) - j9);
        int[] iArr4 = iArr2;
        iArr4[0] = (int) j8;
        long j12 = j7;
        j8 = (j8 >> 32) + ((((((long) iArr[1]) & 4294967295L) + j) - j4) - j10);
        iArr4[1] = (int) j8;
        long j13 = (j8 >> 32) + (((((long) iArr[2]) & 4294967295L) + j2) - j5);
        iArr4[2] = (int) j13;
        j13 = (j13 >> 32) + ((((((long) iArr[3]) & 4294967295L) + (j3 << 1)) + j9) - j10);
        iArr4[3] = (int) j13;
        j13 = (j13 >> 32) + ((((((long) iArr[4]) & 4294967295L) + (j4 << 1)) + j6) - j);
        iArr4[4] = (int) j13;
        j13 = (j13 >> 32) + (((((long) iArr[5]) & 4294967295L) + (j5 << 1)) - j2);
        iArr4[5] = (int) j13;
        j13 = (j13 >> 32) + (((((long) iArr[6]) & 4294967295L) + (j10 << 1)) + j9);
        iArr4[6] = (int) j13;
        j13 = (j13 >> 32) + (((((((long) iArr[7]) & 4294967295L) + (j12 << 1)) + j11) - j2) - j4);
        iArr4[7] = (int) j13;
        reduce32((int) ((j13 >> 32) + 6), iArr4);
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            j = ((long) i) & 4294967295L;
            long j2 = ((((long) iArr[0]) & 4294967295L) + j) + 0;
            iArr[0] = (int) j2;
            j2 >>= 32;
            if (j2 != 0) {
                j2 += ((long) iArr[1]) & 4294967295L;
                iArr[1] = (int) j2;
                j2 = (j2 >> 32) + (((long) iArr[2]) & 4294967295L);
                iArr[2] = (int) j2;
                j2 >>= 32;
            }
            j2 += (((long) iArr[3]) & 4294967295L) - j;
            iArr[3] = (int) j2;
            j2 >>= 32;
            if (j2 != 0) {
                j2 += ((long) iArr[4]) & 4294967295L;
                iArr[4] = (int) j2;
                j2 = (j2 >> 32) + (((long) iArr[5]) & 4294967295L);
                iArr[5] = (int) j2;
                j2 >>= 32;
            }
            j2 += (((long) iArr[6]) & 4294967295L) - j;
            iArr[6] = (int) j2;
            j2 = (j2 >> 32) + ((4294967295L & ((long) iArr[7])) + j);
            iArr[7] = (int) j2;
            j = j2 >> 32;
        } else {
            j = 0;
        }
        if (j != 0 || (iArr[7] == -1 && Nat256.gte(iArr, P))) {
            addPInvTo(iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = Nat256.createExt();
        Nat256.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] createExt = Nat256.createExt();
        Nat256.square(iArr, createExt);
        while (true) {
            reduce(createExt, iArr2);
            i--;
            if (i > 0) {
                Nat256.square(iArr2, createExt);
            } else {
                return;
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j = (((long) iArr[0]) & 4294967295L) - 1;
        iArr[0] = (int) j;
        j >>= 32;
        if (j != 0) {
            j += ((long) iArr[1]) & 4294967295L;
            iArr[1] = (int) j;
            j = (j >> 32) + (((long) iArr[2]) & 4294967295L);
            iArr[2] = (int) j;
            j >>= 32;
        }
        j += (((long) iArr[3]) & 4294967295L) + 1;
        iArr[3] = (int) j;
        j >>= 32;
        if (j != 0) {
            j += ((long) iArr[4]) & 4294967295L;
            iArr[4] = (int) j;
            j = (j >> 32) + (((long) iArr[5]) & 4294967295L);
            iArr[5] = (int) j;
            j >>= 32;
        }
        j += (((long) iArr[6]) & 4294967295L) + 1;
        iArr[6] = (int) j;
        iArr[7] = (int) ((j >> 32) + ((4294967295L & ((long) iArr[7])) - 1));
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.sub(iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.sub(16, iArr, iArr2, iArr3) != 0) {
            Nat.addTo(16, PExt, iArr3);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (Nat.shiftUpBit(8, iArr, 0, iArr2) != 0 || (iArr2[7] == -1 && Nat256.gte(iArr2, P))) {
            addPInvTo(iArr2);
        }
    }
}
