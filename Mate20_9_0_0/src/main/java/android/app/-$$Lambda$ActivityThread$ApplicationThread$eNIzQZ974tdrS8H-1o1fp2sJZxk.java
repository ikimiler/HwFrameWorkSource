package android.app;

import com.android.internal.util.function.TriConsumer;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$ActivityThread$ApplicationThread$eNIzQZ974tdrS8H-1o1fp2sJZxk implements TriConsumer {
    public static final /* synthetic */ -$$Lambda$ActivityThread$ApplicationThread$eNIzQZ974tdrS8H-1o1fp2sJZxk INSTANCE = new -$$Lambda$ActivityThread$ApplicationThread$eNIzQZ974tdrS8H-1o1fp2sJZxk();

    private /* synthetic */ -$$Lambda$ActivityThread$ApplicationThread$eNIzQZ974tdrS8H-1o1fp2sJZxk() {
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        ((ActivityThread) ((ActivityThread) obj)).handleTrimMemory(((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
    }
}
