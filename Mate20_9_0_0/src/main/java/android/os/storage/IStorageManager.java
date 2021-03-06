package android.os.storage;

import android.content.pm.IPackageMoveObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IVoldTaskListener;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.os.AppFuseMount;

public interface IStorageManager extends IInterface {

    public static abstract class Stub extends Binder implements IStorageManager {
        private static final String DESCRIPTOR = "android.os.storage.IStorageManager";
        static final int TRANSACTION_abortIdleMaintenance = 81;
        static final int TRANSACTION_addUserKeyAuth = 71;
        static final int TRANSACTION_allocateBytes = 79;
        static final int TRANSACTION_benchmark = 60;
        static final int TRANSACTION_changeEncryptionPassword = 29;
        static final int TRANSACTION_clearPassword = 38;
        static final int TRANSACTION_createUserKey = 62;
        static final int TRANSACTION_createUserKeyISec = 601;
        static final int TRANSACTION_decryptStorage = 27;
        static final int TRANSACTION_destroyUserKey = 63;
        static final int TRANSACTION_destroyUserKeyISec = 602;
        static final int TRANSACTION_destroyUserStorage = 68;
        static final int TRANSACTION_encryptStorage = 28;
        static final int TRANSACTION_fixateNewestUserKeyAuth = 72;
        static final int TRANSACTION_forgetAllVolumes = 57;
        static final int TRANSACTION_forgetVolume = 56;
        static final int TRANSACTION_format = 50;
        static final int TRANSACTION_fstrim = 73;
        static final int TRANSACTION_getAllocatableBytes = 78;
        static final int TRANSACTION_getCacheQuotaBytes = 76;
        static final int TRANSACTION_getCacheSizeBytes = 77;
        static final int TRANSACTION_getDisks = 45;
        static final int TRANSACTION_getEncryptionState = 32;
        static final int TRANSACTION_getField = 40;
        static final int TRANSACTION_getKeyDesc = 507;
        static final int TRANSACTION_getMaxTimeCost = 1006;
        static final int TRANSACTION_getMinTimeCost = 1007;
        static final int TRANSACTION_getMountedObbPath = 25;
        static final int TRANSACTION_getNotificationLevel = 1004;
        static final int TRANSACTION_getPassword = 37;
        static final int TRANSACTION_getPasswordType = 36;
        static final int TRANSACTION_getPercentComplete = 1008;
        static final int TRANSACTION_getPreLoadPolicyFlag = 505;
        static final int TRANSACTION_getPrimaryStorageUuid = 58;
        static final int TRANSACTION_getUndiscardInfo = 1005;
        static final int TRANSACTION_getVolumeList = 30;
        static final int TRANSACTION_getVolumeRecords = 47;
        static final int TRANSACTION_getVolumes = 46;
        static final int TRANSACTION_isConvertibleToFBE = 69;
        static final int TRANSACTION_isObbMounted = 24;
        static final int TRANSACTION_isSecure = 1001;
        static final int TRANSACTION_isUserKeyUnlocked = 66;
        static final int TRANSACTION_lastMaintenance = 42;
        static final int TRANSACTION_lockUserKey = 65;
        static final int TRANSACTION_lockUserKeyISec = 502;
        static final int TRANSACTION_lockUserScreenISec = 504;
        static final int TRANSACTION_mkdirs = 35;
        static final int TRANSACTION_mount = 48;
        static final int TRANSACTION_mountObb = 22;
        static final int TRANSACTION_mountProxyFileDescriptorBridge = 74;
        static final int TRANSACTION_openProxyFileDescriptor = 75;
        static final int TRANSACTION_partitionMixed = 53;
        static final int TRANSACTION_partitionPrivate = 52;
        static final int TRANSACTION_partitionPublic = 51;
        static final int TRANSACTION_prepareUserStorage = 67;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_runIdleMaintenance = 80;
        static final int TRANSACTION_runMaintenance = 43;
        static final int TRANSACTION_setDebugFlags = 61;
        static final int TRANSACTION_setField = 39;
        static final int TRANSACTION_setPrimaryStorageUuid = 59;
        static final int TRANSACTION_setScreenStateFlag = 506;
        static final int TRANSACTION_setVolumeNickname = 54;
        static final int TRANSACTION_setVolumeUserFlags = 55;
        static final int TRANSACTION_shutdown = 20;
        static final int TRANSACTION_startClean = 1002;
        static final int TRANSACTION_stopClean = 1003;
        static final int TRANSACTION_unlockUserKey = 64;
        static final int TRANSACTION_unlockUserKeyISec = 501;
        static final int TRANSACTION_unlockUserScreenISec = 503;
        static final int TRANSACTION_unmount = 49;
        static final int TRANSACTION_unmountObb = 23;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_verifyEncryptionPassword = 33;

        private static class Proxy implements IStorageManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void registerListener(IStorageEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterListener(IStorageEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdown(IStorageShutdownObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void mountObb(String rawPath, String canonicalPath, String key, IObbActionListener token, int nonce) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    _data.writeString(canonicalPath);
                    _data.writeString(key);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(nonce);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unmountObb(String rawPath, boolean force, IObbActionListener token, int nonce) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    _data.writeInt(force);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(nonce);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isObbMounted(String rawPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    boolean z = false;
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getMountedObbPath(String rawPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int decryptStorage(String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int encryptStorage(int type, String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(password);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int changeEncryptionPassword(int type, String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(password);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StorageVolume[] getVolumeList(int uid, String packageName, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    StorageVolume[] _result = (StorageVolume[]) _reply.createTypedArray(StorageVolume.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getEncryptionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int verifyEncryptionPassword(String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void mkdirs(String callingPkg, String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(path);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearPassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void setField(String field, String contents) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(field);
                    _data.writeString(contents);
                    this.mRemote.transact(39, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public String getField(String field) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(field);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long lastMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void runMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DiskInfo[] getDisks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    DiskInfo[] _result = (DiskInfo[]) _reply.createTypedArray(DiskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VolumeInfo[] getVolumes(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    VolumeInfo[] _result = (VolumeInfo[]) _reply.createTypedArray(VolumeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VolumeRecord[] getVolumeRecords(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    VolumeRecord[] _result = (VolumeRecord[]) _reply.createTypedArray(VolumeRecord.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void mount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unmount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void format(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void partitionPublic(String diskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void partitionPrivate(String diskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void partitionMixed(String diskId, int ratio) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    _data.writeInt(ratio);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeNickname(String fsUuid, String nickname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    _data.writeString(nickname);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeUserFlags(String fsUuid, int flags, int mask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    _data.writeInt(flags);
                    _data.writeInt(mask);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forgetVolume(String fsUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forgetAllVolumes() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPrimaryStorageUuid() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrimaryStorageUuid(String volumeUuid, IPackageMoveObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDebugFlags(int flags, int mask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(mask);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createUserKey(int userId, int serialNumber, boolean ephemeral) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(ephemeral);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyUserKey(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unlockUserKey(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void lockUserKey(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUserKeyUnlocked(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareUserStorage(String volumeUuid, int userId, int serialNumber, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(flags);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyUserStorage(String volumeUuid, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConvertibleToFBE() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addUserKeyAuth(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fixateNewestUserKeyAuth(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fstrim(int flags, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    AppFuseMount _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (AppFuseMount) AppFuseMount.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openProxyFileDescriptor(int mountPointId, int fileId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    ParcelFileDescriptor _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mountPointId);
                    _data.writeInt(fileId);
                    _data.writeInt(mode);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCacheQuotaBytes(String volumeUuid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCacheSizeBytes(String volumeUuid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getAllocatableBytes(String volumeUuid, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void allocateBytes(String volumeUuid, long bytes, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(bytes);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void runIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abortIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSecure() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(1001, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int startClean() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1002, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int stopClean() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1003, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNotificationLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1004, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUndiscardInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1005, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMaxTimeCost() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1006, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMinTimeCost() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1007, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPercentComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1008, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unlockUserKeyISec(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    this.mRemote.transact(501, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void lockUserKeyISec(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(502, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unlockUserScreenISec(int userId, int serialNumber, byte[] token, byte[] secret, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    _data.writeInt(type);
                    this.mRemote.transact(503, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void lockUserScreenISec(int userId, int serialNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    this.mRemote.transact(504, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPreLoadPolicyFlag(int userId, int serialNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    this.mRemote.transact(505, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setScreenStateFlag(int userId, int serialNumber, int flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(flag);
                    boolean z = false;
                    this.mRemote.transact(506, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getKeyDesc(int userId, int serialNumber, int sdpClass) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(sdpClass);
                    this.mRemote.transact(Stub.TRANSACTION_getKeyDesc, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createUserKeyISec(int userId, int serialNumber, boolean ephemeral) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(ephemeral);
                    this.mRemote.transact(601, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyUserKeyISec(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(602, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStorageManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IStorageManager)) {
                return new Proxy(obj);
            }
            return (IStorageManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Parcel parcel = data;
            Parcel parcel2 = reply;
            String descriptor = DESCRIPTOR;
            switch (code) {
                case 1:
                    parcel.enforceInterface(descriptor);
                    registerListener(android.os.storage.IStorageEventListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(descriptor);
                    unregisterListener(android.os.storage.IStorageEventListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                default:
                    boolean _arg2 = false;
                    String _arg0;
                    boolean _result;
                    switch (code) {
                        case 22:
                            parcel.enforceInterface(descriptor);
                            mountObb(data.readString(), data.readString(), data.readString(), android.os.storage.IObbActionListener.Stub.asInterface(data.readStrongBinder()), data.readInt());
                            reply.writeNoException();
                            return true;
                        case 23:
                            parcel.enforceInterface(descriptor);
                            _arg0 = data.readString();
                            if (data.readInt() != 0) {
                                _arg2 = true;
                            }
                            unmountObb(_arg0, _arg2, android.os.storage.IObbActionListener.Stub.asInterface(data.readStrongBinder()), data.readInt());
                            reply.writeNoException();
                            return true;
                        case 24:
                            parcel.enforceInterface(descriptor);
                            _result = isObbMounted(data.readString());
                            reply.writeNoException();
                            parcel2.writeInt(_result);
                            return true;
                        case 25:
                            parcel.enforceInterface(descriptor);
                            _arg0 = getMountedObbPath(data.readString());
                            reply.writeNoException();
                            parcel2.writeString(_arg0);
                            return true;
                        default:
                            int _result2;
                            int _result3;
                            switch (code) {
                                case 27:
                                    parcel.enforceInterface(descriptor);
                                    _result2 = decryptStorage(data.readString());
                                    reply.writeNoException();
                                    parcel2.writeInt(_result2);
                                    return true;
                                case 28:
                                    parcel.enforceInterface(descriptor);
                                    _result3 = encryptStorage(data.readInt(), data.readString());
                                    reply.writeNoException();
                                    parcel2.writeInt(_result3);
                                    return true;
                                case 29:
                                    parcel.enforceInterface(descriptor);
                                    _result3 = changeEncryptionPassword(data.readInt(), data.readString());
                                    reply.writeNoException();
                                    parcel2.writeInt(_result3);
                                    return true;
                                case 30:
                                    parcel.enforceInterface(descriptor);
                                    StorageVolume[] _result4 = getVolumeList(data.readInt(), data.readString(), data.readInt());
                                    reply.writeNoException();
                                    parcel2.writeTypedArray(_result4, 1);
                                    return true;
                                default:
                                    int _result5;
                                    switch (code) {
                                        case 32:
                                            parcel.enforceInterface(descriptor);
                                            _result5 = getEncryptionState();
                                            reply.writeNoException();
                                            parcel2.writeInt(_result5);
                                            return true;
                                        case 33:
                                            parcel.enforceInterface(descriptor);
                                            _result2 = verifyEncryptionPassword(data.readString());
                                            reply.writeNoException();
                                            parcel2.writeInt(_result2);
                                            return true;
                                        default:
                                            String _result6;
                                            switch (code) {
                                                case 35:
                                                    parcel.enforceInterface(descriptor);
                                                    mkdirs(data.readString(), data.readString());
                                                    reply.writeNoException();
                                                    return true;
                                                case 36:
                                                    parcel.enforceInterface(descriptor);
                                                    _result5 = getPasswordType();
                                                    reply.writeNoException();
                                                    parcel2.writeInt(_result5);
                                                    return true;
                                                case 37:
                                                    parcel.enforceInterface(descriptor);
                                                    _result6 = getPassword();
                                                    reply.writeNoException();
                                                    parcel2.writeString(_result6);
                                                    return true;
                                                case 38:
                                                    parcel.enforceInterface(descriptor);
                                                    clearPassword();
                                                    return true;
                                                case 39:
                                                    parcel.enforceInterface(descriptor);
                                                    setField(data.readString(), data.readString());
                                                    return true;
                                                case 40:
                                                    parcel.enforceInterface(descriptor);
                                                    _arg0 = getField(data.readString());
                                                    reply.writeNoException();
                                                    parcel2.writeString(_arg0);
                                                    return true;
                                                default:
                                                    switch (code) {
                                                        case 42:
                                                            parcel.enforceInterface(descriptor);
                                                            long _result7 = lastMaintenance();
                                                            reply.writeNoException();
                                                            parcel2.writeLong(_result7);
                                                            return true;
                                                        case 43:
                                                            parcel.enforceInterface(descriptor);
                                                            runMaintenance();
                                                            reply.writeNoException();
                                                            return true;
                                                        default:
                                                            switch (code) {
                                                                case 45:
                                                                    parcel.enforceInterface(descriptor);
                                                                    DiskInfo[] _result8 = getDisks();
                                                                    reply.writeNoException();
                                                                    parcel2.writeTypedArray(_result8, 1);
                                                                    return true;
                                                                case 46:
                                                                    parcel.enforceInterface(descriptor);
                                                                    VolumeInfo[] _result9 = getVolumes(data.readInt());
                                                                    reply.writeNoException();
                                                                    parcel2.writeTypedArray(_result9, 1);
                                                                    return true;
                                                                case 47:
                                                                    parcel.enforceInterface(descriptor);
                                                                    VolumeRecord[] _result10 = getVolumeRecords(data.readInt());
                                                                    reply.writeNoException();
                                                                    parcel2.writeTypedArray(_result10, 1);
                                                                    return true;
                                                                case 48:
                                                                    parcel.enforceInterface(descriptor);
                                                                    mount(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 49:
                                                                    parcel.enforceInterface(descriptor);
                                                                    unmount(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 50:
                                                                    parcel.enforceInterface(descriptor);
                                                                    format(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 51:
                                                                    parcel.enforceInterface(descriptor);
                                                                    partitionPublic(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 52:
                                                                    parcel.enforceInterface(descriptor);
                                                                    partitionPrivate(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 53:
                                                                    parcel.enforceInterface(descriptor);
                                                                    partitionMixed(data.readString(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 54:
                                                                    parcel.enforceInterface(descriptor);
                                                                    setVolumeNickname(data.readString(), data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 55:
                                                                    parcel.enforceInterface(descriptor);
                                                                    setVolumeUserFlags(data.readString(), data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 56:
                                                                    parcel.enforceInterface(descriptor);
                                                                    forgetVolume(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 57:
                                                                    parcel.enforceInterface(descriptor);
                                                                    forgetAllVolumes();
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 58:
                                                                    parcel.enforceInterface(descriptor);
                                                                    _result6 = getPrimaryStorageUuid();
                                                                    reply.writeNoException();
                                                                    parcel2.writeString(_result6);
                                                                    return true;
                                                                case 59:
                                                                    parcel.enforceInterface(descriptor);
                                                                    setPrimaryStorageUuid(data.readString(), android.content.pm.IPackageMoveObserver.Stub.asInterface(data.readStrongBinder()));
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 60:
                                                                    parcel.enforceInterface(descriptor);
                                                                    benchmark(data.readString(), android.os.IVoldTaskListener.Stub.asInterface(data.readStrongBinder()));
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 61:
                                                                    parcel.enforceInterface(descriptor);
                                                                    setDebugFlags(data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 62:
                                                                    parcel.enforceInterface(descriptor);
                                                                    _result2 = data.readInt();
                                                                    _result3 = data.readInt();
                                                                    if (data.readInt() != 0) {
                                                                        _arg2 = true;
                                                                    }
                                                                    createUserKey(_result2, _result3, _arg2);
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 63:
                                                                    parcel.enforceInterface(descriptor);
                                                                    destroyUserKey(data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 64:
                                                                    parcel.enforceInterface(descriptor);
                                                                    unlockUserKey(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 65:
                                                                    parcel.enforceInterface(descriptor);
                                                                    lockUserKey(data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 66:
                                                                    parcel.enforceInterface(descriptor);
                                                                    _result = isUserKeyUnlocked(data.readInt());
                                                                    reply.writeNoException();
                                                                    parcel2.writeInt(_result);
                                                                    return true;
                                                                case 67:
                                                                    parcel.enforceInterface(descriptor);
                                                                    prepareUserStorage(data.readString(), data.readInt(), data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 68:
                                                                    parcel.enforceInterface(descriptor);
                                                                    destroyUserStorage(data.readString(), data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 69:
                                                                    parcel.enforceInterface(descriptor);
                                                                    _arg2 = isConvertibleToFBE();
                                                                    reply.writeNoException();
                                                                    parcel2.writeInt(_arg2);
                                                                    return true;
                                                                default:
                                                                    long _result11;
                                                                    switch (code) {
                                                                        case 71:
                                                                            parcel.enforceInterface(descriptor);
                                                                            addUserKeyAuth(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray());
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 72:
                                                                            parcel.enforceInterface(descriptor);
                                                                            fixateNewestUserKeyAuth(data.readInt());
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 73:
                                                                            parcel.enforceInterface(descriptor);
                                                                            fstrim(data.readInt(), android.os.IVoldTaskListener.Stub.asInterface(data.readStrongBinder()));
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 74:
                                                                            parcel.enforceInterface(descriptor);
                                                                            AppFuseMount _result12 = mountProxyFileDescriptorBridge();
                                                                            reply.writeNoException();
                                                                            if (_result12 != null) {
                                                                                parcel2.writeInt(1);
                                                                                _result12.writeToParcel(parcel2, 1);
                                                                            } else {
                                                                                parcel2.writeInt(0);
                                                                            }
                                                                            return true;
                                                                        case 75:
                                                                            parcel.enforceInterface(descriptor);
                                                                            ParcelFileDescriptor _result13 = openProxyFileDescriptor(data.readInt(), data.readInt(), data.readInt());
                                                                            reply.writeNoException();
                                                                            if (_result13 != null) {
                                                                                parcel2.writeInt(1);
                                                                                _result13.writeToParcel(parcel2, 1);
                                                                            } else {
                                                                                parcel2.writeInt(0);
                                                                            }
                                                                            return true;
                                                                        case 76:
                                                                            parcel.enforceInterface(descriptor);
                                                                            _result11 = getCacheQuotaBytes(data.readString(), data.readInt());
                                                                            reply.writeNoException();
                                                                            parcel2.writeLong(_result11);
                                                                            return true;
                                                                        case 77:
                                                                            parcel.enforceInterface(descriptor);
                                                                            _result11 = getCacheSizeBytes(data.readString(), data.readInt());
                                                                            reply.writeNoException();
                                                                            parcel2.writeLong(_result11);
                                                                            return true;
                                                                        case 78:
                                                                            parcel.enforceInterface(descriptor);
                                                                            long _result14 = getAllocatableBytes(data.readString(), data.readInt(), data.readString());
                                                                            reply.writeNoException();
                                                                            parcel2.writeLong(_result14);
                                                                            return true;
                                                                        case 79:
                                                                            parcel.enforceInterface(descriptor);
                                                                            allocateBytes(data.readString(), data.readLong(), data.readInt(), data.readString());
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 80:
                                                                            parcel.enforceInterface(descriptor);
                                                                            runIdleMaintenance();
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 81:
                                                                            parcel.enforceInterface(descriptor);
                                                                            abortIdleMaintenance();
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        default:
                                                                            switch (code) {
                                                                                case 501:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    unlockUserKeyISec(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray());
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 502:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    lockUserKeyISec(data.readInt());
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 503:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    unlockUserScreenISec(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray(), data.readInt());
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 504:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    lockUserScreenISec(data.readInt(), data.readInt());
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 505:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    _result3 = getPreLoadPolicyFlag(data.readInt(), data.readInt());
                                                                                    reply.writeNoException();
                                                                                    parcel2.writeInt(_result3);
                                                                                    return true;
                                                                                case 506:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    boolean _result15 = setScreenStateFlag(data.readInt(), data.readInt(), data.readInt());
                                                                                    reply.writeNoException();
                                                                                    parcel2.writeInt(_result15);
                                                                                    return true;
                                                                                case TRANSACTION_getKeyDesc /*507*/:
                                                                                    parcel.enforceInterface(descriptor);
                                                                                    String _result16 = getKeyDesc(data.readInt(), data.readInt(), data.readInt());
                                                                                    reply.writeNoException();
                                                                                    parcel2.writeString(_result16);
                                                                                    return true;
                                                                                default:
                                                                                    switch (code) {
                                                                                        case 601:
                                                                                            parcel.enforceInterface(descriptor);
                                                                                            _result2 = data.readInt();
                                                                                            _result3 = data.readInt();
                                                                                            if (data.readInt() != 0) {
                                                                                                _arg2 = true;
                                                                                            }
                                                                                            createUserKeyISec(_result2, _result3, _arg2);
                                                                                            reply.writeNoException();
                                                                                            return true;
                                                                                        case 602:
                                                                                            parcel.enforceInterface(descriptor);
                                                                                            destroyUserKeyISec(data.readInt());
                                                                                            reply.writeNoException();
                                                                                            return true;
                                                                                        default:
                                                                                            switch (code) {
                                                                                                case 1001:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _arg2 = isSecure();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_arg2);
                                                                                                    return true;
                                                                                                case 1002:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = startClean();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                case 1003:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = stopClean();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                case 1004:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = getNotificationLevel();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                case 1005:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = getUndiscardInfo();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                case 1006:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = getMaxTimeCost();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                case 1007:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = getMinTimeCost();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                case 1008:
                                                                                                    parcel.enforceInterface(descriptor);
                                                                                                    _result5 = getPercentComplete();
                                                                                                    reply.writeNoException();
                                                                                                    parcel2.writeInt(_result5);
                                                                                                    return true;
                                                                                                default:
                                                                                                    switch (code) {
                                                                                                        case 20:
                                                                                                            parcel.enforceInterface(descriptor);
                                                                                                            shutdown(android.os.storage.IStorageShutdownObserver.Stub.asInterface(data.readStrongBinder()));
                                                                                                            reply.writeNoException();
                                                                                                            return true;
                                                                                                        case IBinder.INTERFACE_TRANSACTION /*1598968902*/:
                                                                                                            parcel2.writeString(descriptor);
                                                                                                            return true;
                                                                                                        default:
                                                                                                            return super.onTransact(code, data, reply, flags);
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                            }
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }
    }

    void abortIdleMaintenance() throws RemoteException;

    void addUserKeyAuth(int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void allocateBytes(String str, long j, int i, String str2) throws RemoteException;

    void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    int changeEncryptionPassword(int i, String str) throws RemoteException;

    void clearPassword() throws RemoteException;

    void createUserKey(int i, int i2, boolean z) throws RemoteException;

    void createUserKeyISec(int i, int i2, boolean z) throws RemoteException;

    int decryptStorage(String str) throws RemoteException;

    void destroyUserKey(int i) throws RemoteException;

    void destroyUserKeyISec(int i) throws RemoteException;

    void destroyUserStorage(String str, int i, int i2) throws RemoteException;

    int encryptStorage(int i, String str) throws RemoteException;

    void fixateNewestUserKeyAuth(int i) throws RemoteException;

    void forgetAllVolumes() throws RemoteException;

    void forgetVolume(String str) throws RemoteException;

    void format(String str) throws RemoteException;

    void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    long getAllocatableBytes(String str, int i, String str2) throws RemoteException;

    long getCacheQuotaBytes(String str, int i) throws RemoteException;

    long getCacheSizeBytes(String str, int i) throws RemoteException;

    DiskInfo[] getDisks() throws RemoteException;

    int getEncryptionState() throws RemoteException;

    String getField(String str) throws RemoteException;

    String getKeyDesc(int i, int i2, int i3) throws RemoteException;

    int getMaxTimeCost() throws RemoteException;

    int getMinTimeCost() throws RemoteException;

    String getMountedObbPath(String str) throws RemoteException;

    int getNotificationLevel() throws RemoteException;

    String getPassword() throws RemoteException;

    int getPasswordType() throws RemoteException;

    int getPercentComplete() throws RemoteException;

    int getPreLoadPolicyFlag(int i, int i2) throws RemoteException;

    String getPrimaryStorageUuid() throws RemoteException;

    int getUndiscardInfo() throws RemoteException;

    StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException;

    VolumeRecord[] getVolumeRecords(int i) throws RemoteException;

    VolumeInfo[] getVolumes(int i) throws RemoteException;

    boolean isConvertibleToFBE() throws RemoteException;

    boolean isObbMounted(String str) throws RemoteException;

    boolean isSecure() throws RemoteException;

    boolean isUserKeyUnlocked(int i) throws RemoteException;

    long lastMaintenance() throws RemoteException;

    void lockUserKey(int i) throws RemoteException;

    void lockUserKeyISec(int i) throws RemoteException;

    void lockUserScreenISec(int i, int i2) throws RemoteException;

    void mkdirs(String str, String str2) throws RemoteException;

    void mount(String str) throws RemoteException;

    void mountObb(String str, String str2, String str3, IObbActionListener iObbActionListener, int i) throws RemoteException;

    AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException;

    ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws RemoteException;

    void partitionMixed(String str, int i) throws RemoteException;

    void partitionPrivate(String str) throws RemoteException;

    void partitionPublic(String str) throws RemoteException;

    void prepareUserStorage(String str, int i, int i2, int i3) throws RemoteException;

    void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    void runIdleMaintenance() throws RemoteException;

    void runMaintenance() throws RemoteException;

    void setDebugFlags(int i, int i2) throws RemoteException;

    void setField(String str, String str2) throws RemoteException;

    void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    boolean setScreenStateFlag(int i, int i2, int i3) throws RemoteException;

    void setVolumeNickname(String str, String str2) throws RemoteException;

    void setVolumeUserFlags(String str, int i, int i2) throws RemoteException;

    void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException;

    int startClean() throws RemoteException;

    int stopClean() throws RemoteException;

    void unlockUserKey(int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void unlockUserKeyISec(int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void unlockUserScreenISec(int i, int i2, byte[] bArr, byte[] bArr2, int i3) throws RemoteException;

    void unmount(String str) throws RemoteException;

    void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) throws RemoteException;

    void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    int verifyEncryptionPassword(String str) throws RemoteException;
}
