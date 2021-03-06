package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public final class ContentValues implements Parcelable {
    public static final Creator<ContentValues> CREATOR = new Creator<ContentValues>() {
        public ContentValues createFromParcel(Parcel in) {
            return new ContentValues(in.readHashMap(null), null);
        }

        public ContentValues[] newArray(int size) {
            return new ContentValues[size];
        }
    };
    public static final String TAG = "ContentValues";
    private HashMap<String, Object> mValues;

    /* synthetic */ ContentValues(HashMap x0, AnonymousClass1 x1) {
        this(x0);
    }

    public ContentValues() {
        this.mValues = new HashMap(8);
    }

    public ContentValues(int size) {
        this.mValues = new HashMap(size, 1.0f);
    }

    public ContentValues(ContentValues from) {
        this.mValues = new HashMap(from.mValues);
    }

    private ContentValues(HashMap<String, Object> values) {
        this.mValues = values;
    }

    public boolean equals(Object object) {
        if (object instanceof ContentValues) {
            return this.mValues.equals(((ContentValues) object).mValues);
        }
        return false;
    }

    public int hashCode() {
        return this.mValues.hashCode();
    }

    public void put(String key, String value) {
        this.mValues.put(key, value);
    }

    public void putAll(ContentValues other) {
        this.mValues.putAll(other.mValues);
    }

    public void put(String key, Byte value) {
        this.mValues.put(key, value);
    }

    public void put(String key, Short value) {
        this.mValues.put(key, value);
    }

    public void put(String key, Integer value) {
        this.mValues.put(key, value);
    }

    public void put(String key, Long value) {
        this.mValues.put(key, value);
    }

    public void put(String key, Float value) {
        this.mValues.put(key, value);
    }

    public void put(String key, Double value) {
        this.mValues.put(key, value);
    }

    public void put(String key, Boolean value) {
        this.mValues.put(key, value);
    }

    public void put(String key, byte[] value) {
        this.mValues.put(key, value);
    }

    public void putNull(String key) {
        this.mValues.put(key, null);
    }

    public int size() {
        return this.mValues.size();
    }

    public boolean isEmpty() {
        return this.mValues.isEmpty();
    }

    public void remove(String key) {
        this.mValues.remove(key);
    }

    public void clear() {
        this.mValues.clear();
    }

    public boolean containsKey(String key) {
        return this.mValues.containsKey(key);
    }

    public Object get(String key) {
        return this.mValues.get(key);
    }

    public String getAsString(String key) {
        Object value = this.mValues.get(key);
        return value != null ? value.toString() : null;
    }

    public Long getAsLong(String key) {
        Object value = this.mValues.get(key);
        Long l = null;
        if (value != null) {
            try {
                l = Long.valueOf(((Number) value).longValue());
            } catch (ClassCastException e) {
                if (value instanceof CharSequence) {
                    try {
                        return Long.valueOf(value.toString());
                    } catch (NumberFormatException e2) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Long value for ");
                        stringBuilder.append(value);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(key);
                        Log.e(str, stringBuilder.toString());
                        return null;
                    }
                }
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Cannot cast value for ");
                stringBuilder2.append(key);
                stringBuilder2.append(" to a Long: ");
                stringBuilder2.append(value);
                Log.e(str2, stringBuilder2.toString(), e);
                return null;
            }
        }
        return l;
    }

    public Integer getAsInteger(String key) {
        Object value = this.mValues.get(key);
        Integer num = null;
        if (value != null) {
            try {
                num = Integer.valueOf(((Number) value).intValue());
            } catch (ClassCastException e) {
                if (value instanceof CharSequence) {
                    try {
                        return Integer.valueOf(value.toString());
                    } catch (NumberFormatException e2) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Integer value for ");
                        stringBuilder.append(value);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(key);
                        Log.e(str, stringBuilder.toString());
                        return null;
                    }
                }
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Cannot cast value for ");
                stringBuilder2.append(key);
                stringBuilder2.append(" to a Integer: ");
                stringBuilder2.append(value);
                Log.e(str2, stringBuilder2.toString(), e);
                return null;
            }
        }
        return num;
    }

    public Short getAsShort(String key) {
        Object value = this.mValues.get(key);
        Short sh = null;
        if (value != null) {
            try {
                sh = Short.valueOf(((Number) value).shortValue());
            } catch (ClassCastException e) {
                if (value instanceof CharSequence) {
                    try {
                        return Short.valueOf(value.toString());
                    } catch (NumberFormatException e2) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Short value for ");
                        stringBuilder.append(value);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(key);
                        Log.e(str, stringBuilder.toString());
                        return null;
                    }
                }
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Cannot cast value for ");
                stringBuilder2.append(key);
                stringBuilder2.append(" to a Short: ");
                stringBuilder2.append(value);
                Log.e(str2, stringBuilder2.toString(), e);
                return null;
            }
        }
        return sh;
    }

    public Byte getAsByte(String key) {
        Object value = this.mValues.get(key);
        Byte b = null;
        if (value != null) {
            try {
                b = Byte.valueOf(((Number) value).byteValue());
            } catch (ClassCastException e) {
                if (value instanceof CharSequence) {
                    try {
                        return Byte.valueOf(value.toString());
                    } catch (NumberFormatException e2) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Byte value for ");
                        stringBuilder.append(value);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(key);
                        Log.e(str, stringBuilder.toString());
                        return null;
                    }
                }
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Cannot cast value for ");
                stringBuilder2.append(key);
                stringBuilder2.append(" to a Byte: ");
                stringBuilder2.append(value);
                Log.e(str2, stringBuilder2.toString(), e);
                return null;
            }
        }
        return b;
    }

    public Double getAsDouble(String key) {
        Object value = this.mValues.get(key);
        Double d = null;
        if (value != null) {
            try {
                d = Double.valueOf(((Number) value).doubleValue());
            } catch (ClassCastException e) {
                if (value instanceof CharSequence) {
                    try {
                        return Double.valueOf(value.toString());
                    } catch (NumberFormatException e2) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Double value for ");
                        stringBuilder.append(value);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(key);
                        Log.e(str, stringBuilder.toString());
                        return null;
                    }
                }
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Cannot cast value for ");
                stringBuilder2.append(key);
                stringBuilder2.append(" to a Double: ");
                stringBuilder2.append(value);
                Log.e(str2, stringBuilder2.toString(), e);
                return null;
            }
        }
        return d;
    }

    public Float getAsFloat(String key) {
        Object value = this.mValues.get(key);
        Float f = null;
        if (value != null) {
            try {
                f = Float.valueOf(((Number) value).floatValue());
            } catch (ClassCastException e) {
                if (value instanceof CharSequence) {
                    try {
                        return Float.valueOf(value.toString());
                    } catch (NumberFormatException e2) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Float value for ");
                        stringBuilder.append(value);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(key);
                        Log.e(str, stringBuilder.toString());
                        return null;
                    }
                }
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Cannot cast value for ");
                stringBuilder2.append(key);
                stringBuilder2.append(" to a Float: ");
                stringBuilder2.append(value);
                Log.e(str2, stringBuilder2.toString(), e);
                return null;
            }
        }
        return f;
    }

    public Boolean getAsBoolean(String key) {
        Object value = this.mValues.get(key);
        try {
            return (Boolean) value;
        } catch (ClassCastException e) {
            boolean z = true;
            if (value instanceof CharSequence) {
                if (!(Boolean.valueOf(value.toString()).booleanValue() || "1".equals(value))) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (value instanceof Number) {
                if (((Number) value).intValue() == 0) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot cast value for ");
                stringBuilder.append(key);
                stringBuilder.append(" to a Boolean: ");
                stringBuilder.append(value);
                Log.e(str, stringBuilder.toString(), e);
                return null;
            }
        }
    }

    public byte[] getAsByteArray(String key) {
        Object value = this.mValues.get(key);
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        return null;
    }

    public Set<Entry<String, Object>> valueSet() {
        return this.mValues.entrySet();
    }

    public Set<String> keySet() {
        return this.mValues.keySet();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeMap(this.mValues);
    }

    @Deprecated
    public void putStringArrayList(String key, ArrayList<String> value) {
        this.mValues.put(key, value);
    }

    @Deprecated
    public ArrayList<String> getStringArrayList(String key) {
        return (ArrayList) this.mValues.get(key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name : this.mValues.keySet()) {
            String value = getAsString(name);
            if (sb.length() > 0) {
                sb.append(" ");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(name);
            stringBuilder.append("=");
            stringBuilder.append(value);
            sb.append(stringBuilder.toString());
        }
        return sb.toString();
    }
}
